package com.gametomax.paleotale.stages;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.gametomax.paleotale.MyRunGame;
import com.gametomax.paleotale.enums.GameState;
import com.gametomax.paleotale.screens.GameScreen;
import com.gametomax.paleotale.utils.Constants;

import java.util.ArrayList;

public class ScoreStage extends Stage implements ContactListener {
    AssetManager am;
    MyRunGame game;
    ArrayList<String> beast_appeared, beast_hunted;
    int final_score,score;
    public SpriteBatch batch;
    private SpriteBatch batch_ui;
    private double startTime;
    private BitmapFont font;
    private Texture bg, red_cross,rhino,deer;

    public OrthographicCamera camera;

    public ScoreStage(AssetManager am, MyRunGame g, ArrayList<String> beast_appeared, ArrayList<String> beast_hunted, int final_score) {
        this.am = am;
        this.game = g;
        this.beast_appeared = beast_appeared;
        this.beast_hunted = beast_hunted;
        this.final_score = final_score;
        this.score = 0;
        font = new BitmapFont(Gdx.files.internal(Constants.FONT_NAME),false);
        font.setColor(255f, 255f, 255f, 1f);
        font.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        font.getData().setScale(Constants.FONT_SCALE*1.5f,Constants.FONT_SCALE*1.5f);
        startTime = System.currentTimeMillis();
        setupUICamera();
        bg = am.get(Constants.BACKGROUND_REGION_NAMES[0],Texture.class);
        if (beast_hunted.size()>0) {
            rhino = am.get(Constants.RHINO_RUN_REGION_NAMES[0],Texture.class);
            deer = am.get(Constants.DEER_RUN_REGION_NAMES[0],Texture.class);
            if (!am.contains(Constants.RED_ERROR_CROSS_PATH,Texture.class)) {
                am.load(Constants.RED_ERROR_CROSS_PATH,Texture.class);
                am.finishLoading();
            }
            red_cross = am.get(Constants.RED_ERROR_CROSS_PATH,Texture.class);
        }
        Gdx.input.setInputProcessor(this);
    }

    private void setupUICamera() {
        OrthographicCamera camera_ui = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera_ui.position.set(camera_ui.viewportWidth / 2, camera_ui.viewportHeight / 2, 0f);
        camera_ui.update();
        batch_ui = new SpriteBatch();
        batch_ui.setProjectionMatrix(camera_ui.combined);
    }

    @Override
    public void act(float delta) {
        super.act(delta);

        double elapsed_time_ratio = (System.currentTimeMillis() - startTime)/Constants.DISPLAY_ANIMATION_TIME;
        //TODO TICK SOUND
        batch.begin();
        batch.draw(bg,0,0,camera.viewportWidth,camera.viewportHeight);
        if (beast_hunted.size() > 0) {
            int num_beasts = (int) (elapsed_time_ratio/(1.0/(double) beast_hunted.size()));
            for (int i=0;i<Math.min(num_beasts,beast_hunted.size());i++) {
                drawBeastTexture(beast_hunted, i);
            }
        }
        batch.end();
        batch_ui.begin();
        if (score >= final_score) {
            score = final_score;
        }
        else {
            score = (int) (elapsed_time_ratio * (double) final_score);
        }
        String score_string = "score: " + score;
        font.draw(batch_ui, score_string,
                Gdx.graphics.getWidth()/2f - score_string.length() * 24f, Gdx.graphics.getHeight() - 96f);
        String replay_string = "Touch to play again!";
        font.draw(batch_ui, replay_string,
                Gdx.graphics.getWidth()/2f - replay_string.length() * 24f, Gdx.graphics.getHeight()/4f);
        batch_ui.end();

    }

    public void drawBeastTexture(ArrayList<String> list,int index) {
        int num_beasts = beast_hunted.size();
        float[] result = getBeastSize(num_beasts);
        float x = result[0];
        float y = result[1];
        switch (list.get(index)) {
            case "nope":
                drawBeastTexture(beast_appeared,index);
                batch.draw(red_cross,camera.viewportWidth/2f + ((float)index - (float) (num_beasts - 1)/2f)*x - x/2f,(camera.viewportHeight - y)/2f,x,y);
                break;
            case "rhino":
                batch.draw(rhino,camera.viewportWidth/2f + ((float)index - (float) (num_beasts - 1)/2f)*x - x/2f,(camera.viewportHeight - y)/2f,x,y);
                break;
            case "deer":
                batch.draw(deer,camera.viewportWidth/2f + ((float)index - (float) (num_beasts - 1)/2f)*x - x/2f,(camera.viewportHeight - y)/2f,x,y);
                break;
        }
    }

    public float[] getBeastSize(int size) {
        if (camera.viewportWidth - 2*Constants.BEAST_PADDING >= Constants.RHINO_WIDTH*size) {
            return new float[] {Constants.RHINO_WIDTH,Constants.RHINO_HEIGHT};
        } else {
            float ratio = (camera.viewportWidth - 2*Constants.BEAST_PADDING)/(Constants.RHINO_WIDTH*size);
            return new float[] {Constants.RHINO_WIDTH*ratio,Constants.RHINO_HEIGHT*ratio};
        }
    }

    @Override
    public boolean touchDown(int x, int y, int pointer, int button) {
        this.clear();
        this.dispose();
        GameScreen gs = new GameScreen(am,game);
        gs.stage.session.setGameState(GameState.RUNNING);
        gs.stage.startTime = System.currentTimeMillis();
        game.setScreen(gs);
        return super.touchDown(x, y, pointer, button);
    }

    @Override
    public void beginContact(Contact contact) {

    }

    @Override
    public void endContact(Contact contact) {

    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
