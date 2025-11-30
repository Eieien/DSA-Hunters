package io.github.entities.enemies;

import com.badlogic.gdx.math.Vector2;

import io.github.components.Gun;

public class Data extends Enemy{

    public Data(Vector2 position){
        super(position, "sprites/memory.png");
        setStats(100f, 25f, 15f);

        gun1 = new Gun("normal" , CATEGORY_PLAYER,"sprites/projectiles/bullet_circle.png", 2f, 0.2f, 10f, 1, 2);
        gun2 = new Gun("radial", CATEGORY_PLAYER , "sprites/projectiles/bullet.png",3f, 0.2f , getBaseAtk(), 35f, 4);
        addGun(gun1);
        addGun(gun2);
    }

    
    
}
