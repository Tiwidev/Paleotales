package com.gametomax.paleotale.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.gametomax.paleotale.enums.GameState;

import java.util.ArrayList;

public class GameUtils {
    public AssetManager assmanager;
    private GameState gameState;
    private ArrayList<String> platformHistory;
    public static final String PREFERENCES_NAME = "preferences";
    private static final String MAX_SCORE_PREFERENCE = "max_score";

    public GameUtils(AssetManager am) {
        gameState = GameState.OVER;
        assmanager = am;
        platformHistory = new ArrayList();
    }

    public GameState getGameState() {
        return gameState;
    }

    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }

    private Preferences getPreferences() {
        return Gdx.app.getPreferences(PREFERENCES_NAME);
    }

    public void saveScore(int score) {
        Preferences preferences = getPreferences();
        int maxScore = preferences.getInteger(MAX_SCORE_PREFERENCE, 0);
        if (score > maxScore) {
            preferences.putInteger(MAX_SCORE_PREFERENCE, score);
            preferences.flush();
        }
    }

    public int getMaxScore() {
        Preferences preferences = getPreferences();
        return preferences.getInteger(MAX_SCORE_PREFERENCE, 0);
    }

    public Animation createAnimation(String[] regionNames, float duration) {

        TextureRegion[] runningFrames = new TextureRegion[regionNames.length];

        for (int i = 0; i < regionNames.length; i++) {
            String path = regionNames[i];
            runningFrames[i] = new TextureRegion((Texture)assmanager.get(regionNames[i]));
        }

        return new Animation(duration, runningFrames);
    }
}