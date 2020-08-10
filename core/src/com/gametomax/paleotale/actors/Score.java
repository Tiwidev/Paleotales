package com.gametomax.paleotale.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.gametomax.paleotale.utils.Constants;

public class Score extends Actor {

    private float score;
    private int multiplier;
    private Rectangle bounds;
    private BitmapFont font;

    public Score(Rectangle bounds, AssetManager am) {
        this.bounds = bounds;
        setWidth(bounds.width);
        setHeight(bounds.height);
        score = 0;
        multiplier = 5;
        // Fonts
        font = new BitmapFont(Gdx.files.internal(Constants.FONT_NAME),false);
        font.setColor(255f, 255f, 255f, 1f);
        font.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        font.getData().setScale(Constants.FONT_SCALE,Constants.FONT_SCALE);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
    }

    public int getScore() {
        return (int) Math.floor(score);
    }

    public void setScore(float delta, float mult) {
        score += multiplier*delta*mult;
    }

    public void setMultiplier(int multiplier) {
        this.multiplier = multiplier;
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public BitmapFont getFont() {
        return font;
    }

}