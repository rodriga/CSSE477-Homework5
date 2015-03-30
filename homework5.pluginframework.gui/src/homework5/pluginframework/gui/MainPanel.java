package homework5.pluginframework.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

@SuppressWarnings("serial")
public class MainPanel extends JPanel{

	private JFrame mainFrame;
	private JPanel mainPanel;
	private ListingPanel listPanel;
	private ExecutionPanel execPanel;
	private StatusPanel statusPanel;
	private Color panelColor = new Color(0xE6E6E6);
	
	public MainPanel(){
		//set up main frame
		this.mainFrame = new JFrame("RAJ Plugin Framework");
		this.mainFrame.setVisible(true);
		this.mainFrame.setSize(1200,800);
		this.mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//set up main panel
		this.mainPanel = new JPanel();
		this.mainPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
		this.mainPanel.setBackground(this.panelColor);
		
		// add main panel to main frame
		this.mainFrame.add(this.mainPanel);
		
		// Set up other panels and add them to main panel 
		this.listPanel = new ListingPanel("Plugins", new Dimension(200,500));
		this.execPanel = new ExecutionPanel("Display", new Dimension(400,500));
		this.statusPanel = new StatusPanel("Status Messages", new Dimension(600,100));
		
		this.mainPanel.setLayout(new BorderLayout(10,10));
		this.mainPanel.add(this.listPanel, BorderLayout.LINE_START);
		this.mainPanel.add(this.execPanel,BorderLayout.CENTER);
		this.mainPanel.add(this.statusPanel,BorderLayout.PAGE_END);
		
		
	}
	
	public static void main (String [] args){
		
		new MainPanel();
	}
}
