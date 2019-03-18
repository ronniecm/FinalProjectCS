import java.io.File;
import java.util.*;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


public class Database {
	private ArrayList<Song> db = new ArrayList<Song>();

	public Database() {
		try {
			generateDatabase();
		} catch(Exception e) {
			System.out.println("You have a " + e.getClass());
		}
	}
	
	private void generateDatabase() throws Exception {
		File file = new File("/Users/frcprogramming/Documents/songsv2.xlsx");
		
		int count = 0;
		XSSFWorkbook songSpreadsheet = new XSSFWorkbook(file);
		XSSFSheet sheet = songSpreadsheet.getSheetAt(0);
		
		for(Row row : sheet) {
			String[] data = new String[4];
			int i = 0;
			for(Cell c : row)
				data[i++] = c.getStringCellValue();
			
			db.add(new Song(data[0], data[1], data[2], data[3]));
			System.out.println(++count);
		}
		
		songSpreadsheet.close();
	}
	
	public String toString() {
		String result = "";
		for(Song s : db)
			result += s + "\n";
		
		return result;
			
	}
	
	public static void main(String[] args) {
		System.out.println(new Database());
	}
}
