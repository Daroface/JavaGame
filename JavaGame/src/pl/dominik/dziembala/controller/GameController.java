package pl.dominik.dziembala.controller;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import pl.dominik.dziembala.model.GameMouseListener;
import pl.dominik.dziembala.model.MusicPlayer;
import pl.dominik.dziembala.model.gameobject.*;
import pl.dominik.dziembala.view.WindowView;

public class GameController {

	/*
	 * Constants
	 */
	private static final int HEIGHT = 300;
	private static final int WIDTH = 480;
	private static final int BIGOBSTACLEYPOSITION = (int) (HEIGHT * 0.7) - 100;
	private static final int SMALLOBSTACLEYPOSITION = (int) (HEIGHT * 0.7) - 50;
	private static final int BIGOBSTACLEHEIGHT = 100;
	private static final int SMALLOBSTACLEHEIGHT = 50;
	private static final int OBSTACLEWIDTH = 20;
	private static final int LENGTH = OBSTACLEWIDTH + 210;
	private static final String MUSICNAME = "./media/musicFile.wav";
	private static final String PLAYERIMAGENAME = "./media/monkey.png";
	private static final String BIGOBSTACLEIMAGENAME = "./media/bigPalm.png";
	private static final String SMALLOBSTACLEIMAGENAME = "./media/smallPalm.png";
	private static final double SPEED = 2.0;
	
	/*
	 * Variables
	 */
	private volatile WindowView WindowView;
	private JButton playButton;
	private JLabel scoreLabel;
	private Player player;
	private StaticObject floor;
	private StaticObject sky;
	private volatile ArrayList<ObstacleObject> obstacleList;
	private int arrayOfLength[];
	private Timer playerMoveTimer;
	private Timer obstaclesMoveTimer;
	private Random generator;
	private MusicPlayer musicPlayer;
	private KeyListener keyListener;
	private GameMouseListener mouseListener;
	private boolean keyIsDown = false;
	private int obstacleToCheck = 0;
	private boolean obstacleAdded = false;
	

	
	/*
	 * Controller's constructor.
	 */
	public GameController() throws Exception {
		musicPlayer = new MusicPlayer(MUSICNAME);		
		generator = new Random();
		createKeyListener();
		mouseListener = new GameMouseListener();
		createPlayButton();
		createScoreLabel();
		WindowView = new WindowView(WIDTH, HEIGHT, keyListener, mouseListener, playButton, scoreLabel);
		createArrayOfLength();
		createPlayerAndStaticGameObjects();
		createObstacleGameObjects();
		createTimers();
	}
	
	/*
	 * Method which creates and implements key listener to control player.
	 */
	private void createKeyListener() {
		keyListener = new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e) {

			}

			@Override
			public void keyReleased(KeyEvent e) {
				if (keyIsDown) {
					if (e.getKeyCode() == KeyEvent.VK_SPACE) {
						keyIsDown = false;
					}
				}
			}

			@Override
			public void keyPressed(KeyEvent e) {
				if (!keyIsDown) {
					if (e.getKeyCode() == KeyEvent.VK_SPACE) {
						player.increaseJumpAmount();
						keyIsDown = true;
					}
				}

			}
		};
	}

	/*
	 * Method which creates play button.
	 */
	private void createPlayButton() {
		playButton = new JButton("Play");
		playButton.setBounds(140, 130, 200, 50);
	}

	/*
	 * Method which creates score label.
	 */
	private void createScoreLabel() {
		scoreLabel = new JLabel("", SwingConstants.CENTER);
		scoreLabel.setBounds(190, 50, 100, 50);
		scoreLabel.setBackground(Color.decode("#b2adad"));
		scoreLabel.setOpaque(true);
	}

	/*
	 * Method which creates array with lengths between obstacles.
	 */
	private void createArrayOfLength() {
		arrayOfLength = new int[5];
		for (int i = 0; i < 4; i++) {
			arrayOfLength[i] = WIDTH + LENGTH * i;
		}
		arrayOfLength[4] = 4 * LENGTH - OBSTACLEWIDTH;
	}

	/*
	 * Method which creates player object and static game objects.
	 */
	private void createPlayerAndStaticGameObjects() throws IOException {
		sky = new StaticObject(Color.decode("#9393FF"), 0, 0, WIDTH, (int) (HEIGHT * 0.7));
		floor = new StaticObject(Color.decode("#39C939"), 0, (int) (HEIGHT * 0.7), WIDTH, (int) (HEIGHT * 0.2));
		player = new Player(PLAYERIMAGENAME, 0, (int) (HEIGHT * 0.7) - 30, 30, SPEED);
	}

	/*
	 * Method which creates obstacles and inserted them into list.
	 */
	private void createObstacleGameObjects() throws IOException {
		obstacleList = new ArrayList<>();
		obstacleList.add(new ObstacleObject(SMALLOBSTACLEIMAGENAME, arrayOfLength[0], SMALLOBSTACLEYPOSITION,
				OBSTACLEWIDTH, SMALLOBSTACLEHEIGHT, SPEED));
		obstacleList.add(new ObstacleObject(SMALLOBSTACLEIMAGENAME, arrayOfLength[1], SMALLOBSTACLEYPOSITION,
				OBSTACLEWIDTH, SMALLOBSTACLEHEIGHT, SPEED));
		obstacleList.add(new ObstacleObject(BIGOBSTACLEIMAGENAME, arrayOfLength[2], BIGOBSTACLEYPOSITION,
				OBSTACLEWIDTH, BIGOBSTACLEHEIGHT, SPEED));
		obstacleList.add(new ObstacleObject(BIGOBSTACLEIMAGENAME, arrayOfLength[3], BIGOBSTACLEYPOSITION,
				OBSTACLEWIDTH, BIGOBSTACLEHEIGHT, SPEED));
	}

	/*
	 * Method which creates timers.
	 */
	private void createTimers() {
		createPlayerMoveTimer();
		createObstaclesMoveTimer();
	}

	/*
	 * 	Method which creates timer in which player is moving forward and can jump.																																																																														.
	 */
	private void createPlayerMoveTimer() {
		playerMoveTimer = new Timer(15, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				player.move();
				player.jump();				
			}
		});
	}

	/*
	 * Method which creates timer in which obstacles are moving and player can jump.
	 */
	private void createObstaclesMoveTimer() {
		obstaclesMoveTimer = new Timer(15, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				moveObstacles();
				player.jump();
				checkPlayerCollision(e);
				incrementScore();
				try {
					controlObstacleInList();
				} catch (IOException e1) {
					System.out.println(e1.getMessage());
				}
				WindowView.repaintView();
			}
		});
	}

	/*
	 * Method which moves obstacles in list.
	 */
	private void moveObstacles() {
		for (int i = 0; i < obstacleList.size(); i++)
			obstacleList.get(i).move();
	}

	/*
	 * Method which checks if player is not in collision with obstacle from list.
	 */
	private void checkPlayerCollision(ActionEvent e) {
		if (player.checkCollision(obstacleList.get(obstacleToCheck)))
			((Timer) e.getSource()).stop();
	}

	/*
	 * Method which increments player's score and showing it in label.
	 */
	private void incrementScore() {
		if ((obstacleList.get(0).getXPosition() + obstacleList.get(0).getWidth()) < player.getXPosition()
				&& !obstacleAdded) {
			player.incrementScore(10);
			obstacleAdded = true;
			obstacleToCheck = 1;
			WindowView.showScore(String.valueOf((player.getScore())));
		}
	}

	/*
	 * Method which checks if obstacle is out of painting window.
	 */
	private void controlObstacleInList() throws IOException {
		if ((obstacleList.get(0).getXPosition() + 20) <= 0) {
			obstacleList.remove(0);
			obstacleToCheck = 0;
			obstacleAdded = false;
			int choose = generator.nextInt(2);
			if (choose == 0)
				obstacleList.add(new ObstacleObject(BIGOBSTACLEIMAGENAME, arrayOfLength[4], BIGOBSTACLEYPOSITION,
						OBSTACLEWIDTH, BIGOBSTACLEHEIGHT, SPEED));
			else
				obstacleList.add(new ObstacleObject(SMALLOBSTACLEIMAGENAME, arrayOfLength[4], SMALLOBSTACLEYPOSITION,
						OBSTACLEWIDTH, SMALLOBSTACLEHEIGHT, SPEED));
			WindowView.refreshObstacleLabelList(obstacleList);
		}
	}

	/*
	 * Method which controls whole game.
	 */
	public void run() throws Exception {
		while (true) {
			addGameObjectsToMap();
			mouseListener.start = false;
			while (!mouseListener.start) {
			}
			playerMoveTimerRunning();
			while (obstaclesMoveTimer.isRunning()) {
			}
			musicPlayer.stopMusic();
			clearAndShowScore();
		}
	}

	/*
	 * Method which adds game objects to painting window.
	 */
	private void addGameObjectsToMap() {
		WindowView.addObject("Sky", sky);
		WindowView.addObject("Floor", floor);
		WindowView.addPlayerLabel(player.getImageLabel());
		WindowView.addObstacleLabelList(obstacleList);
		WindowView.repaintView();
	}

	/*
	 * Method which is called when game is started.
	 */
	private void playerMoveTimerRunning() {
		WindowView.deactivateButton();
		musicPlayer.playMusic();
		WindowView.showScore(String.valueOf((player.getScore())));
		playerMoveTimer.start();
		while (playerMoveTimer.isRunning()) {
			if (player.getXPosition() == 196) {
				obstaclesMoveTimer.start();
			}
			if (player.getXPosition() == 200)
				playerMoveTimer.stop();
		}
	}

	/*
	 * Method which allows for again playing.
	 */
	private void clearAndShowScore() throws IOException {
		WindowView.showScore(String.valueOf((player.getScore())));
		player = null;
		floor = null;
		sky = null;
		obstacleList = null;
		obstacleAdded = false;
		obstacleToCheck = 0;
		createPlayerAndStaticGameObjects();
		createObstacleGameObjects();		
		WindowView.activeButton();
	}
}
