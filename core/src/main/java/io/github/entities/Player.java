package io.github.entities;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;

import io.github.components.Gun;

public class Player extends Entity {

    public final float ACCEL = 70f;
    public final float deACCEL = 0.80f;
    public final float MAX_SPEED = 10f;
    public Vector2 shootPoint;
    public Gun gun;

    public Player(float screenX, float screenY, World world){
        
        super(0f, 0f, "Dynamic", "placeholder_char.png", 1f, true);
        
        //SHape of the Collider
        CircleShape shape = new CircleShape();
        shape.setRadius(0.5f);
        
        // Physics Behavior
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 1f; // affects inertia
        fixtureDef.friction = 0.4f; // How slippery it is
        fixtureDef.restitution = 0f; // bounce

        body.createFixture(fixtureDef).setUserData(this);
        body.setFixedRotation(true);
        shape.dispose();

        velocity.set(0, 0);
        acceleration.set(0, 0);

        gun = new Gun(0.3f, 10f, 0.5f, 1);
    }

}
