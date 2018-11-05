package pl.dominik.dziembala.controller;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.Timer;

import pl.dominik.dziembala.model.gameobject.*;
import pl.dominik.dziembala.view.WindowView;

public class GameController {

	private volatile WindowView windowView;
	private Player player;
	private StaticObject floor;
	private StaticObject sky;
	private volatile ArrayList<GameObject> obstacleList;
	private static final int HEIGHT = 300;
	private static final int WIDTH = 480;
	private static final int LENGTH = 200;
	private static final int BIGOBSTACLEYPOSITION = (int) (HEIGHT * 0.7) - 100;
	private static final int SMALLOBSTACLEYPOSITION = (int) (HEIGHT * 0.7) - 50;
	private int arrayOfLength[];
	private Timer playerMoveTimer;
	private Timer obstaclesMoveTimer;
	private Random generator;
	private KeyListener keyListener;
	private boolean keyIsDown = false;
	private int obstacleToCheck = 0;

	public GameController() {
		createKeyListener();
		windowView = new WindowView(WIDTH, HEIGHT, keyListener);
		createPlayerAndStaticGameObjects();
		createArrayOfLength();
		generator = new Random();
		createObstacleGameObjects();
		createTimers();
	}

	private void createArrayOfLength() {
		arrayOfLength = new int[5];
		for (int i = 0; i < 4; i++) {
			arrayOfLength[i] = WIDTH + 20 * i + LENGTH * i;
		}
		arrayOfLength[4] = 880;
	}

	private void createPlayerAndStaticGameObjects() {
		sky = new StaticObject(Color.decode("#9393FF"), 0, 0, WIDTH, (int) (HEIGHT * 0.7));
		floor = new StaticObject(Color.decode("#39C939"), 0, (int) (HEIGHT * 0.7), WIDTH, (int) (HEIGHT * 0.2));
		player = new Player(Color.decode("#000000"), 0, (int) (HEIGHT * 0.7) - 30, 30);
	}

	private void createObstacleGameObjects() {
		obstacleList = new ArrayList<>();
		obstacleList
				.add(new ObstacleObject(Color.decode("#990000"), arrayOfLength[0], SMALLOBSTACLEYPOSITION, 20, 50, 1));
		obstacleList
				.add(new ObstacleObject(Color.decode("#990000"), arrayOfLength[1], SMALLOBSTACLEYPOSITION, 20, 50, 1));
		obstacleList
				.add(new ObstacleObject(Color.decode("#660000"), arrayOfLength[2], BIGOBSTACLEYPOSITION, 20, 100, 1));
		obstacleList
				.add(new ObstacleObject(Color.decode("#660000"), arrayOfLength[3], BIGOBSTACLEYPOSITION, 20, 100, 1));
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
				for (int i = 0; i < obstacleList.size(); i++)
					obstacleList.get(i).move();
				player.jump();
				if ((obstacleToCheck == 0) && (obstacleList.get(0).getXPosition()
						+ obstacleList.get(0).getWidth() < player.getXPosition()))
					obstacleToCheck = 1;
				if (player.checkCollision(obstacleList.get(0)))
						((Timer) e.getSource()).stop();
				if ((obstacleList.get(0).getXPosition() + 20) <= 0)
					controlObstacleInList();
				windowView.repaintView();
			}
		});
	}
		
	public void run() {
		addPlayerAndStaticObjectsToMap();
		addObstacleObjectsToMap();
		windowView.repaintView();
		playerMoveTimer.start();
		while (playerMoveTimer.isRunning()) {
			if (player.getXPosition() == 195) {
				obstaclesMoveTimer.start();
			}

			if (player.getXPosition() == 200)
				playerMoveTimer.stop();
		}

	}

	private void addPlayerAndStaticObjectsToMap() {
		windowView.addObject("Sky", sky);
		windowView.addObject("Floor", floor);
		windowView.addObject("Player", player);
	}

	private void addObstacleObjectsToMap() {
		windowView.addObject("Obstacle", obstacleList);
	}

	private void controlObstacleInList() {
		obstacleList.remove(0);
		int choose = generator.nextInt(2);
		if (choose == 0)
			obstacleList.add(
					new ObstacleObject(Color.decode("#660000"), arrayOfLength[4], BIGOBSTACLEYPOSITION, 20, 100, 1));
		else
			obstacleList.add(
					new ObstacleObject(Color.decode("#990000"), arrayOfLength[4], SMALLOBSTACLEYPOSITION, 20, 50, 1));
		windowView.addObject("Obstacle", obstacleList);
	}

}
