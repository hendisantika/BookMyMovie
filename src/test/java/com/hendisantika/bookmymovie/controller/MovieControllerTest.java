package com.hendisantika.bookmymovie.controller;

import com.hendisantika.bookmymovie.entity.Movie;
import com.hendisantika.bookmymovie.service.ScreeningService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashSet;
import java.util.Set;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

/**
 * Unit tests for MovieController
 */
@WebMvcTest(MovieController.class)
class MovieControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ScreeningService screeningService;

    private Set<Movie> testMovies;

    @BeforeEach
    void setUp() {
        testMovies = new HashSet<>();
        Movie movie1 = new Movie(1, "The Shawshank Redemption", "https://image.tmdb.org/t/p/w500/9cqNxx0GxF0bflZmeSMuL5tnGzr.jpg", "Drama");
        Movie movie2 = new Movie(2, "The Godfather", "https://image.tmdb.org/t/p/w500/3bhkrj58Vtu7enYsRolD1fZdja1.jpg", "Crime,Drama");
        testMovies.add(movie1);
        testMovies.add(movie2);
    }

    @Test
    void getMovies_withoutDate_shouldReturnMoviesForToday() throws Exception {
        // Given
        when(screeningService.getMoviesByDate(any())).thenReturn(testMovies);

        // When & Then
        mockMvc.perform(get("/movies"))
                .andExpect(status().isOk())
                .andExpect(view().name("movies"))
                .andExpect(model().attributeExists("movies"))
                .andExpect(model().attributeExists("movieBooking"))
                .andExpect(model().attribute("movies", hasSize(2)));
    }

    @Test
    void getMovies_withValidDate_shouldReturnMoviesForSpecificDate() throws Exception {
        // Given
        when(screeningService.getMoviesByDate(any())).thenReturn(testMovies);

        // When & Then
        mockMvc.perform(get("/movies").param("date", "2025-10-21"))
                .andExpect(status().isOk())
                .andExpect(view().name("movies"))
                .andExpect(model().attributeExists("movies"))
                .andExpect(model().attributeExists("movieBooking"))
                .andExpect(model().attribute("movies", hasSize(2)));
    }

    @Test
    void getMovies_withInvalidDate_shouldReturnMoviesForToday() throws Exception {
        // Given
        when(screeningService.getMoviesByDate(any())).thenReturn(testMovies);

        // When & Then
        mockMvc.perform(get("/movies").param("date", "invalid-date"))
                .andExpect(status().isOk())
                .andExpect(view().name("movies"))
                .andExpect(model().attributeExists("movies"))
                .andExpect(model().attributeExists("movieBooking"));
    }

    @Test
    void getMovies_noMoviesAvailable_shouldReturnEmptySet() throws Exception {
        // Given
        when(screeningService.getMoviesByDate(any())).thenReturn(new HashSet<>());

        // When & Then
        mockMvc.perform(get("/movies"))
                .andExpect(status().isOk())
                .andExpect(view().name("movies"))
                .andExpect(model().attributeExists("movies"))
                .andExpect(model().attribute("movies", hasSize(0)));
    }
}
