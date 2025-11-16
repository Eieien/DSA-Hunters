package io.github.some_example_name;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.utils.ScreenUtils;

import io.github.controllers.CameraController;
import io.github.controllers.CharacterController;
import io.github.entities.Player;
import io.github.entities.Wall;
import io.github.managers.EnemyManager;
import io.github.managers.WorldManager;
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
    private EnemyManager enemyManager;

    @Override
    public void show() {
        // Prepare your screen here.
        WorldManager.Initialize();
        CameraController.Initialize(VIRTUAL_WIDTH, VIRTUAL_HEIGHT);
        
        batch = new SpriteBatch();
        debugRenderer = new Box2DDebugRenderer();
        player = new Player(0, 0, WorldManager.getWorld());
        wall = new Wall(new Vector2(10f, 10f));
        playerController = new CharacterController(player);
        enemyManager = new EnemyManager(player);
   
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
        CameraController.getViewport().update(width, height, true);
        // Resize your screen here. The parameters represent the new window size.
    }

    private void input(){
        playerController.handleInput();
        
    }

    private void logic(){
        float delta = Gdx.graphics.getDeltaTime();
        WorldManager.Update(delta);
        CameraController.Follow(player.getPosition());

        if(player.gun.getOnCooldown()){
            // System.out.println("Get On Cooldown");
            player.gun.GunDelay(delta);
        }

        enemyManager.Spawn(delta);

    }

    private void draw(){
        ScreenUtils.clear(Color.BLUE);
        // Color.valueOf("0f0d0f")
        CameraController.getViewport().apply();

        batch.setProjectionMatrix(CameraController.getCamera().combined);
        batch.begin();
        playerController.RotateSprite(CameraController.getCamera());
        playerController.Draw(batch);
        wall.Draw(batch);
        player.gun.renderBullets(batch);
        enemyManager.render(batch);


        batch.end();
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
        WorldManager.dispose();
        debugRenderer.dispose();
    }
}