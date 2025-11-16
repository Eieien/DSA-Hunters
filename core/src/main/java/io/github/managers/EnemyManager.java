package io.github.managers;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

import io.github.entities.Enemy;
import io.github.entities.Player;

public class EnemyManager {
    
    private final Array<Enemy> enemies;
    private float timePerSpawn = 0f;
    private float spawnRate = 0.5f;
    private Player player;
    private boolean isSpawned = false;
    
    public EnemyManager(Player player){
        enemies = new Array<>();
        this.player = player;
    }

    public void Spawn(float delta){
        float randomFloatX = MathUtils.random(-10f, 10f);
        float randomFloatY = MathUtils.random(-10f, 10f);
        Vector2 playerPos = player.getPosition();
        Vector2 spawnPos = new Vector2(playerPos.x + randomFloatX, playerPos.y + randomFloatY).scl(5);
        if(timePerSpawn <= 0f){
            enemies.add(new Enemy(spawnPos));
            timePerSpawn = spawnRate;
            // isSpawned = true;
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
            if(e.gun.getOnCooldown()){
                e.gun.GunDelay(delta);
            }
            
            if(e.getPosition().dst(player.getPosition()) < 10 && !e.gun.getOnCooldown()){
                e.gun.Shoot(e.getTargetDirection(player.getPosition()), e.getPosition().cpy().add(e.getTargetDirection(player.getPosition()).scl(1.2f)));
                // System.out.println("Player within range");
            }
            e.gun.renderBullets(batch);
            e.render(batch);
        }
    }
    


}
