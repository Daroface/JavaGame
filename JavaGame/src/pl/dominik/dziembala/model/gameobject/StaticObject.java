package pl.dominik.dziembala.model.gameobject;

import java.awt.Color;
import java.awt.geom.Rectangle2D;

public class StaticObject extends GameObject {

	/*
	 * Static object's constructor.
	 */
	public StaticObject(Color color, int xPosition, int yPosition, int width, int height) {
		super(xPosition, yPosition, width, height);
		this.color = color;
		this.shape = new Rectangle2D.Double(this.xPosition, this.yPosition, this.width, this.height);
	}
	
}
