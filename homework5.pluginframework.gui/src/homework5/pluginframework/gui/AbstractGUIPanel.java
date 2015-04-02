package homework5.pluginframework.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public abstract class AbstractGUIPanel extends JPanel {

	private JLabel title;
	private Color panelColor = new Color(0xFFFFFF);
	private Color titlebgColor = new Color(0x003366);
	private Color titlefgColor = new Color(0xFFFFFF);

	public AbstractGUIPanel(String title, Dimension preferredSize) {
		this.setPreferredSize(preferredSize);
		this.setBackground(this.panelColor);
		this.setLayout(new BorderLayout());
		this.add(createTitle(title), BorderLayout.PAGE_START);

	}

	public JLabel createTitle(String titleName) {
		this.title = new JLabel();
		this.title.setFont(new Font("Helvetica", Font.BOLD, 20));
		this.title.setText(titleName);
		this.title.setOpaque(true);
		this.title.setBackground(this.titlebgColor);
		this.title.setForeground(this.titlefgColor);
		return title;
	}
	
	public String getTitle(){
		return this.title.getText();
	}
}
