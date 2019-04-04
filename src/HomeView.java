import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.border.*;

public class HomeView extends JFrame {
	private DefaultListModel<String> listModel = new DefaultListModel<String>();
	private JPanel contentPane;
	private JTextField userText;
	private Map<String, Playlist> playlists = new HashMap<String, Playlist>();
	private PlaylistViewer viewer = new PlaylistViewer();

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
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		JList<String> playlistList = new JList<String>(listModel);
		playlistList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		playlistList.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				viewer.updateTo(playlists.get(playlistList.getSelectedValue()));
				viewer.setVisible(true);
			}
		});
		/*
		playlistList.addListSelectionListener(new ListSelectionListener() {
			
			@Override
			public void valueChanged(ListSelectionEvent e) {
				new PlaylistViewer(playlists.get(playlistList.getSelectedValue())).setVisible(true);
				System.out.println("changed");
			}
		});
		*/
		
		contentPane.add(playlistList, BorderLayout.CENTER);

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
				JTextField source = (JTextField)e.getSource();
				if(source.getText().equals(""))
					source.setText("Enter playlist name");
			}
			
			@Override
			public void focusGained(FocusEvent e) {
				// TODO Auto-generated method stub
				((JTextField)e.getSource()).setText("");
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
				playlists.remove(playlistList.getSelectedValue());
			}
		});
		panel_1.add(removePlaylistBtn);
		addPlaylistBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Playlist p = new Playlist(userText.getText());// take string from another frame as the playlist name
				playlists.put(userText.getText(), p);
				listModel.insertElementAt(p.getName(), 0);
			}
		});

		JLabel lblNewLabel = new JLabel("Playlists");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 20));
		contentPane.add(lblNewLabel, BorderLayout.NORTH);
	}
}
