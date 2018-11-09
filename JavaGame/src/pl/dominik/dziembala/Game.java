package pl.dominik.dziembala;

import pl.dominik.dziembala.controller.GameController;

public class Game {
	
	public static void main(String[] args) {
		try {
		GameController gameController = new GameController();
		gameController.run();
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
		}
		
	}

}
