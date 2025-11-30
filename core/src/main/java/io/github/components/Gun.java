package io.github.components;

import java.util.Vector;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

import io.github.entities.Bullet;
import io.github.pools.BulletPool;

public class Gun {
    
    public float fireRate;
    private final int bulletsPerShot;
    private BulletData bulletData;

    private boolean onCooldown;
    private boolean onBurst;
    private float bulletSpeed;
    private float damage;
    private float radialAngle;
    private Vector2 lastShootPoint;
    private Vector2 lastShootDirection;
    private String gunType;
    int burstShotsleft;
    float burstDelayTimer;
    float fireDelay = 0f;
    float burstDelay = 0f;
    
    public Gun(String type, short category, String bulletSprite, float fireRate, float bulletSpeed, float damage, float radialAngle, int bulletsPerShot){
        this.fireRate = fireRate;
        this.damage = damage;
        this.bulletsPerShot = bulletsPerShot;
        this.radialAngle = radialAngle;
        this.gunType =type;
        this.bulletData = new BulletData(bulletSprite, category, bulletSpeed, 2f, damage);
    }

    public void Shoot(Vector2 direction, Vector2 shootPoint){

        if(fireDelay > 0)return;
        burstShotsleft = bulletsPerShot;
        burstDelayTimer = burstDelay;
        this.lastShootPoint = shootPoint.cpy();
        this.lastShootDirection = direction.cpy();

        fireDelay = fireRate;
        onCooldown = true;
        fire();
    }

    public void Update(float delta){
        if(fireDelay > 0) fireDelay -= delta;

        if(burstShotsleft > 0){
            burstDelayTimer -= delta;
            if(burstDelayTimer <= 0f){
                fire();
                burstShotsleft--;
                burstDelayTimer = burstDelay;
            }
        }
    }

    public void fire(){
        switch(gunType){

            case "radial":
                radialFire();
                break;
            default:
                standardFire();
        }
    }

    public void standardFire(){
        float angleOffset = (MathUtils.random(-radialAngle, radialAngle));
        Vector2 dir = lastShootDirection.cpy().setAngleDeg(lastShootDirection.angleDeg() + angleOffset);
        BulletPool.bullets.add(new Bullet(lastShootPoint, dir, bulletData));
    }

    public void radialFire(){
        float step = 360f / (bulletsPerShot);
        float angleOffset = (MathUtils.random(-radialAngle, radialAngle));
        
        float start = lastShootDirection.angleDeg() - radialAngle * 0.5f + angleOffset;
        
        for(int i = 0; i < bulletsPerShot; i++){
            float angle = start + i * step;
            Vector2 dir = new Vector2(1, 0).setAngleDeg(angle);
            BulletPool.bullets.add(new Bullet(lastShootPoint, dir, bulletData));

        }
    }


    public void GunDelay(float delta){
        if(!onCooldown) return;
        fireDelay -= delta;
        if(fireDelay <= 0){
            fireDelay = 0f; 
            onCooldown = false;
        }

    }

    public boolean getOnBurst(){
        return onBurst;
    }

    public boolean getOnCooldown(){
        return fireDelay > 0;
    }


}
