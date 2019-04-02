
// Credits: https://docs.oracle.com/javase/tutorial/uiswing/components/list.html#mutable
//			https://www.dreamincode.net/forums/topic/339909-text-field-text-overlay/

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JList;
import javax.swing.JButton;
import java.awt.GridLayout;
import javax.swing.AbstractListModel;
import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.JTextField;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

public class HomeView extends JFrame
{
	private DefaultListModel listModel = new DefaultListModel();
	private JPanel contentPane;
	private JTextField userText;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args)
	{
		EventQueue.invokeLater(new Runnable()
		{
			public void run()
			{
				try
				{
					HomeView frame = new HomeView();
					frame.setVisible(true);
				} catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public HomeView()
	{
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		JList playlistList = new JList(listModel);

		contentPane.add(playlistList, BorderLayout.CENTER);

		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.SOUTH);
		panel.setLayout(new GridLayout(0, 1, 0, 0));

		userText = new JTextField();
		userText.setText("Enter PlayList Name Here");
		userText.addFocusListener(new FocusAdapter()
		{
			public void focusGained(FocusEvent arg0)
			{
				if (userText.getText().equals("Enter Playlist Name Here"))
					userText.setText("");
			}

			public void focusLost(FocusEvent e)
			{
				if (userText.getText().equals(""))
					userText.setText("Enter Playlist Name Here");
			}
		});

		panel.add(userText);
		userText.setColumns(10);

		JPanel panel_1 = new JPanel();
		panel.add(panel_1);
		panel_1.setLayout(new GridLayout(0, 2, 0, 0));

		JButton addPlaylistBtn = new JButton("Add Playlist");
		panel_1.add(addPlaylistBtn);

		JButton removePlaylistBtn = new JButton("Remove Playlist");
		panel_1.add(removePlaylistBtn);
		removePlaylistBtn.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{
				listModel.remove(playlistList.getSelectedIndex());
			}
		});
		addPlaylistBtn.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{
				Playlist p = new Playlist(userText.getText());// take string from another frame as the playlist name
				listModel.insertElementAt(p.getName(), 0);
			}
		});

		JLabel lblNewLabel = new JLabel("Playlists");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 20));
		contentPane.add(lblNewLabel, BorderLayout.NORTH);
	}

}
