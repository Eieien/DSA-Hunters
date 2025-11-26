package io.github.controllers;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
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
        Vector3 worldCoordinates = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
        Vector2 mousePos = new Vector2(worldCoordinates.x, worldCoordinates.y);
        float delta = Gdx.graphics.getDeltaTime();
        float cameraRadius = 5f;
  
        camera.position.x += (target.x - camera.position.x) * cameraRadius * delta;
        camera.position.y += (target.y - camera.position.y) * cameraRadius * delta;
        camera.update();
    }
    
    public static void Shake(float intensity, float duration){
        float delta = Gdx.graphics.getDeltaTime();
        float shakeTime = duration;
        Vector3 originalPosition = camera.position;
        if(duration > 0){
            duration -= delta;

            float currentIntesity = intensity * (shakeTime / duration);

            float offsetX = (MathUtils.random() - 0.5f) * 2 * currentIntesity;
            float offsetY = (MathUtils.random() - 0.5f) * 2 * currentIntesity;

            camera.position.x = originalPosition.x + offsetX;
            camera.position.y = originalPosition.y + offsetY;

            camera.update();
        }else{
            camera.position.set(originalPosition);
        }

        
    }

    public static OrthographicCamera getCamera(){
        return camera;
    }

    public static FitViewport getViewport(){
        return viewport;
    }
}
