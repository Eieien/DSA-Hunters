package io.github.managers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.MusicLoader;
import com.badlogic.gdx.assets.loaders.SoundLoader;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.ObjectMap;

public class AudioManager implements Disposable {
    private static AudioManager instance;

    private final AssetManager assets;
    private final ObjectMap<String, Sound> sounds = new ObjectMap<>();
    private final ObjectMap<String, Music> musics = new ObjectMap<>();

    private float masterVolume = 1f;      // master multiplier 0..1
    private float sfxVolume = 1f;         // effects 0..1
    private float musicVolume = 1f;       // music 0..1

    private String currentMusicKey = null;
    private long fadeTaskId = -1L; // simple id if you implement scheduling

    private AudioManager() {
        assets = new AssetManager();
        // register loaders (usually automatic, but explicit is OK)
        assets.setLoader(Sound.class, new SoundLoader(new InternalFileHandleResolver()));
        assets.setLoader(Music.class, new MusicLoader(new InternalFileHandleResolver()));
    }

    public static AudioManager i() {
        if (instance == null) instance = new AudioManager();
        return instance;
    }

    public void LoadAudio(){
        AudioManager.i().loadImmediateMusic("menu_bgm", "music/Saving Light.mp3");
        AudioManager.i().loadImmediateMusic("game_bgm", "music/Edge of Truth.mp3");
        AudioManager.i().loadImmediateSound("hit", "sound/hit.wav");
        AudioManager.i().loadImmediateSound("Enemyhit", "sound/enemy Hurt.wav");
        AudioManager.i().loadImmediateSound("enemyshoot", "sound/enemyShoot.wav");
        AudioManager.i().loadImmediateSound("shoot", "sound/shoot.wav");
    }

    // ============== LOADING ==============
    /** Queue a sound to be loaded by AssetManager */
    public void queueSound(String key, String internalPath) {
        assets.load(internalPath, Sound.class);
        sounds.put(key, null); // placeholder to remember key
        // you can map key -> path in a separate map if needed
    }

    public void queueMusic(String key, String internalPath) {
        assets.load(internalPath, Music.class);
        musics.put(key, null);
    }

    /** Call after queueing to finish loading (blocking) */
    public void finishLoading() {
        assets.finishLoading();
        // assign loaded assets to maps
        for (String key : sounds.keys()) {
            if (sounds.get(key) == null) {
                String path = findPathForKey(key); // implement mapping or pass path directly
                if (path != null && assets.isLoaded(path, Sound.class)) {
                    sounds.put(key, assets.get(path, Sound.class));
                }
            }
        }
        for (String key : musics.keys()) {
            if (musics.get(key) == null) {
                String path = findPathForKey(key);
                if (path != null && assets.isLoaded(path, Music.class)) {
                    Music m = assets.get(path, Music.class);
                    m.setVolume(musicVolume * masterVolume);
                    musics.put(key, m);
                }
            }
        }
    }

    // If you prefer simpler API: loadImmediate(soundKey, "sounds/boom.wav")
    public void loadImmediateSound(String key, String internalPath) {
        Sound s = Gdx.audio.newSound(Gdx.files.internal(internalPath));
        sounds.put(key, s);
    }
    public void loadImmediateMusic(String key, String internalPath) {
        Music m = Gdx.audio.newMusic(Gdx.files.internal(internalPath));
        m.setLooping(false);
        m.setVolume(musicVolume * masterVolume);
        musics.put(key, m);
    }

    // ============== SFX ==============
    /** Play a sound. Returns soundId (for stop/volume control if needed). */
    public long playSfx(String key) {
        Sound s = sounds.get(key);
        if (s == null) return -1L;
        return s.play(sfxVolume * masterVolume);
    }

    public long playSfx(String key, float pitch, float pan, float volume) {
        Sound s = sounds.get(key);
        if (s == null) return -1L;
        float finalVol = volume * sfxVolume * masterVolume;
        return s.play(finalVol, pitch, pan);
    }

    public void stopSfx(String key) {
        Sound s = sounds.get(key);
        if (s != null) s.stop();
    }

    // ============== MUSIC ==============
    /** Play music; stops previous music unless keepPlaying==true */
    public void playMusic(String key, boolean looping) {
        Music next = musics.get(key);
        if (next == null) {
            Gdx.app.log("AudioManager", "Music not found: " + key);
            return;
        }
        if (currentMusicKey != null && !currentMusicKey.equals(key)) {
            Music cur = musics.get(currentMusicKey);
            if (cur != null) cur.stop();
        }
        currentMusicKey = key;
        next.setLooping(looping);
        next.setVolume(musicVolume * masterVolume);
        next.play();
    }

    public void stopMusic() {
        if (currentMusicKey == null) return;
        Music m = musics.get(currentMusicKey);
        if (m != null) m.stop();
        currentMusicKey = null;
    }

    public void pauseMusic() {
        if (currentMusicKey == null) return;
        Music m = musics.get(currentMusicKey);
        if (m != null) m.pause();
    }

    public void resumeMusic() {
        if (currentMusicKey == null) return;
        Music m = musics.get(currentMusicKey);
        if (m != null) m.play();
    }

    // ============== VOLUME ==============
    public void setMasterVolume(float v) {
        masterVolume = clamp01(v);
        updateMusicVolumes();
    }

    public void setSfxVolume(float v) {
        sfxVolume = clamp01(v);
    }

    public void setMusicVolume(float v) {
        musicVolume = clamp01(v);
        updateMusicVolumes();
    }

    private void updateMusicVolumes() {
        for (Music m : musics.values()) {
            if (m != null) m.setVolume(musicVolume * masterVolume);
        }
    }

    private float clamp01(float v) {
        if (v < 0f) return 0f;
        if (v > 1f) return 1f;
        return v;
    }

    // ============== FADE (simple polling approach) ==============
    /** Simple fade out current music over duration seconds (needs to be called from render loop). */
    private boolean isFading = false;
    private float fadeTarget = 0f;
    private float fadeDuration = 0f;
    private float fadeElapsed = 0f;
    private String fadeMusicKey = null;
    private float fadeStartVol = 1f;

    public void fadeOutCurrentMusic(float seconds) {
        if (currentMusicKey == null) return;
        Music m = musics.get(currentMusicKey);
        if (m == null) return;
        isFading = true;
        fadeMusicKey = currentMusicKey;
        fadeDuration = Math.max(0.01f, seconds);
        fadeElapsed = 0f;
        fadeStartVol = m.getVolume();
        fadeTarget = 0f;
    }

    /** Call this in render(delta) to process fade */
    public void update(float delta) {
        if (isFading && fadeMusicKey != null) {
            Music m = musics.get(fadeMusicKey);
            if (m == null) {
                isFading = false;
                fadeMusicKey = null;
                return;
            }
            fadeElapsed += delta;
            float t = Math.min(1f, fadeElapsed / fadeDuration);
            float vol = fadeStartVol + (fadeTarget - fadeStartVol) * t;
            m.setVolume(vol * masterVolume);
            if (t >= 1f) {
                // finished
                isFading = false;
                if (fadeTarget == 0f) m.stop();
                fadeMusicKey = null;
            }
        }
    }

    // ============== CLEANUP ==============
    @Override
    public void dispose() {
        for (Sound s : sounds.values()) {
            if (s != null) s.dispose();
        }
        for (Music m : musics.values()) {
            if (m != null) m.dispose();
        }
        sounds.clear();
        musics.clear();
        if (assets != null) assets.dispose();
        instance = null;
    }

    // ============== Helpers (implement your own mapping) ==============
    private String findPathForKey(String key) {
        // Minimal example: key == path. In real app store a Map<String,String> key->path.
        return key;
    }
}
