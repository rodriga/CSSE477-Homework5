package homework5.pluginframework.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class StatusPanel extends AbstractGUIPanel {
	private ArrayList<JLabel> posts;
	
	public StatusPanel(String title, Dimension preferredSize) {
		super(title, preferredSize);
		posts = new ArrayList<JLabel>();
		
		this.setLayout(new BorderLayout());
	}
	
	public void postMessage(String message)
	{
		JPanel messagePanel = new JPanel();
		messagePanel.setLayout(new BoxLayout(messagePanel, BoxLayout.Y_AXIS));
		JLabel statusMessage = new JLabel(message);
		statusMessage.setFont(new Font("Helvetica", Font.BOLD, 14));
		posts.add(0,statusMessage);
		this.removeAll();
		for (JLabel status : posts)
		{
			messagePanel.add(status);
		}
		this.add(messagePanel, BorderLayout.CENTER);
		this.add(this.createTitle(this.getTitle()), BorderLayout.PAGE_START);
		repaint();
	}
}
