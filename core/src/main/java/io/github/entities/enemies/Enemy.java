package io.github.entities.enemies;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;

import io.github.components.Gun;
import io.github.entities.Entity;
public class Enemy extends Entity {
    
    public Gun gun1, gun2;

    public Enemy(Vector2 position, String sprite){
        super(position.x, position.y, "Dynamic", sprite, 1f, false);
        UpdateSpritePosition();
        setStats(100f, 25f, 15f);

        createPhysics();

        velocity.set(0, 0);
        acceleration.set(0, 0);
        gun1 = new Gun("normal" , CATEGORY_PLAYER,"sprites/projectiles/bullet_circle.png", 2f, 0.2f, 10f, 1, 2);
        gun2 = new Gun("radial", CATEGORY_PLAYER , "sprites/projectiles/bullet.png",3f, 0.2f , getBaseAtk(), 35f, 4);
        addGun(gun1);
        addGun(gun2);
    }

    private void createPhysics(){
        CircleShape shape = new CircleShape();
        shape.setRadius(0.4f);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density =1f;
        fixtureDef.restitution = 0.4f;
        fixtureDef.friction = 0f;
        fixtureDef.restitution = 0f;
        fixtureDef.isSensor = false;
        fixtureDef.filter.categoryBits = CATEGORY_ENEMY;
        fixtureDef.filter.maskBits = CATEGORY_PLAYER | CATEGORY_BULLET;

        body.createFixture(fixtureDef).setUserData(this);
        body.setFixedRotation(true);
        shape.dispose();
    }

    public void Follow(Vector2 target){
        Vector2 direction = target.sub(getPosition()).nor();
        Vector2 impulse = direction.scl(3f);
        // body.applyLinearImpulse(impulse, body.getWorldCenter(), true);
        body.setLinearVelocity(impulse);
    }

    public void facePlayer(Vector2 target){
        Vector2 direction = target.sub(getPosition());
        float degrees = direction.angleDeg();
        sprite.setOriginCenter();
        sprite.setRotation(degrees - 90);
    }

    public void render(SpriteBatch batch){
        UpdateSpritePosition();
        sprite.draw(batch);
    }
}
