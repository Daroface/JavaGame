package pl.dominik.dziembala.controller;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
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

	private volatile WindowView windowView;
	private JButton playButton;
	private JLabel scoreLabel;
	private Player player;
	private StaticObject floor;
	private StaticObject sky;
	private volatile ArrayList<GameObject> obstacleList;
	private static final int HEIGHT = 300;
	private static final int WIDTH = 480;
	private static final int BIGOBSTACLEYPOSITION = (int) (HEIGHT * 0.7) - 100;
	private static final int SMALLOBSTACLEYPOSITION = (int) (HEIGHT * 0.7) - 50;
	private static final int BIGOBSTACLEHEIGHT = 100;
	private static final int SMALLOBSTACLEHEIGHT = 50;
	private static final int OBSTACLEWIDTH = 20;
	private static final int LENGTH = OBSTACLEWIDTH + 210;
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
	private String musicName = "/home/daroface/Pobrane/IntroTheme.wav";
	private double speed = 1.0;

	public GameController() {
		try {
			musicPlayer = new MusicPlayer(musicName);
		} catch (Exception e) {
			System.out.println("Cannot open wav file.");
		}
		generator = new Random();
		createKeyListener();
		mouseListener = new GameMouseListener();
		createPlayButton();
		createScoreLabel();
		windowView = new WindowView(WIDTH, HEIGHT, keyListener, mouseListener, playButton, scoreLabel);
		createArrayOfLength();
		createPlayerAndStaticGameObjects();
		createObstacleGameObjects();
		createTimers();
	}

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

	private void createPlayButton() {
		playButton = new JButton("Play");
		playButton.setBounds(140, 130, 200, 50);
	}

	private void createScoreLabel() {
		scoreLabel = new JLabel("", SwingConstants.CENTER);
		scoreLabel.setBounds(190, 50, 100, 50);
		scoreLabel.setBackground(Color.decode("#b2adad"));
		scoreLabel.setOpaque(true);
	}

	private void createArrayOfLength() {
		arrayOfLength = new int[5];
		for (int i = 0; i < 4; i++) {
			arrayOfLength[i] = WIDTH + LENGTH * i;
		}
		arrayOfLength[4] = 4 * LENGTH - OBSTACLEWIDTH;
	}

	private void createPlayerAndStaticGameObjects() {
		sky = new StaticObject(Color.decode("#9393FF"), 0, 0, WIDTH, (int) (HEIGHT * 0.7));
		floor = new StaticObject(Color.decode("#39C939"), 0, (int) (HEIGHT * 0.7), WIDTH, (int) (HEIGHT * 0.2));
		player = new Player(Color.decode("#000000"), 0, (int) (HEIGHT * 0.7) - 30, 30, speed);
	}

	private void createObstacleGameObjects() {
		obstacleList = new ArrayList<>();
		obstacleList.add(new ObstacleObject(Color.decode("#990000"), arrayOfLength[0], SMALLOBSTACLEYPOSITION,
				OBSTACLEWIDTH, SMALLOBSTACLEHEIGHT, speed));
		obstacleList.add(new ObstacleObject(Color.decode("#990000"), arrayOfLength[1], SMALLOBSTACLEYPOSITION,
				OBSTACLEWIDTH, SMALLOBSTACLEHEIGHT, speed));
		obstacleList.add(new ObstacleObject(Color.decode("#660000"), arrayOfLength[2], BIGOBSTACLEYPOSITION,
				OBSTACLEWIDTH, BIGOBSTACLEHEIGHT, speed));
		obstacleList.add(new ObstacleObject(Color.decode("#660000"), arrayOfLength[3], BIGOBSTACLEYPOSITION,
				OBSTACLEWIDTH, BIGOBSTACLEHEIGHT, speed));
	}

	private void createTimers() {
		createPlayerMoveTimer();
		createObstaclesMoveTimer();
	}

	private void createPlayerMoveTimer() {
		playerMoveTimer = new Timer(15, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				player.move();
				player.jump();
				windowView.repaintView();
			}
		});
	}

	private void createObstaclesMoveTimer() {
		obstaclesMoveTimer = new Timer(15, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				moveObstacles();
				player.jump();
				checkPlayerCollision(e);
				incrementScore();
				controlObstacleInList();
				windowView.repaintView();
			}
		});
	}

	private void moveObstacles() {
		for (int i = 0; i < obstacleList.size(); i++)
			obstacleList.get(i).move();
	}

	private void checkPlayerCollision(ActionEvent e) {
		if (player.checkCollision(obstacleList.get(obstacleToCheck)))
			((Timer) e.getSource()).stop();
	}

	private void incrementScore() {
		if ((obstacleList.get(0).getXPosition() + obstacleList.get(0).getWidth()) < player.getXPosition()
				&& !obstacleAdded) {
			player.incrementScore(10);
			obstacleAdded = true;
			obstacleToCheck = 1;
			windowView.setScore(String.valueOf((player.getScore())));
		}
	}

	private void controlObstacleInList() {
		if ((obstacleList.get(0).getXPosition() + 20) <= 0) {
			obstacleList.remove(0);
			obstacleToCheck = 0;
			obstacleAdded = false;
			int choose = generator.nextInt(2);
			if (choose == 0)
				obstacleList.add(new ObstacleObject(Color.decode("#660000"), arrayOfLength[4], BIGOBSTACLEYPOSITION, 20,
						100, 1));
			else
				obstacleList.add(new ObstacleObject(Color.decode("#990000"), arrayOfLength[4], SMALLOBSTACLEYPOSITION,
						20, 50, 1));
			windowView.addObject("Obstacle", obstacleList);
		}
	}

	public void run() {
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

	private void addGameObjectsToMap() {
		windowView.addObject("Sky", sky);
		windowView.addObject("Floor", floor);
		windowView.addObject("Player", player);
		windowView.addObject("Obstacle", obstacleList);
		windowView.repaintView();
	}

	private void playerMoveTimerRunning() {
		windowView.deactiveButton();
		musicPlayer.playMusic();
		playerMoveTimer.start();
		while (playerMoveTimer.isRunning()) {
			if (player.getXPosition() == 195) {
				obstaclesMoveTimer.start();
			}
			if (player.getXPosition() == 200)
				playerMoveTimer.stop();
		}
	}

	private void clearAndShowScore() {
		player = null;
		floor = null;
		sky = null;
		obstacleList = null;
		obstacleAdded = false;
		obstacleToCheck = 0;
		createPlayerAndStaticGameObjects();
		createObstacleGameObjects();
		windowView.setScore(String.valueOf((player.getScore())));
		windowView.activeButton();
	}
}
