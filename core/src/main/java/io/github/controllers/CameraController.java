package io.github.controllers;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class CameraController {
    public static OrthographicCamera camera; 
    public static FitViewport viewport;

    // float screenWidth = Gdx.graphics.getWidth();
    // float screenHeight = Gdx.graphics.getHeight();

    public CameraController(float width, float height){
       
    }

    public static void Initialize(float width, float height){
        camera = new OrthographicCamera();
        viewport = new FitViewport(width, height, camera);
        // camera.position.set(target.x, target.y, 0);
        camera.position.set((int)camera.position.x, (int)camera.position.y, 0);
        camera.update();
    }

    public static void Follow(Vector2 target){
        float delta = Gdx.graphics.getDeltaTime();
        float cameraRadius = 5f;
        camera.position.x += (target.x - camera.position.x) * cameraRadius * delta;
        camera.position.y += (target.y - camera.position.y) * cameraRadius * delta;
        camera.update();
    }

    public void Shake(float duration, float intensity){
        
    }

    public static OrthographicCamera getCamera(){
        return camera;
    }

    public static FitViewport getViewport(){
        return viewport;
    }
}
