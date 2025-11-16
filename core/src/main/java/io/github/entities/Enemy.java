package io.github.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;

import io.github.components.Gun;
public class Enemy extends Entity {
    
    public Gun gun;

    public Enemy(Vector2 position){
        super(position.x, position.y, "Dynamic", "doge.jpg", 1f, false);
        
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(sprite.getWidth() / 2, sprite.getHeight() /2);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density =1f;
        fixtureDef.restitution = 0.4f;
        fixtureDef.friction = 0f;
        fixtureDef.restitution = 0f;

        body.createFixture(fixtureDef);
        body.setFixedRotation(true);
        shape.dispose();

        velocity.set(0, 0);
        acceleration.set(0, 0);
        gun = new Gun(2f, 10f, 1, 1);
        // System.out.println("Enemy Spawned");
    }

    public void Follow(Vector2 target){
        float delta = Gdx.graphics.getDeltaTime();
        Vector2 direction = target.sub(getPosition()).nor();
        Vector2 impulse = direction.scl(2f);
        // body.applyLinearImpulse(impulse, body.getWorldCenter(), true);
        body.setLinearVelocity(impulse);
    }

    
    public void render(SpriteBatch batch){
        UpdateSpritePosition();
        sprite.draw(batch);
    }
}
