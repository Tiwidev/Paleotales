package com.gametomax.paleotale.utils;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.gametomax.paleotale.box2d.DeerUserData;
import com.gametomax.paleotale.box2d.GroundUserData;
import com.gametomax.paleotale.box2d.RhinoUserData;
import com.gametomax.paleotale.box2d.RunnerUserData;
import com.gametomax.paleotale.box2d.UserData;

public class WorldUtils {

    public static World createWorld() {
        return new World(Constants.WORLD_GRAVITY, true);
    }

    public static Body createGround(World world, float x, float length) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(new Vector2(x, Constants.GROUND_Y));
        Body body = world.createBody(bodyDef);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(length, Constants.GROUND_HEIGHT);

        // Create a fixture definition to apply our shape to
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.friction = 0.0f;
        fixtureDef.density = Constants.GROUND_DENSITY;
        fixtureDef.filter.categoryBits = 0x0001;
        fixtureDef.filter.maskBits = 0x0002 | 0x0003;

        body.createFixture(fixtureDef);
        body.setUserData(new GroundUserData(length,Constants.GROUND_HEIGHT));
        shape.dispose();
        return body;
    }

    public static Body createRunner(World world) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.fixedRotation = true;
        bodyDef.position.set(new Vector2(Constants.RUNNER_X, Constants.RUNNER_Y));

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(Constants.RUNNER_WIDTH, Constants.RUNNER_HEIGHT);

        // Create a fixture definition to apply our shape to
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.friction = .0f;
        fixtureDef.density = Constants.RUNNER_DENSITY;
        fixtureDef.filter.categoryBits = 0x0002;
        fixtureDef.filter.maskBits = 0x0001;

        Body body = world.createBody(bodyDef);
        body.setGravityScale(Constants.RUNNER_GRAVITY_SCALE);
        body.createFixture(fixtureDef);
        body.resetMassData();
        body.setUserData(new RunnerUserData(Constants.RUNNER_WIDTH, Constants.RUNNER_HEIGHT));

        shape.dispose();
        return body;
    }

    public static Body createRhino(World world) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.fixedRotation = true;
        bodyDef.position.set(new Vector2(Constants.APP_WIDTH + 2*Constants.RHINO_WIDTH, Constants.RUNNER_Y));

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(Constants.RHINO_WIDTH, Constants.RHINO_HEIGHT);

        // Create a fixture definition to apply our shape to
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.friction = .0f;
        fixtureDef.density = Constants.RUNNER_DENSITY;
        fixtureDef.filter.categoryBits = 0x0003;
        fixtureDef.filter.maskBits = 0x0001;

        Body body = world.createBody(bodyDef);
        body.createFixture(fixtureDef);
        body.setUserData(new RhinoUserData(Constants.RHINO_WIDTH, Constants.RHINO_HEIGHT));

        shape.dispose();
        return body;
    }

    public static Body createDeer(World world) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.fixedRotation = true;
        bodyDef.position.set(new Vector2(Constants.DEER_INITIAL_X, Constants.RUNNER_Y));

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(Constants.DEER_WIDTH, Constants.DEER_HEIGHT);

        // Create a fixture definition to apply our shape to
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.friction = .0f;
        fixtureDef.density = Constants.RUNNER_DENSITY;
        fixtureDef.filter.categoryBits = 0x0003;
        fixtureDef.filter.maskBits = 0x0001;

        Body body = world.createBody(bodyDef);
        body.createFixture(fixtureDef);
        body.setUserData(new DeerUserData(Constants.DEER_WIDTH, Constants.DEER_HEIGHT));

        shape.dispose();
        return body;
    }

}