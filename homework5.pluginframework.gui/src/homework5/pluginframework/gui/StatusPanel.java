package homework5.pluginframework.gui;

import java.awt.Dimension;
import java.awt.Font;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JLabel;

@SuppressWarnings("serial")
public class StatusPanel extends AbstractGUIPanel {
	private ArrayList<JLabel> posts;
	
	public StatusPanel(String title, Dimension preferredSize) {
		super(title, preferredSize);
		posts = new ArrayList<JLabel>();
		
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
	}
	
	public void postMessage(String message)
	{
		JLabel statusMessage = new JLabel(message);
		statusMessage.setFont(new Font("Helvetica", Font.BOLD, 14));
		posts.add(0,statusMessage);
		this.removeAll();
		for (JLabel status : posts)
		{
			this.add(status);
		}
		repaint();
	}
}
