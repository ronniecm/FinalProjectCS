import java.io.File;
import java.util.*;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


public class Database {
	private ArrayList<Song> db = new ArrayList<Song>();
	private Map<String, Song> db2 = new HashMap<String, Song>();

	public Database() {
		try {
			generateDatabase();
		} catch(Exception e) {
			System.out.println("You have a " + e.getClass());
		}
	}
	
	private void generateDatabase() throws Exception {
		File file = new File("songsv2.xlsx");
		
		XSSFWorkbook songSpreadsheet = new XSSFWorkbook(file);
		XSSFSheet sheet = songSpreadsheet.getSheetAt(0);
		
		int j = 0;
		for(Row row : sheet) {
			String[] data = new String[4];
			int i = 0;
			for(Cell c : row)
				data[i++] = c.getStringCellValue();
			
			Song s = new Song(data[0], data[1], data[2], data[3]);
			db2.put(data[0], s);
			j++;
		}
		
		songSpreadsheet.close();
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
