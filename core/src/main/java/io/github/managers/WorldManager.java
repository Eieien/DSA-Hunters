package io.github.managers;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

public class WorldManager {
    private static World world;

    public static void Initialize(){
        if(world == null){
            world = new World(new Vector2(0, 0), true);
        }
    }

    public static World getWorld(){
        return world;
    }

    //Update to detect collisions
    public static void Update(float delta){
        world.step(delta, 6, 2);
    }

    public static void dispose(){
        if(world != null) world.dispose(); 
    }
}
