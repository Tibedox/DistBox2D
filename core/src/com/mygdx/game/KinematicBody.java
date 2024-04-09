package com.mygdx.game;

import static com.mygdx.game.DistBox2D.WORLD_WIDTH;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class KinematicBody {
    private float x, y;
    private float width, height;
    private Body body;
    private float velocity = 1.2f;
    private float omega = 2f;

    public KinematicBody(World world, float x, float y, float width, float height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.KinematicBody;
        bodyDef.position.set(x, y);

        body = world.createBody(bodyDef);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(width/2, height/2);
        Fixture fixture = body.createFixture(shape, 0);

        shape.dispose();

        body.setLinearVelocity(velocity, 0);
        body.setAngularVelocity(omega);
    }

    public void move() {
        x = body.getPosition().x;
        if(x > WORLD_WIDTH | x < 0) {
            velocity = -velocity;
            omega = -omega;
            body.setLinearVelocity(velocity, 0);
            body.setAngularVelocity(omega);
        }
    }

    public float getX() {
        return body.getPosition().x-width/2;
    }

    public float getY() {
        return body.getPosition().y-height/2;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

    public float getAngle() {
        return MathUtils.radiansToDegrees*body.getAngle();
    }
}
