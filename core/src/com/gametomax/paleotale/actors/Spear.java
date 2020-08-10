package com.gametomax.paleotale.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.gametomax.paleotale.utils.Constants;
import com.gametomax.paleotale.utils.GameUtils;

public class Spear extends Actor {
    public TextureRegion texture;
    public float x,y,velocityX,velocityY;
    public float angle;
    public boolean flat;
    public float trailTime;

    public Spear(GameUtils session, float[] target_coordinates) {
        texture = new TextureRegion(session.assmanager.get(Constants.SPEAR_IMAGE_PATH,Texture.class));
        x = Constants.RUNNER_X + Constants.RUNNER_WIDTH;
        y = Constants.RUNNER_Y + Constants.RUNNER_HEIGHT - 0.1f;
        velocityY = target_coordinates[0];
        velocityX = Constants.SPEAR_X_VELOCITY;
        angle = target_coordinates[1];
        flat = velocityY <= 1.8f;
        trailTime=0f;
    }

    @Override
    public void act(float delta) {
        x += Gdx.graphics.getDeltaTime()* velocityX;
        y += Gdx.graphics.getDeltaTime()* velocityY;
        velocityX -= 0.1f;
        velocityY -= 0.1f;
        angle -= 0.1f;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {

    }

    public boolean isFlat() {return flat;}
}
