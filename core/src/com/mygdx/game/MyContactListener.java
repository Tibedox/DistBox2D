package com.mygdx.game;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;

public class MyContactListener implements ContactListener {
    DistBox2D main;

    public MyContactListener(DistBox2D main) {
        this.main = main;
    }

    @Override
    public void beginContact(Contact contact) {
        Fixture fixtureA = contact.getFixtureA();
        Fixture fixtureB = contact.getFixtureB();

        Body bodyA = fixtureA.getBody();
        Body bodyB = fixtureB.getBody();

        if ((bodyA.getType() == BodyDef.BodyType.DynamicBody && bodyB.getType() == BodyDef.BodyType.DynamicBody)) {
            // Обработка момента столкновения DynamicBody с StaticBody
            main.sndKnock.play();
        }

        /*if(fixtureA.getBody().getUserData() instanceof DynamicBody && fixtureB.getBody().getUserData() instanceof DynamicBody) {
            // Обработка столкновения двух DynamicBody
            DynamicBody bodyA = (DynamicBody)fixtureA.getBody().getUserData();
            DynamicBody bodyB = (DynamicBody)fixtureB.getBody().getUserData();

            // Далее можно добавить логику для определения момента столкновения
        }*/
    }

    @Override
    public void endContact(Contact contact) {

    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
