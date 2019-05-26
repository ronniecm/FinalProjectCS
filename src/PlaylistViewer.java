
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;

public class PlaylistViewer extends JPanel {
	private JPanel btnPanel, songsPanel;
	private JLabel playlistLabel;
	private JButton removeBtn, backBtn, addBtn;
	private JList<String> databaseList, playList;
	private DefaultListModel<String> listModel = new DefaultListModel<String>();
	private Playlist m_playlist = null;

	public PlaylistViewer() {
		// configure JFrame
		setBorder(new EmptyBorder(5, 5, 5, 5));
		setLayout(new BorderLayout(0, 0));

		// title of playlist displayed at the top
		playlistLabel = new JLabel();
		playlistLabel.setFont(new Font("Calibri", Font.PLAIN, 24));
		playlistLabel.setHorizontalAlignment(SwingConstants.CENTER);
		add(playlistLabel, BorderLayout.NORTH);

		// btnPanel used for buttons at bottom
		btnPanel = new JPanel();
		add(btnPanel, BorderLayout.SOUTH);
		btnPanel.setLayout(new GridLayout(1, 0, 0, 0));

		// removeBtn used to remove a song
		removeBtn = new JButton("Remove Song");
		removeBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String selectedsong = playList.getSelectedValue();
				String title = selectedsong.substring(0, selectedsong.indexOf(" by"));
				m_playlist.removeSong(PlayingWindow.d.getSong(title));
				listModel.remove(playList.getSelectedIndex());
			}
		});
		btnPanel.add(removeBtn);

		// backBtn for going back to HomeView window
		backBtn = new JButton("Back");
		backBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Main.app.goHome();
			}
		});
		btnPanel.add(backBtn);

		// addBtn for adding songs to playlist
		addBtn = new JButton("Add Song");
		addBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String selectedSong = databaseList.getSelectedValue();
				String title = selectedSong.substring(0, selectedSong.indexOf(" by"));
				m_playlist.addSong(PlayingWindow.d.getSong(title));
				listModel.addElement(databaseList.getSelectedValue());
			}
		});
		btnPanel.add(addBtn);

		// songsPanel for two JLists
		songsPanel = new JPanel();
		add(songsPanel, BorderLayout.CENTER);
		songsPanel.setLayout(new GridLayout(1, 0, 0, 0));

		// playList displays songs in playlist
		playList = new JList<String>(listModel);
		playList.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				JPopupMenu menu = new JPopupMenu();
				JMenuItem play = new JMenuItem("Play Song");
				play.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						String selectedSong = playList.getSelectedValue();
						String title = selectedSong.substring(0, selectedSong.indexOf(" by"));

						try {
							Main.app.playSong(PlayingWindow.d.getSong(title));
						} catch (Exception ex) {
							ex.getStackTrace();
						}

					}
				});
				JMenuItem addToQueue = new JMenuItem("Add to Queue");
				addToQueue.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						String selectedSong = playList.getSelectedValue();
						String title = selectedSong.substring(0, selectedSong.indexOf(" by"));
						Main.app.addToQueue(PlayingWindow.d.getSong(title));
					}
				});
				JMenuItem removeFromPlaylist = new JMenuItem("Remove from Playlist");
				removeFromPlaylist.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						String selectedsong = playList.getSelectedValue();
						String title = selectedsong.substring(0, selectedsong.indexOf(" by"));
						m_playlist.removeSong(PlayingWindow.d.getSong(title));
						listModel.remove(playList.getSelectedIndex());
					}
				});
				menu.add(play);
				menu.add(addToQueue);
				menu.add(removeFromPlaylist);
				menu.show(playList, e.getX(), e.getY());
			}
		});
		songsPanel.add(playList);

		// scrollPane used to make database JList scrollable
		JScrollPane scrollPane = new JScrollPane();
		songsPanel.add(scrollPane);

		// databaseList used to view what songs are available to use
		databaseList = new JList<String>();
		databaseList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		databaseList.setListData(PlayingWindow.d.toArray());
		scrollPane.setViewportView(databaseList);
	}

	// closes PlaylistViewer window by calling setVisible()
	public void closeWindow() {
		setVisible(false);
		// Main.app.showHomeView();
	}

	// update certain components to match the given Playlist p
	public void updateTo(Playlist p) {
		if (p == null) {
			m_playlist = null;
			playlistLabel.setText("");
			playlistLabel.setHorizontalAlignment(SwingConstants.CENTER);
			listModel.clear();
			databaseList.clearSelection();
		} else {
			m_playlist = p;
			playlistLabel.setText(m_playlist.getName());
			playlistLabel.setHorizontalAlignment(SwingConstants.CENTER);
			listModel.clear();
			for (String s : m_playlist.toStringArray())
				listModel.addElement(s);
		}
	}
}