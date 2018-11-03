package pl.dominik.dziembala.model.gameobject;

import java.awt.Color;
import java.awt.geom.Rectangle2D;

public class ObstacleObject extends GameObject {

	public int speed;

	public ObstacleObject(Color color, int xPosition, int yPosition, int width, int height, int speed) {
		super(color, xPosition, yPosition, width, height);
		shape = new Rectangle2D.Double(xPosition, yPosition, width, height);
		this.speed = speed;
	}

	@Override
	public void move() {
		if ((xPosition + width) > 0) {
			xPosition -= speed;
			shape = null;
			shape = new Rectangle2D.Double(xPosition, yPosition, width, height);
		}
	}

	@Override
	public void jump() {
	}

}
