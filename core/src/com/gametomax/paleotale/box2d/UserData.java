package com.gametomax.paleotale.box2d;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.gametomax.paleotale.enums.UserDataType;
import com.gametomax.paleotale.utils.Constants;

import java.util.Random;

public abstract class UserData {


    protected UserDataType userDataType;
    protected float height;
    protected float width;
    private boolean next;
    private boolean fake;
    private boolean grass;
    private boolean grassActivated;
    private boolean tree;
    private boolean bird;
    private boolean spear;
    private float birdTime;
    private float fallTime;
    private boolean birdActivated;
    private int treeNumber;
    private boolean rock;
    private float grassTime;
    private boolean monkeytree;
    private float monkeyTime;
    private float smilodonTime;
    private boolean dolmen;
    private boolean fallentree;
    private boolean tutoSwipeUp;
    private boolean tutoSwipeDown;
    private boolean tutoSwipeRight;

    public UserData(float width, float height) {
        this.width = width;
        this.height = height;
        this.grassTime = 0f;
        this.birdTime = 0f;
    }

    public float getWidth() {
        return width;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public UserDataType getUserDataType() {
        return userDataType;
    }

    public boolean hasNext() {
        return next;
    }

    public void setNext(boolean next) {
        this.next = next;
    }

    public boolean isFake() {
        return fake;
    }

    public void setFake(boolean fake) {
        this.fake = fake;
    }

    public boolean isGrass() {
        return grass;
    }

    public void setGrass(boolean grass) {
        this.grass = grass;
    }

    public boolean isTree() {
        return tree;
    }

    public void setTree(boolean tree) {
        this.tree = tree;
        if (tree) {
            Random r = new Random();
            this.treeNumber = r.nextInt(3);
        }
    }

    public int getTreeNumber() {return treeNumber;}

    public boolean isMonkeyTree() {
        return monkeytree;
    }

    public void setMonkeyTree(boolean monkeytree) {
        this.monkeytree = monkeytree;
    }

    public boolean isDolmen() {
        return dolmen;
    }

    public void setDolmen(boolean dolmen) {
        this.dolmen = dolmen;
    }

    public boolean isFallenTree() {
        return fallentree;
    }

    public void setFallenTree(boolean fallentree) {
        this.fallentree = fallentree;
    }

    public boolean isRock() {
        return rock;
    }

    public void setRock(boolean rock) {
        this.rock = rock;
    }

    public boolean isSpear() {
        return spear;
    }

    public void setSpear(boolean spear) {
        this.spear = spear;
    }

    public boolean isBird() {
        return bird;
    }

    public void setBird(boolean bird) {
        this.bird = bird;
    }

    public void setBirdActivated(boolean birdA) {
        this.birdActivated = birdA;
    }

    public boolean getBirdActivated() {
        return this.birdActivated;
    }


    public float getBirdTime() {
        return birdTime;
    }

    public void setBirdTime(float birdTime) {
        this.birdTime = birdTime;
    }

    public float getFallTime() {
        return fallTime;
    }

    public void setFallTime(float fallTime) {
        this.fallTime = fallTime;
    }


    public float getGrassTime() {
        return grassTime;
    }

    public void setGrassTime(float grassT) {
        this.grassTime = grassT;
    }

    public boolean getGrassActivated() {
        return grassActivated;
    }

    public void setGrassActivated(boolean grassActivated) {
        this.grassActivated = grassActivated;
    }

    public float getMonkeyTime() {
        return monkeyTime;
    }

    public void setSmilodonTime(float smilodonTime) {
        this.smilodonTime = smilodonTime;
    }

    public float getSmilodonTime() {
        return smilodonTime;
    }

    public void setMonkeyTime(float monkeyTime) {
        this.monkeyTime = monkeyTime;
    }

    public boolean isTutoSwipeUp() {
        return tutoSwipeUp;
    }

    public void setTutoSwipeUp(boolean tutoSwipeUp) {
        this.tutoSwipeUp = tutoSwipeUp;
    }

    public boolean isTutoSwipeDown() {
        return tutoSwipeDown;
    }

    public void setTutoSwipeDown(boolean tutoSwipeDown) {
        this.tutoSwipeDown = tutoSwipeDown;
    }

    public boolean isTutoSwipeRight() {
        return tutoSwipeRight;
    }

    public void setTutoSwipeRight(boolean tutoSwipeRight) {
        this.tutoSwipeRight = tutoSwipeRight;
    }

}