package homework5.pluginframework.gui;

import java.awt.Dimension;
import java.awt.Font;

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
		pluginList = new JList<>(listModel);
		pluginList.setFont(new Font("Helvetica", Font.BOLD, 14));
		pluginList.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
	}

	public JList<Object> getPluginList(){
		return this.pluginList;
	}
	
	public void addPlugin(Display plugin) {
		listModel.addElement(plugin.getName());
	}

}
