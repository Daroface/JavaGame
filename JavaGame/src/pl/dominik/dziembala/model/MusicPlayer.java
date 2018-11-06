package pl.dominik.dziembala.model;

import java.awt.Toolkit;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import javafx.application.Application;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;
import sun.audio.AudioData;
import sun.audio.AudioPlayer;
import sun.audio.AudioStream;
import sun.audio.ContinuousAudioDataStream;


public class MusicPlayer {

	private String name;
	AudioInputStream audioInputStream;
	Clip clip;
	
	public MusicPlayer(String name) throws Exception {
		this.name = name;		
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
