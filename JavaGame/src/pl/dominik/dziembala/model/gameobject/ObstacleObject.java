package pl.dominik.dziembala.model.gameobject;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class ObstacleObject extends GameObject {

	/*
	 * Variable
	 */
	public double speed;

	/*
	 * Obstacle object's constructor.
	 */
	public ObstacleObject(String imageName, int xPosition, int yPosition, int width, int height, double speed) throws IOException {
		super(xPosition, yPosition, width, height);
		this.speed = speed;
		image = ImageIO.read(new File(imageName));
		imageLabel = new JLabel(new ImageIcon(image));
		imageLabel.setBounds(this.xPosition, this.yPosition, this.width, this.height);
	}
	
	/*
	 * Method which moves obstacle.
	 */
	public void move() {
		if ((xPosition + width) > 0) {
			xPosition -= speed;
			imageLabel.setBounds(this.xPosition, this.yPosition, this.width, this.height);
		}
	}
}
