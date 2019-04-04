import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;

public class PlaylistViewer extends JFrame {

	private JPanel contentPane, btnPanel, songsPanel;
	private DefaultListModel<String> listModel = new DefaultListModel<String>();
	private Database d = new Database();
	private JLabel playlistLabel;
	private JButton removeBtn, backBtn, addBtn;
	private JList<String> databaseList, playList;
	private Playlist m_playlist;
	/**
	 * Create the frame.
	 */
	public PlaylistViewer() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(0, 0, 1280, 730);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		playlistLabel = new JLabel();
		playlistLabel.setHorizontalAlignment(SwingConstants.CENTER);
		contentPane.add(playlistLabel, BorderLayout.NORTH);
		
		btnPanel = new JPanel();
		contentPane.add(btnPanel, BorderLayout.SOUTH);
		btnPanel.setLayout(new GridLayout(1, 0, 0, 0));
		
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
		
		backBtn = new JButton("Back");
		backBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
			}
		});
		btnPanel.add(backBtn);
		
		addBtn = new JButton("Add Song");
		btnPanel.add(addBtn);
		
		songsPanel = new JPanel();
		contentPane.add(songsPanel, BorderLayout.CENTER);
		songsPanel.setLayout(new GridLayout(1, 0, 0, 0));
		
		playList = new JList<String>(listModel);
		songsPanel.add(playList);
				
		JScrollPane scrollPane = new JScrollPane();
		songsPanel.add(scrollPane);
		
		databaseList = new JList<String>();
		databaseList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		databaseList.setListData(d.toArray());
		scrollPane.setViewportView(databaseList);

		backBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				closeWindow();
			}
		});
		
		addBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String selectedSong = databaseList.getSelectedValue();
				String title = selectedSong.substring(0, selectedSong.indexOf(" by"));
				m_playlist.addSong(d.getSong(title));
				listModel.addElement(databaseList.getSelectedValue());
			}
		});
	}
	
	public void closeWindow() {
		setVisible(false);
	}
	
	public void updateTo(Playlist p) {
		m_playlist = p;
		playlistLabel.setText(m_playlist.getName());
		playlistLabel.setHorizontalAlignment(SwingConstants.CENTER);
		listModel.clear();
		for(String s : m_playlist.toStringArray())
			listModel.addElement(s);
	}

}
