import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Set;

public class PlayingWindow extends JFrame {

	private JPanel contentPane;
	private Database d = new Database();
	private Object[] queue = d.getNames().toArray();
	private int currentSongIndex = 0;
	private Song currentSong = d.getSong((String)queue[currentSongIndex]);
	private JSlider slider;
	private JLabel timeLabel;
	private JLabel songLabel;
	private JButton albumArtwork;
	private boolean previousPressed = false;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PlayingWindow frame = new PlayingWindow();
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
	 * @throws Exception 
	 */
	public PlayingWindow() throws Exception {		
		currentSong.playFromStart();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1289, 730);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_1 = new JPanel();
		contentPane.add(panel_1, BorderLayout.CENTER);
		panel_1.setLayout(null);
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.SOUTH);
		panel.setLayout(new GridLayout(1, 0, 0, 0));
		
		songLabel = new JLabel("Playing – " + currentSong.toString());
		songLabel.setHorizontalAlignment(SwingConstants.CENTER);
		songLabel.setBounds(489, 50, 300, 16);
		panel_1.add(songLabel);

		albumArtwork = new JButton();
		albumArtwork.setIcon(new ImageIcon(currentSong.getArtworkPath()));
		albumArtwork.setBounds(489, 184, 300, 300);
		panel_1.add(albumArtwork);

		slider = new JSlider(0, currentSong.getRunningTimeInSeconds());
		slider.setValue(0);
		slider.setBounds(489, 499, 300, 29);
		panel_1.add(slider);
		
		timeLabel = new JLabel("0:00");
		timeLabel.setBounds(780, 505, 28, 16);
		panel_1.add(timeLabel);
		
		JButton previousBtn = new JButton("Previous");
		previousBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)  {
				previousPressed = true;
				currentSong.stop();
				currentSongIndex--;
				if(currentSongIndex < 0) {
					currentSongIndex = queue.length - 1;
				}
				
				currentSong = d.getSong((String)queue[currentSongIndex]);
				songLabel.setText("Playing – " + currentSong.toString());
				try {
					currentSong.playFromStart();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				slider.setValue(0);
				slider.setMaximum(currentSong.getRunningTimeInSeconds());
				albumArtwork.setIcon(new ImageIcon(currentSong.getArtworkPath()));
				previousPressed = false;
			}
		});
		panel.add(previousBtn);
		
		JButton playPauseBtn = new JButton("Pause");
		playPauseBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(((JButton)e.getSource()).getText().equals("Pause")) {
					currentSong.pause();
					((JButton)e.getSource()).setText("Play");
				} else {
					currentSong.resume();
					((JButton)e.getSource()).setText("Pause");
				}
			}
		});
		panel.add(playPauseBtn);
		
		JButton nextBtn = new JButton("Next");
		nextBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				next();
			}
		});
		panel.add(nextBtn);
		
		slider.addMouseMotionListener(new MouseMotionListener() {
			
			@Override
			public void mouseMoved(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseDragged(MouseEvent e) {
				currentSong.playFromPoint(((JSlider)e.getSource()).getValue());
			}
		});
			
	
		updateTimeThread();
		updateSongThread();
	}
	
	private void updateTimeThread() {
		Runnable r = new Runnable() {
			@Override
			public void run() {
				while(true) {
					int time = currentSong.getCurrentTimeInSeconds();
					slider.setValue(time);
					int min = time / 60;
					int sec = time % 60;
					if(sec < 10)
						timeLabel.setText(min + ":0" + sec);
					else
						timeLabel.setText(min + ":" + sec);
				}
			}
		};
		
		new Thread(r).start();
	}
	
	private void updateSongThread() {
		Runnable r = new Runnable() {
			@Override
			public void run() {
				while(true) {
					if(currentSong.isOver() && !previousPressed)
						next();
				}
			}
			
		};
		
		new Thread(r).start();
	}
	
	private void next() {
		currentSong.stop();
		currentSongIndex++;
		if(currentSongIndex > queue.length - 1)
			currentSongIndex = 0;
		currentSong = d.getSong((String)queue[currentSongIndex]);
		songLabel.setText("Playing – " + currentSong.toString());
		try {
			currentSong.playFromStart();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		slider.setValue(0);
		slider.setMaximum(currentSong.getRunningTimeInSeconds());
		System.out.println(slider.getMaximum());
		albumArtwork.setIcon(new ImageIcon(currentSong.getArtworkPath()));
	}
}
