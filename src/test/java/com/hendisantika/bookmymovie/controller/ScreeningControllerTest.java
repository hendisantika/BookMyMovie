package com.hendisantika.bookmymovie.controller;

import com.hendisantika.bookmymovie.business.domain.MovieScreening;
import com.hendisantika.bookmymovie.entity.Movie;
import com.hendisantika.bookmymovie.repository.MovieRepository;
import com.hendisantika.bookmymovie.service.ScreeningService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

/**
 * Unit tests for ScreeningController
 */
@WebMvcTest(ScreeningController.class)
@AutoConfigureMockMvc(addFilters = false)
class ScreeningControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ScreeningService screeningService;

    @MockBean
    private MovieRepository movieRepository;

    private List<MovieScreening> testScreenings;
    private Movie testMovie;

    @BeforeEach
    void setUp() {
        testMovie = new Movie(1, "The Shawshank Redemption",
                "https://image.tmdb.org/t/p/w500/9cqNxx0GxF0bflZmeSMuL5tnGzr.jpg", "Drama");

        testScreenings = new ArrayList<>();
        MovieScreening screening1 = new MovieScreening(
                "The Shawshank Redemption",
                "https://image.tmdb.org/t/p/w500/9cqNxx0GxF0bflZmeSMuL5tnGzr.jpg",
                1L,
                "CGV Grand Indonesia",
                "Jakarta",
                "2025-10-21",
                "10:00:00",
                0
        );
        MovieScreening screening2 = new MovieScreening(
                "The Shawshank Redemption",
                "https://image.tmdb.org/t/p/w500/9cqNxx0GxF0bflZmeSMuL5tnGzr.jpg",
                2L,
                "XXI Plaza Senayan",
                "Jakarta",
                "2025-10-21",
                "14:00:00",
                0
        );
        testScreenings.add(screening1);
        testScreenings.add(screening2);
    }

    @Test
    void getScreenings_withValidMovie_shouldReturnScreenings() throws Exception {
        // Given
        when(screeningService.getMovieScreeningsByMovie(anyString())).thenReturn(testScreenings);
        when(movieRepository.findByMovieName(anyString())).thenReturn(testMovie);

        // When & Then
        mockMvc.perform(get("/screenings").param("movie", "The Shawshank Redemption"))
                .andExpect(status().isOk())
                .andExpect(view().name("screenings"))
                .andExpect(model().attributeExists("screenings"))
                .andExpect(model().attributeExists("movie"))
                .andExpect(model().attribute("screenings", hasSize(2)))
                .andExpect(model().attribute("movie", testMovie));
    }

    @Test
    void getScreenings_withoutMovieParam_shouldReturnBadRequest() throws Exception {
        // When & Then
        mockMvc.perform(get("/screenings"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void bookSeats_withValidBooking_shouldReturnResultView() throws Exception {
        // Given
        when(screeningService.getBookedSeats(any(MovieScreening.class))).thenReturn(10);
        when(screeningService.getTotalSeats(any(MovieScreening.class))).thenReturn(100);
        when(screeningService.bookSeats(any(MovieScreening.class), any(Integer.class))).thenReturn(13);

        // When & Then
        mockMvc.perform(post("/screenings")
                        .param("movieName", "The Shawshank Redemption")
                        .param("theatreCity", "Jakarta")
                        .param("theatreName", "CGV Grand Indonesia")
                        .param("screeningTime", "10:00:00")
                        .param("screeningDate", "2025-10-21")
                        .param("numSeats", "3"))
                .andExpect(status().isOk())
                .andExpect(view().name("result"));
    }

    @Test
    void bookSeats_whenNotEnoughSeats_shouldReturnErrorView() throws Exception {
        // Given - 95 seats already booked, trying to book 10 more when total is 100
        when(screeningService.getBookedSeats(any(MovieScreening.class))).thenReturn(95);
        when(screeningService.getTotalSeats(any(MovieScreening.class))).thenReturn(100);

        // When & Then
        mockMvc.perform(post("/screenings")
                        .param("movieName", "The Shawshank Redemption")
                        .param("theatreCity", "Jakarta")
                        .param("theatreName", "CGV Grand Indonesia")
                        .param("screeningTime", "10:00:00")
                        .param("screeningDate", "2025-10-21")
                        .param("numSeats", "10"))
                .andExpect(status().isOk())
                .andExpect(view().name("error"));
    }

    @Test
    void bookSeats_withExactlyEnoughSeats_shouldReturnResultView() throws Exception {
        // Given - 97 seats booked, trying to book 3 more when total is 100
        when(screeningService.getBookedSeats(any(MovieScreening.class))).thenReturn(97);
        when(screeningService.getTotalSeats(any(MovieScreening.class))).thenReturn(100);
        when(screeningService.bookSeats(any(MovieScreening.class), any(Integer.class))).thenReturn(100);

        // When & Then
        mockMvc.perform(post("/screenings")
                        .param("movieName", "The Shawshank Redemption")
                        .param("theatreCity", "Jakarta")
                        .param("theatreName", "CGV Grand Indonesia")
                        .param("screeningTime", "10:00:00")
                        .param("screeningDate", "2025-10-21")
                        .param("numSeats", "3"))
                .andExpect(status().isOk())
                .andExpect(view().name("result"));
    }
}
