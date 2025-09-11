package com.has_akh.flying_football;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Ellipse;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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
	boolean collision, soundEnabled;
	BitmapFont font, font1, font2, leaderboardFont;
	Rectangle endlessButton, classicButton, storyButton, arcadeButton, leaderboardButton, ContinueGameButton, soundToggleButton;
	Rectangle playGameButton, settingsButton, exitButton, pauseButton, mainMenuButton, endGameButton, NewGameButton;
	private String enteredUsername = null;
	int collisionCooldown = 0;
	int scoringBarrier = 0;
	Sound jumpSound, goalSound, barrierHitSound, bounceSound, saveScoreSound;

	// Define game states
	final int STATE_NOT_STARTED = -1;
	final int STATE_START_SCREEN = 0;
	final int STATE_RUNNING = 1;
	final int STATE_GAME_OVER = 2;
	final int STATE_PAUSED = 3;
	final int STATE_OTHER_SCREEN = 4;
	final int STATE_SETTINGS_SCREEN = 5;
	final int STATE_LEADERBOARD_SCREEN = 6;
	final int STATE_RUNNING2 = 7;
	final int STATE_CONTINUE_SCREEN = 8;
	private int previousGameMode;
	private String filename;
	private FileHandle scoresFile, endlessScoresFile;

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
		jumpSound = Gdx.audio.newSound(Gdx.files.internal("jump.mp3"));
		goalSound = Gdx.audio.newSound(Gdx.files.internal("goal.mp3"));
		barrierHitSound = Gdx.audio.newSound(Gdx.files.internal("barrier.mp3"));
		bounceSound = Gdx.audio.newSound(Gdx.files.internal("bounce.mp3"));
		saveScoreSound = Gdx.audio.newSound(Gdx.files.internal("save.mp3"));
		soundEnabled = true;

		try {
			FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("Lora-VariableFont_wght.ttf"));
			FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();

			parameter.size = 100;
			parameter.color = Color.WHITE;
			font = generator.generateFont(parameter);

			parameter.size = 50;
			font1 = generator.generateFont(parameter);

			parameter.size = 120;
			font2 = generator.generateFont(parameter);

			parameter.size = 30;
			leaderboardFont = generator.generateFont(parameter);

			generator.dispose();
		} catch (Exception e) {
			Gdx.app.log("Font Error", "Could not load font: " + e.getMessage());
			font = new BitmapFont();
			font.setColor(Color.WHITE);
			font.getData().setScale(10);
			font1 = new BitmapFont();
			font1.setColor(Color.WHITE);
			font1.getData().setScale(5);
			font2 = new BitmapFont();
			font2.setColor(Color.BLUE);
			font2.getData().setScale(15);
			leaderboardFont = new BitmapFont();
			leaderboardFont.setColor(Color.WHITE);
			leaderboardFont.getData().setScale(3);
		}
		startGame();
		shapes = new ShapeRenderer();
		collision = false;

		scoresFile = Gdx.files.local("scores.txt");
		if (!scoresFile.exists()) {
			scoresFile.writeString("", false);
		}
		endlessScoresFile = Gdx.files.local("endless_scores.txt");
		if (!endlessScoresFile.exists()) {
			endlessScoresFile.writeString("", false);
		}

		// Define menu button areas
		playGameButton = new Rectangle(Gdx.graphics.getWidth() / 2 - 250, 500, 500, 100);
		endlessButton = new Rectangle(Gdx.graphics.getWidth() / 2 - 250, 750, 500, 100);
		classicButton = new Rectangle(Gdx.graphics.getWidth() / 2 - 250, 600, 500, 100);
		storyButton = new Rectangle(Gdx.graphics.getWidth() / 2 - 250, 300, 500, 100);
		arcadeButton = new Rectangle(Gdx.graphics.getWidth() / 2 - 250, 150, 500, 100);
		settingsButton = new Rectangle(Gdx.graphics.getWidth() / 2 - 250, 200, 500, 100);
		exitButton = new Rectangle(Gdx.graphics.getWidth() / 2 - 250, 50, 500, 100);
		leaderboardButton = new Rectangle(Gdx.graphics.getWidth() / 2 - 250, 350, 500, 100);
		endGameButton = new Rectangle(Gdx.graphics.getWidth() / 2 - 250, Gdx.graphics.getHeight() / 2 + 50, 500, 150);
		ContinueGameButton = new Rectangle(Gdx.graphics.getWidth() / 2 - 400, Gdx.graphics.getHeight() / 2 + 50, 500, 100);
		NewGameButton = new Rectangle(Gdx.graphics.getWidth() / 2 + 400, Gdx.graphics.getHeight() / 2 + 50, 400, 100);
		soundToggleButton = new Rectangle(30, Gdx.graphics.getHeight() - 100, 600, 80);

		gameState = STATE_START_SCREEN; // Start in menu screen

		// Define button areas
		pauseButton = new Rectangle(Gdx.graphics.getWidth() - 300, Gdx.graphics.getHeight() - 200, 150, 100);
		mainMenuButton = new Rectangle(Gdx.graphics.getWidth() / 2 - 250, Gdx.graphics.getHeight() / 2 + 250, 500, 150);
	}

	public void startGame() {
		score = 0;
		this.scoringGoal = 0;
		this.scoringBarrier = 0;
		ballY = (Gdx.graphics.getHeight()/2) - 100;

		for (int i = 0; i < numOfGoals; i++) {
			supportX[i] = (Gdx.graphics.getWidth()/2) - 100 + Gdx.graphics.getWidth()/2 + i * distanceBetweenGoals;
			supportHeight[i] = randomGenerator.nextInt(600);
			lowerBarriers[i] = new Rectangle();
			upperBarriers[i] = new Rectangle();
		}
	}

	public void saveEndlessState() {
		FileHandle file = Gdx.files.local("endless_state.txt");

		StringBuilder data = new StringBuilder();
		data.append(score).append(",").append(ballY).append(",").append(velocity).append("\n");
		for (int i = 0; i < numOfGoals; i++) {
			data.append(supportX[i]).append(",").append(supportHeight[i]).append("\n");
		}

		file.writeString(data.toString(), false);
	}

	public void loadEndlessState() {
		FileHandle file = Gdx.files.local("endless_state.txt");
		if (!file.exists()) return;

		String[] lines = file.readString().split("\n");

		String[] header = lines[0].split(",");
		score = Integer.parseInt(header[0]);
		ballY = Float.parseFloat(header[1]);
		velocity = Float.parseFloat(header[2]);

		for (int i = 1; i <= numOfGoals; i++) {
			String[] parts = lines[i].split(",");
			supportX[i - 1] = Float.parseFloat(parts[0]);
			supportHeight[i - 1] = Float.parseFloat(parts[1]);
		}
	}

	private void restartGame() {
		gameState = STATE_RUNNING;
		startGame();
		velocity = 0;
		ballY = (Gdx.graphics.getHeight() / 2) - 100;
		enteredUsername = null; // Reset for next input
	}

	@Override
	public void render () {
		int width = Gdx.graphics.getWidth();
		int height = Gdx.graphics.getHeight();
		int centreX = (width/2) - 150;
		int centreY = (height/2) - 100;

		batch.begin();
		batch.draw(background, 0, 0, width, height);
		batch.end();

		if (score < 1) {
			goalVelocity = 5;
		} else {
			goalVelocity = 2 * Math.round(3 + (float) Math.sin(score * 0.2) * 2);
		}

		if ((gameState == STATE_RUNNING || gameState == STATE_RUNNING2) && Gdx.input.justTouched()) {
			if (pauseButton.contains(Gdx.input.getX(), Gdx.graphics.getHeight() - Gdx.input.getY())) {
				previousGameMode = gameState;
				gameState = STATE_PAUSED;
				return;
			} else {
				velocity = -10;
			}
		}

		// Handle the START SCREEN menu
		if (gameState == STATE_START_SCREEN) {
			batch.begin();
			font.draw(batch, "Flying Football", width / 2 - 300, height - 200);
			batch.end();

			shapes.begin(ShapeRenderer.ShapeType.Filled);
			shapes.setColor(new Color(0.8f, 0.4f, 0.0f, 1f)); // Dark orange
			shapes.rect(soundToggleButton.x, soundToggleButton.y, soundToggleButton.width, soundToggleButton.height);
			shapes.setColor(Color.BLACK);
			shapes.rect(playGameButton.x, playGameButton.y, playGameButton.width, playGameButton.height);
			shapes.setColor(new Color(0.8f, 0.6f, 0.1f, 1f)); // Mustard yellow
			shapes.rect(leaderboardButton.x, leaderboardButton.y, leaderboardButton.width, leaderboardButton.height);
			shapes.setColor(Color.GRAY);
			shapes.rect(settingsButton.x, settingsButton.y, settingsButton.width, settingsButton.height);
			shapes.setColor(Color.DARK_GRAY);
			shapes.rect(exitButton.x, exitButton.y, exitButton.width, exitButton.height);
			shapes.end();

			batch.begin();

			if (soundEnabled) {
				font1.draw(batch, "Disable Sound Effects", soundToggleButton.x + 20, soundToggleButton.y + 55);
			} else {
				font1.draw(batch, "Enable Sound Effects", soundToggleButton.x + 20, soundToggleButton.y + 55);
			}
			font1.draw(batch, "Play Game", playGameButton.x + 50, playGameButton.y + 60);
			font1.draw(batch, "Leaderboard", leaderboardButton.x + 50, leaderboardButton.y + 60);
			font1.draw(batch, "Settings", settingsButton.x + 50, settingsButton.y + 60);
			font1.draw(batch, "Exit", exitButton.x + 50, exitButton.y + 60);

			batch.end();

			if (Gdx.input.justTouched()) {
				float touchX = Gdx.input.getX();
				float touchY = height - Gdx.input.getY(); // Convert Y-coordinate to match screen

				if (playGameButton.contains(touchX, touchY)) {
					gameState = STATE_NOT_STARTED;
				} else if (settingsButton.contains(touchX, touchY)) {
					gameState = STATE_SETTINGS_SCREEN;
				} else if (exitButton.contains(touchX, touchY)) {
					Gdx.app.exit(); // Close game
				} else if (leaderboardButton.contains(touchX, touchY)) {
					gameState = STATE_LEADERBOARD_SCREEN; // Switch to leaderboard screen
				} else if (soundToggleButton.contains(Gdx.input.getX(), Gdx.input.getY())) {
					soundEnabled = !soundEnabled;
				}
			}

			return;
		}

		if (gameState == STATE_NOT_STARTED) {
			batch.begin();
			font.draw(batch, "Flying Football", width / 2 - 300, height - 100);
			batch.end();

			shapes.begin(ShapeRenderer.ShapeType.Filled);
			shapes.setColor(new Color(0.8f, 0.4f, 0.0f, 1f)); // Dark orange
			shapes.rect(soundToggleButton.x, soundToggleButton.y, soundToggleButton.width, soundToggleButton.height);
			shapes.setColor(Color.ORANGE); shapes.rect(endlessButton.x, endlessButton.y, endlessButton.width, endlessButton.height);
			shapes.setColor(Color.GREEN); shapes.rect(classicButton.x, classicButton.y, classicButton.width, classicButton.height);
			shapes.setColor(Color.BLUE); shapes.rect(storyButton.x, storyButton.y, storyButton.width, storyButton.height);
			shapes.setColor(Color.RED); shapes.rect(arcadeButton.x, arcadeButton.y, arcadeButton.width, arcadeButton.height);
			shapes.end();

			batch.begin();

			if (soundEnabled) {
				font1.draw(batch, "Disable Sound Effects", soundToggleButton.x + 20, soundToggleButton.y + 55);
			} else {
				font1.draw(batch, "Enable Sound Effects", soundToggleButton.x + 20, soundToggleButton.y + 55);
			}
			font1.draw(batch, "Endless", endlessButton.x + 50, endlessButton.y + 60);
			font1.draw(batch, "Classic", classicButton.x + 50, classicButton.y + 60);
			font1.draw(batch, "Story", storyButton.x + 50, storyButton.y + 60);
			font1.draw(batch, "Arcade", arcadeButton.x + 50, arcadeButton.y + 60);

			batch.end();
			if (Gdx.input.justTouched()) {
				float touchX = Gdx.input.getX();
				float touchY = height - Gdx.input.getY(); // Convert Y-coordinate to match screen

				if (endlessButton.contains(touchX, touchY)) {
					FileHandle file = Gdx.files.local("endless_state.txt");
					if (file.exists()) {
						gameState = STATE_CONTINUE_SCREEN; // Show prompt
						return;
					} else {
						gameState = STATE_RUNNING2;
						velocity = 0;
					}
				} else if (classicButton.contains(touchX, touchY)) {
					gameState = STATE_RUNNING; // Start classic game mode
					velocity = 0;  // Ensure velocity starts from 0
				} else if (storyButton.contains(touchX, touchY) || arcadeButton.contains(touchX, touchY)) {
					gameState = STATE_OTHER_SCREEN;
					return;
				} else if (soundToggleButton.contains(Gdx.input.getX(), Gdx.input.getY())) {
					soundEnabled = !soundEnabled;
				} else {
					gameState = STATE_START_SCREEN; // 👈 Tap outside buttons returns to main menu
					return;
				}
			}

		}

		if (gameState == STATE_SETTINGS_SCREEN) {
			batch.begin();
			font2.draw(batch, "Settings Coming Soon", width / 2 - 600, height / 2 + 200);
			batch.end();

			if (Gdx.input.justTouched()) {
				gameState = STATE_START_SCREEN; // Return to the main menu
			}
			return;
		}

		if (gameState == STATE_OTHER_SCREEN) {
			batch.begin();
			font2.draw(batch, "Coming Soon", width / 2 - 400, height / 2 + 100);
			batch.end();

			if (Gdx.input.justTouched()) {
				float touchX = Gdx.input.getX();
				float touchY = height - Gdx.input.getY();

				if (!NewGameButton.contains(touchX, touchY) && !ContinueGameButton.contains(touchX, touchY)) {
					gameState = STATE_START_SCREEN; // Only return to menu if they tap outside
				}
			}
			return;
		}

		if (gameState == STATE_CONTINUE_SCREEN) {

			shapes.begin(ShapeRenderer.ShapeType.Filled);
			shapes.setColor(Color.GREEN); shapes.rect(NewGameButton.x, NewGameButton.y, NewGameButton.width, NewGameButton.height);
			shapes.setColor(Color.BLUE); shapes.rect(ContinueGameButton.x, ContinueGameButton.y, ContinueGameButton.width, ContinueGameButton.height);
			shapes.end();

			batch.begin();

			font1.draw(batch, "New Game", NewGameButton.x + 50, NewGameButton.y + 60);
			font1.draw(batch, "Continue Game", ContinueGameButton.x + 50, ContinueGameButton.y + 60);

			batch.end();

			if (Gdx.input.justTouched()) {
				float touchX = Gdx.input.getX();
				float touchY = height - Gdx.input.getY(); // Convert Y-coordinate to match screen

				if (NewGameButton.contains(touchX, touchY)) {
					Gdx.files.local("endless_state.txt").delete();
					gameState = STATE_RUNNING2;
					startGame();
				} else if (ContinueGameButton.contains(touchX, touchY)) {
					loadEndlessState();
					gameState = STATE_RUNNING2;
				} else {
					gameState = STATE_START_SCREEN; // Only return to menu if they tap outside
					return;
				}
			}
		}

		// **PAUSED STATE**: Stop all movement and show menu
		if (gameState == STATE_PAUSED) {
			batch.begin();
			batch.setColor(1, 1, 1, 0.9f); // White with 90% opacity
			batch.draw(pausedGraphic, 0, 0, width, height); // Draw pause overlay
			batch.setColor(1, 1, 1, 1); // Reset color to full opacity after drawing
			batch.end();

			shapes.begin(ShapeRenderer.ShapeType.Filled);
			shapes.setColor(new Color(0.8f, 0.4f, 0.0f, 1f)); // Dark orange
			shapes.rect(soundToggleButton.x, soundToggleButton.y, soundToggleButton.width, soundToggleButton.height);
			shapes.setColor(Color.BLUE); // Set blue background
			shapes.rect(mainMenuButton.x, mainMenuButton.y, mainMenuButton.width, mainMenuButton.height); // Draw button shape
			shapes.end();

			batch.begin();
			if (soundEnabled) {
				font1.draw(batch, "Disable Sound Effects", soundToggleButton.x + 20, soundToggleButton.y + 55);
			} else {
				font1.draw(batch, "Enable Sound Effects", soundToggleButton.x + 20, soundToggleButton.y + 55);
			}
			font1.draw(batch, "Main Menu", mainMenuButton.x + 80, mainMenuButton.y + 100); // Draw menu text
			batch.end();

			if (gameState == STATE_PAUSED && previousGameMode == STATE_RUNNING2) {
				saveEndlessState();
				shapes.begin(ShapeRenderer.ShapeType.Filled);
				shapes.setColor(Color.FIREBRICK); // Reddish for emphasis
				shapes.rect(endGameButton.x, endGameButton.y, endGameButton.width, endGameButton.height);
				shapes.end();

				batch.begin();
				font1.draw(batch, "End Endless Game", endGameButton.x + 30, endGameButton.y + 50);
				batch.end();

				if (Gdx.input.justTouched()) {
					float touchX = Gdx.input.getX();
					float touchY = height - Gdx.input.getY();

					if (endGameButton.contains(touchX, touchY)) {
						Gdx.files.local("endless_state.txt").delete();
						previousGameMode = STATE_RUNNING2;
						gameState = STATE_GAME_OVER;
						return;
					} else if (soundToggleButton.contains(Gdx.input.getX(), Gdx.input.getY())) {
						soundEnabled = !soundEnabled;
					}
				}
			}

			// Resume when clicking **anywhere except main menu**
			if (Gdx.input.justTouched()) {
				if (!mainMenuButton.contains(Gdx.input.getX(), Gdx.graphics.getHeight() - Gdx.input.getY())) {
					gameState = STATE_RUNNING; // Resume game
				} else if (soundToggleButton.contains(Gdx.input.getX(), Gdx.input.getY())) {
					soundEnabled = !soundEnabled;
				} else {
					gameState = STATE_START_SCREEN; // Return to the main menu
				}
			}
			return; // **Stops all further updates while paused**
		}

		if ((gameState == STATE_RUNNING) || (gameState == STATE_RUNNING2)) {

			if ((supportX[scoringGoal] < centreX) && (supportHeight[scoringGoal] < ballY)) {
				score++;
				if (soundEnabled) {
					goalSound.play();
				}
				scoringGoal = (scoringGoal + 1) % numOfGoals;
			}

			batch.begin();

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

			batch.end();


			batch.begin();
			batch.draw(footballs[thisFootball], centreX, ballY, 300, 200);
			batch.end();

			ShapeRenderer shapeRenderer = new ShapeRenderer();
			shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
			shapeRenderer.setColor(new Color(0.8f, 0.4f, 0.0f, 1f)); // Dark orange
			shapeRenderer.rect(soundToggleButton.x, soundToggleButton.y, soundToggleButton.width, soundToggleButton.height);
			shapeRenderer.setColor(Color.BLUE); // Set blue background
			shapeRenderer.rect(pauseButton.x, pauseButton.y, pauseButton.width, pauseButton.height); // Draw button shape
			shapeRenderer.end();

			// Draw pause symbol
			batch.begin();
			font1.draw(batch, "⏸️", pauseButton.x + 20, pauseButton.y + 80);
			if (soundEnabled) {
				font1.draw(batch, "Disable Sound Effects", soundToggleButton.x + 20, soundToggleButton.y + 55);
			} else {
				font1.draw(batch, "Enable Sound Effects", soundToggleButton.x + 20, soundToggleButton.y + 55);
			}

			footballOval.set(centreX, ballY, 300, 200);
			football.set(footballOval.x, footballOval.y, 100);

			font.draw(batch, String.valueOf(score), centreX+150, height-200);

			batch.end();

		} else if (gameState == STATE_GAME_OVER) {
			if (previousGameMode == STATE_RUNNING) {
				filename = "scores.txt";
			} else if (previousGameMode == STATE_RUNNING2) {
				filename = "endless_scores.txt";
			}

			ArrayList<String> savedUsernames = getSavedUsernames(filename);
			scoringBarrier = 0;

			float scaleFactor = 2f;
			float newWidth = gameover.getWidth() * scaleFactor;
			float newHeight = gameover.getHeight() * scaleFactor;

			// Step 1: Draw boxes first (ShapeRenderer)
			int rowHeight = 60;
			int boxHeight = 50;
			int startY = height - 180;
			float enterButtonY = 80;

			shapes.begin(ShapeRenderer.ShapeType.Filled);

			// Username boxes
			for (int i = 0; i < savedUsernames.size(); i++) {
				float boxY = startY - (i * rowHeight);
				shapes.setColor(Color.LIGHT_GRAY);
				shapes.rect(width / 2f - 220, boxY - 40, 440, boxHeight);
			}

			// Enter New Name button
			shapes.setColor(Color.YELLOW);
			shapes.rect(width / 2f - 220, enterButtonY, 440, 60);

			shapes.end();

			// Step 2: Draw everything else with SpriteBatch
			batch.begin();
			batch.draw(gameover, centreX - (newWidth / 4), centreY - (newHeight / 4), newWidth, newHeight);
			font1.draw(batch, "Select a Username or Enter a New One:", 50, height - 100);

			for (int i = 0; i < savedUsernames.size(); i++) {
				float boxY = startY - (i * rowHeight);
				font1.draw(batch, savedUsernames.get(i), width / 2f - 200, boxY);
			}

			font1.draw(batch, "Enter New Name", width / 2f - 150, enterButtonY + 40);
			batch.end();

			if (enteredUsername != null) {
				saveScore(enteredUsername, score, filename);
				restartGame();
			}

			// Step 3: Handle input
			if (Gdx.input.justTouched()) {
				float touchX = Gdx.input.getX();
				float touchY = height - Gdx.input.getY();

				if (touchY >= enterButtonY && touchY <= enterButtonY + 60 &&
						touchX >= width / 2f - 220 && touchX <= width / 2f + 220) {
					getUserInput(); // open keyboard for new name
				} else {
					int selectedIndex = (int) ((startY - touchY) / rowHeight);
					if (selectedIndex >= 0 && selectedIndex < savedUsernames.size()) {
						saveScore(savedUsernames.get(selectedIndex), score, filename);
						restartGame();
					}
				}
			}

			return;
		}

		if (gameState == STATE_LEADERBOARD_SCREEN) {
			String title_string = "";

			if (filename == "endless_scores.txt") {
				title_string = "Leaderboard (Endless)";
			} else if (filename == null) {
				filename = "scores.txt";
				title_string = "Leaderboard (Classic)";
			} else {
				title_string = "Leaderboard (Classic)";
			}


			ArrayList<String> topScores = getTopScores(filename);

			batch.begin();
			font1.draw(batch, title_string, width / 2f - 250, height - 50); // Title with spacing
			int yOffset = 0;

			for (String entry : topScores) {
				leaderboardFont.draw(batch, entry, width / 2f - 200, height - 160 - yOffset);
				yOffset += 60; // Proper spacing between rows
			}
			batch.end();

			if (Gdx.input.justTouched()) {
				gameState = STATE_START_SCREEN; // Return to the main menu
			}
			return;

		}

		if ((gameState == STATE_RUNNING || gameState == STATE_RUNNING2)) {
			velocity++;
			ballY -= velocity;

			// Bounce if ball hits the bottom
			if (ballY <= 0) {
				ballY = 0;
				velocity = -10; // Bounce upward
				if (soundEnabled) {
					bounceSound.play();
				}
			}

			// Clamp ball so it doesn't go too high
			if (ballY >= height - 100) {
				ballY = height - 100;
			}
		}

		if (Gdx.input.isTouched()) {
			thisFootball = 1;
			if (soundEnabled) {
				jumpSound.play();
			}
		} else {
			thisFootball = 0;
		}

		for (int i = 0; i < numOfGoals; i++) {
			if (Intersector.overlaps(football, lowerBarriers[scoringBarrier]) || Intersector.overlaps(football, upperBarriers[scoringBarrier])) {
				if (gameState == STATE_RUNNING) {
					collision = true;
					if (soundEnabled) {
						barrierHitSound.play();
					}
					previousGameMode = STATE_RUNNING;
					gameState = STATE_GAME_OVER;
					return;
				}
				else if (gameState == STATE_RUNNING2) {
					score--;
					if (soundEnabled) {
						barrierHitSound.play();
					}
					scoringBarrier = (scoringBarrier + 1) % numOfGoals;
				}
			}
		}

		if (collision) {
			collision = false;
		}

		if (collisionCooldown > 0) {
			collisionCooldown--;
		}

		shapes.end();
	}

	public void saveScore(String username, int score, String filename) {
		if (filename.equals("scores.txt")) {
			scoresFile.writeString(username + " " + score + "\n", true); // Append scores
		} else if (filename.equals("endless_scores.txt")) {
			endlessScoresFile.writeString(username + " " + score + "\n", true); // Append scores
		}
		if (soundEnabled) {
			saveScoreSound.play();
		}
	}

	public ArrayList<String> getSavedUsernames(String filename) {
		String data = "";
		if (filename.equals("scores.txt")) {
			data = scoresFile.readString();
		} else if (filename.equals("endless_scores.txt")) {
			data = endlessScoresFile.readString();
		}

		ArrayList<String> usernames = new ArrayList<>();
		for (String line : data.split("\n")) {
			line = line.trim();
			if (!line.isEmpty()) {
				String[] parts = line.split(" ");
				if (parts.length >= 2 && !usernames.contains(parts[0])) {
					usernames.add(parts[0]);
				}
			}
		}
		return usernames;
	}

	public ArrayList<String> getTopScores(String filename) {
		FileHandle scoresFile = Gdx.files.local(filename);
		ArrayList<String> scores = new ArrayList<>();

		if (scoresFile.exists()) {
			String[] lines = scoresFile.readString().split("\n");

			for (String line : lines) {
				line = line.trim();
				if (!line.isEmpty()) {
					String[] parts = line.split(" ");
					if (parts.length >= 2) {
						try {
							Integer.parseInt(parts[1]); // Validate score
							scores.add(line); // Only add valid lines
						} catch (NumberFormatException e) {
							// Skip malformed lines
						}
					}
				}
			}

			Collections.sort(scores, new Comparator<String>() {
				@Override
				public int compare(String a, String b) {
					try {
						int scoreA = Integer.parseInt(a.split(" ")[1]);
						int scoreB = Integer.parseInt(b.split(" ")[1]);
						return Integer.compare(scoreB, scoreA); // Descending
					} catch (Exception e) {
						return 0; // Treat malformed lines as equal
					}
				}
			});
		}

		return scores;
	}

	public void getUserInput() {
		Gdx.input.getTextInput(new Input.TextInputListener() {
			@Override
			public void input(String text) {
				if (text.length() > 5) {
					text = text.substring(0, 5); // Limit to 5 characters
				}
				enteredUsername = text;
				saveScore(enteredUsername, score, filename); // Save score locally
				gameState = STATE_LEADERBOARD_SCREEN; // Redirect to leaderboard screen
			}

			@Override
			public void canceled() {
				enteredUsername = null; // Reset if user cancels input
				gameState = STATE_GAME_OVER;
			}
		}, "Enter Username", "", "Max 5 characters");
	}

}

