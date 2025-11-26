package io.github.managers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.ScreenViewport;


public class UiManager {
    
    private static Stage stage;
    private static Skin skin;
    private static Table table;
    private static float timer;

    // Labels
    private static Label fpsLabel;
    private static Label timerLabel;

    public static void Init(){
        stage = new Stage(new ScreenViewport());
        skin = new Skin(Gdx.files.internal("uiskin.json"));

        Gdx.input.setInputProcessor(stage);
        
        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);
        
        fpsLabel = new Label("fps: " + Gdx.graphics.getFramesPerSecond(), skin);
        table.add(fpsLabel).expand().top().right();
   
        // timerLabel = new Label(String.valueOf(timer), skin);
        // table.add(timerLabel).expand().top().center();
        // table.setDebug(true);
    }

    public static void showFps(){
        fpsLabel.setText("fps: " + Gdx.graphics.getFramesPerSecond());
    }

    public static void updateTimer(){
        timer += Gdx.graphics.getDeltaTime();
        timerLabel.setText(String.valueOf(timer));
    }

    public static Stage getStage(){
        return stage;
    }

    public static void Draw(){
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
    }

    public static void Dispose(){
        stage.dispose();
        skin.dispose();
    }

}
