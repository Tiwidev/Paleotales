package com.gametomax.paleotale.actors;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.physics.box2d.Body;
import com.gametomax.paleotale.box2d.RhinoUserData;
import com.gametomax.paleotale.utils.Constants;
import com.gametomax.paleotale.utils.GameUtils;

public class Rhino extends GameActor {

    public Animation runningAnimation;
    public Animation jumpingAnimation;
    public Animation FallAnimation;
    public float stateTime,alpha;
    public boolean landfx;
    private float x;
    private boolean jumping;
    private boolean falling;

    public Rhino(Body body, GameUtils session) {
        super(body);
        x = Constants.APP_WIDTH + 2*Constants.RHINO_WIDTH;
        stateTime = 0f;
        alpha = 1f;
        runningAnimation = session.createAnimation(Constants.RHINO_RUN_REGION_NAMES,Constants.RHINO_FRAME_DURATION);
        jumpingAnimation = session.createAnimation(Constants.RHINO_JUMP_REGION_NAMES,Constants.RHINO_FRAME_DURATION);
        FallAnimation = session.createAnimation(Constants.RHINO_FALL_REGION_NAMES,Constants.RHINO_FRAME_DURATION);
    }

    public boolean arrived() {
        return x <= Constants.RHINO_FINAL_X;
    }

    public void setX(float new_x) {
        x = new_x;
    }

    public float getX() {
        return x;
    }

    public boolean isJumping() {
        return jumping;
    }

    public void setJumping(boolean jumping) {
        this.jumping = jumping;
    }

    public boolean isFalling() {
        return falling;
    }

    public void setFalling(boolean falling) {
        this.falling = falling;
    }

    @Override
    public RhinoUserData getUserData() {
        return (RhinoUserData) userData;
    }
}
