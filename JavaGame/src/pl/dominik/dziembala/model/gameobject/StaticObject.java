package pl.dominik.dziembala.model.gameobject;

import java.awt.Color;
import java.awt.geom.Rectangle2D;

public class StaticObject extends GameObject {

	public StaticObject(Color color, int xPosition, int yPosition, int width, int height) {
		super(color, xPosition, yPosition, width, height);
		this.shape = new Rectangle2D.Double(this.xPosition, this.yPosition, this.width, this.height);
	}

	@Override
	public void move() {		
	}

	@Override
	public void jump() {		
	}

}
