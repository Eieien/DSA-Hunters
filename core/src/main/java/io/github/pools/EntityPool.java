package io.github.pools;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;

import io.github.entities.Bullet;
import io.github.entities.Enemy;

public class EntityPool {
    
    public static Array<Enemy> enemies;

    public static void Initialize(){
        enemies = new Array<>();
    }

    public static void renderBullets(SpriteBatch batch){
        for (Enemy E : enemies){
            E.render(batch);
        }
    }


    

}
