import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.eclipse.osgi.internal.log.Arguments;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.*;
import java.security.InvalidParameterException;
import java.util.Timer;

public class Song {
	private String title, artist, album, filepath;
	private Clip song;

	public Song(String title, String artist, String album, String path) {
		this.title = title;
		this.artist = artist;
		this.album = album;
		this.filepath = path;
		try {
			song = createSong();
		} catch (Exception e) {
			System.out.println("Error creating the song. Check constructor for " + this.title + ".");
		}
	}
	
	
	public String getInfo(Metadata data) throws InvalidParameterException {
		switch(data) {
		case TITLE:
			return title;
		case ARTIST:
			return artist;
		case ALBUM:
			return album;
		default:
			throw new InvalidParameterException();
		}
	}
	
	public int getRunningTimeInSeconds() {
		return (int)song.getMicrosecondLength() / 1000 / 1000;
	}
	
	public int getCurrentTimeInSeconds() {
		return (int)song.getMicrosecondPosition() / 1000 * 1000;
	}
	
	public void playFromStart() {
		song.setMicrosecondPosition(0);
		song.start();
	}
	
	public void playFromPoint(int seconds) {
		long position = (long)seconds * 1000 * 1000;
		song.stop();
		song.setMicrosecondPosition(position);
		song.start();
	}
	
	public void pause() {
		song.stop();
	}
	
	public void resume() {
		song.start();
	}
	
	public boolean isOver() {
		return song.getMicrosecondPosition() == song.getMicrosecondLength();
	}

	
	private Clip createSong() throws Exception {
		File audioFile = new File(filepath);
		Clip audio = AudioSystem.getClip();
		audio.open(AudioSystem.getAudioInputStream(audioFile));
		return audio;
	}	
}