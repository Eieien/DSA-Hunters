package io.github.pools;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;

import io.github.entities.Bullet;

public class BulletPool {
    
    public static Array<Bullet> bullets;

    public static void Initialize(){
        bullets = new Array<>();
    }

    public static void renderBullets(SpriteBatch batch){
        for (Bullet B : bullets){
            B.render(batch);
        }
    }


    
    
}
