package com.gametomax.paleotale.box2d;

import com.gametomax.paleotale.enums.UserDataType;

public class RhinoUserData extends UserData {

    public RhinoUserData(float width, float height) {
        super(width,height);
        userDataType = UserDataType.RHINO;
    }
}