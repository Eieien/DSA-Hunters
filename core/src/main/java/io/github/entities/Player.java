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
    public Gun gun1;
    public Gun gun2;

    public Player(float screenX, float screenY, World world){
        
        super(0f, 0f, "Dynamic", "sprites/placeholder_char.png", 1f, true);
        
        setStats(100f, 30f, 15f);
        //SHape of the Collider
        CircleShape shape = new CircleShape();
        shape.setRadius(0.3f);
        
        // Physics Behavior
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 1f; // affects inertia
        fixtureDef.friction = 0.4f; // How slippery it is
        fixtureDef.restitution = 0f; // bounce
        fixtureDef.filter.categoryBits = CATEGORY_PLAYER;
        fixtureDef.filter.maskBits = CATEGORY_ENEMY | CATEGORY_BULLET;

        body.createFixture(fixtureDef).setUserData(this);
        body.setFixedRotation(true);
        shape.dispose();

        velocity.set(0, 0);
        acceleration.set(0, 0);

        // gun1 = new Gun("radial", CATEGORY_ENEMY , "sprites/projectiles/bullet.png",0.3f, 2f , getBaseAtk(), 5f, 4);
        gun2 = new Gun("standard", CATEGORY_ENEMY, "sprites/projectiles/bullet.png", 1f, 1f, getBaseAtk(), 0f, 2);
        addGun(gun2);
    }

}
