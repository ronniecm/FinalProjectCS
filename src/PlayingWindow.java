import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.Timer;

public class PlayingWindow extends JPanel {
	private JPanel panel_1;
	private JSlider slider;
	private JLabel timeLabel;
	private JButton playPauseBtn;
	private JButton albumArtwork;
	public static Database d = new Database();
	private ArrayList<Song> queue = new ArrayList<Song>();
	private int currentSongIndex;
	private Song currentSong = null;
	private MarqueePanel mp = new MarqueePanel(new Song("", "", "", "").toString(), 15);
	private boolean previousPressed = false;
	private boolean isPaused = false;
	private JButton nextBtn;

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
					//frame.setVisible(true);
					
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
		if(queue.isEmpty()) {
			currentSong = d.getRandomSong();
		} else {
			currentSong = queue.get(0);
		}
		//currentSong.playFromStart();
		setBorder(new EmptyBorder(5, 5, 5, 5));
		setLayout(new BorderLayout(0, 0));
		
		panel_1 = new JPanel();
		add(panel_1, BorderLayout.CENTER);
		panel_1.setLayout(null);
		int r = (int)(Math.random() * 256);
		int g = (int)(Math.random() * 256);
		int b = (int)(Math.random() * 256);
		panel_1.setBackground(new Color(r, g, b));
		
		JPanel panel = new JPanel();
		add(panel, BorderLayout.SOUTH);
		panel.setLayout(new GridLayout(1, 0, 0, 0));
		
		mp.setBounds(489, 50, 300, 30);
		mp.setText(currentSong);
		panel_1.add(mp, BorderLayout.NORTH);

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
				if(queue.isEmpty()) {
					currentSong = d.getRandomSong();
				} else {
					currentSongIndex--;
					if(currentSongIndex < 0)
						currentSongIndex = queue.size() - 1;
					currentSong = queue.get(currentSongIndex);
				}
				
				try {
					currentSong.playFromStart();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				mp.setText(currentSong);
				playPauseBtn.setText("Pause");
				isPaused = false;
				slider.setValue(0);
				slider.setMaximum(currentSong.getRunningTimeInSeconds());
				albumArtwork.setIcon(new ImageIcon(currentSong.getArtworkPath()));
				previousPressed = false;
			}
		});
		panel.add(previousBtn);
		
		playPauseBtn = new JButton("Pause");
		playPauseBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				playPause();
			}
		});
		panel.add(playPauseBtn);
		
		nextBtn = new JButton("Next: " + (queue.isEmpty() ? "Random" : queue.get(currentSongIndex+1).getInfo(Metadata.TITLE)));
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
				currentSong.playFromPoint(((JSlider)e.getSource()).getValue(), isPaused);
			}
		});
			
	
		updateTimeThread();
		updateSongThread();
		mp.start();
		
		addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyChar() == ' ')
					playPause();
			}
		});
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
					if(currentSong.isOver() && !previousPressed) {
						System.out.println("auto changing song");
						next();
					}
				}
			}
			
		};
		
		new Thread(r).start();
	}
	
	private void next() {
		System.out.println(queue);
		currentSong.stop();
		
		if(currentSongIndex+1 > queue.size()-1) {
			currentSong = d.getRandomSong();
		} else {
			if(queue.size() == 1)
				currentSongIndex = 0;
			else
				currentSongIndex++;
			
			System.out.println(queue.size());
			
			if(currentSongIndex > queue.size() - 1)
				currentSongIndex = 0;
			
			currentSong = queue.get(currentSongIndex);
		}
		
		try {
			currentSong.playFromStart();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		
		playPauseBtn.setText("Pause");
		isPaused = false;
		mp.setText(currentSong);
		slider.setValue(0);
		slider.setMaximum(currentSong.getRunningTimeInSeconds());
		System.out.println(slider.getMaximum());
		albumArtwork.setIcon(new ImageIcon(currentSong.getArtworkPath()));
		
		int r = (int)(Math.random() * 256);
		int g = (int)(Math.random() * 256);
		int b = (int)(Math.random() * 256);
		panel_1.setBackground(new Color(r, g, b));
		System.out.println("index: " + currentSongIndex);
		nextBtn.setText("Next: " + (currentSongIndex+1 > queue.size()-1 ? "Random" : queue.get(currentSongIndex+1).getInfo(Metadata.TITLE)));
	}
	
	private void playPause() {
		if(playPauseBtn.getText().equals("Pause")) {
			currentSong.pause();
			isPaused = true;
			playPauseBtn.setText("Play");
		} else {
			currentSong.resume();
			playPauseBtn.setText("Pause");
			isPaused = false;
		}
	}
	
	public void addToQueue(Song s) {
		if(currentSongIndex+1 > queue.size()-1) {
			queue.add(s);
			nextBtn.setText("Next: " + s.getInfo(Metadata.TITLE));
		} else {
			queue.add(s);
		}
	}
	
	public void removeFromQueue(Song s) {
		queue.remove(s);
	}
	
	public void updateWindow(Song s) {
		currentSong.stop();
		currentSong = s;
		try {
			currentSong.playFromStart();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		mp.setText(currentSong);
		slider.setValue(0);
		slider.setMaximum(currentSong.getRunningTimeInSeconds());
		System.out.println(currentSong.getRunningTimeInSeconds());
		albumArtwork.setIcon(new ImageIcon(currentSong.getArtworkPath()));
	}
	
	private class MarqueePanel extends JPanel implements ActionListener {
	    private static final int RATE = 10;
	    private final Timer timer = new Timer(1000 / RATE, this);
	    private final JLabel label = new JLabel();
	    private String s;
	    private int n;
	    private int index;

	    public MarqueePanel(String s, int n) {
	        if (s == null || n < 1) {
	            throw new IllegalArgumentException("Null string or n < 1");
	        }
	        StringBuilder sb = new StringBuilder(n);
	        for (int i = 0; i < n; i++) {
	            sb.append(' ');
	        }
	        this.s = sb + s + sb;
	        this.n = n;
	        label.setFont(new Font("Serif", Font.ITALIC, 24));
	        label.setText(sb.toString());
	        label.setHorizontalAlignment(SwingConstants.CENTER);
	        this.add(label);
	    }
	    
	    public void setText(Song newSong) {
			stop();
			s = newSong.toString();
			StringBuilder sb = new StringBuilder(n);
	        for (int i = 0; i < n; i++) {
	            sb.append(' ');
	        }
	        s = sb + newSong.toString() + sb;
	        index = 0;
			//n = s.length() / 2;
	        timer.restart();
	    }
	    
	    public void start() {
	        timer.start();
	    }

	    public void stop() {
	        timer.stop();
	    }

	    @Override
	    public void actionPerformed(ActionEvent e) {
	        index++;
	        if (index > s.length() - n) {
	            index = 0;
	            label.setText(" ");
	        } else {
	        	label.setText(s.substring(index, index + n));
	        }
	    }
	}
}