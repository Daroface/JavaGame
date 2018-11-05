package pl.dominik.dziembala.model.gameobject;

import java.awt.Color;
import java.awt.Shape;

public abstract class GameObject {

	protected int xPosition;

	protected int yPosition;

	protected int width;

	protected int height;

	protected Color color;

	protected Shape shape;

	protected GameObject(Color color, int xPosition, int yPosition, int width, int height) {
		this.color = color;
		this.xPosition = xPosition;
		this.yPosition = yPosition;
		this.width = width;
		this.height = height;
	}

	public Shape getShape() {
		return shape;
	}

	public abstract void move();

	public Color getColor() {
		return color;
	}
	
	public int getXPosition() {
		return xPosition;
	}
	
	public int getWidth() {
		return width;
	}
}
