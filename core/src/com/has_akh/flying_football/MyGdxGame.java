package com.has_akh.flying_football;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

import java.util.Random;

public class MyGdxGame extends ApplicationAdapter {
	SpriteBatch batch;
	Texture background;
	Texture[] footballs;
	Texture goalSupport;
	Texture goal;
	int thisFootball;
	float ballY;
	float velocity;
	int gameState;
	Random randomGenerator;
	int goalVelocity;
	int numOfGoals = 6;
	float[] supportX = new float[numOfGoals];
	float[] supportHeight = new float[numOfGoals];
	int distanceBetweenGoals;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		background = new Texture("Background.jpg");
		footballs = new Texture[2];
		goalSupport = new Texture("GoalSupport.png");
		goal = new Texture("Goal.png");
		footballs[0] = new Texture("Football.png");
		footballs[1] = new Texture("Football2.png");
		ballY = (Gdx.graphics.getHeight()/2) - 100;
		randomGenerator = new Random();
		goalVelocity = 4;
		distanceBetweenGoals = Gdx.graphics.getWidth() / 3;

		for (int i = 0; i < numOfGoals; i++) {
			supportX[i] = (Gdx.graphics.getWidth()/2) - 100 + i * distanceBetweenGoals;
			supportHeight[i] = randomGenerator.nextInt(600);

		}
	}

	@Override
	public void render () {
		int width = Gdx.graphics.getWidth();
		int height = Gdx.graphics.getHeight();
		int centreX = (width/2) - 150;
		int centreY = (height/2) - 100;

		batch.begin();
		batch.draw(background, 0, 0, width, height);

		if (gameState != 0) {

			if (Gdx.input.justTouched()) {
				velocity = -30;
			}

			for (int i = 0; i < numOfGoals; i++) {

				if (supportX[i] < -goal.getWidth()) {
					supportX[i] += numOfGoals * distanceBetweenGoals;
					supportHeight[i] = randomGenerator.nextInt(600);
				} else {
					supportX[i] -= goalVelocity;
				}

				batch.draw(goalSupport, supportX[i], 0, 200, supportHeight[i]);
				batch.draw(goal, supportX[i], supportHeight[i], 300, 300);
			}

			if (ballY >= (height-100)) {
				ballY = height - 100;
			}

			if (Gdx.input.isTouched()) {
				thisFootball = 1;
			} else {
				thisFootball = 0;
			}

			if (ballY > 0 || velocity < 0) {
				velocity++;
				ballY -= velocity;
			}

		} else {

			if (Gdx.input.justTouched()) {
				gameState = 1;
			}

		}

		batch.draw(footballs[thisFootball], centreX, ballY, 300, 200);
		batch.end();
	}
}


