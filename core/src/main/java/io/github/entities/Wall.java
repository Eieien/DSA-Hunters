package io.github.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.PolygonShape;

public class Wall extends Entity {

    PolygonShape wallShape;

    public Wall(Vector2 position){
        super(position.x, position.y, "Static", "wiwa.png", 10f, true);

        wallShape = new PolygonShape();
        wallShape.setAsBox(sprite.getWidth() / 2, sprite.getHeight() /2);

        body.createFixture(wallShape, 0).setUserData("Wall");;
        wallShape.dispose();
        System.out.println("Wall: " + getPosition().x + ", " + getPosition().y);
        
    }
    
    public void Draw(SpriteBatch batch){
        UpdateSpritePosition();
        getPosition().set(10, 10);
        sprite.draw(batch);
    }
    
}
