package com.gametomax.paleotale.actors;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.physics.box2d.Body;
import com.gametomax.paleotale.box2d.DeerUserData;
import com.gametomax.paleotale.utils.Constants;
import com.gametomax.paleotale.utils.GameUtils;

public class Deer extends GameActor {

    public Animation startAnimation;
    public Animation runningAnimation;
    public Animation jumpingAnimation;
    public Animation fallingAnimation;
    public float stateTime, alpha;
    private float x;
    private boolean jumping;
    private boolean falling;
    private boolean starting;
    private boolean leaving;
    public boolean arrived;
    public int jump_numbers;

    public Deer(Body body, GameUtils session) {
        super(body);
        x = Constants.DEER_INITIAL_X;
        stateTime = 0f;
        alpha = 1f;
        startAnimation = session.createAnimation(Constants.DEER_START_REGION_NAMES,Constants.DEER_FRAME_DURATION);
        runningAnimation = session.createAnimation(Constants.DEER_RUN_REGION_NAMES,Constants.DEER_FRAME_DURATION);
        jumpingAnimation = session.createAnimation(Constants.DEER_JUMP_REGION_NAMES,Constants.DEER_FRAME_DURATION);
        fallingAnimation = session.createAnimation(Constants.DEER_FALL_REGION_NAMES,Constants.DEER_FRAME_DURATION);
        starting = true;
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
        if (jumping ) { //&& initiateJump) {
            jump_numbers ++;
            if (jump_numbers == Constants.DEER_MAX_JUMP) {
                leaving = true;
            }
        }
        this.jumping = jumping;
    }



    public boolean jumpProcessStarted() {
        return jumping;
    }

    public boolean isFalling() {
        return falling;
    }

    public void setFalling(boolean falling) {
        this.falling = falling;
    }

    @Override
    public DeerUserData getUserData() {
        return (DeerUserData) userData;
    }

    public boolean isStarting() {
        return starting;
    }

    public void setStarting(boolean starting) {
        this.starting = starting;
    }


    public boolean isLeaving() {
        return leaving;
    }

    public void setLeaving(boolean leaving) {
        this.leaving = leaving;
    }

    public float frameToY() {
        if (jumping) {
            int frame = jumpingAnimation.getKeyFrameIndex(stateTime);
            if (0 <= frame && frame < 5) {
                return 0.5f;
            } else if ((5 <= frame && frame < 8) || (16 <= frame && frame < 19)) {
                return Constants.RUNNER_Y + Constants.DEER_HEIGHT;
            } else {
                return Constants.RUNNER_Y + Constants.DEER_HEIGHT*2;
            }
        } else {
            return 0.5f;
        }
    }
}
