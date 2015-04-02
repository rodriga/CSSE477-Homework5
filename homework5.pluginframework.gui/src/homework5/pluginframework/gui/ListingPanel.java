package homework5.pluginframework.gui;

import java.awt.Dimension;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.ListSelectionModel;

@SuppressWarnings("serial")
public class ListingPanel extends AbstractGUIPanel {
	private DefaultListModel<Object> listModel;
	private JList<Object> pluginList;

	public ListingPanel(String title, Dimension preferredSize) {
		super(title, preferredSize);
		listModel = new DefaultListModel<Object>();
	}

	public JList<Object> getPluginList(){
		return this.pluginList;
	}
	
	public void addPlugin(Display plugin) {
		listModel.addElement("Plugin 1");
		listModel.addElement("Plugin 2");
		listModel.addElement(plugin.getName());
		createPluginList();
	}

	private void createPluginList() {
		pluginList = new JList<>(listModel);
		pluginList.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		pluginList.setSelectedIndex(0);

		
	}

}
