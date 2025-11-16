package io.github.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;


public class Bullet extends Entity {
    
    public float x, y, bulletSpeed;

    public Bullet(Vector2 position, Vector2 direction){
        super(position.x, position.y, "Dynamic" , "bullet.png", 1f, true);

        CircleShape shape = new CircleShape();
        shape.setRadius(0.1f);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density =1f;
        fixtureDef.restitution = 0.2f;
        fixtureDef.friction = 0f;
        body.createFixture(fixtureDef);

        shape.dispose();
        
        Vector2 impulse = direction.scl(0.1f);

        // System.out.println(direction);
        body.applyLinearImpulse(impulse, body.getWorldCenter(), true);

        UpdateSpritePosition();
    }

    public void render(SpriteBatch batch){
        UpdateSpritePosition();
        sprite.draw(batch);
    }

}
