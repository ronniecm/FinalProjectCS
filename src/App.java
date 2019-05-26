
import javax.swing.*;
import java.awt.*;

public class App extends JFrame {
	private HomeView home = new HomeView();
	private PlaylistViewer viewer = new PlaylistViewer();
	private PlayingWindow playingWindow;
	private JTabbedPane tabbedPane;

	public App() {
		try {
			UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
		} catch (Exception e) {
			// System.out.println("help");
			e.getStackTrace();
		}
		tabbedPane = new JTabbedPane();
		tabbedPane.setFocusable(false);
		tabbedPane.addTab("Home View", home);
		tabbedPane.addTab("Playlist Viewer", viewer);
		try {
			playingWindow = new PlayingWindow();
			tabbedPane.addTab("Playing Window", playingWindow);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		setSize(1280, 800);
		setLocation((int)(screenSize.width/2 - getSize().getWidth()/2), (int)(screenSize.height/2 - getSize().getHeight()/2));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setContentPane(tabbedPane);
		setVisible(true);
	}

	public void updatePlaylistViewer(Playlist p) {
		viewer.updateTo(p);
		if (p != null)
			tabbedPane.setSelectedIndex(1);
	}

	public void goHome() {
		tabbedPane.setSelectedIndex(0);
	}

	public void playSong(Song s) {
		playingWindow.updateWindow(s);
	}

	public void addToQueue(Song s) {
		playingWindow.addToQueue(s);
	}
}
