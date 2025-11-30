package io.github.managers;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.utils.Array;
import com.crashinvaders.vfx.VfxManager;
import com.crashinvaders.vfx.effects.BloomEffect;
import com.crashinvaders.vfx.effects.ChainVfxEffect;
import com.crashinvaders.vfx.effects.ChromaticAberrationEffect;
import com.crashinvaders.vfx.effects.CrtEffect;
import com.crashinvaders.vfx.effects.FilmGrainEffect;
import com.crashinvaders.vfx.effects.OldTvEffect;
import com.crashinvaders.vfx.effects.RadialDistortionEffect;
import com.crashinvaders.vfx.effects.VignettingEffect;

public class EffectsManager {
    private Array<ChainVfxEffect> effects = new Array<>();
    private VfxManager vfxManager;
    private BloomEffect bloom;
    private OldTvEffect oldTVEffct;
    private CrtEffect crtEffect;
    private VignettingEffect vignette;
    private RadialDistortionEffect curvature;
    private FilmGrainEffect filmGrain;
    private ChromaticAberrationEffect chromAbb;
    private static EffectsManager instance;

    public static EffectsManager i(){
        if(instance == null) instance = new EffectsManager();
        return instance;
    }

    public void init(){
        vfxManager = new VfxManager(Pixmap.Format.RGBA8888);

        effects = Array.with(
            bloom =new BloomEffect(), 
            oldTVEffct = new OldTvEffect(), 
            // crtEffect = new CrtEffect(), 
            chromAbb = new ChromaticAberrationEffect(10),
            filmGrain = new FilmGrainEffect(),
            vignette = new VignettingEffect(false),
            curvature = new RadialDistortionEffect()
        );

        for(ChainVfxEffect e : effects){
            vfxManager.addEffect(e);
        }

        chromAbb.setMaxDistortion(0.2f);
        vignette.setIntensity(0.2f);
    }

    public void resize(int width, int height){
        vfxManager.resize(width, height);
    }

    public VfxManager getVfxManager(){
        return this.vfxManager;
    }

    public void dispose(){
        vfxManager.dispose();

        for(ChainVfxEffect e: effects){
            e.dispose();
        }
    }
    
}
