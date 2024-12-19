package com.example.ProjectUsingWatcher;


import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.*;
import java.util.Properties;



@Component("customFileWatcher")
public class FileWatcher {

    private static final String FILE_PATH = "C:/Users/Kanika.Vashist/Downloads/keyvalue.properties";
    private Properties properties = new Properties();
    private WatchService watchService;
    private Path path;

    
    public FileWatcher() {
        try {
        	
            path = Paths.get(FILE_PATH).getParent();
            loadProperties();
            startWatching();
            
        } catch (IOException e) {
        	
            e.printStackTrace();
        }
    }
    
    
    //to load the values from the file
    private void loadProperties() throws IOException {
    	
        properties.clear();
        Path filePath = Paths.get(FILE_PATH);

        if (Files.exists(filePath)) {
        	
            properties.load(Files.newInputStream(filePath));
            System.out.println("Loaded properties: " + properties);
            
        } else {
        	
            System.err.println("Property file not found at: " + filePath);
        }
        
        
    }

    
    
    public String getProperty(String key) {
        return properties.getProperty(key, "Key not found: " + key);
    }
    
    
    

    private void startWatching() 
    {
        Thread thread = new Thread(() -> {
            try {
            	
                watchService = FileSystems.getDefault().newWatchService();
                path.register(watchService, StandardWatchEventKinds.ENTRY_MODIFY);

                while (true) {
                	
                    WatchKey key = watchService.take();
                    
                    for (WatchEvent<?> event : key.pollEvents()) {
                    	
                        WatchEvent.Kind<?> kind = event.kind();
                        
                        if (kind == StandardWatchEventKinds.ENTRY_MODIFY) 
                        {
                            Path modifiedPath = (Path) event.context();
                            if (modifiedPath.toString().equals(Paths.get(FILE_PATH).getFileName().toString())) 
                            {
                                System.out.println("File changed, reloading properties...");
                                loadProperties();
                                
                            }
                        }
                    }
                    
                    key.reset();
                }
                
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        });

        thread.setDaemon(true);
        thread.start();
    }
    
}