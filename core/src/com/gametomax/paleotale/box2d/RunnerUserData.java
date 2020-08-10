package com.gametomax.paleotale.box2d;

import com.badlogic.gdx.math.Vector2;
import com.gametomax.paleotale.enums.UserDataType;
import com.gametomax.paleotale.utils.Constants;

public class RunnerUserData extends UserData {

    private final Vector2 runningPosition = new Vector2(Constants.RUNNER_X, Constants.RUNNER_Y);
    private final Vector2 dodgePosition = new Vector2(Constants.RUNNER_DODGE_X, Constants.RUNNER_DODGE_Y);
    private Vector2 jumpingLinearImpulse;
    private Vector2 jumpFailLinearImpulse;

    public RunnerUserData(float width, float height) {
        super(width,height);
        jumpFailLinearImpulse = Constants.RUNNER_JUMP_FAIL_LINEAR_IMPULSE;
        jumpingLinearImpulse = Constants.RUNNER_JUMPING_LINEAR_IMPULSE;
        userDataType = UserDataType.RUNNER;
    }

    public Vector2 getJumpingLinearImpulse() {
        return jumpingLinearImpulse;
    }

    public Vector2 getJumpFailLinearImpulse() {
        return jumpFailLinearImpulse;
    }

    public void setJumpingLinearImpulse(Vector2 jumpingLinearImpulse) {
        this.jumpingLinearImpulse = jumpingLinearImpulse;
    }

    public float getDodgeAngle() {
        // In radians
        return (float) (-90f * (Math.PI / 180f));
    }

    public Vector2 getRunningPosition() {
        return runningPosition;
    }

    public Vector2 getDodgePosition() {
        return dodgePosition;
    }
}