package com.gametomax.paleotale.stages;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.gametomax.paleotale.MyRunGame;
import com.gametomax.paleotale.actors.Background;
import com.gametomax.paleotale.actors.Deer;
import com.gametomax.paleotale.actors.Rhino;
import com.gametomax.paleotale.actors.Score;
import com.gametomax.paleotale.actors.Spear;
import com.gametomax.paleotale.box2d.UserData;
import com.gametomax.paleotale.enums.GameState;
import com.gametomax.paleotale.screens.ScoreScreen;
import com.gametomax.paleotale.utils.Constants;
import com.gametomax.paleotale.utils.GameUtils;
import com.gametomax.paleotale.utils.Shake;
import com.gametomax.paleotale.utils.WorldUtils;
import com.gametomax.paleotale.actors.Runner;
import com.gametomax.paleotale.actors.Ground;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Random;

import static com.gametomax.paleotale.utils.BodyUtils.bodyGroundHasBird;
import static com.gametomax.paleotale.utils.BodyUtils.bodyGroundHasDolmen;
import static com.gametomax.paleotale.utils.BodyUtils.bodyGroundHasFallenTree;
import static com.gametomax.paleotale.utils.BodyUtils.bodyGroundHasGrass;
import static com.gametomax.paleotale.utils.BodyUtils.bodyGroundHasMonkeyTree;
import static com.gametomax.paleotale.utils.BodyUtils.bodyGroundHasNext;
import static com.gametomax.paleotale.utils.BodyUtils.bodyGroundHasRock;
import static com.gametomax.paleotale.utils.BodyUtils.bodyGroundHasSpear;
import static com.gametomax.paleotale.utils.BodyUtils.bodyGroundHasTree;
import static com.gametomax.paleotale.utils.BodyUtils.bodyGroundIsFake;
import static com.gametomax.paleotale.utils.BodyUtils.bodyIsDeer;
import static com.gametomax.paleotale.utils.BodyUtils.bodyIsGround;
import static com.gametomax.paleotale.utils.BodyUtils.bodyIsRhino;
import static com.gametomax.paleotale.utils.BodyUtils.bodyIsRunner;
import static com.gametomax.paleotale.utils.Constants.SPEAR_WIDTH;
import static com.gametomax.paleotale.utils.Constants.getGroundSpeed;
import static com.gametomax.paleotale.utils.Constants.getGroundSpeedMultiplier;
import static com.gametomax.paleotale.utils.WorldUtils.createGround;

public class GameStage extends Stage implements ContactListener {

    private World world;
    private Runner runner;
    private Rhino rhino;
    private Shake shake;
    private Deer deer;
    private Body runner_body;
    private Spear spear;
    private ArrayList<Spear> spear_list;
    private Ground ground;
    private Background bg;
    private Texture ground_texture;
    private Texture rock_texture;
    private Texture hole_texture;
    private Texture menu_texture;
    private Texture pause_texture;
    private Texture planted_spear;
    private ArrayList<String> platformArch;
    private String actionQueue;
    private boolean canQueue;
    private boolean waitRhino;
    private boolean waitDeer;
    private ArrayList<String> beast_appeared;
    private ArrayList<String> beast_hunted;
    private boolean firstDownObstacle = false;

    private float accumulator = 0f;
    public SpriteBatch batch;
    private SpriteBatch batch_ui;
    private Score score;
    private float vignetteTime;
    public double startTime;

    public OrthographicCamera camera;
    private Box2DDebugRenderer renderer;

    private Animation grassAnimation;
    private Texture[] trees;
    private Texture[] hills;
    private Texture back_dolmen_texture;
    private Texture front_dolmen_texture;
    private Animation smilodonAnimation;

    private Texture back_fallentree_texture;
    private Texture front_fallentree_texture;
    private Animation birdAnimation;
    private Animation groundFallAnimation;
    private Animation monkeytreeAnimation;
    private Animation dustFxAnimation;
    private Animation spearCircleFxAnimation;
    private Animation spearTrailFxAnimation;
    private Animation swipeUpAnimation;
    private Animation swipeDownAnimation;
    private Animation swipeRightAnimation;
    private Animation vignette;
    private Animation ember;

    public GameUtils session;
    private MyRunGame game;
    private int tempx,tempy,num_spears;
    private ArrayList<float[]> front_elems;
    private ArrayList<float[]> fxs_list;

    public GameStage(AssetManager assmanager, MyRunGame g) {
        tempx = 0;
        tempy = 0;
        num_spears = 0;
        game = g;
        session = new GameUtils(assmanager);
        queueInGameAssets();
        platformArch = new ArrayList<>();
        setUpWorld();
        setupUICamera();
        setupTouchControlAreas();
        renderer = new Box2DDebugRenderer();
        ground_texture = assmanager.get(Constants.GROUND_IMAGE_PATH,Texture.class);
        rock_texture = assmanager.get(Constants.ROCK_IMAGE_PATH,Texture.class);
        hole_texture = assmanager.get(Constants.HOLE_IMAGE_PATH,Texture.class);
        planted_spear = assmanager.get(Constants.PLANTED_SPEAR_IMAGE_PATH,Texture.class);
        trees = new Texture[Constants.TREE_REGION_NAMES.length];
        for (int i=0; i < Constants.TREE_REGION_NAMES.length ; i++) {
            trees[i] = assmanager.get(Constants.TREE_REGION_NAMES[i],Texture.class);
        }
        hills = new Texture[Constants.MOUNTAIN_REGION_NAMES.length];
        for (int i=0; i < Constants.MOUNTAIN_REGION_NAMES.length ; i++) {
            hills[i] = assmanager.get(Constants.MOUNTAIN_REGION_NAMES[i],Texture.class);
        }
        back_fallentree_texture = assmanager.get(Constants.FALLEN_TREE_BACK_PATH,Texture.class);
        front_fallentree_texture = assmanager.get(Constants.FALLEN_TREE_FRONT_PATH,Texture.class);
        menu_texture = assmanager.get(Constants.MENU_IMAGE_PATH,Texture.class);
        pause_texture = assmanager.get(Constants.PAUSE_IMAGE_PATH,Texture.class);

        vignette = session.createAnimation(Constants.VIGNETTE_REGION_NAMES,Constants.VIGNETTE_FRAME_DURATION);
        ember = session.createAnimation(Constants.EMBER_REGION_NAMES,Constants.VIGNETTE_FRAME_DURATION);
        grassAnimation = session.createAnimation(Constants.GRASS_REGION_NAMES,Constants.GRASS_FRAME_DURATION);
        birdAnimation = session.createAnimation(Constants.BIRD_REGION_NAMES,Constants.BIRD_FRAME_DURATION);
        groundFallAnimation = session.createAnimation(Constants.GROUND_FALL_REGION_NAMES,Constants.GROUND_FALL_FRAME_DURATION);
        monkeytreeAnimation = session.createAnimation(Constants.MONKEY_TREE_REGION_NAMES,Constants.BIRD_FRAME_DURATION);
        dustFxAnimation = session.createAnimation(Constants.FX_DUST_REGION_NAMES,Constants.FX_DUST_FRAMERATE);
        spearCircleFxAnimation = session.createAnimation(Constants.FX_SPEAR_CIRCLE_REGION_NAMES,Constants.FX_DUST_FRAMERATE);
        spearTrailFxAnimation = session.createAnimation(Constants.FX_SPEAR_TRAIL_REGION_NAMES,Constants.FX_DUST_FRAMERATE);
        swipeUpAnimation = session.createAnimation(Constants.TUTO_SWIPE_UP,Constants.FX_DUST_FRAMERATE);
        swipeDownAnimation = session.createAnimation(Constants.TUTO_SWIPE_DOWN,Constants.FX_DUST_FRAMERATE);
        swipeRightAnimation = null;

        Music menu = assmanager.get(Constants.MENU_MUSIC_PATH, Music.class);
        menu.setLooping(true);
        menu.play();
    }

    private void setUpWorld() {
        world = WorldUtils.createWorld();
        // Let the world now you are handling contacts
        world.setContactListener(this);
        setUpBackground();

        beast_appeared = new ArrayList<>();
        beast_hunted = new ArrayList<>();

        generatePlatform(0, 5, true, true);
        generatePlatform(12*Constants.GROUND_WIDTH,0);
        setUpRunner();
        spear_list = new ArrayList<>();
        front_elems = new ArrayList<>();
        fxs_list = new ArrayList<>();
        setUpScore();
        vignetteTime = 0f;
        shake = new Shake();
    }

    private void setUpBackground() {
        bg = new Background(session.assmanager);
        addActor(bg);
    }

    private void setUpRhino() {
        rhino = new Rhino(WorldUtils.createRhino(world),session);
        addActor(rhino);
        beast_appeared.add("rhino");
    }

    private void setUpDeer() {
        deer = new Deer(WorldUtils.createDeer(world),session);
        addActor(deer);
        beast_appeared.add("deer");
    }

    private void setUpRunner() {
        runner = new Runner(WorldUtils.createRunner(world),session);
        addActor(runner);
    }

    private void setupUICamera() {
        OrthographicCamera camera_ui = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera_ui.position.set(camera_ui.viewportWidth / 2, camera_ui.viewportHeight / 2, 0f);
        camera_ui.update();
        batch_ui = new SpriteBatch();
        batch_ui.setProjectionMatrix(camera_ui.combined);
    }

    private void setupTouchControlAreas() {
        //TODO set up areas for buttons and menus
        Gdx.input.setInputProcessor(this);
    }

    private void setUpScore() {
        Rectangle scoreBounds = new Rectangle(Constants.APP_WIDTH* Constants.TO_PIXEL_RATIO * 47 / 64,
                Constants.APP_HEIGHT* Constants.TO_PIXEL_RATIO * 57 / 64, Constants.APP_WIDTH* Constants.TO_PIXEL_RATIO / 4,
                Constants.APP_HEIGHT* Constants.TO_PIXEL_RATIO / 8);
        score = new Score(scoreBounds,session.assmanager);
        addActor(score);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        if (session.getGameState() == GameState.OVER) {
            batch.begin();
            batch.draw(menu_texture, 0, 0, Constants.APP_WIDTH,
                    camera.viewportHeight);
            batch.end();
            int length = ("Highscore: " + session.getMaxScore()).length();
            batch_ui.begin();
            score.getFont().draw(batch_ui, "Highscore: " + session.getMaxScore(),
                    Gdx.graphics.getWidth()/2f - length  * 48f, Gdx.graphics.getHeight() - 48f);
            batch_ui.end();
        } else if (session.getGameState() == GameState.RUNNING || session.getGameState() == GameState.PAUSED) {
            //TODO PAUSE PHYSIC ?
            batch.begin();
            Array<Body> bodies;
            bodies = new Array<>(world.getBodyCount());
            world.getBodies(bodies);
            bg.updateXBounds(getDelta(),startTime);
            int x=0;
            for (TextureRegion region: bg.textureRegions) {
                if(bg.xs[x] <= Constants.APP_WIDTH) {
                    batch.draw(region, bg.xs[x], 0, Constants.APP_WIDTH,
                            camera.viewportHeight);
                }
                x++;
            }
            batch.draw(hills[bg.current_hill], bg.xs[1], 0, Constants.APP_WIDTH, camera.viewportHeight);
            float furthest_ground = 0f;
            Body rhino_body = null, deer_body = null;
            for (Body body : bodies) {
                if (bodyIsGround(body)) {
                    if (bodyGroundHasSpear(body) && body.getPosition().x - (Constants.RUNNER_X + Constants.RUNNER_WIDTH * 2) <= getMinDistanceToCatch() && body.getPosition().x - (Constants.RUNNER_X + Constants.RUNNER_WIDTH * 2) > 0 && runner.notBusy()) {
                        runner.setCatching(true);
                        runner.catchingGround = body;
                        runner.stateTime = 0f;
                    }
                    float[] front_elem = updateGround(body);
                    if (body.getPosition().x > furthest_ground) {
                        furthest_ground = body.getPosition().x;
                    }
                    if (front_elem != null) {
                        front_elems.add(front_elem);
                    }
                } else if (bodyIsRunner(body)) {
                    runner_body = body;
                } else if (bodyIsRhino(body)) {
                    rhino_body = body;
                } else if (bodyIsDeer(body)) {
                    deer_body = body;
                }
            }
            updateFxs();
            updateRunner(runner_body);
            for (int i = 0; i < front_elems.size(); i++) {
                float[] elem = front_elems.get(i);
                Texture texture;
                if (elem[0] == 1f) {
                    texture = front_dolmen_texture;
                } else {
                    texture = front_fallentree_texture;
                }
                batch.draw(texture, elem[1], elem[2], elem[3], elem[4]);
            }
            front_elems.clear();
            Iterator<Spear> itr = spear_list.iterator();
            while (itr.hasNext()) {
                Spear spear_tmp = itr.next();
                spear_tmp.trailTime = spear_tmp.trailTime + getDelta();
                //TODO ROTATE SPEAR IMAGE
                batch.draw(spear_tmp.texture,
                        spear_tmp.x - Constants.SPEAR_WIDTH/2f,spear_tmp.y - Constants.SPEAR_HEIGHT/2f,

                        Constants.SPEAR_WIDTH, Constants.SPEAR_HEIGHT);
                batch.draw((TextureRegion) spearTrailFxAnimation.getKeyFrame(spear_tmp.trailTime,true),spear_tmp.x - SPEAR_WIDTH/2f,spear_tmp.y - Constants.SPEAR_HEIGHT/2f - SPEAR_WIDTH/2f,SPEAR_WIDTH,SPEAR_WIDTH);
                //TODO MEGA NICE, STUCK THE SPEAR IN EVERY POTENTIAL
                if(rhino != null && !rhino.isFalling() && spear_tmp.x > Constants.RHINO_FINAL_X - Constants.SPEAR_WIDTH) {
                    rhino.setJumping(false);
                    rhino.setFalling(true);
                    beast_hunted.add("rhino");
                    score.setScore(Constants.RHINO_SCORE,1f);
                    rhino.stateTime = 0f;
                    for (Body body : bodies) {
                        if (bodyGroundIsFake(body) && body.getPosition().x > Constants.GROUND_START_FALLING_X)  {
                            UserData ud = (UserData) body.getUserData();
                            ud.setFake(false);
                            if (body.getPosition().x <= Constants.APP_WIDTH) {
                                //if ground is already in screen we fill the fake with an actual ground
                                ud.setNext(false);
                                ground = new Ground(createGround(world, body.getPosition().x + 2*Constants.GROUND_WIDTH,Constants.GROUND_WIDTH), session.assmanager);
                                addActor(ground);
                            }
                        }
                    }
                    spear_list.remove(spear_tmp);
                } else if(deer != null && !deer.isFalling() && deer.frameToY() == 0.5f && spear_tmp.isFlat() && spear_tmp.x > deer.getX() - Constants.SPEAR_WIDTH && spear_tmp.x <= deer.getX() - Constants.SPEAR_WIDTH/2f) {
                    deer.setFalling(true);
                    score.setScore(Constants.DEER_SCORE,1f);
                    deer.stateTime = 0f;
                    spear_list.remove(spear_tmp);
                    beast_hunted.add("deer");
                } else if (spear_tmp.x > Constants.APP_WIDTH) {
                    spear_list.remove(spear_tmp);
                }
            }
            if (rhino_body != null && rhino != null) {
                updateRhino(rhino_body);
            }
            if (deer_body != null && deer != null) {
                updateDeer(deer_body);
            }

            batch.draw(pause_texture,0.5f,camera.viewportHeight - 0.7f , 1f, 0.55f);

            vignetteTime += getDelta();

            batch.draw((TextureRegion) vignette.getKeyFrame(vignetteTime, true), 0, 0, Constants.APP_WIDTH, camera.viewportHeight);
            batch.draw((TextureRegion) ember.getKeyFrame(vignetteTime, true), 0, 0, Constants.APP_WIDTH, camera.viewportHeight);
            int length = ("" + score.getScore()).length();
            batch.end();
            score.setScore(getDelta(),getGroundSpeedMultiplier(startTime));
            if (score.getScore() > Constants.MIN_SCORE_BEAST && rhino == null && deer == null && !waitDeer && !waitRhino && session.assmanager.update()) {
                if (swipeRightAnimation == null) {
                    swipeRightAnimation = session.createAnimation(Constants.TUTO_SWIPE_RIGHT,Constants.FX_DUST_FRAMERATE);
                }
                Random r = new Random();
                if (r.nextInt(2) == 1) {
                    waitDeer = true;
                } else {
                    waitRhino = true;
                }
            }
            batch_ui.begin();
            score.getFont().draw(batch_ui, "" + score.getScore(),
                    Gdx.graphics.getWidth() - length * 48f, Gdx.graphics.getHeight() - 15f);
            score.getFont().draw(batch_ui, Integer.toString(num_spears) + "X spear",
                    Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight() - 15f);
            batch_ui.end();

            //TODO ADAPT OTHER FRAME DURATIONS TO GROUND SPEED IN A UTIL METHOD
            runner.runningAnimation.setFrameDuration(Constants.RUNNER_FRAME_DURATION / getGroundSpeedMultiplier(startTime));

            //uncomment to see debug bodies
            //renderer.render(world, camera.combined);

            if (furthest_ground < Constants.APP_WIDTH) {
                if (waitRhino) {
                    waitRhino = false;
                    setUpRhino();
                    generatePlatform(furthest_ground + 4 * Constants.GROUND_WIDTH, 6, true);
                } else if (waitDeer) {
                    waitDeer = false;
                    setUpDeer();
                    generatePlatform(furthest_ground + 4 * Constants.GROUND_WIDTH, 5, true);
                } else {
                    generatePlatform(furthest_ground + 4 * Constants.GROUND_WIDTH, 0);
                }
            }

            // Fixed timestep
            accumulator += delta;

            while (accumulator >= delta) {
                float TIME_STEP = 1 / 300f;
                world.step(TIME_STEP, 6, 2);
                accumulator -= TIME_STEP;
            }

            shake.update(getDelta(), camera);
            batch.setProjectionMatrix(camera.combined);
        }

    }

    private void generatePlatform(float x, int length) {
        generatePlatform(x, length, false, false);
    }

    private void generatePlatform(float x, int length, boolean virgin) {
        generatePlatform(x, length, virgin, false);
    }

    private void generatePlatform(float x, int length, boolean virgin, boolean tuto) {
        if (length == 0) {
            length = Constants.MAX_PLATFORM_LENGTH;
        }
        int size = 1;
        boolean finish = false;
        //maximum one flock of birds per platform
        boolean bird = false;
        //first ground always empty
        if (!virgin) {
            platformArch.add("V");
        }
        ArrayList<String> obstacles = new ArrayList<>(Arrays.asList("R", "MT"));
        if (session.assmanager.update()) {
            // when smilodon finished loading we can add 'em as obstacles
            obstacles.add("D");
            // the first time we declare their variables
            if (smilodonAnimation == null) {
                back_dolmen_texture = session.assmanager.get(Constants.DOLMEN_BACK_PATH,Texture.class);
                front_dolmen_texture = session.assmanager.get(Constants.DOLMEN_FRONT_PATH,Texture.class);
                smilodonAnimation = session.createAnimation(Constants.SMILODON_REGION_NAMES,Constants.SMILODON_FRAME_DURATION);
            }
        }
        Random random = new Random();
        for (float i = 0; i < length; i++) {
            if (virgin) {
                platformArch.add("V");
            } else {
                if (!finish) {
                    size++;
                    if (size > Constants.MAX_PLATFORM_LENGTH) {
                        finish = true;
                    }
                    //algo to determine platform content
                    boolean addElement = random.nextInt(9) > 2;
                    if (i % 2 == 0) {
                        //add deco
                        switch ( bird ? random.nextInt(3) : random.nextInt(4)) {
                            case 0:
                                platformArch.add("G");
                                break;
                            case 1:
                                platformArch.add("T");
                                break;
                            case 2:
                                platformArch.add("GT");
                            case 3:
                                bird = true;
                                platformArch.add("B");
                                break;

                        }
                    } else if ((i + 1) % 2 == 0) {
                        if (addElement && obstacles.size() > 0) {
                            String obstacle;
                            if (obstacles.size() == 1) {
                                obstacle = obstacles.get(0);
                            } else {
                                obstacle = obstacles.get(random.nextInt(obstacles.size() - 1));
                            }
                            platformArch.add(obstacle);
                            if (obstacle == "MT") {
                                platformArch.add("V");
                            }
                            obstacles.remove(obstacle);
                        } else finish = true;
                    }
                }
            }
        }

        if (!virgin) {
            //squash empty grounds
            while (platformArch.size() > Constants.MIN_PLATFORM_LENGTH && platformArch.get(platformArch.size() - 1).equals("V")) {
                platformArch.remove(platformArch.size()-1);
            }
            if(random.nextInt(2) == 0) {
                platformArch.add("S");
                platformArch.add("V");
            }

            platformArch.add("V");
        }
        Ground previous_ground = null;
        for (float i = 0; i < platformArch.size(); i++) {
            ground = new Ground(createGround(world, x + i*Constants.GROUND_WIDTH*2,Constants.GROUND_WIDTH),session.assmanager);
            if (virgin && i==0 && beast_appeared.size() == 1) {
                ground.setTutoSwipeRight(true);
            }
            switch (platformArch.get((int)i)) {
                case "G":
                    ground.setGrass(true);
                    break;
                case "T":
                    ground.setTree(true);
                    break;
                case "B":
                    ground.setBird(true);
                    break;
                case "R":
                    ground.setRock(true);
                    break;
                case "GT":
                    ground.setGrass(true);
                    ground.setTree(true);
                    break;
                case "MT":
                    if (!firstDownObstacle) {
                        firstDownObstacle = true;
                        ground.setTutoSwipeDown(true);
                        previous_ground.setTree(false);
                    }
                    if (random.nextInt(2) == 1) {
                        ground.setGrass(true);
                    }
                    ground.setMonkeytree(true);
                    break;
                case "FT":
                    ground.setFallentree(true);
                    break;
                case "D":
                    if (!firstDownObstacle) {
                        firstDownObstacle = true;
                        ground.setTutoSwipeDown(true);
                        previous_ground.setTree(false);
                    }
                    ground.setDolmen(true);
                    break;
                case "S":
                    ground.setSpear(true);
                    break;
                default:
                    break;
            }
            addActor(ground);
            previous_ground = ground;
        }
        ground.setNext(true);
        if (tuto) {
            ground.setTutoSwipeUp(true);
        }
        if (rhino != null && !rhino.isFalling()) {
            ground.setFake(true);
        }
        platformArch.clear();
    }

    private void updateFxs() {
        for (float[] fx : fxs_list) {
            fx[1] += getDelta();
            switch ((int) fx[0]) {
                case 1:
                    fx[2] -= getGroundSpeed(startTime, getDelta());
                    batch.draw((TextureRegion) dustFxAnimation.getKeyFrame(fx[1], false),fx[2],Constants.RUNNER_Y - 0.6f,0.8f,0.4f);
                    break;
                case 2:
                    fx[2] -= getGroundSpeed(startTime, getDelta());
                    TextureRegion dust = (TextureRegion) dustFxAnimation.getKeyFrame(fx[1], false);
                    batch.draw(dust, fx[2],Constants.RUNNER_Y - 0.6f,1.2f,0.6f);
                    batch.draw(dust,fx[2] + 3*Constants.RHINO_WIDTH/2f,Constants.RUNNER_Y - 0.6f,-1.2f,0.6f);
                    break;
                case 3:
                    batch.draw((TextureRegion) spearCircleFxAnimation.getKeyFrame(fx[1], false),fx[2],Constants.RUNNER_Y + Constants.RUNNER_HEIGHT - 0.5f,0.8f,0.8f);
                    break;

            }
        }
    }

    private void updateRunner(Body body) {
        if (body.getPosition().y < -2 * Constants.RUNNER_HEIGHT) {
            finishGame();
        }
        body.applyForceToCenter(Constants.WORLD_GRAVITY, true);
        // Running
        if (!runner.isJumping() || runner.getAnimationToFinish() || runner.jumpingAnimation.getKeyFrameIndex(runner.stateTime) != 23) {
            runner.stateTime += getDelta();
        }

        if (runner.jumpProcessStarted()) {
            if (runner.isJumpFail()) {
                batch.draw((TextureRegion) runner.jumpFailAnimation.getKeyFrame(runner.stateTime, true), body.getPosition().x - Constants.RUNNER_WIDTH * 2, body.getPosition().y - Constants.RUNNER_HEIGHT - Constants.GROUND_TO_RUNNER, Constants.RUNNER_WIDTH * 4, Constants.RUNNER_HEIGHT * 2);
                if (runner.jumpFailAnimation.getKeyFrameIndex(runner.stateTime) >= 45) {
                    runner.setJumpFail(false);
                    runner.stateTime = 0f;
                    checkActionQueue();
                }
            } else {
                if (runner.isInitiateJump()) {
                    //TODO CREATE FX OBJECT
                    batch.draw((TextureRegion) runner.jumpingAnimation.getKeyFrame(runner.stateTime, true), body.getPosition().x - Constants.RUNNER_WIDTH * 2, body.getPosition().y - Constants.RUNNER_HEIGHT - Constants.GROUND_TO_RUNNER, Constants.RUNNER_WIDTH * 4, Constants.RUNNER_HEIGHT * 2);
                    if (runner.jumpingAnimation.getKeyFrameIndex(runner.stateTime) > Constants.JUMP_IMPULSION_FRAMES) {
                        runner.jump();
                        fxs_list.add(new float[]{1f, 0f, Constants.RUNNER_X - Constants.RUNNER_WIDTH});
                    }
                } else if (runner.isJumping()) {
                    batch.draw((TextureRegion) runner.jumpingAnimation.getKeyFrame(runner.stateTime, true), body.getPosition().x - Constants.RUNNER_WIDTH * 2, body.getPosition().y - Constants.RUNNER_HEIGHT - Constants.GROUND_TO_RUNNER, Constants.RUNNER_WIDTH * 4, Constants.RUNNER_HEIGHT * 2);
                    if (runner.jumpingAnimation.getKeyFrameIndex(runner.stateTime) >= 30) {
                        runner.setAnimationToFinish(false);
                        runner.setJumping(false);
                        runner.stateTime = 0f;
                        checkActionQueue();
                    }
                }
            }
        } else if (runner.isFalling() != 0) {
            switch (runner.isFalling()) {
                case 1:
                    batch.draw((TextureRegion) runner.rockFallAnimation.getKeyFrame(runner.stateTime, false), body.getPosition().x - Constants.RUNNER_WIDTH * 2, body.getPosition().y - Constants.RUNNER_HEIGHT - Constants.GROUND_TO_RUNNER, Constants.RUNNER_WIDTH * 4, Constants.RUNNER_HEIGHT * 2);
                    if (runner.rockFallAnimation.isAnimationFinished(runner.stateTime)) {
                        finishGame();
                    }
                    break;
                case 2:
                    batch.draw((TextureRegion) runner.failUpAnimation.getKeyFrame(runner.stateTime, false), body.getPosition().x - Constants.RUNNER_WIDTH * 2, body.getPosition().y - Constants.RUNNER_HEIGHT - Constants.GROUND_TO_RUNNER, Constants.RUNNER_WIDTH * 4, Constants.RUNNER_HEIGHT * 2);
                    if (runner.failUpAnimation.isAnimationFinished(runner.stateTime)) {
                        finishGame();
                    }
                    break;
            }

        } else if (runner.isWaitToParkour() && runner.getNextRock().getPosition().x - Constants.GROUND_WIDTH - (Constants.RUNNER_X + Constants.RUNNER_WIDTH * 2) <= Constants.DELTA_MIN_ENGAGE_PARKOUR) {
            runner.setParkour(true);
            runner.setWaitToParkour(false, null);
            runner.stateTime = 0f;
        } else if (runner.isParkour()) {
            batch.draw((TextureRegion) runner.parkourAnimation.getKeyFrame(runner.stateTime, false), body.getPosition().x - Constants.RUNNER_WIDTH * 2, body.getPosition().y - Constants.RUNNER_HEIGHT - Constants.GROUND_TO_RUNNER, Constants.RUNNER_WIDTH * 4, Constants.RUNNER_HEIGHT * 2);
            if (runner.parkourAnimation.isAnimationFinished(runner.stateTime)) {
                runner.setParkour(false);
                runner.stateTime = 0f;
                checkActionQueue();
            }
        } else if (runner.isDodging() != 0){
            switch(runner.isDodging()) {
                case 1:
                    batch.draw((TextureRegion) runner.dodgeINAnimation.getKeyFrame(runner.stateTime, false), body.getPosition().x - Constants.RUNNER_WIDTH * 2, body.getPosition().y - Constants.RUNNER_WIDTH - Constants.GROUND_TO_RUNNER, Constants.RUNNER_WIDTH * 4, Constants.RUNNER_HEIGHT * 2);
                    if (runner.dodgeINAnimation.isAnimationFinished(runner.stateTime)) {
                        if (!runner.getRedodge()) {
                            runner.setDodging(2);
                        } else {
                            runner.setDodging(3);
                        }
                        runner.stateTime = 0f;
                    }
                    break;
                case 2:
                    batch.draw((TextureRegion) runner.dodgeOUTAnimation.getKeyFrame(runner.stateTime, false), body.getPosition().x - Constants.RUNNER_WIDTH * 2, body.getPosition().y - Constants.RUNNER_WIDTH - Constants.GROUND_TO_RUNNER, Constants.RUNNER_WIDTH * 4, Constants.RUNNER_HEIGHT * 2);
                    if (runner.dodgeOUTAnimation.isAnimationFinished(runner.stateTime)) {
                        runner.stopDodge();
                        runner.stateTime = 0f;
                        checkActionQueue();
                    }
                    break;
                case 3:
                    batch.draw((TextureRegion) runner.dodgeBISAnimation.getKeyFrame(runner.stateTime, false), body.getPosition().x - Constants.RUNNER_WIDTH * 2, body.getPosition().y - Constants.RUNNER_WIDTH - Constants.GROUND_TO_RUNNER, Constants.RUNNER_WIDTH * 4, Constants.RUNNER_HEIGHT * 2);
                    if (runner.dodgeBISAnimation.isAnimationFinished(runner.stateTime)) {
                        if (!runner.getRedodge()) {
                            runner.setDodging(2);
                        }
                        runner.stateTime = 0f;
                    }
                    break;
            }
        } else if(runner.isThrowing()) {
            batch.draw((TextureRegion) runner.throwSpearAnimation.getKeyFrame(runner.stateTime, false), body.getPosition().x - Constants.RUNNER_WIDTH * 2, body.getPosition().y - Constants.RUNNER_HEIGHT- Constants.GROUND_TO_RUNNER, Constants.RUNNER_WIDTH * 4, Constants.RUNNER_HEIGHT * 2);
            if (runner.throwSpearAnimation.getKeyFrameIndex(runner.stateTime) == 11 && spear_list.isEmpty() && spear == null) {
                spear = new Spear(session, getTarget());
                fxs_list.add(new float[]{3f, 0f, Constants.RUNNER_X + Constants.RUNNER_WIDTH});
                spear_list.add(spear);
                addActor(spear);
            }
            if (runner.throwSpearAnimation.isAnimationFinished(runner.stateTime)) {
                runner.setThrowing(false);
                runner.stateTime = 0f;
                spear = null;
            }
        } else if(runner.isCatching()) {
            batch.draw((TextureRegion) runner.catchSpearAnimation.getKeyFrame(runner.stateTime, false), body.getPosition().x - Constants.RUNNER_WIDTH * 2, body.getPosition().y - Constants.RUNNER_HEIGHT- Constants.GROUND_TO_RUNNER, Constants.RUNNER_WIDTH * 4, Constants.RUNNER_HEIGHT * 2);
            if (runner.catchSpearAnimation.getKeyFrameIndex(runner.stateTime) == 4 && runner.catchingGround != null) {
                UserData ud = (UserData) runner.catchingGround.getUserData();
                ud.setSpear(false);
            }

            if (runner.catchSpearAnimation.isAnimationFinished(runner.stateTime)) {
                runner.setCatching(false);
                runner.stateTime = 0f;
                num_spears++;
                runner.catchingGround = null;
                checkActionQueue();
            }
        } else {
            batch.draw((TextureRegion) runner.runningAnimation.getKeyFrame(runner.stateTime, true), body.getPosition().x - Constants.RUNNER_WIDTH * 2, body.getPosition().y - Constants.RUNNER_HEIGHT- Constants.GROUND_TO_RUNNER, Constants.RUNNER_WIDTH * 4, Constants.RUNNER_HEIGHT * 2);
        }
    }

    private float[] getTarget() {
        float flat_target = 1.8f;
        float flat_angle = 10f;
        //TODO IMPROVE SPEAR TARGETING HEURISTIC
        if (rhino != null && rhino.arrived()) {
            // rhino is a static target so no heuristic
            return new float[] {flat_target,flat_angle};
        } else if (deer != null && deer.arrived) {
            float delta_y = deer.frameToY() - Constants.RUNNER_Y;
            float delta_x = deer.getX() - Constants.RUNNER_X;
            float angle = (float) Math.toDegrees(Math.atan(delta_y/delta_x));
            return new float[] {flat_target+delta_y, angle};
        } else {
            //throw it to the ground
            return new float[] {0.1f,5f};
        }
    }

    private void updateRhino(Body body) {
        rhino.stateTime += getDelta();
        if (rhino.isJumping()) {
            batch.draw((TextureRegion) rhino.jumpingAnimation.getKeyFrame(rhino.stateTime, false), body.getPosition().x - Constants.RHINO_WIDTH, body.getPosition().y - Constants.RHINO_HEIGHT- Constants.GROUND_TO_RUNNER/2, Constants.RHINO_WIDTH * 2, Constants.RHINO_HEIGHT * 2);
            if (rhino.jumpingAnimation.getKeyFrameIndex(rhino.stateTime) == 10 && !rhino.landfx) {
                fxs_list.add(new float[]{2f, 0f, Constants.RHINO_FINAL_X - Constants.RHINO_WIDTH/2f});
                rhino.landfx = true;
                shake.shake(0.3f);
            }
            if (rhino.jumpingAnimation.isAnimationFinished(rhino.stateTime)) {
                rhino.stateTime = 0f;
                rhino.setJumping(false);
                rhino.landfx = false;
            }
        } else if (rhino.isFalling()){
            rhino.alpha -= Constants.TRANSPARENCY_DELTA;
            batch.setColor(1,1,1,rhino.alpha);
            batch.draw((TextureRegion) rhino.FallAnimation.getKeyFrame(rhino.stateTime, false), body.getPosition().x - Constants.RHINO_WIDTH, body.getPosition().y - Constants.RHINO_HEIGHT- Constants.GROUND_TO_RUNNER/2, Constants.RHINO_WIDTH * 2, Constants.RHINO_HEIGHT * 2);
            batch.setColor(1,1,1,1);
            body.setTransform(body.getPosition().x - getGroundSpeed(startTime,getDelta()), body.getPosition().y,0);
            rhino.setX(rhino.getX() - getGroundSpeed(startTime, getDelta()));

            if (body.getPosition().x < -Constants.APP_WIDTH) {
                world.destroyBody(body);
                rhino.remove();
                rhino = null;
            }
        } else {
            if (!rhino.arrived()) {
                body.setTransform(body.getPosition().x - getGroundSpeedMultiplier(startTime)*getDelta()*Constants.RHINO_VELOCITY, body.getPosition().y,0);
                rhino.setX(rhino.getX() - getGroundSpeedMultiplier(startTime)*getDelta()*Constants.RHINO_VELOCITY);
            }
            batch.draw((TextureRegion) rhino.runningAnimation.getKeyFrame(rhino.stateTime, true), body.getPosition().x - Constants.RHINO_WIDTH, body.getPosition().y - Constants.RHINO_HEIGHT- Constants.GROUND_TO_RUNNER/2, Constants.RHINO_WIDTH * 2, Constants.RHINO_HEIGHT * 2);
        }
    }

    private void updateDeer(Body body) {
        if (deer.arrived ) {
            deer.stateTime += getDelta();
        }
        if (deer.isFalling()) {
            deer.alpha -= Constants.TRANSPARENCY_DELTA;
            batch.setColor(1,1,1,deer.alpha);
            batch.draw((TextureRegion) deer.fallingAnimation.getKeyFrame(deer.stateTime, false), body.getPosition().x - Constants.DEER_WIDTH, body.getPosition().y - Constants.DEER_HEIGHT- Constants.GROUND_TO_RUNNER/2, Constants.DEER_WIDTH * 2, Constants.DEER_HEIGHT * 2);
            batch.setColor(1,1,1,1);
            body.setTransform(body.getPosition().x - getGroundSpeed(startTime,getDelta()), body.getPosition().y,0);
            deer.setX(deer.getX() - getGroundSpeed(startTime,getDelta()));


            if (body.getPosition().x < -Constants.APP_WIDTH) {
                world.destroyBody(body);
                deer.remove();
                deer = null;
            }
        } else if (deer.jumpProcessStarted()) {
            batch.draw((TextureRegion) deer.jumpingAnimation.getKeyFrame(deer.stateTime, false), body.getPosition().x - Constants.DEER_WIDTH, body.getPosition().y - Constants.DEER_HEIGHT- Constants.GROUND_TO_RUNNER/2, Constants.DEER_WIDTH * 2, Constants.DEER_HEIGHT * 3.2f);
            if (deer.jumpingAnimation.isAnimationFinished(deer.stateTime)) {
                deer.stateTime = 0f;
                deer.setJumping(false);
            }
        } else if (deer.isStarting()) {
            if(body.getPosition().x < Constants.DEER_START_X && !deer.arrived) {
                deer.arrived = true;
            }
            if (!deer.arrived) {
                body.setTransform(body.getPosition().x - getGroundSpeed(startTime,getDelta()), body.getPosition().y,0);
                deer.setX(deer.getX() - getGroundSpeed(startTime,getDelta()));
            }
            batch.draw((TextureRegion) deer.startAnimation.getKeyFrame(deer.stateTime, false), body.getPosition().x - Constants.DEER_WIDTH, body.getPosition().y - Constants.DEER_HEIGHT- Constants.GROUND_TO_RUNNER/2, Constants.DEER_WIDTH * 2, Constants.DEER_HEIGHT * 3.2f);
            if (deer.startAnimation.isAnimationFinished(deer.stateTime)) {
                deer.stateTime = 0f;
                deer.setStarting(false);
            }
        } else {
            batch.draw((TextureRegion) deer.runningAnimation.getKeyFrame(deer.stateTime, true), body.getPosition().x - Constants.DEER_WIDTH, body.getPosition().y - Constants.DEER_HEIGHT- Constants.GROUND_TO_RUNNER/2, Constants.DEER_WIDTH * 2, Constants.DEER_HEIGHT * 2);
        }

        if (deer != null && deer.isLeaving()) {
            body.setTransform(body.getPosition().x + getGroundSpeedMultiplier(startTime)*getDelta(), body.getPosition().y,0);
            deer.setX(deer.getX() + getGroundSpeedMultiplier(startTime)*getDelta());
            if (body.getPosition().x > Constants.APP_WIDTH + Constants.DEER_WIDTH) {
                world.destroyBody(body);
                deer.remove();
                deer = null;
                beast_hunted.add("nope");
            }
        }
    }

    private float[] updateGround(Body body) {
        UserData userData = (UserData) body.getUserData();
        body.setTransform(body.getPosition().x - getGroundSpeed(startTime,getDelta()), body.getPosition().y,0);
        if (body.getPosition().x - 2*Constants.GROUND_WIDTH > Constants.APP_WIDTH) {
            return null;
        }
        batch.draw(ground_texture, body.getPosition().x-Constants.GROUND_WIDTH, body.getPosition().y - Constants.GROUND_HEIGHT/2, Constants.GROUND_WIDTH*2,
                Constants.GROUND_HEIGHT*3/2);
        float[] front_elem = null;
        if (bodyGroundHasSpear(body)) {
            batch.draw(planted_spear, body.getPosition().x, body.getPosition().y + Constants.GROUND_HEIGHT, Constants.RUNNER_WIDTH,
                    Constants.RUNNER_HEIGHT);
        }
        if (bodyGroundHasBird(body)) {
            batch.draw((TextureRegion) birdAnimation.getKeyFrame(userData.getBirdTime(), false),body.getPosition().x + 4*Constants.GROUND_WIDTH - Constants.APP_WIDTH/2f,body.getPosition().y+Constants.GROUND_HEIGHT*3/4,Constants.APP_WIDTH/2f,Constants.APP_HEIGHT/2);

            if (userData.getBirdActivated()) {
                userData.setBirdTime(userData.getBirdTime() + getDelta());
            }
            if (!userData.getBirdActivated() && body.getPosition().x <= Constants.APP_WIDTH) {
                userData.setBirdActivated(true);
            }
        }
        if (userData.isTutoSwipeDown()) {
            batch.draw((TextureRegion) swipeDownAnimation.getKeyFrame(vignetteTime, true), body.getPosition().x - 2*Constants.GROUND_WIDTH, body.getPosition().y + 2*Constants.GROUND_HEIGHT, 2*Constants.GROUND_WIDTH, 2*Constants.GROUND_HEIGHT);
        } else if (userData.isTutoSwipeRight()) {
            batch.draw((TextureRegion) swipeRightAnimation.getKeyFrame(vignetteTime, true), body.getPosition().x, body.getPosition().y + 2*Constants.GROUND_HEIGHT, 2*Constants.GROUND_WIDTH, 2*Constants.GROUND_HEIGHT);
        }
        if (bodyGroundHasNext(body)) {
            if (userData.isTutoSwipeUp()) {
                batch.draw((TextureRegion) swipeUpAnimation.getKeyFrame(vignetteTime, true), body.getPosition().x - Constants.GROUND_WIDTH, body.getPosition().y + 2*Constants.GROUND_HEIGHT, 2*Constants.GROUND_WIDTH, 2*Constants.GROUND_HEIGHT);
            }
            if (bodyGroundIsFake(body)) {
                if (body.getPosition().x > Constants.GROUND_START_FALLING_X) {
                    batch.draw(ground_texture, body.getPosition().x+Constants.GROUND_WIDTH, body.getPosition().y - Constants.GROUND_HEIGHT/2, 0.095f + Constants.GROUND_WIDTH*2,
                            Constants.GROUND_HEIGHT*3/2);
                    if (rhino != null && !rhino.isJumping() && !rhino.isFalling() && body.getPosition().x < Constants.APP_WIDTH*11/16f) {
                        //TODO ground fissure first than rapidly crumbles
                        rhino.stateTime = 0f;
                        rhino.setJumping(true);
                    }
                } else {
                    userData.setFallTime(userData.getFallTime() + getDelta());
                    batch.draw((TextureRegion) groundFallAnimation.getKeyFrame(userData.getFallTime(), false), body.getPosition().x+Constants.GROUND_WIDTH, body.getPosition().y - Constants.GROUND_HEIGHT/2, 0.095f + Constants.GROUND_WIDTH*2,
                            Constants.GROUND_HEIGHT*3/2);
                }
            } else {
                batch.draw(hole_texture, body.getPosition().x+Constants.GROUND_WIDTH, body.getPosition().y - Constants.GROUND_HEIGHT/2, 0.095f + Constants.GROUND_WIDTH*2,
                        Constants.GROUND_HEIGHT*3/2);
            }
            if (deer != null && !deer.isStarting() && !deer.jumpProcessStarted() && !deer.isFalling() && body.getPosition().x <= deer.getX() + 1.0f &&  body.getPosition().x > deer.getX()) {
                deer.setJumping(true);
                deer.stateTime = 0f;
            }
        }
        if (bodyGroundHasTree(body)) {
            batch.draw(trees[userData.getTreeNumber()], body.getPosition().x,body.getPosition().y + Constants.GROUND_HEIGHT - Constants.GRASS_BELOW_GROUND,Constants.GROUND_WIDTH*2,Constants.GROUND_HEIGHT*3);
        }
        if (bodyGroundHasGrass(body)) {
            float grassBegin = body.getPosition().x-Constants.GROUND_WIDTH*5/4;
            if (grassBegin <= Constants.RUNNER_X + Constants.RUNNER_WIDTH && !userData.getGrassActivated()) {
                if (!runner.isJumping()) {
                    userData.setGrassTime(userData.getGrassTime() + getDelta());
                }
                userData.setGrassActivated(true);
            } else {
                if (userData.getGrassActivated() && userData.getGrassTime() != 0) {
                    if (grassAnimation.getKeyFrameIndex(userData.getGrassTime()) != 19) {
                        userData.setGrassTime(userData.getGrassTime() + getDelta());
                    } else {
                        userData.setGrassTime(0);
                    }
                }
            }
            batch.draw((TextureRegion) grassAnimation.getKeyFrame(userData.getGrassTime(), true), grassBegin,body.getPosition().y + Constants.GROUND_HEIGHT - Constants.GRASS_BELOW_GROUND,Constants.GROUND_WIDTH*3/2,Constants.GROUND_HEIGHT);
        }
        if (bodyGroundHasRock(body)) {
            if ((body.getPosition().x - Constants.GROUND_WIDTH) - (Constants.RUNNER_X + Constants.RUNNER_WIDTH*2) <= Constants.DELTA_MIN_ENGAGE_PARKOUR && (body.getPosition().x - Constants.GROUND_WIDTH) - (Constants.RUNNER_X + Constants.RUNNER_WIDTH*2) >= Constants.DELTA_MIN_ENGAGE_PARKOUR - 0.1 && !runner.isParkour() && !runner.isWaitToParkour() && !runner.jumpProcessStarted()) {
                runner.setFalling(1);
                runner.stateTime = 0f;
            }
            batch.draw(rock_texture, body.getPosition().x-Constants.GROUND_WIDTH*1/2, body.getPosition().y + Constants.GROUND_HEIGHT*9/10, Constants.GROUND_WIDTH,
                    Constants.GROUND_HEIGHT);
        } else {
            boolean need_dodge = false;
            if (bodyGroundHasMonkeyTree(body)) {
                need_dodge = true;
                batch.draw((TextureRegion) monkeytreeAnimation.getKeyFrame(userData.getMonkeyTime(), false), body.getPosition().x,body.getPosition().y + Constants.GROUND_HEIGHT - Constants.GRASS_BELOW_GROUND,Constants.GROUND_WIDTH*2.5f,Constants.GROUND_HEIGHT*3.5f);
                if (body.getPosition().x < Constants.ACTIVATE_MONKEY) {
                    userData.setMonkeyTime(userData.getMonkeyTime() + getDelta());
                }
            } else if (bodyGroundHasFallenTree(body)) {
                need_dodge = true;
                batch.draw(back_fallentree_texture, body.getPosition().x - Constants.GROUND_WIDTH,body.getPosition().y + Constants.GROUND_HEIGHT - 0.1f,Constants.GROUND_WIDTH*2,3*Constants.RUNNER_HEIGHT);
                front_elem = new float[]{2f, body.getPosition().x - Constants.GROUND_WIDTH,body.getPosition().y + Constants.GROUND_HEIGHT - 0.1f, Constants.GROUND_WIDTH*2, 3*Constants.RUNNER_HEIGHT};
            } else if (bodyGroundHasDolmen(body)) {
                need_dodge = true;
                batch.draw(back_dolmen_texture, body.getPosition().x - Constants.GROUND_WIDTH,body.getPosition().y + Constants.GROUND_HEIGHT - 0.1f,Constants.GROUND_WIDTH*2,2*Constants.RUNNER_HEIGHT);
                batch.draw((TextureRegion) smilodonAnimation.getKeyFrame(userData.getSmilodonTime(), false), body.getPosition().x - Constants.GROUND_WIDTH - 0.1f,body.getPosition().y + Constants.GROUND_HEIGHT - 0.1f,Constants.GROUND_WIDTH*2,5f*Constants.RUNNER_HEIGHT);
                if (body.getPosition().x < Constants.ACTIVATE_SMILODON && runner.jumpProcessStarted()) {
                    userData.setSmilodonTime(userData.getSmilodonTime() + getDelta());
                }
                front_elem = new float[]{1f, body.getPosition().x - Constants.GROUND_WIDTH,body.getPosition().y + Constants.GROUND_HEIGHT - 0.1f, Constants.GROUND_WIDTH*2, 2*Constants.RUNNER_HEIGHT};
            }
            if (need_dodge && runner.isFalling() == 0 && (body.getPosition().x + Constants.GROUND_WIDTH/2) - (Constants.RUNNER_X + Constants.RUNNER_WIDTH*2) <= 0 && (body.getPosition().x + Constants.GROUND_WIDTH) - (Constants.RUNNER_X - Constants.RUNNER_WIDTH) >= 0 && runner.isDodging() == 0) {
                runner.setFalling(2);
                runner.stateTime = 0f;
            }
        }
        if (body.getPosition().x < -4*Constants.GROUND_WIDTH) {
            world.destroyBody(body);
        }
        return front_elem;
    }

    @Override
    public void draw() {
        super.draw();
    }

    public void finishGame() {
        //TODO CREATE SCREEN MENU
        int final_score = score.getScore();
        session.saveScore(final_score);
        this.clear();
        this.dispose();
        game.setScreen(new ScoreScreen(session.assmanager,game,beast_appeared,beast_hunted,final_score));
    }

    @Override
    public boolean touchDown(int x, int y, int pointer, int button) {
        tempx = x;
        tempy = y;
        if (!runner.notBusy()) {
            canQueue = true;
        }
        return super.touchDown(x, y, pointer, button);
    }

    @Override
    public boolean touchDragged(int x, int y, int pointer) {
        int dist_x = Math.abs(x-tempx);
        int dir_x=0;
        if(dist_x != 0) {
            dir_x = dist_x/(x-tempx);
        }

        int dist_y = Math.abs(y-tempy);
        int dir_y=0;
        if(dist_y != 0) {
            dir_y = dist_y/(y-tempy);
        }
        if (session.getGameState() == GameState.RUNNING) {
            if (dist_y >= Constants.MIN_Y_SWIPE_DIST && dir_y == 1) {
                //implement dodge roll
                if (runner.notBusy()) {
                    runner.setRedodge(true);
                    actionDown();
                } else if (canQueue && actionQueue == null && !runner.isJumpFail()) {
                    actionQueue = "down";
                }
            } else if (dist_x >= Constants.MIN_X_SWIPE_DIST && dir_x == 1) {
                if (runner.notBusy()) {
                    actionSide();
                } else if (canQueue && actionQueue == null && !runner.isJumpFail()) {
                    actionQueue = "side";
                }
            } else if (dist_y >= Constants.MIN_Y_SWIPE_DIST && dir_y == -1){
                if (runner.notBusy()) {
                    actionUp();
                } else if (canQueue && actionQueue == null && !runner.isJumpFail()) {
                    actionQueue = "up";
                }
            }
        }
        return super.touchDragged(x, y, pointer);
    }

    @Override
    public boolean touchUp(int x, int y, int pointer, int button) {
        // Implement touch
        if (session.getGameState() == GameState.PAUSED) {
            session.setGameState(GameState.RUNNING);
        } else if (session.getGameState() == GameState.OVER) {
            session.setGameState(GameState.RUNNING);
            startTime = System.currentTimeMillis();
        } else if (session.getGameState() == GameState.RUNNING) {
            canQueue = false;
            if (runner.getRedodge()) {
                runner.setRedodge(false);
            }
            if (x < 1.5f * Gdx.graphics.getWidth() / camera.viewportWidth && y < 1.5f*Gdx.graphics.getHeight() / camera.viewportHeight) {
                session.setGameState(GameState.PAUSED);
                actionQueue = null;
            }
        }
        return super.touchDown(x, y, pointer, button);
    }

    public void actionUp() {
        Array<Body> bodies = new Array<Body>(world.getBodyCount());
        world.getBodies(bodies);
        boolean can_act = false;
        boolean jump_fail = false;
        Body next_rock = null;
        for (Body body : bodies) {
            if (bodyIsGround(body) && body.getPosition().x - Constants.GROUND_WIDTH <= Constants.RUNNER_X && body.getPosition().x + Constants.GROUND_WIDTH >= Constants.RUNNER_X) {
                can_act = true;
            }
            if (bodyIsGround(body) && bodyGroundHasNext(body) && (body.getPosition().x + Constants.GROUND_WIDTH) - (Constants.RUNNER_X + Constants.RUNNER_WIDTH * 2) < getMinDistanceToJump() && (body.getPosition().x + Constants.GROUND_WIDTH) - (Constants.RUNNER_X + Constants.RUNNER_WIDTH * 2) > Constants.DELTA_MIN_JUMP_FAIL) {
                jump_fail = true;
            }
            if (bodyIsGround(body) && bodyGroundHasRock(body) && (body.getPosition().x - Constants.GROUND_WIDTH) - (Constants.RUNNER_X + Constants.RUNNER_WIDTH * 2) < Constants.DELTA_MAX_ENGAGE_PARKOUR && (body.getPosition().x - Constants.GROUND_WIDTH) - (Constants.RUNNER_X + Constants.RUNNER_WIDTH * 2) >= Constants.DELTA_MIN_ENGAGE_PARKOUR) {
                next_rock = body;
            }
        }

        if (can_act) {
            if (next_rock != null) {
                runner.setWaitToParkour(true, next_rock);
            } else {
                if (jump_fail) {
                    runner.setJumpFail(true);
                    runner.stateTime = 0f;
                } else {
                    runner.setInitiateJump(true);
                    runner.stateTime = 0f;
                }
            }
        }
    }

    public void actionDown() {
        runner.dodge();
    }

    public void actionSide() {
        if (rhino == null || rhino.arrived()) {
            if (num_spears > 0) {
                runner.setThrowing(true);
                runner.stateTime = 0f;
                num_spears --;
            } else {
                //TODO ERROR EFFECT SOUND AND ZOOM OR SHAKE ON SPEAR TEXT
            }

        }
    }

    public void checkActionQueue() {
        if (actionQueue != null) {
            switch(actionQueue) {
                case "up":
                    actionUp();
                    actionQueue = null;
                    break;
                case "down":
                    actionDown();
                    actionQueue = null;
                    break;
                case "side":
                    actionSide();
                    actionQueue = null;
            }
            canQueue = false;
        }
    }

    @Override
    public void beginContact(Contact contact) {

        Body a = contact.getFixtureA().getBody();
        Body b = contact.getFixtureB().getBody();

        if ((bodyIsRunner(a) && bodyIsGround(b)) ||
                (bodyIsGround(a) && bodyIsRunner(b))) {
            runner.landed();
        }
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

    public float getDelta() {
        if (session.getGameState() == GameState.PAUSED) {
            return 0f;
        } else {
            return Gdx.graphics.getDeltaTime();
        }
    }

    public float getMinDistanceToJump() {
        return Constants.getGroundSpeed(startTime,Constants.RUNNER_FRAME_DURATION);
    }

    public float getMinDistanceToCatch() {
        return Constants.getGroundSpeed(startTime,Constants.RUNNER_FRAME_DURATION*3);
    }

    public void queueInGameAssets() {
        for (String path : Constants.TUTO_SWIPE_RIGHT) {
            session.assmanager.load(path, Texture.class);
        }
        for (String path : Constants.RHINO_RUN_REGION_NAMES) {
            session.assmanager.load(path, Texture.class);
        }
        for (String path : Constants.RHINO_JUMP_REGION_NAMES) {
            session.assmanager.load(path, Texture.class);
        }
        for (String path : Constants.RHINO_FALL_REGION_NAMES) {
            session.assmanager.load(path, Texture.class);
        }
        for (String path : Constants.DEER_START_REGION_NAMES) {
            session.assmanager.load(path, Texture.class);
        }
        for (String path : Constants.DEER_RUN_REGION_NAMES) {
            session.assmanager.load(path, Texture.class);
        }
        for (String path : Constants.DEER_JUMP_REGION_NAMES) {
            session.assmanager.load(path, Texture.class);
        }
        for (String path : Constants.DEER_FALL_REGION_NAMES) {
            session.assmanager.load(path, Texture.class);
        }
        //As they only go together they load at the same time
        for (String path : Constants.SMILODON_REGION_NAMES) {
            session.assmanager.load(path, Texture.class);
        }
        session.assmanager.load(Constants.DOLMEN_FRONT_PATH, Texture.class);
        session.assmanager.load(Constants.DOLMEN_BACK_PATH, Texture.class);
    }

}