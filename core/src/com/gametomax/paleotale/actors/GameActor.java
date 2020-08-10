package com.gametomax.paleotale.actors;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.gametomax.paleotale.box2d.UserData;
import com.gametomax.paleotale.utils.Constants;

public abstract class GameActor extends Actor {

    protected Body body;
    protected UserData userData;

    public GameActor(Body body) {
        this.body = body;
        this.userData = (UserData) body.getUserData();
    }

    @Override
    public void act(float delta) {
        super.act(delta);

        if (body.getUserData() == null) {
            remove();
        }

    }

    public abstract UserData getUserData();

}
