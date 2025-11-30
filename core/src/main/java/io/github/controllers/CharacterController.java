package io.github.controllers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

import io.github.components.Gun;
import io.github.entities.Player;
import io.github.managers.AudioManager;
import io.github.managers.GameStateManager;

public class CharacterController {
    
    private final Player player;

    public CharacterController(Player player){
        this.player = player;
    }

    public void handleInput(){
        player.acceleration.set(0, 0);
        float ACCEL = player.ACCEL;
        if (Gdx.input.isKeyPressed(Keys.RIGHT) || Gdx.input.isKeyPressed(Keys.D)){player.acceleration.x = ACCEL;}
        if (Gdx.input.isKeyPressed(Keys.LEFT) || Gdx.input.isKeyPressed(Keys.A))  player.acceleration.x = -ACCEL;
        if (Gdx.input.isKeyPressed(Keys.UP) || Gdx.input.isKeyPressed(Keys.W))    player.acceleration.y = ACCEL;
        if (Gdx.input.isKeyPressed(Keys.DOWN) || Gdx.input.isKeyPressed(Keys.S))  player.acceleration.y = -ACCEL;
        
        boolean leftMouseClick = Gdx.input.isButtonPressed(Input.Buttons.LEFT);
        if(leftMouseClick){
            Shoot();
        }

    }

    public void RotateSprite(OrthographicCamera camera){
        if(!GameStateManager.i().isGameOver()){
            Vector3 worldCoordinates = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(worldCoordinates);
            Vector2 mousePosition = new Vector2(worldCoordinates.x, worldCoordinates.y);
            Vector2 centerPos = new Vector2(player.getPosition().x, player.getPosition().y);
            Vector2 direction = mousePosition.sub(centerPos).nor();
            float mouseAngle = direction.angleDeg();
            player.sprite.setOriginCenter();
            player.sprite.setRotation(mouseAngle - 90);

        }
    }

    public void Shoot(){
        Vector3 worldCoordinates = CameraController.getCamera().unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));
        Vector2 mousePos = new Vector2(worldCoordinates.x, worldCoordinates.y);
        Vector2 direction = mousePos.sub(player.getPosition()).nor();
        float offsetDistance = 0.7f;
        Vector2 shootPoint = player.getPosition().cpy().add(direction.scl(offsetDistance));
        for(Gun gun : player.getGuns()){
            if(!gun.getOnCooldown()) AudioManager.i().playSfx("shoot", 1f, 0f, 0.4f);
            gun.Shoot(direction, shootPoint);

        }
        
    }

    public void Draw(SpriteBatch batch, float delta){

        Vector2 vel = player.velocity;
        Vector2 pos = player.getPosition();
        Vector2 accel = player.acceleration;
        
        vel.add(accel.x * delta, accel.y * delta);
        pos.add(vel.x * delta, vel.y * delta);
        vel.scl(player.deACCEL);
        player.body.setLinearVelocity(vel);

        player.UpdateSpritePosition();
        player.sprite.draw(batch);
        // if(player.getIsInvinsible()) player.invinsibilityFrames(0.5f, Gdx.graphics.getDeltaTime());
    }
    
}
