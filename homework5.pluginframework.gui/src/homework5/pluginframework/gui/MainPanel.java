package homework5.pluginframework.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

@SuppressWarnings("serial")
public class MainPanel extends JPanel {

	private JFrame mainFrame;
	public JPanel mainPanel;
	private ListingPanel listPanel;
	private ExecutionPanel execPanel;
	private StatusPanel statusPanel;
	private Color panelColor = new Color(0xE6E6E6);
	private ArrayList<Display> plugins;

	public MainPanel() {
		// set up main frame
		this.mainFrame = new JFrame("RAJ Plugin Framework");
		this.mainFrame.setSize(1200, 800);
		this.mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// set up main panel
		this.mainPanel = new JPanel();
		this.mainPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
		this.mainPanel.setBackground(this.panelColor);

		// add main panel to main frame
		this.mainFrame.add(this.mainPanel);

		// Set up other panels and add them to main panel
		this.listPanel = new ListingPanel("Plugins", new Dimension(200, 500));
		this.execPanel = new ExecutionPanel("Display", new Dimension(400, 500));
		this.statusPanel = new StatusPanel("Status Messages", new Dimension(
				600, 100));

		this.mainPanel.setLayout(new BorderLayout(10, 10));
		this.mainPanel.add(this.listPanel, BorderLayout.LINE_START);
		this.mainPanel.add(this.execPanel, BorderLayout.CENTER);
		this.mainPanel.add(this.statusPanel, BorderLayout.PAGE_END);

		this.mainFrame.setVisible(true);
		plugins = new ArrayList<Display>();
	}

	public void addPlugin(Display plugin) {
		postStatus(plugin.getInitializingMessage());
		// TODO: Add to list Panel
		this.plugins.add(plugin);
		this.listPanel.addPlugin(plugin);
		JList<Object> pluginList = this.listPanel.getPluginList();
		pluginList.addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent evt) {
				boolean adjust = evt.getValueIsAdjusting();
				if (!adjust) {

					int selections[] = pluginList.getSelectedIndices();
					for (int i = 0, n = selections.length; i < n; i++) {
						if (pluginList.getSelectedValue() == plugin.getName()) {
							removePluginExecution();
							executePlugin(plugin);
						}
					}

				}

			}

		});

		this.listPanel.add(pluginList);

		repaintAll();
	}

	public void removePluginExecution() {
		this.execPanel.removeAll();
		repaintAll();
	}

	public void executePlugin(Display plugin) {
		postStatus(plugin.getExecutingMessage());
		this.execPanel.add(plugin.getComponent());

		repaintAll();
	}

	public void postStatus(String post) {
		this.statusPanel.postMessage(post);
		repaintAll();
	}

	private void repaintAll() {
		this.statusPanel.repaint();
		this.execPanel.repaint();
		this.listPanel.repaint();
		this.mainPanel.repaint();
		this.mainPanel.revalidate();
		this.mainFrame.repaint();
		this.repaint();
	}
}
