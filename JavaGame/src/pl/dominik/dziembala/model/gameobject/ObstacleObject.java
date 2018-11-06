package pl.dominik.dziembala.model.gameobject;

import java.awt.Color;
import java.awt.geom.Rectangle2D;

public class ObstacleObject extends GameObject {

	public double speed;

	public ObstacleObject(Color color, int xPosition, int yPosition, int width, int height, double speed) {
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
}
