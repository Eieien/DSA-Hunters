package io.github.managers;

import com.badlogic.gdx.Gdx;

import io.github.entities.Player;

public class GameStateManager {
    
    private static boolean gameOver;
    private static GameStateManager instance;
    private static boolean paused;
    private static Player player;
    private static float timeScale;


    

    public void init(Player player){
        this.player = player;
        paused = false;
        timeScale = 1.0f;
        gameOver = false;
    }

    public static GameStateManager i(){
        if(instance == null) instance = new GameStateManager();
        return instance;
    }

    // Deltas
    public float scaleDelta(float delta){
        return (paused) ? delta * 0 : delta * timeScale;
    }

    public void setTimeScale(float scale){
        this.timeScale = scale;
    }

    public float slowMotion(float delta){
        if(timeScale > 0){
            timeScale -= delta * 0.5f;
        }
        return timeScale;
    }

    public float getTimeScale(){
        return timeScale;
    }

    // Booleans
    public void setGameOver(){
        gameOver = true;
    }

    public boolean isGameOver(){
        return this.gameOver;
    }

    public boolean isPaused() {
        return paused;
    }

    public void setPaused(boolean paused) {
        this.paused = paused;
    }

    

}
