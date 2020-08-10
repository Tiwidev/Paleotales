package com.gametomax.paleotale.actors;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.gametomax.paleotale.box2d.GroundUserData;
import com.gametomax.paleotale.utils.Constants;

public class Ground extends GameActor {

    private final TextureRegion textureRegion;

    private boolean next;

    private boolean fake;

    private boolean grass;

    private boolean tree;

    private boolean rock;

    private boolean bird;

    private boolean spear;

    private boolean monkeytree;

    private boolean fallentree;

    private boolean dolmen;

    private boolean tutoSwipeUp;

    private boolean tutoSwipeDown;

    private boolean tutoSwipeRight;

    public Ground(Body body, AssetManager am) {
        super(body);
        textureRegion = new TextureRegion(am.get(Constants.GROUND_IMAGE_PATH,Texture.class));
        next = false;
    }

    @Override
    public GroundUserData getUserData() {
        return (GroundUserData) userData;
    }

    @Override
    public void act(float delta) {
        super.act(delta);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
    }


    public boolean hasNext() {return next;}

    public void setNext(boolean hasNext) {
        this.next = hasNext;
        getUserData().setNext(hasNext);
    }

    public void setFake(boolean fake) {
        this.fake = fake;
        getUserData().setFake(fake);
    }

    public void setGrass(boolean grass) {
        this.grass = grass;
        getUserData().setGrass(grass);
    }

    public void setTree(boolean tree) {
        this.tree = tree;
        getUserData().setTree(tree);
    }

    public void setRock(boolean rock) {
        this.rock = rock;
        getUserData().setRock(rock);
    }

    public void setBird(boolean bird) {
        this.bird = bird;
        getUserData().setBird(bird);
    }

    public boolean isMonkeytree() {
        return monkeytree;
    }

    public void setMonkeytree(boolean monkeytree) {
        this.monkeytree = monkeytree;
        getUserData().setMonkeyTree(monkeytree);
    }

    public boolean isDolmen() {
        return dolmen;
    }

    public void setDolmen(boolean dolmen) {
        this.dolmen = dolmen;
        getUserData().setDolmen(dolmen);
    }

    public boolean isSpear() {
        return spear;
    }

    public void setSpear(boolean spear) {
        this.spear = spear;
        getUserData().setSpear(spear);
    }

    public boolean isFallentree() {
        return fallentree;
    }

    public void setFallentree(boolean fallentree) {
        this.fallentree = fallentree;
        getUserData().setFallenTree(fallentree);
    }

    public boolean isTutoSwipeUp() {
        return tutoSwipeUp;
    }

    public void setTutoSwipeUp(boolean tutoSwipeUp) {
        this.tutoSwipeUp = tutoSwipeUp;
        getUserData().setTutoSwipeUp(tutoSwipeUp);
    }

    public boolean isTutoSwipeDown() {
        return tutoSwipeDown;
    }

    public void setTutoSwipeDown(boolean tutoSwipeDown) {
        this.tutoSwipeDown = tutoSwipeDown;
        getUserData().setTutoSwipeDown(tutoSwipeDown);
    }

    public boolean isTutoSwipeRight() {
        return tutoSwipeRight;
    }

    public void setTutoSwipeRight(boolean tutoSwipeRight) {
        this.tutoSwipeRight = tutoSwipeRight;
        getUserData().setTutoSwipeRight(tutoSwipeRight);
    }

}