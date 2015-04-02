package homework5.pluginframework.gui;

import javax.swing.JComponent;


public interface Display {

	<T> JComponent getComponent();
	<T> String getInitializingMessage();
	<T> String getExecutingMessage();
}
