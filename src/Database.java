import java.io.File;
import java.util.*;

/*import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;*/


public class Database {
	//private ArrayList<Song> db = new ArrayList<Song>();
	private Map<String, Song> db2 = new HashMap<String, Song>();
	
	public Database() {
		try
		{
			generateDatabase();
		} catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void generateDatabase() throws Exception {
		Scanner infile = new Scanner(new File("songs.txt"));
		
		String temp;
		String title;
		String artist;
		String album;
		String path;
		for(int k = 0; k < db2.keySet().size(); k++)
		{
			temp = infile.nextLine();
			title = temp.substring(0, temp.indexOf("	"));
			temp = temp.substring(temp.indexOf("	"));
			artist = temp.substring(0, temp.indexOf("	"));
			temp = temp.substring(temp.indexOf("	"));
			album = temp.substring(0, temp.indexOf("	"));
			temp = temp.substring(temp.indexOf("	"));
			path = temp.substring(0, temp.indexOf("	"));		
			//db.add(new Song(title, artist, album, path));
			db2.put(artist, new Song(title, artist, album, path));
		}
		infile.close();
	}
	
	public Song getSong(String key) {
		return db2.get(key);
	}
	
	public void playDB() throws Exception {
		Song s = db2.get("Bohemian Rhapsody");
		s.playFromStart();
		Thread.sleep(s.getRunningTimeInSeconds() * 1000);
	}
	public String toString() {
		String result = "";
		Set<String> keySet = db2.keySet();
		for(String key : keySet)
			result += db2.get(key) + "\n";
		
		return result;
	}
	
	public Set<String> getNames() {
		return db2.keySet();
	}
	
	public String[] toArray() {
		String[] songs = new String[db2.size()];
		Set<String> keySet = db2.keySet();
		int i = 0;
		for(String key : keySet)
			songs[i++] = db2.get(key).toString();
		return songs;
	}
	
	public Song getRandomSong() {
		Set<String> keySet = db2.keySet();
		Object[] keySetArray = keySet.toArray();
		int randomInd = (int)(Math.random() * keySetArray.length);
		return db2.get(keySetArray[randomInd]);
	}
}
