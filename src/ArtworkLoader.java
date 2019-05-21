import java.util.*;
import java.io.*;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ArtworkLoader {
	private Map<String, String> filepathMap = new HashMap<String, String>();
	
	public ArtworkLoader() {
		try {
			generateLoader();			
		} catch(Exception e) {
			e.getStackTrace();
		}
	}
	
	private void generateLoader() throws Exception {
		File artworkSpreadsheet = new File("songsv2.xlsx");
		XSSFWorkbook workbook = new XSSFWorkbook(artworkSpreadsheet);
		XSSFSheet sheet = workbook.getSheetAt(1);
		
		for(Row row : sheet) {
			String key = row.getCell(0).getStringCellValue();
			String value = row.getCell(1).getStringCellValue();
			
			filepathMap.put(key, value);
		}
		
		workbook.close();
	}
	
	public String loadFilepath(String songName) {
		return filepathMap.get(songName);
	}
	
	public String toString() {
		return filepathMap.toString();
	}
	
	public static void main(String[] args) {
		System.out.println(new ArtworkLoader());
	}
}
