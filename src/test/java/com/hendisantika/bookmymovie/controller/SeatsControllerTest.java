package com.hendisantika.bookmymovie.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

/**
 * Unit tests for SeatsController
 */
@WebMvcTest(SeatsController.class)
class SeatsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void bookSeats_withValidCount_shouldReturnSeatsView() throws Exception {
        // When & Then
        mockMvc.perform(get("/seats").param("count", "3"))
                .andExpect(status().isOk())
                .andExpect(view().name("seats"))
                .andExpect(model().attributeExists("count"))
                .andExpect(model().attribute("count", 3));
    }

    @Test
    void bookSeats_withSingleSeat_shouldReturnSeatsView() throws Exception {
        // When & Then
        mockMvc.perform(get("/seats").param("count", "1"))
                .andExpect(status().isOk())
                .andExpect(view().name("seats"))
                .andExpect(model().attributeExists("count"))
                .andExpect(model().attribute("count", 1));
    }

    @Test
    void bookSeats_withMaxSeats_shouldReturnSeatsView() throws Exception {
        // When & Then
        mockMvc.perform(get("/seats").param("count", "5"))
                .andExpect(status().isOk())
                .andExpect(view().name("seats"))
                .andExpect(model().attributeExists("count"))
                .andExpect(model().attribute("count", 5));
    }

    @Test
    void bookSeats_withoutCount_shouldReturnBadRequest() throws Exception {
        // When & Then
        mockMvc.perform(get("/seats"))
                .andExpect(status().isBadRequest());
    }
}
