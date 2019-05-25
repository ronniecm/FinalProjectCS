import javax.swing.*;
import java.awt.*;

public class Application extends JFrame {
	private HomeView home = new HomeView();
	private PlaylistViewer viewer = new PlaylistViewer();
	private PlayingWindow playingWindow;
	private JTabbedPane tabbedPane;
	
	public Application() {
		try {
			UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
			//UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
		} catch(Exception e) {
			System.out.println(e.getClass());
			e.getStackTrace();
		}
		tabbedPane = new JTabbedPane();
		tabbedPane.addTab("Home View", home);
		tabbedPane.addTab("Playlist Viewer", viewer);
		try {
			playingWindow = new PlayingWindow();
			tabbedPane.addTab("Playing Window", playingWindow);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		setBounds(0,0,1280,730);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setContentPane(tabbedPane);
		setVisible(true);
	}
	
	public void updatePlaylistViewer(Playlist p) {
		viewer.updateTo(p);
		if(p != null)
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
