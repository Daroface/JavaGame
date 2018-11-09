package pl.dominik.dziembala.model.gameobject;

import java.awt.Color;
import java.awt.Shape;
import java.awt.image.BufferedImage;
import javax.swing.JLabel;

public abstract class GameObject {

	/*
	 * Variables
	 */
	protected int xPosition;
	protected int yPosition;
	protected int width;
	protected int height;
	protected Color color;
	protected Shape shape;	
	protected BufferedImage image;	
	protected JLabel imageLabel;

	/*
	 * Game object's constructor.
	 */
	protected GameObject(int xPosition, int yPosition, int width, int height) {
		this.xPosition = xPosition;
		this.yPosition = yPosition;
		this.width = width;
		this.height = height;
	}

	/*
	 * Method which returns object's shape.
	 */
	public Shape getShape() {
		return shape;
	}

	/*
	 * Method which returns object's color.
	 */
	public Color getColor() {
		return color;
	}
	
	/*
	 * Method which returns object's position in the x-axis.
	 */
	public int getXPosition() {
		return xPosition;
	}
	
	/*
	 * Method which returns object's width.
	 */
	public int getWidth() {
		return width;
	}
	
	/*
	 * Method which returns object's image.
	 */
	public JLabel getImageLabel() {
		return imageLabel;
	}
}
