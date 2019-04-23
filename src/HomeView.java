import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.border.*;

public class HomeView extends JFrame {
	public static PlayingWindow playingWindow;
	private DefaultListModel<String> listModel = new DefaultListModel<String>();
	private JPanel contentPane;
	private JTextField userText;
	private Map<String, Playlist> playlistMap = new HashMap<String, Playlist>();
	private PlaylistViewer viewer;
	private JList<String> playlistList;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					HomeView frame = new HomeView();
					frame.setLocation(0, 0);
					frame.setSize(1280, 730);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public HomeView() {
		try {
			playingWindow = new PlayingWindow();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		viewer = new PlaylistViewer();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 943, 624);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.SOUTH);
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
					source.setText("Enter playlist name");
			}

			@Override
			public void focusGained(FocusEvent e) {
				// TODO Auto-generated method stub
				((JTextField) e.getSource()).setText("");
			}
		});

		JPanel panel_1 = new JPanel();
		panel.add(panel_1);
		panel_1.setLayout(new GridLayout(0, 2, 0, 0));

		JButton addPlaylistBtn = new JButton("Add Playlist");
		panel_1.add(addPlaylistBtn);

		JButton removePlaylistBtn = new JButton("Remove Playlist");
		removePlaylistBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				playlistMap.remove(playlistList.getSelectedValue());
				listModel.remove(playlistList.getSelectedIndex());
			}
		});
		panel_1.add(removePlaylistBtn);
		addPlaylistBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (playlistMap.containsKey(userText.getText()))
					JOptionPane.showMessageDialog(contentPane, "Playlist already exists, choose another name");
				else {
					Playlist p = new Playlist(userText.getText());// take string from another frame as the playlist name
					playlistMap.put(userText.getText(), p);
					listModel.insertElementAt(p.getName(), 0);
				}
			}
		});

		JLabel lblNewLabel = new JLabel("Playlists");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 20));
		contentPane.add(lblNewLabel, BorderLayout.NORTH);

		JPanel panel_2 = new JPanel();
		contentPane.add(panel_2, BorderLayout.CENTER);
		panel_2.setLayout(new GridLayout(0, 1, 0, 0));

		JList<String> playlistList = new JList<String>(listModel);
		panel_2.add(playlistList);
		playlistList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		playlistList.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				viewer.updateTo(playlistMap.get(playlistList.getSelectedValue()));
				viewer.setVisible(true);
			}
		});
	}
}
