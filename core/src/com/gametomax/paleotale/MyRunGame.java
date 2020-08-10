package com.gametomax.paleotale;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.Game;
import com.gametomax.paleotale.screens.GameScreen;
import com.gametomax.paleotale.screens.LoadingScreen;

public class MyRunGame extends Game {
	SpriteBatch batch;
	Texture img;
	
	@Override
	public void create () {
		setScreen(new LoadingScreen(this));
	}

}
