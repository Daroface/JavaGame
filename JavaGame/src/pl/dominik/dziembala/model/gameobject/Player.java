package pl.dominik.dziembala.model.gameobject;

import java.awt.Color;
import java.awt.geom.*;

public class Player extends GameObject {

	private int jumpAmount = 0;
	private static final int JUMPVALUE = 70;
	private static final double JUMPWIDTH = 130.0;
	private double jumpWay = 0.0;
	private double doubleJumpWay = 0.0;
	private double speed = 1.0;
	private int normalYPosition;
	private int actualYPosition;
	private volatile int score = 0;

	public Player(Color color, int xPosition, int yPosition, int diameter) {
		super(color, xPosition, yPosition, diameter, diameter);
		normalYPosition = yPosition;
		actualYPosition = yPosition;
		shape = new Ellipse2D.Double(this.xPosition, this.yPosition, this.width, this.height);
	}

	@Override
	public void move() {
		if (xPosition < 200) {
			xPosition += speed;
			shape = null;
			shape = new Ellipse2D.Double(xPosition, yPosition, width, height);
		}
	}

	public void jump() {
		switch (jumpAmount) {
		case 1: {
			if (jumpWay / JUMPWIDTH != 1.0) {
				jumpWay += speed;
				yPosition = normalYPosition
						- (int) (JUMPVALUE * Math.sin((jumpWay / JUMPWIDTH * Math.toRadians(180.0))));
			} else {
				jumpWay = 0.0;
				jumpAmount = 0;
			}
			break;
		}

		case 2: {
			if (yPosition < normalYPosition) {
				doubleJumpWay += speed;
				yPosition = actualYPosition
						- (int) ((JUMPVALUE) * Math.sin((doubleJumpWay / (JUMPWIDTH) * Math.toRadians(180.0))));
			} else {
				doubleJumpWay = 0.0;
				jumpWay = 0.0;
				jumpAmount = 0;
			}
		}
		default: {
			break;
		}
		}
		if (jumpAmount != 0) {

			shape = null;
			shape = new Ellipse2D.Double(xPosition, yPosition, width, height);
		}
	}

	public void increaseJumpAmount() {
		switch (jumpAmount) {
		case 0: {
			jumpAmount = 1;
			break;
		}
		case 1: {
			jumpAmount = 2;
			actualYPosition = yPosition;
			break;
		}
		default: {
		}
		}
	}

	public boolean checkCollision(GameObject obstacle) {
		double collisionX1Position = this.xPosition + 0.97;
		double collisionX2Position = this.xPosition + this.width - 0.97;
		double collisionY1Position = this.yPosition + this.height - 0.97;
		if ((double) (obstacle.yPosition)  > collisionY1Position)
			return false;
		else if ((double) (obstacle.xPosition) > collisionX2Position)
			return false;
		else if ((double) (obstacle.xPosition + obstacle.width) <= collisionX1Position)
			return false;
		else
			return true;
	}

	public void incrementScore(int points) {
		score += points;
	}
	
	public int getScore() {
		return score;
	}
}
