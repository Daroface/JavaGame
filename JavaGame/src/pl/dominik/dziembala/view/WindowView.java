package pl.dominik.dziembala.view;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import pl.dominik.dziembala.model.gameobject.GameObject;
import pl.dominik.dziembala.model.gameobject.ObstacleObject;

@SuppressWarnings("serial")
public class WindowView extends JPanel {

	/*
	 * Variables
	 */
	private JFrame window;
	private int height;
	private int width;
	private Map<String, ArrayList<GameObject>> objects;
	private JButton playButton;
	private JLabel scoreLabel;
	private JLabel playerLabel;
	private ArrayList<ObstacleObject> obstaclesLabelList;
	
	/*
	 * View's constructor.
	 */
	public WindowView(int width, int height, KeyListener keyListener, MouseListener mouseListener, JButton playButton,
			JLabel scoreLabel) {
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
		this.setLayout(null);
		this.playButton = playButton;
		this.scoreLabel = scoreLabel;
		this.add(this.playButton);
		this.add(this.scoreLabel);
		this.playButton.addMouseListener(mouseListener);
		this.scoreLabel.setVisible(false);
	}

	/*
	 * Method which paints graphics in window.
	 * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
	 */
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		if (!objects.isEmpty()) {
			paintGameObject(g2d, "Sky");
			paintGameObject(g2d, "Floor");
		}
		Toolkit.getDefaultToolkit().sync();
	}

	/*
	 * Method which paints specific object by name.
	 */
	private void paintGameObject(Graphics2D g2d, String name) {
		for (GameObject tmp : objects.get(name)) {
			g2d.setColor(tmp.getColor());
			g2d.fill(tmp.getShape());
		}
	}

	/*
	 * Method which adds object to array list.
	 */
	public void addObject(String name, GameObject gameObject) {
		ArrayList<GameObject> tmp = new ArrayList<>();
		tmp.add(gameObject);
		objects.put(name, tmp);

	}
	
	/*
	 * Method which adds player to window.
	 */
	public void addPlayerLabel(JLabel player) {
		this.playerLabel = player;
		this.add(playerLabel);
	}
	
	/*
	 * Method which adds obstacles to list and window.
	 */
	public void addObstacleLabelList(ArrayList<ObstacleObject> obstacleLabelList) {
		this.obstaclesLabelList = obstacleLabelList;
		for(int i=0; i < this.obstaclesLabelList.size(); i++) {
			this.add(this.obstaclesLabelList.get(i).getImageLabel());
		}
	}
	
	/*
	 * Method which refreshes obstacle list.
	 */
	public void refreshObstacleLabelList(ArrayList<ObstacleObject> obstacleLabelList) {
		for(int i=0; i < obstaclesLabelList.size(); i++) {
			this.remove(this.obstaclesLabelList.get(i).getImageLabel());
		}
		this.obstaclesLabelList = obstacleLabelList;
		for(int i=0; i < this.obstaclesLabelList.size(); i++) {
			this.add(this.obstaclesLabelList.get(i).getImageLabel());
		}
	}

	/*
	 * Method which calls repaint of window.
	 */
	public void repaintView() {
		this.repaint();
	}

	/*
	 * Method which deactivates play button and changes location of scores.
	 */
	public void deactivateButton() {
		playButton.setVisible(false);
		playButton.setFocusable(false);
		window.requestFocusInWindow();
		playButton.setText("Play Again");
		scoreLabel.setVisible(true);
		scoreLabel.setBounds(380, 0, 100, 50);
	}

	/*
	 * Method which activates play button and changes location of scores.
	 */
	public void activeButton() {
		this.remove(playerLabel);
		for(int i=0; i < obstaclesLabelList.size(); i++) {
			this.remove(this.obstaclesLabelList.get(i).getImageLabel());
		}		
		playButton.setVisible(true);
		playButton.setFocusable(true);
		scoreLabel.setBounds(190, 50, 100, 50);
	}
	
	/*
	 * Method which shows scores.
	 */
	public void showScore(String score) {
		scoreLabel.setText(score);
	}
}
