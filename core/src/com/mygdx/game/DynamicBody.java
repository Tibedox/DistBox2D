package com.mygdx.game;

import static com.mygdx.game.DistBox2D.*;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class DynamicBody {
    public int type;
    private float x, y;
    private float r;
    private float width, height;
    private Body body;

    public DynamicBody(World world, float x, float y, float r) {
        type = TYPE_CIRCLE;

        this.x = x;
        this.y = y;
        this.r = r;
        width = height = r*2;

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.linearDamping = 0.3f;
        bodyDef.angularDamping = 0.3f;
        bodyDef.position.set(x, y);

        body = world.createBody(bodyDef);

        CircleShape shape = new CircleShape();
        shape.setRadius(r);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 0.5f;
        fixtureDef.friction = 0.4f;
        fixtureDef.restitution = 0.6f;

        Fixture fixture = body.createFixture(fixtureDef);

        shape.dispose();
    }

    public DynamicBody(World world, float x, float y, float width, float height) {
        type = TYPE_BOX;

        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(x, y);

        body = world.createBody(bodyDef);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(width/2, height/2);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 0.5f;
        fixtureDef.friction = 0.4f;
        fixtureDef.restitution = 0.6f;

        Fixture fixture = body.createFixture(fixtureDef);

        shape.dispose();
    }

    public DynamicBody(World world, float x, float y, Polygon p) {
        type = TYPE_POLY;

        this.x = x;
        this.y = y;
        this.width = p.getBoundingRectangle().getWidth();
        this.height = p.getBoundingRectangle().getHeight();

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(x, y);

        body = world.createBody(bodyDef);

        PolygonShape shape = new PolygonShape();
        shape.set(p.getVertices());

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 0.5f;
        fixtureDef.friction = 0.4f;
        fixtureDef.restitution = 0.6f;

        Fixture fixture = body.createFixture(fixtureDef);

        shape.dispose();
    }

    public DynamicBody(World world, float x, float y, Polygon p1, Polygon p2) {
        type = TYPE_POLY;

        this.x = x;
        this.y = y;
        //this.width = p.getBoundingRectangle().getWidth();
        //this.height = p.getBoundingRectangle().getHeight();

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(x, y);

        body = world.createBody(bodyDef);
        // тело 1
        PolygonShape shape1 = new PolygonShape();
        shape1.set(p1.getVertices());

        FixtureDef fixtureDef1 = new FixtureDef();
        fixtureDef1.shape = shape1;
        fixtureDef1.density = 0.5f;
        fixtureDef1.friction = 0.4f;
        fixtureDef1.restitution = 0.6f;

        Fixture fixture1 = body.createFixture(fixtureDef1);

        shape1.dispose();

        // тело 2
        PolygonShape shape2 = new PolygonShape();
        shape2.set(p2.getVertices());

        FixtureDef fixtureDef2 = new FixtureDef();
        fixtureDef2.shape = shape2;
        fixtureDef2.density = 0.5f;
        fixtureDef2.friction = 0.4f;
        fixtureDef2.restitution = 0.6f;

        Fixture fixture2 = body.createFixture(fixtureDef2);

        shape2.dispose();
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

    public Body getBody() {
        return body;
    }

    public boolean hit(float tx, float ty) {
        for(Fixture f: body.getFixtureList()){
            if(f.testPoint(tx, ty)) return f.testPoint(tx, ty);
        }
        return false;
    }

    public void setImpulse(Vector2 p) {
        body.applyLinearImpulse(p, body.getWorldCenter(), true);
    }
}
