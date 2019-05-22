import java.util.*;
import java.io.*;

/*
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
*/

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
		Scanner infile = new Scanner(new File("songs.txt"));
		
		String temp;
		while(infile.hasNextLine())
		{
			temp = infile.nextLine();
			filepathMap.put(temp.substring(0, temp.indexOf("	")), 
							temp.substring(temp.indexOf("/")));
		}
		
		infile.close();
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
