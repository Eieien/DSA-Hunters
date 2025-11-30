package io.github.managers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;


public class UiManager {
    
    private Stage stage;
    private Skin skin;
    private Table table;
    private float timer;
    private boolean showFps;
    private static UiManager instance;

    // Labels
    private Label fpsLabel;
    private Label timerLabel;

    private Window pauseWindow;
    private Image overlay;

    public void Init(){
        stage = new Stage(new ScreenViewport());
        skin = new Skin(Gdx.files.internal("uiskin.json"));

        Gdx.input.setInputProcessor(stage);
        
        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);
        
        fpsLabel = new Label("fps: " + Gdx.graphics.getFramesPerSecond(), skin);
        table.add(fpsLabel).expand().top().right();
        fpsLabel.setVisible(false);
        
        overlay = new Image(new TextureRegionDrawable(new TextureRegion(
        makeTexture(1, 1, new Color(0, 0, 0, 0.6f))
        )));

        overlay.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        overlay.setVisible(false);
        stage.addActor(overlay);

        pauseWindow = new Window("Paused", skin);
        pauseWindow.setSize(300, 200);
        pauseWindow.setPosition(
                (Gdx.graphics.getWidth() - pauseWindow.getWidth()) / 2f,
                (Gdx.graphics.getHeight() - pauseWindow.getHeight()) / 2f
        );
        pauseWindow.setVisible(false);
        stage.addActor(pauseWindow);

        TextButton resumeBtn = new TextButton("Resume", skin);
        pauseWindow.add(resumeBtn).pad(20);

        resumeBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                togglePause();
                System.out.println("Resumed");
            }
        });


   
        // timerLabel = new Label(String.valueOf(timer), skin);
        // table.add(timerLabel).expand().top().center();
        // table.setDebug(true);
    }

    public void togglePause() {
        boolean paused = GameStateManager.i().isPaused();
        paused = !paused;
        overlay.setVisible(paused);
        pauseWindow.setVisible(paused);
    
        if (paused) {
            Gdx.input.setInputProcessor(stage);   // route input to UI
        } else {
            Gdx.input.setInputProcessor(null);       // or your game input processor
        }
    }
    

    private Texture makeTexture(int w, int h, Color color) {
        Pixmap pm = new Pixmap(w, h, Pixmap.Format.RGBA8888);
        pm.setColor(color);
        pm.fill();
        Texture tex = new Texture(pm);
        pm.dispose();
        return tex;
    }


    public static UiManager I(){
        if(instance == null) instance = new UiManager();
        return instance;
    }

    public void toggleGameOverScreen(){
        
    }


    public void toggleFps(){
        fpsLabel.setVisible(true);
        fpsLabel.setText("fps: " + Gdx.graphics.getFramesPerSecond());
    }
    

    public void updateTimer(){
        timer += Gdx.graphics.getDeltaTime();
        timerLabel.setText(String.valueOf(timer));
    }

    public void showGameOver(){

    }

    public void showPauseScreen(){
        
    }

    public Stage getStage(){
        return stage;
    }

    public void Draw(){
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
    }

    public void Dispose(){
        stage.dispose();
        skin.dispose();
    }

}
