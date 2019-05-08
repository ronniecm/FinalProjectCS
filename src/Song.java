import javax.sound.sampled.*;
import java.io.*;
import java.security.InvalidParameterException;

public class Song {
	private String title, artist, album, filepath, artworkFilePath;
	private Clip song;
	private AudioInputStream ais;
	private ArtworkLoader artworkLoader = new ArtworkLoader();
	private File audioFile;

	public Song(String title, String artist, String album, String path) {
		this.title = title;
		this.artist = artist;
		this.album = album;
		this.filepath = path;
		artworkFilePath = artworkLoader.loadFilepath(album);
		try {
			audioFile = new File(filepath);
			song = createSong();
		} catch (Exception e) {
			System.out.println("Error creating the song. Check constructor for " + this.title + ".");
		}
	}

	public String getInfo(Metadata data) throws InvalidParameterException {
		switch (data) {
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
		return (int) song.getMicrosecondLength() / 1000 / 1000;
	}

	public int getCurrentTimeInSeconds() {
		return (int) song.getMicrosecondPosition() / 1000 / 1000;
	}

	public void playFromStart() throws Exception {
		song.open(AudioSystem.getAudioInputStream(audioFile));
		song.setMicrosecondPosition(0);
		song.start();
	}

	public void playFromPoint(int seconds, boolean isPaused) {
		long position = (long) seconds * 1000 * 1000;
		pause();
		song.setMicrosecondPosition(position);
		if(!isPaused)
			song.start();
	}

	public void pause() {
		song.stop();
	}
	
	public void stop() {
		song.close();
	}
	public void resume() {
		song.start();
	}

	public boolean isOver() {
		
		return song.isOpen() && getCurrentTimeInSeconds() == getRunningTimeInSeconds();
	}
	
	public String getArtworkPath() {
		return artworkFilePath;
	}
	
	public String toString() {
		return title + " by " + artist;
	}
	
	public int hashCode() {
		return toString().hashCode();
	}
	private Clip createSong() throws Exception {
		Clip audio = AudioSystem.getClip();
		//audio.open(AudioSystem.getAudioInputStream(audioFile));
		//audio.start();
		return audio;
	}
}