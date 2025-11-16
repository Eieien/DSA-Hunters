package io.github.components;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

import io.github.entities.Bullet;

public class Gun {
    
    private final Array<Bullet> bullets;
    public float fireRate;
    private final int bulletsPerShot;
    
    private boolean onCooldown;
    
    float fireDelay = 0f;
    float burstDelay = 0f;
    
    public Gun(float fireRate, float damage, float bulletSpread, int bulletsPerShot){
        bullets = new Array<>();
        this.fireRate = fireRate;
        this.bulletsPerShot = bulletsPerShot;
    }

    public void Shoot(Vector2 direction, Vector2 shootPoint){
        float delta =  Gdx.graphics.getDeltaTime();
        
        if(fireDelay <= 0){

            for(int i = bulletsPerShot; i > 0; ){
                if(burstDelay <= 0){
                    bullets.add(new Bullet(shootPoint, direction));
                    burstDelay = 1.5f;
                    i--;
                    // System.out.println("Shoot");
                }else{
                    burstDelay -= delta;
                    // System.out.println("Delaying");

                }
            }
            
            fireDelay = fireRate;
            onCooldown = true;
            // System.out.println("Now On Cooldown");
        }

    }

    public void GunDelay(float delta){
        if(!onCooldown) return;
        
        fireDelay -= delta;

        if(fireDelay <= 0){
            fireDelay = 0f; 
            onCooldown = false;
            // System.out.println("Ready to fire!");
        }
    }


    public boolean getOnCooldown(){
        return fireDelay > 0;
    }

    public void renderBullets(SpriteBatch batch){
        for(Bullet b: bullets){
            b.render(batch);
        }
    }
}
