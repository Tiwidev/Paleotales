package com.gametomax.paleotale.utils;

import java.util.Random;
import com.badlogic.gdx.graphics.OrthographicCamera;

public class Shake {
    Random rand = new Random();
    private float shakeDuration = 0;


    private boolean cameraOff = false;

    int sampleCount;

    public Shake(){
    }

    /**
     * Called every frame will shake the camera if it has a shake duration
     * @param dt Gdx.graphics.getDeltaTime() or your dt in seconds
     * @param camera your camera
     */
    public void update(float dt, OrthographicCamera camera){
        if (shakeDuration > 0){
            cameraOff = true;
            shakeDuration -= dt;
            // how much you want to shake
            float amplitude = 0.1f;
            float deltaX = -amplitude + rand.nextFloat() * (2* amplitude);
            float deltaY = -amplitude + rand.nextFloat() * (2* amplitude);
            float x = camera.viewportWidth / 2f + deltaX;
            float y = camera.viewportHeight /2f + deltaY;
            camera.position.set(x,y, 0f);
            camera.update();
        } else if (cameraOff) {
            cameraOff = false;
            camera.position.set(camera.viewportWidth / 2f, camera.viewportHeight /2f, 0f);
            camera.update();
        }
    }

    /**
     * Will make the camera shake for the duration passed in in seconds
     * @param d duration of the shake in seconds
     */
    public void shake(float d){
        shakeDuration = d;
    }
}