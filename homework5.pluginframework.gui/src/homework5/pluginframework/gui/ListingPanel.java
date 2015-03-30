package homework5.pluginframework.gui;

import java.awt.Dimension;

import javax.swing.DefaultListModel;
import javax.swing.JList;


@SuppressWarnings("serial")
public class ListingPanel extends AbstractGUIPanel {

	public ListingPanel(String title, Dimension preferredSize) {
		super(title, preferredSize);
		 this.add(getPluginList());
	}

	public static JList<String> getPluginList() {

		DefaultListModel<String> listModel = new DefaultListModel<>();
		listModel.addElement("Plugin 1");
		listModel.addElement("Plugin 2");
		listModel.addElement("Plugin 3");
		listModel.addElement("Plugin 4");

		// create the list
		JList<String> pluginList = new JList<>(listModel);
		return pluginList;

	}
}
