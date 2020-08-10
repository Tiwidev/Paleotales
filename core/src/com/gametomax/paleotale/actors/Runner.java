package com.gametomax.paleotale.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.gametomax.paleotale.box2d.RunnerUserData;
import com.gametomax.paleotale.utils.Constants;
import com.gametomax.paleotale.utils.GameUtils;

public class Runner extends GameActor {

    private int dodging;
    private boolean redodging;
    private boolean initiateJump;
    private boolean jumping;
    private boolean jumpfail;
    private boolean waitToParkour;
    private boolean parkour;
    private boolean animationToFinish;
    private int falling;
    private boolean throwing;
    private boolean catching;
    private Body next_rock;
    public Body catchingGround;
    public Animation runningAnimation;
    public Animation jumpingAnimation;
    public Animation jumpFailAnimation;
    public Animation parkourAnimation;
    public Animation rockFallAnimation;
    public Animation failUpAnimation;
    public Animation dodgeINAnimation;
    public Animation dodgeBISAnimation;
    public Animation dodgeOUTAnimation;
    public Animation throwSpearAnimation;
    public Animation catchSpearAnimation;
    public float stateTime;

    public Runner(Body body, GameUtils session) {
        super(body);
        catchingGround = null;
        runningAnimation = session.createAnimation(Constants.RUNNER_RUNNING_REGION_NAMES,Constants.RUNNER_FRAME_DURATION);
        jumpingAnimation = session.createAnimation(Constants.RUNNER_JUMPING_REGION_NAMES,Constants.JUMPING_FRAME_DURATION);
        jumpFailAnimation = session.createAnimation(Constants.RUNNER_JUMP_FAIL_REGION_NAMES,Constants.JUMPING_FRAME_DURATION);
        parkourAnimation = session.createAnimation(Constants.RUNNER_PARKOUR_REGION_NAMES,Constants.JUMPING_FRAME_DURATION);
        rockFallAnimation = session.createAnimation(Constants.RUNNER_ROCKFALL_REGION_NAMES,Constants.JUMPING_FRAME_DURATION);
        dodgeINAnimation = session.createAnimation(Constants.RUNNER_ROLL_IN_REGION_NAMES,Constants.JUMPING_FRAME_DURATION);
        dodgeBISAnimation = session.createAnimation(Constants.RUNNER_ROLL_BIS_REGION_NAMES,Constants.JUMPING_FRAME_DURATION);
        dodgeOUTAnimation = session.createAnimation(Constants.RUNNER_ROLL_OUT_REGION_NAMES,Constants.JUMPING_FRAME_DURATION);
        throwSpearAnimation = session.createAnimation(Constants.RUNNER_THROW_SPEAR_REGION_NAMES,Constants.RUNNER_FRAME_DURATION);
        catchSpearAnimation = session.createAnimation(Constants.RUNNER_CATCH_SPEAR_REGION_NAMES,Constants.RUNNER_FRAME_DURATION);
        failUpAnimation = session.createAnimation(Constants.RUNNER_FAIL_UP_REGION_NAMES,Constants.JUMPING_FRAME_DURATION);
        dodging = 0;
        falling = 0;
        stateTime = 0f;
    }

    @Override
    public RunnerUserData getUserData() {
        return (RunnerUserData) userData;
    }

    public void jump() {
        if (initiateJump) {
            body.applyLinearImpulse(getUserData().getJumpingLinearImpulse(), body.getWorldCenter(), true);
            jumping = true;
            initiateJump = false;
            animationToFinish = false;
        }
    }

    public void landed() {
        animationToFinish = true;
    }

    public void dodge() {
        if (!jumpProcessStarted() && !parkourProcessStarted()) {
            if (dodging == 0) {
                body.setTransform(getUserData().getDodgePosition(), getUserData().getDodgeAngle());
                dodging = 1;
                stateTime = 0f;
            }
        }
    }

    public boolean getRedodge() {return redodging; }

    public void setRedodge(boolean redo) {
        redodging = redo;
    }

    public void stopDodge() {
        redodging = false;
        dodging = 0;
        body.setTransform(getUserData().getRunningPosition(), 0f);
    }

    public int isDodging() {
        return dodging;
    }

    public void setDodging(int new_d) {
        dodging = new_d;
    }

    public void setJumping(boolean j) {jumping=j;}

    public boolean isJumping() {return jumping;}

    public boolean isInitiateJump() { return initiateJump;}

    public void setInitiateJump(boolean ij) {initiateJump = ij;}

    public boolean jumpProcessStarted() {
        return jumping || initiateJump || jumpfail;
    }

    public boolean parkourProcessStarted() {
        return parkour || waitToParkour;
    }

    public boolean notBusy() {
        return !jumpProcessStarted() && !parkourProcessStarted() && isFalling() == 0 && dodging == 0 && !throwing && !catching;
    }

    public void setAnimationToFinish(boolean anim) {animationToFinish = anim;}

    public boolean getAnimationToFinish() {return animationToFinish;}

    public boolean isWaitToParkour() {
        return waitToParkour;
    }

    public void setWaitToParkour(boolean waitToParkour, Body body) {
        this.next_rock = body;
        this.waitToParkour = waitToParkour;
    }

    public boolean isParkour() {
        return parkour;
    }

    public void setParkour(boolean parkour) {
        this.parkour = parkour;
    }

    public Body getNextRock() {
        return next_rock;
    }

    public boolean isJumpFail() {
        return jumpfail;
    }

    public void setJumpFail(boolean jumpfail) {
        this.jumpfail = jumpfail;
        if (jumpfail) {
            body.applyLinearImpulse(getUserData().getJumpFailLinearImpulse(), body.getWorldCenter(), true);
        }
    }

    public int isFalling() {
        return falling;
    }

    public void setFalling(int falling) {
        this.falling = falling;
    }

    public boolean isThrowing() {
        return throwing;
    }

    public void setThrowing(boolean throwing) {
        this.throwing = throwing;
    }

    public boolean isCatching() {
        return catching;
    }

    public void setCatching(boolean catching) {
        this.catching = catching;
    }

}