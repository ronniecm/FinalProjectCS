import java.util.*;

public class Playlist {
	private String name;
	private ArrayList<Song> list;
	private int index;

	public Playlist(String n) {
		name = n;
		list = new ArrayList<Song>();
		index = 0;
	}

	public String getName() {
		return name;
	}

	public void setName(String n) {
		name = n;
	}

	public void addSong(Song s) {
		list.add(s);
	}

	public void removeSong(Song s) {
		list.remove(s);
	}

	public Song getCurrentSong() {
		return list.get(index);
	}

	public int getSize() {
		return list.size();
	}

	public Song skip() {
		return list.get(++index);
	}

	public Song previous() {
		return list.get(--index);
	}

	public List<Song> getPlaylist() {
		return list;
	}

	public String[] toStringArray() {
		String[] result = new String[list.size()];
		for (int i = 0; i < result.length; i++)
			result[i] = list.get(i).toString();

		return result;
	}

	public String toString() {
		String result = this.name + "\n";
		for (Song s : list)
			result += s.toString();

		return result;
	}
}
