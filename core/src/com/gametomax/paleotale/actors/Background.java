package com.gametomax.paleotale.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.gametomax.paleotale.utils.Constants;

import java.util.Random;

import static com.gametomax.paleotale.utils.Constants.getGroundSpeed;

public class Background extends Actor {

    public final TextureRegion[] textureRegions;
    public final float[] xs;
    public int current_hill;

    public Background(AssetManager am) {
        textureRegions = new TextureRegion[Constants.BACKGROUND_REGION_NAMES.length];
        int i=0;
        for (String path:Constants.BACKGROUND_REGION_NAMES) {
            textureRegions[i] = new TextureRegion(am.get(path,Texture.class));
            i++;
        }
        xs = new float[Constants.BACKGROUND_REGION_NAMES.length];
        int current_x = 0;
        for (int j=0; j<Constants.BACKGROUND_REGION_NAMES.length; j++) {
            xs[j] = current_x;
            current_x += Constants.APP_WIDTH;
        }
        Random r = new Random();
        current_hill = r.nextInt(6);
    }

    @Override
    public void act(float delta) {

    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
    }

    public void updateXBounds(float delta, double time) {
        float max_x = 0;
        for (int i=0; i < xs.length; i++) {
            xs[i] -= getGroundSpeed(time,delta);
            if (xs[i] > max_x) {
                max_x = xs[i];
            }
        }
        for (int i=0; i < xs.length; i++) {
            if (xs[i] <= -Constants.APP_WIDTH) {
                xs[i] = max_x + Constants.APP_WIDTH;
                if (i==1) {
                    Random r = new Random();
                    current_hill = r.nextInt(6);
                }
            }
        }
    }
}