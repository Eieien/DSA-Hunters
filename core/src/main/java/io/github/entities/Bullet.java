package io.github.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;

import io.github.components.BulletData;
import io.github.managers.WorldManager;
import io.github.pools.BulletPool;


public class Bullet extends Entity {
    
    public float x, y;
    public float timeToLive = 15f;
    public BulletData data;

    public Bullet(Vector2 position, Vector2 direction, BulletData bulletData){
        super(position.x, position.y, "Dynamic" , "sprites/bullet.png", 1f, true);
        this.data = bulletData;
        
        CircleShape shape = new CircleShape();
        shape.setRadius(0.1f);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density =1f;
        fixtureDef.restitution = 0.2f;
        fixtureDef.friction = 0f;
        body.createFixture(fixtureDef).setUserData(this);

        shape.dispose();
        
        Vector2 impulse = direction.scl(bulletData.bulletSpeed);

        // System.out.println(direction);
        body.applyLinearImpulse(impulse, body.getWorldCenter(), true);
        sprite.setOriginCenter();
        sprite.setRotation(direction.angleDeg() - 90);
        UpdateSpritePosition();
        
    }

    public void render(SpriteBatch batch){
        float delta = Gdx.graphics.getDeltaTime();
        UpdateSpritePosition();
        sprite.draw(batch);
        
        timeToLive -= delta;

        if(timeToLive <= 0){
            BulletPool.bullets.removeValue(this, true);
            WorldManager.addToDestroyBodies(body);
            System.out.println("Bullet expired");
        }

    }

}
