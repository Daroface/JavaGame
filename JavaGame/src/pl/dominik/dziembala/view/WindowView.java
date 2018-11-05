package pl.dominik.dziembala.view;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JPanel;

import pl.dominik.dziembala.model.gameobject.GameObject;

@SuppressWarnings("serial")
public class WindowView extends JPanel {

	private JFrame window;
	private int height;
	private int width;
	private Map<String, ArrayList<GameObject>> objects;

	public WindowView(int width, int height, KeyListener keyListener) {
		this.height = height;
		this.width = width;
		objects = new HashMap<>();
		window = new JFrame("Java Game");
		window.setSize(this.width, this.height);
		window.setVisible(true);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setResizable(false);
		window.add(this);
		window.addKeyListener(keyListener);
	}

	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;		
		if(!objects.isEmpty()) {
			paintGameObject(g2d, "Sky");
			paintGameObject(g2d, "Floor");
			paintGameObject(g2d, "Obstacle");
			paintGameObject(g2d, "Player");
		}		
		Toolkit.getDefaultToolkit().sync();
	}
	
	private void paintGameObject(Graphics2D g2d, String name) {
		for(GameObject tmp : objects.get(name)) {
			g2d.setColor(tmp.getColor());
			g2d.fill(tmp.getShape());	
		}
			
	}
	
	public void addObject(String name, GameObject gameObject) {
		ArrayList<GameObject> tmp = new ArrayList<>();
		tmp.add(gameObject);
		objects.put(name, tmp);
		
	}
	
	public void addObject(String name, ArrayList<GameObject> gameObjects) {
		objects.put(name, gameObjects);		
	}
	
	public void repaintView() {
		this.repaint();
	}
}
