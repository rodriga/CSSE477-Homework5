package homework5.pluginframework.gui;

import java.awt.Dimension;

import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class ListingPanel extends AbstractGUIPanel {

	public ListingPanel(String title, Dimension preferredSize) {
		super(title, preferredSize);
		 this.add(getPluginList());
	}

	public static JList<String> getPluginList() {
		JPanel pluginPanel = new JPanel();
		pluginPanel.setLayout(new BoxLayout(pluginPanel, BoxLayout.Y_AXIS));
		pluginPanel.setOpaque(false);

		// test
		JLabel p1 = new JLabel("Plugin 1");
		JLabel p2 = new JLabel("Plugin 2");
		JLabel p3 = new JLabel("Plugin 3");

		pluginPanel.add(p1);
		pluginPanel.add(p2);
		pluginPanel.add(p3);

		DefaultListModel<String> listModel = new DefaultListModel<>();
		listModel.addElement("Plugin 1");
		listModel.addElement("India");
		listModel.addElement("Vietnam");
		listModel.addElement("Canada");
		listModel.addElement("Denmark");
		listModel.addElement("France");
		listModel.addElement("Great Britain");
		listModel.addElement("Japan");

		// create the list
		JList<String> countryList = new JList<>(listModel);
		pluginPanel.add(countryList);
		return countryList;

	}
}
