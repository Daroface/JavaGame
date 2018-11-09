package pl.dominik.dziembala.model;

import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class MusicPlayer {

	/*
	 * Variables
	 */
	private volatile String name;
	AudioInputStream audioInputStream;
	Clip clip;
	
	/*
	 * Music player's constructor.
	 */
	public MusicPlayer(String name) throws Exception {
		this.name = name;		
		audioInputStream = AudioSystem.getAudioInputStream(new File(this.name));
		clip = AudioSystem.getClip();
		clip.open(audioInputStream);		
	}
	
	/*
	 * Method which plays music.
	 */
	public void playMusic() {
		clip.setMicrosecondPosition(0);
		clip.loop(Clip.LOOP_CONTINUOUSLY);
		clip.start();		
	}
	
	/*
	 * Method which stops music.
	 */
	public void stopMusic() {
		clip.stop();
	}

	
}
