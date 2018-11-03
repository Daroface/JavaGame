package pl.dominik.dziembala.model.gameobject;

import java.awt.geom.Line2D;

public class Floor implements GameObject {
	
	private static final int BEGINXPOSITION = 0;
	private int yPosition;
	private int endXPosition;
	private Line2D line;
	
	public Floor(int width, int height) {
		this.endXPosition = width;
		this.yPosition = (int)(height * 0.9);
		line = new Line2D.Double(BEGINXPOSITION, yPosition, endXPosition, yPosition);
	}
	
	public Line2D getShape()
	{
		return line;
	}
	
	public void moveX() {
		
	}

}
