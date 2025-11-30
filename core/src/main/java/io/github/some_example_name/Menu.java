package io.github.some_example_name;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

import io.github.controllers.CameraController;
import io.github.managers.EffectsManager;
import io.github.managers.UiManager;

public class Menu implements Screen {
    
    private SpriteBatch batch;
    private final float screenScale = 48f;
    private final float VIRTUAL_WIDTH = 960/ screenScale;
    private final float VIRTUAL_HEIGHT = 540/ screenScale;
    
    @Override
    public void show(){

        batch = new SpriteBatch();

        CameraController.Initialize(VIRTUAL_WIDTH, VIRTUAL_HEIGHT);
        UiManager.I().Init();
        EffectsManager.i().init();
    }

    @Override
    public void render(float delta){
        draw();
    }

    @Override
    public void resize(int width, int height){
        UiManager.I().getStage().getViewport().update(width, height, true);
        EffectsManager.i().resize(width, height);
    }

    public void draw(){

        ScreenUtils.clear(Color.valueOf("0f0d0f"));
        batch.setProjectionMatrix(CameraController.getCamera().combined);

        EffectsManager.i().getVfxManager().cleanUpBuffers();

        EffectsManager.i().getVfxManager().beginInputCapture();
        
        UiManager.I().Draw();


        EffectsManager.i().getVfxManager().endInputCapture();
        
        EffectsManager.i().getVfxManager().applyEffects();

        EffectsManager.i().getVfxManager().renderToScreen();
    }

    @Override
    public void pause(){

    }

    @Override
    public void resume(){

    }

    @Override
    public void hide(){

    }

    @Override
    public void dispose(){
        EffectsManager.i().dispose();
    }


}
