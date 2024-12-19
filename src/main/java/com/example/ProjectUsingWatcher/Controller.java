package com.example.ProjectUsingWatcher;

import org.springframework.beans.factory.annotation.*;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("/search")
public class Controller {

    private final FileWatcher fileWatcher;

    @Autowired
    public Controller(@Qualifier("customFileWatcher") FileWatcher fileWatcher) {
        this.fileWatcher = fileWatcher;
    }

    @GetMapping("/value")
    public String getValue(@RequestParam(name = "key") String key) {
        String value = fileWatcher.getProperty(key);
        if (value == null || value.isEmpty()) {
            throw new KeyNotFoundException("Key '" + key + "' not found");
        }
        return value;
    }
}