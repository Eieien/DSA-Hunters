package io.github.components;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;

import io.github.controllers.CameraController;
import io.github.entities.Bullet;
import io.github.entities.Enemy;
import io.github.entities.Player;
import io.github.managers.EnemyManager;
import io.github.managers.WorldManager;
import io.github.pools.BulletPool;

public class WorldContactListener implements ContactListener {
   
    @Override
    public void beginContact(Contact contact) {
        // Called when two fixtures begin to make contact
        // Retrieve the fixtures involved in the collision
        // Fixture fixtureA = contact.getFixtureA();
        // Fixture fixtureB = contact.getFixtureB();
        // Perform actions based on the collision (e.g., play sound, update game state)
        Fixture fixA = contact.getFixtureA();
        Fixture fixB = contact.getFixtureB();
        if(fixA == null || fixB == null) return;

        Object A = fixA.getUserData();
        Object B = fixB.getUserData();

        // if(A instanceof Enemy && B instanceof Player){
        //     Enemy enemy = (Enemy) A;
        //     EnemyManager.enemies.removeValue(enemy, true);
        //     WorldManager.addToDestroyBodies(enemy.getBody());
        // }

        if(B instanceof Bullet){
            handleBulletHit((Bullet) B, A);
         
        }

        if(A instanceof Bullet){
            handleBulletHit((Bullet) A, B);
            
        }


    }

    @Override
    public void endContact(Contact contact) {
        // Called when two fixtures cease to make contact
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {
        // Called before the collision is resolved, allowing adjustments (e.g., disable collision)
    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {
        // Called after the collision has been resolved, providing impulse information
    }

    private void handleBulletHit(Bullet bullet, Object other){

        if(bullet.isDead()) return;
        
        bullet.kill();

        bullet.texture.dispose();
        BulletPool.bullets.removeValue(bullet, true);
        WorldManager.addToDestroyBodies((bullet).getBody());
        
        if(other instanceof Player){
            CameraController.Shake(0.5f, 0.2f);
        }
        
        if(other instanceof Enemy){
            ((Enemy)other).texture.dispose();
            WorldManager.addToDestroyBodies(((Enemy) other).getBody());
            EnemyManager.enemies.removeValue((Enemy) other, true);
        }
    }


}
