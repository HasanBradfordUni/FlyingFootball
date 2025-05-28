package com.has_akh.flying_football;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Ellipse;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.ScreenUtils;

import java.util.Random;

public class MyGdxGame extends ApplicationAdapter {
	SpriteBatch batch;
	ShapeRenderer shapes;
	Texture background;
	Texture gameover;
	Texture pausedGraphic;
	Texture[] footballs;
	Ellipse footballOval;
	Rectangle[] lowerBarriers, upperBarriers;
	Texture goalSupport, goal;
	Circle football;
	int thisFootball;
	float ballY;
	float velocity;
	int gameState;
	Random randomGenerator;
	int goalVelocity;
	int numOfGoals = 6;
	float[] supportX = new float[numOfGoals];
	float[] supportHeight = new float[numOfGoals];
	float distanceBetweenGoals;
	int score = 0, scoringGoal = 0;
	boolean collision;
	BitmapFont font, font1, font2;
	Rectangle endlessButton, classicButton, storyButton, arcadeButton;
	Rectangle playGameButton, settingsButton, exitButton, pauseButton, mainMenuButton;


	// Define game states
	final int STATE_NOT_STARTED = -1;
	final int STATE_START_SCREEN = 0;
	final int STATE_RUNNING = 1;
	final int STATE_GAME_OVER = 2;
	final int STATE_PAUSED = 3;


	@Override
	public void create () {
		batch = new SpriteBatch();
		background = new Texture("Background.jpg");
		gameover = new Texture("Gameover.png");
		pausedGraphic = new Texture("Paused.png");
		footballs = new Texture[2];
		goalSupport = new Texture("GoalSupport.png");
		goal = new Texture("Goal.png");
		footballOval = new Ellipse();
		footballs[0] = new Texture("Football.png");
		footballs[1] = new Texture("Football2.png");
		ballY = (Gdx.graphics.getHeight()/2) - 100;
		randomGenerator = new Random();
		goalVelocity = 3;
		distanceBetweenGoals = (float) (Gdx.graphics.getWidth() / 2.5);
		lowerBarriers = new Rectangle[numOfGoals];
		upperBarriers = new Rectangle[numOfGoals];
		football = new Circle();
		font = new BitmapFont();
		font.setColor(Color.WHITE);
		font.getData().setScale(10);
		font1 = new BitmapFont();
		font1.setColor(Color.WHITE);
		font1.getData().setScale(5);
		font2 = new BitmapFont();
		font2.setColor(Color.BLUE);
		font2.getData().setScale(20);
		startGame();
		shapes = new ShapeRenderer();
		collision = false;

		// Define menu button areas
		playGameButton = new Rectangle(Gdx.graphics.getWidth() / 2 - 250, 500, 500, 100);
		endlessButton = new Rectangle(Gdx.graphics.getWidth() / 2 - 250, 750, 500, 100);
		classicButton = new Rectangle(Gdx.graphics.getWidth() / 2 - 250, 600, 500, 100);
		storyButton = new Rectangle(Gdx.graphics.getWidth() / 2 - 250, 300, 500, 100);
		arcadeButton = new Rectangle(Gdx.graphics.getWidth() / 2 - 250, 150, 500, 100);
		settingsButton = new Rectangle(Gdx.graphics.getWidth() / 2 - 250, 200, 500, 100);
		exitButton = new Rectangle(Gdx.graphics.getWidth() / 2 - 250, 50, 500, 100);

		gameState = STATE_START_SCREEN; // Start in menu screen

		// Define button areas
		pauseButton = new Rectangle(Gdx.graphics.getWidth() - 300, Gdx.graphics.getHeight() - 200, 150, 100);
		mainMenuButton = new Rectangle(Gdx.graphics.getWidth() / 2 - 250, Gdx.graphics.getHeight() / 2 + 250, 500, 150);
	}

	public void startGame() {
		ballY = (Gdx.graphics.getHeight()/2) - 100;

		for (int i = 0; i < numOfGoals; i++) {
			supportX[i] = (Gdx.graphics.getWidth()/2) - 100 + Gdx.graphics.getWidth()/2 + i * distanceBetweenGoals;
			supportHeight[i] = randomGenerator.nextInt(600);
			lowerBarriers[i] = new Rectangle();
			upperBarriers[i] = new Rectangle();
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

		if (score < 1) {
			goalVelocity = 5;
		} else {
			goalVelocity = 2 * Math.round(3 + (float) Math.sin(score * 0.2) * 2);
		}

		// Check for pause button click **before updating physics**
		if (gameState == STATE_RUNNING && Gdx.input.justTouched()) {
			if (pauseButton.contains(Gdx.input.getX(), Gdx.graphics.getHeight() - Gdx.input.getY())) {
				gameState = STATE_PAUSED; // **Properly pauses the game**
				batch.end();
				return; // **Stop further updates**
			} else {
				velocity = -10; // Jump when clicking elsewhere
			}
		}

		// Handle the START SCREEN menu
		if (gameState == STATE_START_SCREEN) {
			font.draw(batch, "Flying Football", width / 2 - 200, height - 100);
			batch.end();

			shapes.begin(ShapeRenderer.ShapeType.Filled);
			shapes.setColor(Color.BLACK); shapes.rect(playGameButton.x, playGameButton.y, playGameButton.width, playGameButton.height);
			shapes.setColor(Color.GRAY); shapes.rect(settingsButton.x, settingsButton.y, settingsButton.width, settingsButton.height);
			shapes.setColor(Color.DARK_GRAY); shapes.rect(exitButton.x, exitButton.y, exitButton.width, exitButton.height);
			shapes.end();

			batch.begin();

			font1.draw(batch, "Play Game", playGameButton.x + 80, playGameButton.y + 100);
			font1.draw(batch, "Settings", settingsButton.x + 80, settingsButton.y + 100);
			font1.draw(batch, "Exit", exitButton.x + 80, exitButton.y + 100);

			batch.end();

			Boolean settingsTouched = Boolean.FALSE;

			if (Gdx.input.justTouched()) {
				float touchX = Gdx.input.getX();
				float touchY = height - Gdx.input.getY(); // Convert Y-coordinate to match screen

				if (playGameButton.contains(touchX, touchY)) {
					gameState = STATE_NOT_STARTED;
				} else if (settingsButton.contains(touchX, touchY)) {
					settingsTouched = Boolean.TRUE;
				} else if (exitButton.contains(touchX, touchY)) {
					Gdx.app.exit(); // Close game
				}
			}

			if (settingsTouched == Boolean.TRUE) {
				batch.begin();
				font2.draw(batch, "Settings Coming Soon 💀", width / 2 - 500, height / 2 + 200);
				batch.end();
			}

			return;
		} else if (gameState == STATE_NOT_STARTED) {
			font.draw(batch, "Flying Football", width / 2 - 200, height - 100);
			batch.end();

			shapes.begin(ShapeRenderer.ShapeType.Filled);
			shapes.setColor(Color.ORANGE); shapes.rect(endlessButton.x, endlessButton.y, endlessButton.width, endlessButton.height);
			shapes.setColor(Color.GREEN); shapes.rect(classicButton.x, classicButton.y, classicButton.width, classicButton.height);
			shapes.setColor(Color.BLUE); shapes.rect(storyButton.x, storyButton.y, storyButton.width, storyButton.height);
			shapes.setColor(Color.RED); shapes.rect(arcadeButton.x, arcadeButton.y, arcadeButton.width, arcadeButton.height);
			shapes.end();

			batch.begin();

			font1.draw(batch, "Endless", endlessButton.x + 80, endlessButton.y + 100);
			font1.draw(batch, "Classic", classicButton.x + 80, classicButton.y + 100);
			font1.draw(batch, "Story", storyButton.x + 80, storyButton.y + 100);
			font1.draw(batch, "Arcade", arcadeButton.x + 80, arcadeButton.y + 100);

			batch.end();
			if (Gdx.input.justTouched()) {
				float touchX = Gdx.input.getX();
				float touchY = height - Gdx.input.getY(); // Convert Y-coordinate to match screen

				if (endlessButton.contains(touchX, touchY)) {
					gameState = STATE_RUNNING; // Placeholder for endless mode
					velocity = 0;  // Ensure velocity starts from 0
				} else if (classicButton.contains(touchX, touchY)) {
					gameState = STATE_RUNNING; // Start current game mode
					velocity = 0;  // Ensure velocity starts from 0
				} else if (storyButton.contains(touchX, touchY) || arcadeButton.contains(touchX, touchY)) {
					batch.begin();
					font2.draw(batch, "Coming Soon", width / 2 - 300, height / 2 + 100);
					batch.end();
				}
			}
		}


		// **PAUSED STATE**: Stop all movement and show menu
		if (gameState == STATE_PAUSED) {
			batch.setColor(1, 1, 1, 0.9f); // White with 90% opacity
			batch.draw(pausedGraphic, 0, 0, width, height); // Draw pause overlay
			batch.setColor(1, 1, 1, 1); // Reset color to full opacity after drawing
			batch.end();

			shapes.begin(ShapeRenderer.ShapeType.Filled);
			shapes.setColor(Color.BLUE); // Set blue background
			shapes.rect(mainMenuButton.x, mainMenuButton.y, mainMenuButton.width, mainMenuButton.height); // Draw button shape
			shapes.end();

			batch.begin();
			font1.draw(batch, "Main Menu", mainMenuButton.x + 80, mainMenuButton.y + 100); // Draw menu text
			batch.end();

			// Resume when clicking **anywhere except main menu**
			if (Gdx.input.justTouched()) {
				if (!mainMenuButton.contains(Gdx.input.getX(), Gdx.graphics.getHeight() - Gdx.input.getY())) {
					gameState = STATE_RUNNING; // Resume game
				}
			}
			return; // **Stops all further updates while paused**
		}

		if (gameState == STATE_RUNNING) {

			if (supportX[scoringGoal] < centreX) {
				score++;
				String thisMessage = "New score is " + score;
				Gdx.app.log("Score", thisMessage);
				if (scoringGoal < numOfGoals - 1) {
					scoringGoal++;
				} else {
					scoringGoal = 0;
				}
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
				lowerBarriers[i].set(supportX[i], 0, (float) goal.getWidth() /2, supportHeight[i] - 200);
				upperBarriers[i].set(supportX[i], supportHeight[i] + 500, (float) goal.getWidth() /2, height - supportHeight[i] - 400);
			}

			if (ballY >= (height-100)) {
				ballY = height - 100;
			}

			if (Gdx.input.isTouched()) {
				thisFootball = 1;
			} else {
				thisFootball = 0;
			}

			if (ballY > 0) {
				velocity++;
				ballY -= velocity;
			} else {
				velocity = 0;
				ballY = 100;
			}
		} else if (gameState == STATE_GAME_OVER) {

			float scaleFactor = 2f; // Adjust this to increase or decrease the size
			float newWidth = gameover.getWidth() * scaleFactor;
			float newHeight = gameover.getHeight() * scaleFactor;
			batch.draw(gameover, centreX - (newWidth / 4), centreY - (newHeight / 4), newWidth, newHeight);

			if (Gdx.input.justTouched()) {
				gameState = STATE_RUNNING;
				startGame();
				score = 0;
				scoringGoal = 0;
				velocity = 0;  // Reset velocity so the ball jumps
				ballY = centreY;  // Reset ball position
			}

		}

		batch.draw(footballs[thisFootball], centreX, ballY, 300, 200);
		batch.end();

		ShapeRenderer shapeRenderer = new ShapeRenderer();
		shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
		shapeRenderer.setColor(Color.BLUE); // Set blue background
		shapeRenderer.rect(pauseButton.x, pauseButton.y, pauseButton.width, pauseButton.height); // Draw button shape
		shapeRenderer.end();

		// Draw pause symbol
		batch.begin();
		font1.draw(batch, "⏸️", pauseButton.x + 40, pauseButton.y + 80);

		/*
		shapes.begin(ShapeRenderer.ShapeType.Line);
		shapes.setColor(Color.BLACK);
		*/

		footballOval.set(centreX, ballY, 300, 200);
		//shapes.ellipse(footballOval.x, footballOval.y, footballOval.width, footballOval.height);
		football.set(footballOval.x, footballOval.y, 100);

		font.draw(batch, String.valueOf(score), centreX+150, height-200);

		batch.end();

		for (int i = 0; i < numOfGoals; i++) {
			//shapes.rect(supportX[i], 0, (float) goal.getWidth() /2, supportHeight[i] - 200);
			//shapes.rect(supportX[i], supportHeight[i] + 500, (float) goal.getWidth() /2, height - supportHeight[i] - 400);
			if (Intersector.overlaps(football, lowerBarriers[i]) || Intersector.overlaps(football, upperBarriers[i])) {
				Gdx.app.log("Collision", "Yes!");
				collision = true;
				gameState = STATE_GAME_OVER;
			}
		}

		if (collision) {
			collision = false;
		}
		shapes.end();
	}
}

