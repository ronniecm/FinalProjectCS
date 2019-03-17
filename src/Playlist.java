import java.util.ArrayList;

public class Playlist
{
	private String name;
	private ArrayList<Song> list;
	private int index;

	public Playlist(String n)
	{
		name = n;
		list = new ArrayList<Song>();
		index = 0;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String n)
	{
		name = n;
	}

	public void addSong(Song s)
	{
		list.add(s);
	}

	public void removeSong(Song s)
	{
		list.remove(s);
	}

	public Song getCurrentSong()
	{
		return list.get(index);
	}
	
	public int getSize()
	{
		return list.size();
	}
	
	public Song skip()
	{
		return list.get(++index);
	}
	
	public Song previous()
	{
		return list.get(--index);
	}

	public Song[] shuffle()
	{
		int shuffleIndex = (int) (Math.random() * list.size());
		Song[] shuffledSongs = new Song[list.size()];
		for (int k = 0; k < shuffledSongs.length; k++)
		{
			while (shuffledSongs[shuffleIndex] != null)
			{
				shuffleIndex = (int) (Math.random() * list.size());
			}
			shuffledSongs[shuffleIndex] = list.get(k);
		}

		return shuffledSongs;
	}

}
