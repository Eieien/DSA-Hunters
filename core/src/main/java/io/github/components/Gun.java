package io.github.components;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;

import io.github.entities.Bullet;
import io.github.pools.BulletPool;

public class Gun {
    
    public float fireRate;
    private final int bulletsPerShot;
    private BulletData bulletData;

    private boolean onCooldown;
    private float bulletSpeed;
    private float damage;
    
    float fireDelay = 0f;
    float burstDelay = 0f;
    
    public Gun(float fireRate, float bulletSpeed, float damage, float bulletSpread, int bulletsPerShot){
        this.fireRate = fireRate;
        this.damage = damage;
        this.bulletsPerShot = bulletsPerShot;

        this.bulletData = new BulletData(bulletSpeed, 15f, damage, bulletSpread);
    }

    public void Shoot(Vector2 direction, Vector2 shootPoint){
        float delta =  Gdx.graphics.getDeltaTime();
        
        if(fireDelay <= 0){

            for(int i = bulletsPerShot; i > 0; ){
                if(burstDelay <= 0){
                    BulletPool.bullets.add(new Bullet(shootPoint, direction, bulletData));
                    burstDelay = 1.5f;
                    i--;

                }else{
                    burstDelay -= delta;
                }
                
            }
            
            fireDelay = fireRate;
            onCooldown = true;
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


}
