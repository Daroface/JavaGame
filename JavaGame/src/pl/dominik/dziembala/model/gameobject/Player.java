package pl.dominik.dziembala.model.gameobject;

import java.awt.geom.*;

public class Player implements GameObject {
	
	private static final int DIAMETER = 10;
	private Ellipse2D circle;
	private int xPosition = 0;
	private int yPosition = 250;
	
	public Player() {
		circle = new Ellipse2D.Double(xPosition, yPosition, DIAMETER, DIAMETER);
	}
	
	public Ellipse2D getShape() {
		return circle;
	}
	
	public void moveX() {
		if(xPosition < 300) {
			xPosition += 1;
			circle = new Ellipse2D.Double(xPosition, yPosition, DIAMETER, DIAMETER);	
		}			
	}

}
