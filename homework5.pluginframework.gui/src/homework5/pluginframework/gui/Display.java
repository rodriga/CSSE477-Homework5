package homework5.pluginframework.gui;

import javax.swing.JComponent;



public interface Display {

	<T> JComponent getComponent();
	<T> String getName();
	<T> String getInitializingMessage();
	<T> String getExecutingMessage();
	<T> String getPluginRemovalMessage();

}
