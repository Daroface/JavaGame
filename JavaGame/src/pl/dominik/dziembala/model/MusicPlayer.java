package pl.dominik.dziembala.model;

import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class MusicPlayer {

	private volatile String name;
	AudioInputStream audioInputStream;
	Clip clip;
	
	public MusicPlayer(String name) throws Exception {
		this.name = name;		
		System.out.println(this.name);
		audioInputStream = AudioSystem.getAudioInputStream(new File(this.name));
		clip = AudioSystem.getClip();
		clip.open(audioInputStream);		
	}
	
	public void playMusic() {
		clip.setMicrosecondPosition(0);
		clip.loop(Clip.LOOP_CONTINUOUSLY);
		clip.start();		
	}
	
	public void stopMusic() {
		clip.stop();
	}

	
}
