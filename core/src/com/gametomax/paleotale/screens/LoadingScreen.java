package com.gametomax.paleotale.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.gametomax.paleotale.MyRunGame;
import com.gametomax.paleotale.actors.LoadingBar;
import com.gametomax.paleotale.utils.Constants;

public class LoadingScreen implements Screen {

    private Stage stage;
    public AssetManager assmanager;
    private Image logo;
    private Image loadingFrame;
    private Image loadingBarHidden;
    private Image screenBg;
    private Image loadingBg;
    private MyRunGame game;

    private float startX, endX;
    private float percent;

    private Actor loadingBar;

    public LoadingScreen(MyRunGame g) {
        super();
        game = g;
        assmanager = new AssetManager();
    }

    @Override
    public void show() {
        // Tell the manager to load assets for the loading screen
        assmanager.load("loading.pack", TextureAtlas.class);
        // Wait until they are finished loading
        assmanager.finishLoading();

        // Initialize the stage where we will place everything
        stage = new Stage();

        // Get our textureatlas from the manager
        TextureAtlas atlas = assmanager.get("loading.pack", TextureAtlas.class);

        // Grab the regions from the atlas and create some images
        logo = new Image(new Texture(Gdx.files.internal("title.png")));
        loadingFrame = new Image(atlas.findRegion("loading-frame"));
        loadingBarHidden = new Image(atlas.findRegion("loading-bar-hidden"));
        screenBg = new Image(atlas.findRegion("screen-bg"));
        loadingBg = new Image(atlas.findRegion("loading-frame-bg"));

        // Add the loading bar animation
        Animation anim = new Animation(0.05f, atlas.findRegions("loading-bar-anim"));
        loadingBar = new LoadingBar(anim);

        // Or if you only need a static bar, you can do
        // loadingBar = new Image(atlas.findRegion("loading-bar1"));

        // Add all the actors to the stage
        stage.addActor(screenBg);
        stage.addActor(loadingBar);
        stage.addActor(loadingBg);
        stage.addActor(loadingBarHidden);
        stage.addActor(loadingFrame);
        stage.addActor(logo);

        // Add everything to be loaded
        queueAddImages();
    }

    @Override
    public void resize(int width, int height) {
        // Make the background fill the screen
        screenBg.setSize(stage.getWidth(), stage.getHeight());

        // Place the logo in the middle of the screen
        logo.setX((stage.getWidth() - logo.getWidth()) / 2);
        logo.setY((stage.getHeight() - logo.getHeight()) / 2);

        // Place the loading frame in the middle of the screen
        loadingFrame.setX((stage.getWidth() - loadingFrame.getWidth()) / 2);
        loadingFrame.setY(((stage.getHeight() - loadingFrame.getHeight()) / 2 ) - (logo.getHeight() / 2));

        // Place the loading bar at the same spot as the frame, adjusted a few px
        loadingBar.setX(loadingFrame.getX() + 15);
        loadingBar.setY(loadingFrame.getY() + 5);

        // Place the image that will hide the bar on top of the bar, adjusted a few px
        loadingBarHidden.setX(loadingBar.getX() + 35);
        loadingBarHidden.setY(loadingBar.getY() - 3);
        // The start position and how far to move the hidden loading bar
        startX = loadingBarHidden.getX();
        endX = 440;

        // The rest of the hidden bar
        loadingBg.setSize(450, 50);
        loadingBg.setX(loadingBarHidden.getX() + 30);
        loadingBg.setY(loadingBarHidden.getY() + 3);
    }

    @Override
    public void render(float delta) {
        // Clear the screen
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if (assmanager.update()) { // Load some, will return true if done loading
            game.setScreen(new GameScreen(assmanager,game));
        }

        // Interpolate the percentage to make it more smooth
        percent = Interpolation.linear.apply(percent, assmanager.getProgress(), 0.1f);

        // Update positions (and size) to match the percentage
        loadingBarHidden.setX(startX + endX * percent);
        loadingBg.setX(loadingBarHidden.getX() + 30);
        loadingBg.setWidth(450 - 450 * percent);
        loadingBg.invalidate();

        // Show the loading screen
        stage.act();
        stage.draw();
    }

    @Override
    public void hide() {
        // Dispose the loading assets as we no longer need them
        assmanager.unload("loading.pack");
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

    public void queueAddImages(){
        //TODO FINISH SPLITTING LOADING BEFORE MENU/BEFORE GAME(SPECIFIC SKIN FOR RUNNER)/IN GAME
        //LOAD ALL TEXTURES
        assmanager.load(Constants.MENU_IMAGE_PATH, Texture.class);
        assmanager.load(Constants.PAUSE_IMAGE_PATH, Texture.class);
        assmanager.load(Constants.HOLE_IMAGE_PATH, Texture.class);
        assmanager.load(Constants.GROUND_IMAGE_PATH, Texture.class);
        assmanager.load(Constants.ROCK_IMAGE_PATH, Texture.class);
        assmanager.load(Constants.SPEAR_IMAGE_PATH, Texture.class);
        assmanager.load(Constants.PLANTED_SPEAR_IMAGE_PATH, Texture.class);
        assmanager.load(Constants.FALLEN_TREE_FRONT_PATH, Texture.class);
        assmanager.load(Constants.FALLEN_TREE_BACK_PATH, Texture.class);
        for (String path : Constants.MOUNTAIN_REGION_NAMES) {
            assmanager.load(path, Texture.class);
        }
        for (String path : Constants.TREE_REGION_NAMES) {
            assmanager.load(path, Texture.class);
        }
        for (String path : Constants.BACKGROUND_REGION_NAMES) {
            assmanager.load(path, Texture.class);
        }

        //LOAD ALL ANIM
        for (String path : Constants.RUNNER_RUNNING_REGION_NAMES) {
            assmanager.load(path, Texture.class);
        }
        for (String path : Constants.RUNNER_JUMPING_REGION_NAMES) {
            assmanager.load(path, Texture.class);
        }
        for (String path : Constants.RUNNER_JUMP_FAIL_REGION_NAMES) {
            assmanager.load(path, Texture.class);
        }
        for (String path : Constants.RUNNER_PARKOUR_REGION_NAMES) {
            assmanager.load(path, Texture.class);
        }
        for (String path : Constants.RUNNER_ROCKFALL_REGION_NAMES) {
            assmanager.load(path, Texture.class);
        }
        for (String path : Constants.BIRD_REGION_NAMES) {
            assmanager.load(path, Texture.class);
        }
        for (String path : Constants.RUNNER_ROCKFALL_REGION_NAMES) {
            assmanager.load(path, Texture.class);
        }
        for (String path : Constants.VIGNETTE_REGION_NAMES) {
            assmanager.load(path, Texture.class);
        }
        for (String path : Constants.EMBER_REGION_NAMES) {
            assmanager.load(path, Texture.class);
        }
        for (String path : Constants.GROUND_FALL_REGION_NAMES) {
            assmanager.load(path, Texture.class);
        }
        for (String path : Constants.RUNNER_ROLL_IN_REGION_NAMES) {
            assmanager.load(path, Texture.class);
        }
        for (String path : Constants.RUNNER_ROLL_BIS_REGION_NAMES) {
            assmanager.load(path, Texture.class);
        }
        for (String path : Constants.RUNNER_ROLL_OUT_REGION_NAMES) {
            assmanager.load(path, Texture.class);
        }
        for (String path : Constants.RUNNER_THROW_SPEAR_REGION_NAMES) {
            assmanager.load(path, Texture.class);
        }
        for (String path : Constants.RUNNER_CATCH_SPEAR_REGION_NAMES) {
            assmanager.load(path, Texture.class);
        }
        for (String path : Constants.GRASS_REGION_NAMES) {
            assmanager.load(path, Texture.class);
        }
        for (String path : Constants.MONKEY_TREE_REGION_NAMES) {
            assmanager.load(path, Texture.class);
        }
        for (String path : Constants.RUNNER_FAIL_UP_REGION_NAMES) {
            assmanager.load(path, Texture.class);
        }
        for (String path : Constants.FX_DUST_REGION_NAMES) {
            assmanager.load(path, Texture.class);
        }
        for (String path : Constants.FX_SPEAR_CIRCLE_REGION_NAMES) {
            assmanager.load(path, Texture.class);
        }
        for (String path : Constants.FX_SPEAR_TRAIL_REGION_NAMES) {
            assmanager.load(path, Texture.class);
        }
        for (String path : Constants.TUTO_SWIPE_UP) {
            assmanager.load(path, Texture.class);
        }
        for (String path : Constants.TUTO_SWIPE_DOWN) {
            assmanager.load(path, Texture.class);
        }

        //LOAD MUSIC
        assmanager.load(Constants.MENU_MUSIC_PATH, Music.class);
    }
}