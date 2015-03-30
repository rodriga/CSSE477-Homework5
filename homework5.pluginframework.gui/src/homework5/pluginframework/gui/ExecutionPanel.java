package homework5.pluginframework.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class ExecutionPanel extends AbstractGUIPanel {

	public ExecutionPanel(String title, Dimension preferredSize) {
		super(title, preferredSize);
		this.add(getDisplay(), BorderLayout.CENTER);
	}

	public static JPanel getDisplay() {
		// Here we would get the GUI stuff from the plugin to display

		JPanel display = new JPanel();
		display.setOpaque(false);
		JLabel testMess = new JLabel("Plugin GUI components will go here");
		display.add(testMess);

		return display;
	}

}
