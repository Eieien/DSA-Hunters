package io.github.some_example_name;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.ScreenUtils;

import io.github.components.Gun;
import io.github.controllers.CameraController;
import io.github.controllers.CharacterController;
import io.github.entities.Player;
import io.github.entities.Wall;
import io.github.managers.AudioManager;
import io.github.managers.EffectsManager;
import io.github.managers.EnemyManager;
import io.github.managers.GameStateManager;
import io.github.managers.UiManager;
import io.github.managers.WorldManager;
import io.github.pools.BulletPool;
/** First screen of the application. Displayed after the application is created. */
public class FirstScreen implements Screen {

    // Screen;
    private final float screenScale = 48f;
    private final float VIRTUAL_WIDTH = 960/ screenScale;
    private final float VIRTUAL_HEIGHT = 540/ screenScale;

    private Player player;
    private Wall wall;
    private CharacterController playerController;
    private Box2DDebugRenderer debugRenderer;
    private SpriteBatch batch;
    private Texture checker;
    private EnemyManager enemyManager;
    private AudioManager audioManager;
    private Stage stage;
    private Skin skin;


    @Override
    public void show() {
        // Prepare your screen here.
        WorldManager.Initialize();
        CameraController.Initialize(VIRTUAL_WIDTH, VIRTUAL_HEIGHT);
        BulletPool.Initialize();
        batch = new SpriteBatch();
        debugRenderer = new Box2DDebugRenderer();
        checker = new Texture("sprites/checkboard.png");
        player = new Player(0, 0, WorldManager.getWorld());

        //Managers
        UiManager.I().Init();
        GameStateManager.i().init(player);
        AudioManager.i().LoadAudio();
        EffectsManager.i().init();

        checker.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat);

        // wall = new Wall(new Vector2(10f, 10f));
        playerController = new CharacterController(player);
        enemyManager = new EnemyManager(player);
   
        AudioManager.i().setMasterVolume(0.3f);
        AudioManager.i().playMusic("game_bgm", true);
    }


    @Override
    public void render(float delta) {
        // Draw your screen here. "delta" is the time since last render in seconds.
        input();
        logic();
        draw();

        debugRenderer.render(WorldManager.getWorld(), CameraController.getCamera().combined);
    }

    @Override
    public void resize(int width, int height) {
        // If the window is minimized on a desktop (LWJGL3) platform, width and height are 0, which causes problems.
        // In that case, we don't resize anything, and wait for the window to be a normal size before updating.
        // if(width <= 0 || height <= 0) return;

        EffectsManager.i().resize(width, height);
        UiManager.I().getStage().getViewport().update(width, height, true);

        CameraController.getViewport().update(width, height, true);
        // Resize your screen here. The parameters represent the new window size.
    }

    private void input(){
        playerController.handleInput();

        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            UiManager.I().togglePause();
        }

        
    }

    private void logic(){
        if(GameStateManager.i().isGameOver()){
            GameStateManager.i().setTimeScale(GameStateManager.i().slowMotion(Gdx.graphics.getDeltaTime()));
            // player.sprite.getColor().a -= GameStateManager.i().slowMotion(Gdx.graphics.getDeltaTime());
            // player.fadeOut(Gdx.graphics.getDeltaTime());
        }
        
        float delta = GameStateManager.i().scaleDelta(Gdx.graphics.getDeltaTime());
        

        WorldManager.Update(delta);
        WorldManager.destroyBodies();
        CameraController.Follow(player.getPosition());

        for(Gun gun : player.getGuns()){
            if(gun.getOnCooldown()){
                // System.out.println("Get On Cooldown");
                gun.GunDelay(delta);
            }
    
            gun.Update(delta);        

        }
        enemyManager.Spawn(delta);

    }

    private void draw(){
        ScreenUtils.clear(Color.valueOf("0f0d0f"));

        // Color.valueOf("0f0d0f")
        CameraController.getViewport().apply();

        
        EffectsManager.i().getVfxManager().cleanUpBuffers();

        EffectsManager.i().getVfxManager().beginInputCapture();

        batch.setProjectionMatrix(CameraController.getCamera().combined);
        batch.begin();
        batch.draw(
            checker, -120, -120, Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), 0, 0, 20f, 20f
        );
        
        playerController.Draw(batch, GameStateManager.i().scaleDelta(Gdx.graphics.getDeltaTime()));
        playerController.RotateSprite(CameraController.getCamera());
        BulletPool.renderBullets(batch);
        enemyManager.render(batch);
        
        
        batch.end();
        UiManager.I().Draw();
        // UiManager.I().toggleFps();
        // UiManager.I().updateTimer();
        
        EffectsManager.i().getVfxManager().endInputCapture();

        EffectsManager.i().getVfxManager().applyEffects();

        EffectsManager.i().getVfxManager().renderToScreen();
    }

    @Override
    public void pause() {
        // Invoked when your application is paused.
    }

    @Override
    public void resume() {
        // Invoked when your application is resumed after pause.
    }

    @Override
    public void hide() {
        // This method is called when another screen replaces this one.
    }

    @Override
    public void dispose() {
        // Destroy screen's assets here.
        EffectsManager.i().dispose();
        UiManager.I().Dispose();
        AudioManager.i().dispose();
        WorldManager.dispose();
        debugRenderer.dispose();
    }
}