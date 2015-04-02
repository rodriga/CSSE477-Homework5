package homework5.pluginframework.gui;

import java.awt.Dimension;
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
		posts.add(0, new JLabel(message));
		this.removeAll();
		for (JLabel status : posts)
		{
			this.add(status);
		}
		repaint();
	}
}
