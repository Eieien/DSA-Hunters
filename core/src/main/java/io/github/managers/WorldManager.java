package io.github.managers;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;

import io.github.components.WorldContactListener;

public class WorldManager {
    private static World world;
    private static Array<Body> bodies;


    public static void Initialize(){
        if(world == null){
            world = new World(new Vector2(0, 0), true);
            bodies = new Array();
            world.setContactListener(new WorldContactListener());

        }
    }
    

    public static World getWorld(){
        return world;
    }

    public static void addToDestroyBodies(Body body){
        bodies.add(body);
    }

    public static void destroyBodies(){
        for(Body b: bodies){
            world.destroyBody(b);
        }
        bodies.clear();
    }



    //Update to detect collisions
    public static void Update(float delta){
        world.step(delta, 6, 2);
    }

    public static void dispose(){
        if(world != null) world.dispose(); 
        // bodies.clear();
    }
}
