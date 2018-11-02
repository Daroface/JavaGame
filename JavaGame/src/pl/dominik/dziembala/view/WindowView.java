package pl.dominik.dziembala.view;

import javax.swing.JFrame;

public class WindowView {
	
	private JFrame window;
	
	public WindowView() {
		window = new JFrame("Java Game");
		window.setSize(486, 300);
		window.setVisible(true);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);		
	}

}
