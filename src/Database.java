import java.io.*;
import java.util.*;

public class Database {
	private Map<String, Song> database = new TreeMap<String, Song>();

	public Database() {
		try {
			generateDatabase();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.getStackTrace();
		}
	}

	private void generateDatabase() throws FileNotFoundException {
		InputStream is = Database.class.getResourceAsStream("songs.txt");
		Scanner infile = new Scanner(is);
		/*
		 * String title = ""; String artist = ""; String album = ""; String path = "";
		 */
		while (infile.hasNext()) {
			String line = infile.nextLine();
			String title = line.substring(0, line.indexOf("	"));
			line = line.substring(line.indexOf("	") + 1);
			String artist = line.substring(0, line.indexOf("	"));
			line = line.substring(line.indexOf("	") + 1);
			String album = line.substring(0, line.indexOf("	"));
			line = line.substring(line.indexOf("	") + 1);
			String path = line;
			database.put(title, new Song(title, artist, album, path));
		}
		infile.close();
	}

	public Song getSong(String key) {
		return database.get(key);
	}

	public void playDB() throws Exception {
		Song s = database.get("Bohemian Rhapsody");
		s.playFromStart();
		Thread.sleep(s.getRunningTimeInSeconds() * 1000);
	}

	public String toString() {
		String result = "";
		Set<String> keySet = database.keySet();
		for (String key : keySet)
			result += database.get(key) + "\n";

		return result;
	}

	public Set<String> getNames() {
		return database.keySet();
	}

	public String[] toArray() {
		String[] songs = new String[database.size()];
		Set<String> keySet = database.keySet();
		int i = 0;
		for (String key : keySet)
			songs[i++] = database.get(key).toString();
		return songs;
	}

	public Song getRandomSong(Song s) {
		Song rand = null;
		Set<String> keySet = database.keySet();
		Object[] keySetArray = keySet.toArray();
		if (s == null)
			return database.get(keySetArray[(int) (Math.random() * keySetArray.length)]);
		else {
			int randomInd = (int) (Math.random() * keySetArray.length);
			rand = database.get(keySetArray[randomInd]);
			while (rand.equals(s)) {
				randomInd = (int) (Math.random() * keySetArray.length);
				rand = database.get(keySetArray[randomInd]);
			}
			return rand;
		}
	}
}
