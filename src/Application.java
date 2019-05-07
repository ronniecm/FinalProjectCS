import javax.swing.*;
import java.awt.*;

public class Application extends JFrame {
	private HomeView home;
	private PlaylistViewer viewer;
	private PlayingWindow playingWindow;
	private JTabbedPane tabbedPane;
	
	public Application() {
		tabbedPane = new JTabbedPane();
		tabbedPane.addTab("Home View", new HomeView());
		tabbedPane.addTab("Playlist Viewer", new PlaylistViewer());
		try {
			tabbedPane.addTab("Playing Window", new PlayingWindow());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		setBounds(0,0,1280,730);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setContentPane(tabbedPane);
		setVisible(true);
	}
}
