package com.gametomax.paleotale.utils;

import com.badlogic.gdx.math.Vector2;

public class Constants {

    // WORLD CONSTANTS

    public static final int APP_WIDTH = 20;
    public static final float APP_HEIGHT = 11.2f;
    public static final float GRAVITY_INTENSITY = 10f;
    public static final Vector2 WORLD_GRAVITY = new Vector2(0, -GRAVITY_INTENSITY);
    public static float TO_PIXEL_RATIO = 0.0095f;

    public static final float GROUND_Y = 0;
    public static final float GROUND_WIDTH = 2f;
    public static final float GROUND_HEIGHT = 1f;
    public static final float GROUND_DENSITY = 0.0f;
    public static final float GROUND_VELOCITY = 6.5f;
    public static final float GROUND_TO_RUNNER = 0.2f;

    public static final int MAX_PLATFORM_LENGTH = 9;
    public static final int MIN_PLATFORM_LENGTH = 4;

    public static final String MENU_IMAGE_PATH = "menu.jpg";
    public static final String PAUSE_IMAGE_PATH = "pause.png";
    public static final String[] BACKGROUND_REGION_NAMES = getIncrementalString("fond",3,1);
    public static final String GROUND_IMAGE_PATH = "ground.png";
    public static final String HOLE_IMAGE_PATH = "hole.png";

    // RUNNER CONSTANTS

    public static final float RUNNER_WIDTH = 0.4f;
    public static final float RUNNER_HEIGHT = 0.5f;
    public static final float RUNNER_X = 6;
    public static final float RUNNER_Y = GROUND_HEIGHT + RUNNER_HEIGHT;
    public static final float RUNNER_GRAVITY_SCALE = 2f;
    public static float RUNNER_DENSITY = 1.5f;
    public static final float RUNNER_DODGE_X = RUNNER_X;
    public static final float RUNNER_DODGE_Y = GROUND_HEIGHT - RUNNER_WIDTH + RUNNER_HEIGHT;
    public static final Vector2 RUNNER_JUMPING_LINEAR_IMPULSE = new Vector2(0, GRAVITY_INTENSITY + 1);
    public static final Vector2 RUNNER_JUMP_FAIL_LINEAR_IMPULSE = new Vector2(0, GRAVITY_INTENSITY + 0.5f);
    public static final float RUNNER_FRAME_DURATION = 0.05f;
    public static final float JUMPING_FRAME_DURATION = 0.03f;

    public static final String[] RUNNER_RUNNING_REGION_NAMES = getIncrementalString("perso_run/Perso Run_000",16,2);
    public static final String[] RUNNER_JUMPING_REGION_NAMES = getIncrementalString("perso_jump/Perso Jump_000",31,2);
    public static int JUMP_IMPULSION_FRAMES = 7;
    public static final String[] RUNNER_JUMP_FAIL_REGION_NAMES = getIncrementalString("perso_jump_fail/Perso Jump fail_000",46,2);
    public static final String[] RUNNER_PARKOUR_REGION_NAMES = getIncrementalString("perso_parkour/Perso Jump rocher_000",26,2);
    public static final String[] RUNNER_ROCKFALL_REGION_NAMES = getIncrementalString("perso_rockfall/Perso fail rocher_000",35,2);
    public static final String[] RUNNER_ROLL_IN_REGION_NAMES = getIncrementalString("perso_roll/Perso roulade IN/Perso roulade IN_0000",9,1);
    public static final String[] RUNNER_ROLL_BIS_REGION_NAMES = getIncrementalString("perso_roll/Perso roulade BIS/Perso roulade BIS_000",11,2);
    public static final String[] RUNNER_ROLL_OUT_REGION_NAMES = getIncrementalString("perso_roll/Perso roulade OUT/Perso roulade OUT_0000",9,1);
    public static final String[] RUNNER_FAIL_UP_REGION_NAMES = getIncrementalString("perso_failup/Perso fail up_000",17,2);

    public static final float DELTA_MIN_ENGAGE_PARKOUR = -1f;
    public static final float DELTA_MAX_ENGAGE_PARKOUR = 0.7f;
    public static final float DELTA_MIN_JUMP_FAIL = -0.6f;

    public static int MIN_Y_SWIPE_DIST = 100;
    public static int MIN_X_SWIPE_DIST = 150;

    public static final String SPEAR_IMAGE_PATH = "Lance.png";
    public static final String PLANTED_SPEAR_IMAGE_PATH = "planted_spear.png";
    public static final float DELTA_MIN_ENGAGE_CATCH = -0.1f;
    public static final float DELTA_MAX_ENGAGE_CATCH = 0.0f;
    public static final String[] RUNNER_THROW_SPEAR_REGION_NAMES = getIncrementalString("perso_throw/Perso Run throw_000",15,2);
    public static final String[] RUNNER_CATCH_SPEAR_REGION_NAMES = getIncrementalString("perso_catch/Perso Run catch_000",21,2);
    public static final float SPEAR_X_VELOCITY = 20;
    public static final float SPEAR_Y_VELOCITY = 5;
    public static final float SPEAR_WIDTH = 1f;
    public static final float SPEAR_HEIGHT = 0.1f;

    // PRETTY CONSTANTS

    public static final String[] GRASS_REGION_NAMES = getIncrementalString("grass/Herbes_000",20,2);
    public static final float GRASS_FRAME_DURATION = 0.05f;
    public static final float GRASS_BELOW_GROUND = 0.15f;

    public static final String[] TREE_REGION_NAMES = getIncrementalString("pretty/tree_",3,1);

    public static final String ROCK_IMAGE_PATH = "rock.png";

    public static final String[] BIRD_REGION_NAMES = getIncrementalString("bird/Paleotale_oiseau_000",65,2);
    public static final float BIRD_FRAME_DURATION = 0.06f;

    public static final String FONT_NAME = "font.fnt";
    public static final float FONT_SCALE = 2f;

    public static final String[] VIGNETTE_REGION_NAMES = getIncrementalString("vignette/Vignette_Torche000",49,2);
    public static final String[] EMBER_REGION_NAMES = getIncrementalString("ember/Vignette_Torche_braises_000",49,2);
    public static final float VIGNETTE_FRAME_DURATION = 0.08f;

    public static final String[] GROUND_FALL_REGION_NAMES = getIncrementalString("ground_fall/Sol_Fall_000",20,2);
    public static final float GROUND_FALL_FRAME_DURATION = 0.05f;
    public static final float GROUND_START_FALLING_X = 2f*APP_WIDTH/5f;

    public static final String[] MOUNTAIN_REGION_NAMES = getIncrementalString("pretty/Montagnes/Paleotale_Montagne",6,1);

    public static final String FALLEN_TREE_FRONT_PATH = "fallen_tree_front.png";
    public static final String FALLEN_TREE_BACK_PATH = "fallen_tree_back.png";
    public static final String DOLMEN_FRONT_PATH = "dolmen_front.png";
    public static final String DOLMEN_BACK_PATH = "dolmen_back.png";

    public static final String RED_ERROR_CROSS_PATH = "pretty/error_cross.png";

    // BEASTS CONSTANTS

    public static final float MIN_SCORE_BEAST = 50f;
    public static final float TRANSPARENCY_DELTA = 0.005f;

    public static final String[] RHINO_RUN_REGION_NAMES = getIncrementalString("rhino_run/Paleotale_RhinoRun_0000",9,1);
    public static final String[] RHINO_JUMP_REGION_NAMES = getIncrementalString("rhino_jump/Paleotale_RhinoJump_000",16,2);
    public static final String[] RHINO_FALL_REGION_NAMES = getIncrementalString("rhino_fall/Paleotale_RhinoFall_000",15,2);
    public static final float RHINO_FRAME_DURATION = 0.07f;
    public static final float RHINO_WIDTH = 2.4f;
    public static final float RHINO_HEIGHT = 1f;
    public static final float RHINO_VELOCITY = GROUND_VELOCITY/4;
    public static final float RHINO_FINAL_X = APP_WIDTH - RHINO_WIDTH - 0.5f;
    public static final float RHINO_SCORE = 10f;


    public static final String[] DEER_START_REGION_NAMES = getIncrementalString("deer_start/Paleotale_Megaceros_Start-jump_000",24,2);
    public static final String[] DEER_RUN_REGION_NAMES = getIncrementalString("deer_run/Paleotale_Megaceros_Run_0000",9,1);
    public static final String[] DEER_JUMP_REGION_NAMES = getIncrementalString("deer_jump/Paleotale_Megaceros_bigjump_000",24,2);
    public static final String[] DEER_FALL_REGION_NAMES = getIncrementalString("deer_fall/Paleotale_Megaceros_Fall_000",16,2);
    public static final float DEER_FRAME_DURATION = 0.06f;
    public static final float DEER_WIDTH = 2.4f;
    public static final float DEER_HEIGHT = 1f;
    public static final float DEER_START_X = APP_WIDTH - 2*DEER_WIDTH;
    public static final float DEER_INITIAL_X = APP_WIDTH + 4*DEER_WIDTH;
    public static final int DEER_MAX_JUMP = 2;
    public static final Vector2 DEER_JUMPING_LINEAR_IMPULSE = new Vector2(0, GRAVITY_INTENSITY*14);
    public static final float DEER_SCORE = 30;

    public static final String[] SMILODON_REGION_NAMES = getIncrementalString("smilodon/Paleotale_Smilodon_000",28,2);
    public static final float SMILODON_FRAME_DURATION = 0.05f;
    public static float ACTIVATE_SMILODON = 12f;


    public static final String[] MONKEY_TREE_REGION_NAMES = getIncrementalString("monkey_tree/Arbre Singe_000",28,2);
    public static float ACTIVATE_MONKEY = APP_WIDTH/2f;

    // FX

    public static final String[] FX_DUST_REGION_NAMES = getIncrementalString("fxs/FX_Dust/FX_Dust_000",15,2);
    public static final String[] FX_SPEAR_CIRCLE_REGION_NAMES = getIncrementalString("fxs/FX1/FX1_000",16,2);
    public static final String[] FX_SPEAR_TRAIL_REGION_NAMES = getIncrementalString("fxs/FX2/FX2_000",16,2);
    public static final float FX_DUST_FRAMERATE = 0.08f;

    // TUTO

    public static final String[] TUTO_SWIPE_UP = getIncrementalString("tuto/swipe_up/Paleotale_Swipe_Up_000",25,2);
    public static final String[] TUTO_SWIPE_DOWN = getIncrementalString("tuto/swipe_down/Paleotale_Swipe_Down_000",25,2);
    public static final String[] TUTO_SWIPE_RIGHT = getIncrementalString("tuto/swipe_right/Paleotale_Swipe_Right_000",25,2);

    // SCORE SCREEN

    public static double DISPLAY_ANIMATION_TIME = 3000f;
    public static float BEAST_PADDING = 2f;

    // MUSIC

    public static String MENU_MUSIC_PATH = "music/menu.ogg";

    private static String[] getIncrementalString(String init,int number,int padding) {
        String[] result = new String[number];
        for (int i=0; i<number;i++) {
            result[i] = init + String.format("%0" + padding +"d" , i) + ".png";
        }
        return result;
    }

    public static float getGroundSpeed(double startTime, float duration) {
        return duration*GROUND_VELOCITY*getGroundSpeedMultiplier(startTime);
    }

    public static float getGroundSpeedMultiplier(double time) {
        double elapsed = System.currentTimeMillis() - time;

        return  1.0f + Math.min((float) elapsed/1000000.0f, 0.5f);
    }

}
