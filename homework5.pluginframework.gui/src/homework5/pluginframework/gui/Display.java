package homework5.pluginframework.gui;

import javax.swing.JComponent;
import javax.swing.JLabel;


public interface Display {

	<T> JComponent getComponent();
	<T> JLabel getStatusMessage();
	<T> String getName();
}
