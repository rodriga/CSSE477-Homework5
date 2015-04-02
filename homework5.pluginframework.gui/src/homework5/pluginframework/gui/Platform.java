package homework5.pluginframework.gui;

import static java.nio.file.StandardWatchEventKinds.ENTRY_CREATE;
import static java.nio.file.StandardWatchEventKinds.ENTRY_DELETE;
import static java.nio.file.StandardWatchEventKinds.ENTRY_MODIFY;
import static java.nio.file.StandardWatchEventKinds.OVERFLOW;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;

public class Platform {
	private final static String PLATFORM_DIRECTORY = "C:/";

	private final WatchService watcher;
	private final Map<WatchKey, Path> keys;
	private MainPanel mainPanel;

	public static void main(String[] args) {
		try {
			Path dir = Paths.get(PLATFORM_DIRECTORY);
			Platform platform = new Platform(dir);
			platform.processEvents();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	Platform(Path dir) throws IOException {
		this.watcher = FileSystems.getDefault().newWatchService();
		this.keys = new HashMap<WatchKey, Path>();

		mainPanel = new MainPanel();
		processFiles();
		register(dir);

	}

	private void processFiles() {
		mainPanel.postStatus("Platform", "Initializing plugin list");
		File folder = new File(PLATFORM_DIRECTORY);
		File[] fileList = folder.listFiles();
		for (File file : fileList) {
			if (file.getName().endsWith(".jar")) {
				try {
					JarInputStream jarFile = new JarInputStream(
							new FileInputStream(file.getAbsolutePath()));
					JarEntry jarEntry;
					String extensionClassName = "";
					while (true) {
						jarEntry = jarFile.getNextJarEntry();
						if (jarEntry == null) {
							break;
						}
						if ((jarEntry.getName().endsWith(".class"))) {
							String className = jarEntry.getName().replaceAll(
									"/", "\\.");
							String myClass = className.substring(0,
									className.lastIndexOf('.'));
							if (!isNativeClass(myClass))
								extensionClassName = myClass;
							//break;
						}
					}
					URL url = file.toURI().toURL();
					URLClassLoader cl = URLClassLoader
							.newInstance(new URL[] { url });
					Class loadedClass = cl.loadClass(extensionClassName);
					Display plugin = (Display) loadedClass.newInstance();
					mainPanel.addPlugin(plugin);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	private boolean isNativeClass(String className) {
		ArrayList<String> classes = new ArrayList<String>();
		classes.add("homework5.pluginframework.gui.Display");
		classes.add("homework5.pluginframework.gui.StatusPanel");
		classes.add("homework5.pluginframework.gui.ExecutionPanel");
		classes.add("homework5.pluginframework.gui.AbstractGUIPanel");
		classes.add("homework5.pluginframework.gui.MainPanel");
		classes.add("homework5.pluginframework.gui.ListingPanel");
		classes.add("homework5.pluginframework.gui.Platform");
		
		for (String nativeClass : classes)
		{
			if(className.contains(nativeClass))
				return true;
		}
		return false;
	}

	@SuppressWarnings("unchecked")
	static <T> WatchEvent<T> cast(WatchEvent<?> event) {
		return (WatchEvent<T>) event;
	}

	private void register(Path dir) throws IOException {
		WatchKey key = dir.register(watcher, ENTRY_CREATE, ENTRY_DELETE,
				ENTRY_MODIFY);
		keys.put(key, dir);
	}

	void processEvents() {
		for (;;) {

			WatchKey key;
			try {
				key = watcher.take();
			} catch (InterruptedException x) {
				return;
			}

			Path dir = keys.get(key);
			if (dir == null) {
				System.err.println("WatchKey not recognized!!");
				continue;
			}

			for (WatchEvent<?> event : key.pollEvents()) {
				WatchEvent.Kind kind = event.kind();

				if (kind == OVERFLOW) {
					continue;
				}

				// Context for directory entry event is the file name of entry
				WatchEvent<Path> ev = cast(event);
				Path name = ev.context();
				Path child = dir.resolve(name);

				if (kind == ENTRY_CREATE
						&& child.toFile().getName().endsWith(".jar")) {
					try {
						JarInputStream jarFile = new JarInputStream(
								new FileInputStream(child.toFile()
										.getAbsolutePath()));
						JarEntry jarEntry;
						String extensionClassName = "";
						while (true) {
							jarEntry = jarFile.getNextJarEntry();
							if (jarEntry == null) {
								break;
							}
							if ((jarEntry.getName().endsWith(".class"))) {
								String className = jarEntry.getName()
										.replaceAll("/", "\\.");
								String myClass = className.substring(0,
										className.lastIndexOf('.'));
								if (!isNativeClass(myClass))
									extensionClassName = myClass;
							}
						}

						URL url = child.toUri().toURL();
						mainPanel.postStatus("Platform", "Detected new plugin.");
						URLClassLoader cl = URLClassLoader
								.newInstance(new URL[] { url });
						Class loadedClass = cl.loadClass(extensionClassName);
						Display plugin = (Display) loadedClass.newInstance();
						mainPanel.addPlugin(plugin);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}

				// Print out event
				System.out.format("%s: %s\n", event.kind().name(), child);
			}

			// Reset key and remove from set if directory no longer accessible
			boolean valid = key.reset();
			if (!valid) {
				keys.remove(key);

				// all directories are inaccessible
				if (keys.isEmpty()) {
					break;
				}
			}
		}
	}
}