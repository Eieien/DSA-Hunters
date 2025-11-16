package io.github.components;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;

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

        System.out.println("Contact between: " +  fixA.getUserData() + " and " + fixB.getUserData());
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


}
