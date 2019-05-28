import java.util.*;
import java.io.*;

public class ArtworkLoader {
	private Map<String, String> filepathMap = new TreeMap<String, String>();

	public ArtworkLoader() {
		try {
			generateLoader();
		} catch (Exception e) {
			e.getStackTrace();
		}
	}

	private void generateLoader() throws Exception {
		InputStream is = Database.class.getResourceAsStream("artwork.txt");
		Scanner infile = new Scanner(is);
		String temp;
		while (infile.hasNextLine()) {
			temp = infile.nextLine();
			filepathMap.put(temp.substring(0, temp.indexOf("	")), temp.substring(temp.indexOf("Song")));

		}
		infile.close();

	}

	public String loadFilepath(String albumName) {
		return filepathMap.get(albumName);
	}

	public String toString() {
		return filepathMap.toString();
	}
}
