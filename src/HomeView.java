import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.border.*;

public class HomeView extends JPanel {
	//public static PlayingWindow playingWindow;
	private DefaultListModel<String> listModel = new DefaultListModel<String>();
	private JTextField userText;
	private Map<String, Playlist> playlistMap = new HashMap<String, Playlist>();
	private PlaylistViewer viewer;
	private JList<String> playlistList;

	
	public HomeView() {
		viewer = new PlaylistViewer();
		setBorder(new EmptyBorder(5, 5, 5, 5));
		setLayout(new BorderLayout(0, 0));

		JPanel panel = new JPanel();
		add(panel, BorderLayout.SOUTH);
		panel.setLayout(new GridLayout(0, 1, 0, 0));

		userText = new JTextField();
		userText.setText("Enter Playlist Name Here");
		panel.add(userText);
		userText.setColumns(10);
		userText.addFocusListener(new FocusListener() {

			@Override
			public void focusLost(FocusEvent e) {
				JTextField source = (JTextField) e.getSource();
				if (source.getText().equals(""))
					source.setText("Enter Playlist Name Here");
			}

			@Override
			public void focusGained(FocusEvent e) {
				// TODO Auto-generated method stub
				((JTextField) e.getSource()).setText("");
			}
		});

		JPanel panel_1 = new JPanel();
		panel.add(panel_1);
		panel_1.setLayout(new GridLayout(0, 1, 0, 0));

		JButton addPlaylistBtn = new JButton("Add Playlist");
		panel_1.add(addPlaylistBtn);


		addPlaylistBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (playlistMap.containsKey(userText.getText()))
					JOptionPane.showMessageDialog(getHomeView(), "Playlist already exists, choose another name");
				else if(userText.getText().equals("Enter Playlist Name Here"))
					JOptionPane.showMessageDialog(getHomeView(), "Playlist cannot be name '" + userText.getText() + "'");
				else {
					Playlist p = new Playlist(userText.getText());// take string from another frame as the playlist name
					playlistMap.put(userText.getText(), p);
					listModel.addElement(p.getName());
					userText.setText("Enter Playlist Name Here");
				}
			}
		});

		JLabel lblNewLabel = new JLabel("Playlists");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 20));
		add(lblNewLabel, BorderLayout.NORTH);

		JPanel panel_2 = new JPanel();
		add(panel_2, BorderLayout.CENTER);
		panel_2.setLayout(new GridLayout(0, 1, 0, 0));

		JList<String> playlistList = new JList<String>(listModel);
		panel_2.add(playlistList);
		playlistList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		playlistList.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// System.out.println("hello");
				JPopupMenu menu = new JPopupMenu();
				JMenuItem play = new JMenuItem("Play in Order");
				play.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						for(Song s : playlistMap.get(playlistList.getSelectedValue()).getPlaylist())
							Main.app.addToQueue(s);
					}
				});
				menu.add(play);
				
				JMenuItem edit = new JMenuItem("Edit Playlist");
				edit.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						viewer.updateTo(playlistMap.get(playlistList.getSelectedValue()));
						Main.app.updatePlaylistViewer(playlistMap.get(playlistList.getSelectedValue()));
						setVisible(false);
					}
				});
				menu.add(edit);
				
				JMenuItem remove = new JMenuItem("Remove Playlist");
				remove.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						playlistMap.remove(playlistList.getSelectedValue());
						listModel.remove(playlistList.getSelectedIndex());
						Main.app.updatePlaylistViewer(null);
					}
				});
				menu.add(remove);		
				
				menu.show(playlistList, e.getX(), e.getY());
			}
		});
	}
	
	public JPanel getHomeView() {
		return this;
	}
}