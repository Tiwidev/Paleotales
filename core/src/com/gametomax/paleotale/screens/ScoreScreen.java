package com.gametomax.paleotale.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.gametomax.paleotale.MyRunGame;
import com.gametomax.paleotale.stages.ScoreStage;
import com.gametomax.paleotale.utils.Constants;

import java.util.ArrayList;

public class ScoreScreen implements Screen {

    private ScoreStage stage;
    public AssetManager assmanager;
    private MyRunGame game;

    public ScoreScreen(AssetManager am, MyRunGame g, ArrayList<String> beast_appeared, ArrayList<String> beast_hunted, int final_score) {
        game = g;
        assmanager = am;
        stage = new ScoreStage(assmanager, game, beast_appeared, beast_hunted, final_score);
    }

    @Override
    public void render(float delta) {
        //Clear the screen
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.draw();
        stage.act(delta);
    }

    @Override
    public void resize(int width, int height) {
        // Lets check aspect ratio of our visible window
        float screenAR = width / (float) height;
        // Our camera needs to be created with new aspect ratio
        // Our visible gameworld width is still 20m but we need to
        // calculate what height keeps the AR correct.
        stage.camera = new OrthographicCamera(Constants.APP_WIDTH, Constants.APP_HEIGHT/screenAR);
        // Finally set camera position so that (0,0) is at bottom left
        stage.camera.position.set(stage.camera.viewportWidth / 2, stage.camera.viewportHeight / 2, 0);
        stage.camera.update();

        // If we use spritebatch to draw lets update it here for new camera
        stage.batch = new SpriteBatch();
        // This line says:"Camera lower left corner is 0,0. Width is 20 and height is 20/AR. Draw there!"
        stage.batch.setProjectionMatrix(stage.camera.combined);
    }

    @Override
    public void show() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {

    }

}