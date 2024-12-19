package com.example.ProjectUsingWatcher;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import java.util.logging.Logger;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(Controller.class)
public class ControllerTests {

    @Autowired
    private MockMvc mockMvc;

    private static final Logger logger = Logger.getLogger(ControllerTests.class.getName());

    @BeforeEach
    public void setUp() {
        logger.info("Starting test setup...");
    }

    @AfterEach
    public void tearDown() {
        logger.info(".....Test execution completed.....");
    }

    @Test
    public void testGetValue_ExistingKey() throws Exception {
        mockMvc.perform(get("/search/testKey"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("testValue"));
    }

    @Test
    public void testGetValue_AnotherExistingKey() throws Exception {
        mockMvc.perform(get("/search/testKey2"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("testValue2"));
    }

    @Test
    public void testGetValue_NonexistentKey() throws Exception {
        mockMvc.perform(get("/search/invalidKey"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(""));
    }
}
