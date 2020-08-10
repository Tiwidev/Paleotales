package com.gametomax.paleotale.box2d;

import com.badlogic.gdx.math.Vector2;
import com.gametomax.paleotale.enums.UserDataType;
import com.gametomax.paleotale.utils.Constants;

public class DeerUserData extends UserData {

    private Vector2 jumpingLinearImpulse;

    public DeerUserData(float width, float height) {
        super(width,height);
        userDataType = UserDataType.DEER;
        jumpingLinearImpulse = Constants.DEER_JUMPING_LINEAR_IMPULSE;
    }

    public Vector2 getJumpingLinearImpulse() {
        return jumpingLinearImpulse;
    }
}
