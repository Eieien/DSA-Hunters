package io.github.managers;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

import io.github.components.Gun;
import io.github.entities.Player;
import io.github.entities.enemies.Enemy;

public class EnemyManager {
    
    private float timePerSpawn = 0f;
    private float spawnRate = 0.5f;
    private Player player;
    private boolean isSpawned = false;
    public static Array<Enemy> enemies;
    
    public EnemyManager(Player player){
        enemies = new Array<>();
        this.player = player;
    }

    public void Spawn(float delta){
        float randomFloatX = MathUtils.random(-10f, 10f);
        float randomFloatY = MathUtils.random(-10f, 10f);
        Vector2 playerPos = player.getPosition();
        Vector2 spawnPos = new Vector2(playerPos.x + randomFloatX, playerPos.y + randomFloatY).scl(5);
        if(timePerSpawn <= 0f ){
            enemies.add(new Enemy(spawnPos, "sprites/memory.png"));
            timePerSpawn = spawnRate;
            isSpawned = true;
        }else{
            timePerSpawn -= delta;
        }
    }

    public void setSpawnRate(float newSpawnRate){
        this.spawnRate = newSpawnRate;
    }

    public void render(SpriteBatch batch){
        float delta = Gdx.graphics.getDeltaTime();
        for (Enemy e: enemies){
            e.Follow(player.getPosition());
            e.facePlayer(player.getPosition());

            
            for(Gun gun : e.getGuns()){
                gun.Update(delta);
                if(gun.getOnCooldown()){
                    gun.GunDelay(delta);
                }
                if(!GameStateManager.i().isGameOver() && e.getPosition().dst(player.getPosition()) < 10 && !gun.getOnCooldown()){
                    gun.Shoot(e.getTargetDirection(player.getPosition()), e.getPosition());
                    AudioManager.i().playSfx("shoot", 1f, 0f, 0.4f);
                }
                
            }

            if(e.getIsInvinsible()) e.invinsibilityFrames(1f, Gdx.graphics.getDeltaTime());
            e.render(batch);
        }
    }
    


}
