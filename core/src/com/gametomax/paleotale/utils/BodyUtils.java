package com.gametomax.paleotale.utils;

import com.badlogic.gdx.physics.box2d.Body;
import com.gametomax.paleotale.box2d.UserData;
import com.gametomax.paleotale.enums.UserDataType;

public class BodyUtils {

    public static boolean bodyIsRunner(Body body) {
        UserData userData = (UserData) body.getUserData();

        return userData != null && userData.getUserDataType() == UserDataType.RUNNER;
    }

    public static boolean bodyIsRhino(Body body) {
        UserData userData = (UserData) body.getUserData();

        return userData != null && userData.getUserDataType() == UserDataType.RHINO;
    }

    public static boolean bodyIsDeer(Body body) {
        UserData userData = (UserData) body.getUserData();

        return userData != null && userData.getUserDataType() == UserDataType.DEER;
    }

    public static boolean bodyIsGround(Body body) {
        UserData userData = (UserData) body.getUserData();

        return userData != null && userData.getUserDataType() == UserDataType.GROUND;
    }

    public static boolean bodyGroundHasNext(Body body) {
        UserData userData = (UserData) body.getUserData();

        return userData != null && userData.getUserDataType() == UserDataType.GROUND && userData.hasNext();
    }

    public static boolean bodyGroundIsFake(Body body) {
        UserData userData = (UserData) body.getUserData();

        return userData != null && userData.getUserDataType() == UserDataType.GROUND && userData.isFake();
    }

    public static boolean bodyGroundHasGrass(Body body) {
        UserData userData = (UserData) body.getUserData();

        return userData != null && userData.getUserDataType() == UserDataType.GROUND && userData.isGrass();
    }

    public static boolean bodyGroundHasSpear(Body body) {
        UserData userData = (UserData) body.getUserData();

        return userData != null && userData.getUserDataType() == UserDataType.GROUND && userData.isSpear();
    }

    public static boolean bodyGroundHasTree(Body body) {
        UserData userData = (UserData) body.getUserData();

        return userData != null && userData.getUserDataType() == UserDataType.GROUND && userData.isTree();
    }

    public static boolean bodyGroundHasMonkeyTree(Body body) {
        UserData userData = (UserData) body.getUserData();

        return userData != null && userData.getUserDataType() == UserDataType.GROUND && userData.isMonkeyTree();
    }

    public static boolean bodyGroundHasDolmen(Body body) {
        UserData userData = (UserData) body.getUserData();

        return userData != null && userData.getUserDataType() == UserDataType.GROUND && userData.isDolmen();
    }

    public static boolean bodyGroundHasFallenTree(Body body) {
        UserData userData = (UserData) body.getUserData();

        return userData != null && userData.getUserDataType() == UserDataType.GROUND && userData.isFallenTree();
    }

    public static boolean bodyGroundHasRock(Body body) {
        UserData userData = (UserData) body.getUserData();

        return userData != null && userData.getUserDataType() == UserDataType.GROUND && userData.isRock();
    }

    public static boolean bodyGroundHasBird(Body body) {
        UserData userData = (UserData) body.getUserData();

        return userData != null && userData.getUserDataType() == UserDataType.GROUND && userData.isBird();
    }


}