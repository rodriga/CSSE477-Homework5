package homework5.pluginframework.gui;

import java.awt.Dimension;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class StatusPanel extends AbstractGUIPanel {
	
	public StatusPanel(String title, Dimension preferredSize) {
		super(title, preferredSize);
		this.add(getMessages());
	}

	public static JPanel getMessages(){
		JPanel messages = new JPanel();
		messages.setLayout(new BoxLayout(messages, BoxLayout.Y_AXIS));
		messages.setOpaque(false);
		//For each message create a JLabel and add it to the panel
		JLabel m = new JLabel("Just a little example");
		JLabel m2 = new JLabel("And another");
		JLabel m3 = new JLabel("Aaaand another");
		messages.add(m);
		messages.add(m2);
		messages.add(m3);
		return messages;
		
	}
}
