package com.example.Watcher;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@WebMvcTest(Controller.class)
public class ControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private Controller controller;

    private final Map<String, String> mockKeyValuePairs = new HashMap<>();

    private static final Logger logger = Logger.getLogger(ControllerTests.class.getName());

    @BeforeEach
    public void setUp() {
        
        mockKeyValuePairs.put("testKey", "testValue");
        mockKeyValuePairs.put(":test $Key2 *", "/@test Value 2 ^");
        mockKeyValuePairs.put("testKey3", "testValue3");
    }

    @AfterEach
    public void tearDown() {
        logger.info(".....Test execution completed.....");
    }

    @Test
    public void testGetValue_ExistingKey() throws Exception {
        
        when(controller.getValue("testKey")).thenReturn(mockKeyValuePairs.get("testKey"));

        mockMvc.perform(get("/search/value?key=testKey"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("testValue"));
    }

    @Test
    public void testGetValue_AnotherExistingKey() throws Exception {
        
        when(controller.getValue(":test $Key2 *")).thenReturn(mockKeyValuePairs.get(":test $Key2 *"));

        
        mockMvc.perform(get("/search/value?key=:test $Key2 *"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("/@test Value 2 ^"));
    }

    @Test
    public void testGetValue_NonexistentKey() throws Exception {
        
        when(controller.getValue("invalidKey"))
                .thenThrow(new KeyNotFoundException("Key 'invalidKey' not found"));

        
        mockMvc.perform(get("/search/value?key=invalidKey"))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(content().string("Key 'invalidKey' not found"));
    }
}
