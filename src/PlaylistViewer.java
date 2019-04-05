import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;

public class PlaylistViewer extends JFrame {

	private JPanel contentPane, btnPanel, songsPanel;
	private JLabel playlistLabel;
	private JButton removeBtn, backBtn, addBtn;
	private JList<String> databaseList, playList;
	private DefaultListModel<String> listModel = new DefaultListModel<String>();
	private static Database d = new Database();
	private Playlist m_playlist;
	
	public PlaylistViewer() {
		//configure JFrame
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(0, 0, 1280, 730);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		//title of playlist displayed at the top
		playlistLabel = new JLabel();
		playlistLabel.setFont(new Font("Calibri", Font.PLAIN, 24));
		playlistLabel.setHorizontalAlignment(SwingConstants.CENTER);
		contentPane.add(playlistLabel, BorderLayout.NORTH);
		
		//btnPanel used for buttons at bottom
		btnPanel = new JPanel();
		contentPane.add(btnPanel, BorderLayout.SOUTH);
		btnPanel.setLayout(new GridLayout(1, 0, 0, 0));
		
		//removeBtn used to remove a song
		removeBtn = new JButton("Remove Song");
		removeBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String selectedsong = playList.getSelectedValue();
				String title = selectedsong.substring(0, selectedsong.indexOf(" by"));
				m_playlist.removeSong(d.getSong(title));
				listModel.remove(playList.getSelectedIndex());
			}
		});
		btnPanel.add(removeBtn);
		
		//backBtn for going back to HomeView window
		backBtn = new JButton("Back");
		backBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				closeWindow();
			}
		});
		btnPanel.add(backBtn);
		
		//addBtn for adding songs to playlist
		addBtn = new JButton("Add Song");
		addBtn.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				String selectedSong = databaseList.getSelectedValue();
				String title = selectedSong.substring(0, selectedSong.indexOf(" by"));
				m_playlist.addSong(d.getSong(title));
				listModel.addElement(databaseList.getSelectedValue());
			}
		});
		btnPanel.add(addBtn);
		
		//songsPanel for two JLists
		songsPanel = new JPanel();
		contentPane.add(songsPanel, BorderLayout.CENTER);
		songsPanel.setLayout(new GridLayout(1, 0, 0, 0));
		
		//playList displays songs in playlist
		playList = new JList<String>(listModel);
		songsPanel.add(playList);
		
		//scrollPane used to make database JList scrollable
		JScrollPane scrollPane = new JScrollPane();
		songsPanel.add(scrollPane);
		
		//databaseList used to view what songs are available to use
		databaseList = new JList<String>();
		databaseList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		databaseList.setListData(d.toArray());
		scrollPane.setViewportView(databaseList);
	}
	
	//closes PlaylistViewer window by calling setVisible()
	public void closeWindow() {
		setVisible(false);
	}
	
	//update certain components to match the given Playlist p
	public void updateTo(Playlist p) {
		m_playlist = p;
		playlistLabel.setText(m_playlist.getName());
		playlistLabel.setHorizontalAlignment(SwingConstants.CENTER);
		listModel.clear();
		for(String s : m_playlist.toStringArray())
			listModel.addElement(s);
	}

}