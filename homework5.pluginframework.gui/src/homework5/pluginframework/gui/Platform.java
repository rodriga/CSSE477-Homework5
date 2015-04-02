package homework5.pluginframework.gui;

import static java.nio.file.StandardWatchEventKinds.ENTRY_CREATE;
import static java.nio.file.StandardWatchEventKinds.ENTRY_DELETE;
import static java.nio.file.StandardWatchEventKinds.ENTRY_MODIFY;
import static java.nio.file.StandardWatchEventKinds.OVERFLOW;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
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

public class Platform {
	private final static String PLATFORM_DIRECTORY = "C:/";
	
	private final WatchService watcher;
    private final Map<WatchKey,Path> keys;
    private ArrayList<Display> plugins;
    private MainPanel mainPanel;

	public static void main(String[] args) {
		System.out.println("Hello Platform!");
		
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
        this.keys = new HashMap<WatchKey,Path>();

        mainPanel = new MainPanel();
		plugins = new ArrayList<Display>();
		processFiles();
        register(dir);
    }
    
    private void processFiles() {
    	File folder  = new File(PLATFORM_DIRECTORY);
		File[] fileList = folder.listFiles();
		for (File file : fileList)
		{
			if (file.getName().endsWith(".jar"))
			{
				try {
					URL url;
					url = file.toURI().toURL();
					URLClassLoader cl = URLClassLoader.newInstance(new URL[] { url });
					Class loadedClass = cl.loadClass("homework5.pluginframework.gui.Display");
					Constructor cons = loadedClass.getConstructor();
					Display plugin = (Display) cons.newInstance();
					addPlugin(plugin);
				} catch (Exception e) {
					e.printStackTrace();
				} 
			}
		}
    }
    
    private void addPlugin(Display plugin)
    {
		plugins.add(plugin);    	
    }
	
    @SuppressWarnings("unchecked")
    static <T> WatchEvent<T> cast(WatchEvent<?> event) {
        return (WatchEvent<T>)event;
    }
    
	private void register(Path dir) throws IOException {
        WatchKey key = dir.register(watcher, ENTRY_CREATE, ENTRY_DELETE, ENTRY_MODIFY);
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

            for (WatchEvent<?> event: key.pollEvents()) {
                WatchEvent.Kind kind = event.kind();

                if (kind == OVERFLOW) {
                    continue;
                }

                // Context for directory entry event is the file name of entry
                WatchEvent<Path> ev = cast(event);
                Path name = ev.context();
                Path child = dir.resolve(name);
                
                if (kind == ENTRY_CREATE && child.endsWith("jar")) {
					try {
						URL url = child.toUri().toURL();
						URLClassLoader cl = URLClassLoader.newInstance(new URL[] { url });
						Class loadedClass = cl.loadClass("homework5.pluginframework.gui.Display");
						Display plugin = (Display) loadedClass.newInstance();
						plugins.add(plugin);
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