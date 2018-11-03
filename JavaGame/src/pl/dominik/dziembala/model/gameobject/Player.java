package pl.dominik.dziembala.model.gameobject;

import java.awt.Color;
import java.awt.geom.*;

public class Player extends GameObject {
		
	private int life;
	private static final int MAXJUMPAMOUNT = 2;
	private int jumpAmount;
	private static final int JUMPVALUE = 50;
	private int speed = 1;
	
	public Player(Color color, int xPosition, int yPosition, int diameter) {
		super(color, xPosition, yPosition, diameter, diameter);
		shape = new Ellipse2D.Double(this.xPosition, this.yPosition, this.width, this.height);
		life = 1;
		jumpAmount = 0;
	}
		
	@Override
	public void move() {
		if(xPosition < 200) {
			xPosition += speed;
			shape = new Ellipse2D.Double(xPosition, yPosition, width, height);	
		}			
	}

}
