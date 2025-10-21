package com.hendisantika.bookmymovie.service;

import com.hendisantika.bookmymovie.business.domain.MovieScreening;
import com.hendisantika.bookmymovie.entity.Movie;
import com.hendisantika.bookmymovie.entity.Screen;
import com.hendisantika.bookmymovie.entity.Screening;
import com.hendisantika.bookmymovie.entity.Theatre;
import com.hendisantika.bookmymovie.repository.MovieRepository;
import com.hendisantika.bookmymovie.repository.ScreenRepository;
import com.hendisantika.bookmymovie.repository.ScreeningRepository;
import com.hendisantika.bookmymovie.repository.TheatreRepository;
import com.hendisantika.bookmymovie.repository.TicketRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Date;
import java.sql.Time;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Unit tests for ScreeningService
 */
@ExtendWith(MockitoExtension.class)
class ScreeningServiceTest {

    @Mock
    private ScreeningRepository screeningRepository;

    @Mock
    private MovieRepository movieRepository;

    @Mock
    private TheatreRepository theatreRepository;

    @Mock
    private TicketRepository ticketRepository;

    @Mock
    private ScreenRepository screenRepository;

    @InjectMocks
    private ScreeningService screeningService;

    private Movie testMovie;
    private Theatre testTheatre;
    private Screen testScreen;
    private Screening testScreening;
    private MovieScreening testMovieScreening;

    @BeforeEach
    void setUp() {
        testMovie = new Movie(1, "The Shawshank Redemption",
                "https://image.tmdb.org/t/p/w500/9cqNxx0GxF0bflZmeSMuL5tnGzr.jpg", "Drama");

        testTheatre = new Theatre();
        testTheatre.setTheatreId(1L);
        testTheatre.setTheatreName("CGV Grand Indonesia");
        testTheatre.setTheatreCity("Jakarta");

        testScreen = new Screen();
        testScreen.setScreenId(1L);
        testScreen.setSeatsNum(100);

        testScreening = new Screening();
        testScreening.setMovieId(1);
        testScreening.setTheatreId(1L);
        testScreening.setScreenId(1L);
        testScreening.setScreeningDate(Date.valueOf("2025-10-21"));
        testScreening.setScreeningTime(Time.valueOf("10:00:00"));
        testScreening.setBookedTickets(10);

        testMovieScreening = new MovieScreening(
                "The Shawshank Redemption",
                "https://image.tmdb.org/t/p/w500/9cqNxx0GxF0bflZmeSMuL5tnGzr.jpg",
                1L,
                "CGV Grand Indonesia",
                "Jakarta",
                "2025-10-21",
                "10:00:00",
                3
        );
    }

    @Test
    void getMoviesByDate_shouldReturnMoviesForGivenDate() {
        // Given
        List<Screening> screenings = Collections.singletonList(testScreening);
        when(screeningRepository.findByScreeningDate(any(Date.class))).thenReturn(screenings);
        when(movieRepository.findByMovieId(1)).thenReturn(testMovie);

        // When
        Set<Movie> result = screeningService.getMoviesByDate(new java.util.Date());

        // Then
        assertThat(result).hasSize(1);
        assertThat(result).contains(testMovie);
        verify(screeningRepository, times(1)).findByScreeningDate(any(Date.class));
    }

    @Test
    void getMoviesByDate_withNoScreenings_shouldReturnEmptySet() {
        // Given
        when(screeningRepository.findByScreeningDate(any(Date.class))).thenReturn(null);

        // When
        Set<Movie> result = screeningService.getMoviesByDate(new java.util.Date());

        // Then
        assertThat(result).isEmpty();
    }

    @Test
    void getScreeningsByMovie_shouldReturnScreeningsForMovie() {
        // Given
        List<Screening> screenings = Collections.singletonList(testScreening);
        when(movieRepository.findByMovieName(anyString())).thenReturn(testMovie);
        when(screeningRepository.findByMovieId(1)).thenReturn(screenings);

        // When
        List<Screening> result = screeningService.getScreeningsByMovie("The Shawshank Redemption");

        // Then
        assertThat(result).hasSize(1);
        assertThat(result.get(0)).isEqualTo(testScreening);
    }

    @Test
    void getScreeningsByMovie_withNonExistentMovie_shouldReturnEmptyList() {
        // Given
        when(movieRepository.findByMovieName(anyString())).thenReturn(null);

        // When
        List<Screening> result = screeningService.getScreeningsByMovie("NonExistent Movie");

        // Then
        assertThat(result).isEmpty();
    }

    @Test
    void getMovieScreeningsByMovie_shouldReturnMovieScreenings() {
        // Given
        List<Screening> screenings = Collections.singletonList(testScreening);
        when(movieRepository.findByMovieName(anyString())).thenReturn(testMovie);
        when(screeningRepository.findByMovieId(1)).thenReturn(screenings);
        when(theatreRepository.findByTheatreId(1L)).thenReturn(testTheatre);
        when(movieRepository.findByMovieId(1)).thenReturn(testMovie);

        // When
        List<MovieScreening> result = screeningService.getMovieScreeningsByMovie("The Shawshank Redemption");

        // Then
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getMovieName()).isEqualTo("The Shawshank Redemption");
        assertThat(result.get(0).getTheatreName()).isEqualTo("CGV Grand Indonesia");
        assertThat(result.get(0).getTheatreCity()).isEqualTo("Jakarta");
    }

    @Test
    void getBookedSeats_shouldReturnNumberOfBookedSeats() {
        // Given
        when(theatreRepository.findByTheatreNameAndTheatreCity(anyString(), anyString())).thenReturn(testTheatre);
        when(movieRepository.findByMovieName(anyString())).thenReturn(testMovie);
        when(screeningRepository.findByMovieIdAndTheatreIdAndScreeningDateAndScreeningTime(
                any(Integer.class), anyLong(), any(Date.class), any(Time.class))).thenReturn(testScreening);

        // When
        int bookedSeats = screeningService.getBookedSeats(testMovieScreening);

        // Then
        assertThat(bookedSeats).isEqualTo(10);
    }

    @Test
    void getTotalSeats_shouldReturnTotalSeatsInScreen() {
        // Given
        when(theatreRepository.findByTheatreNameAndTheatreCity(anyString(), anyString())).thenReturn(testTheatre);
        when(movieRepository.findByMovieName(anyString())).thenReturn(testMovie);
        when(screeningRepository.findByMovieIdAndTheatreIdAndScreeningDateAndScreeningTime(
                any(Integer.class), anyLong(), any(Date.class), any(Time.class))).thenReturn(testScreening);
        when(screenRepository.findByScreenId(1L)).thenReturn(testScreen);

        // When
        int totalSeats = screeningService.getTotalSeats(testMovieScreening);

        // Then
        assertThat(totalSeats).isEqualTo(100);
    }

    @Test
    void bookSeats_shouldUpdateBookedTicketsAndReturnNewCount() {
        // Given
        when(theatreRepository.findByTheatreNameAndTheatreCity(anyString(), anyString())).thenReturn(testTheatre);
        when(movieRepository.findByMovieName(anyString())).thenReturn(testMovie);
        when(screeningRepository.findByMovieIdAndTheatreIdAndScreeningDateAndScreeningTime(
                any(Integer.class), anyLong(), any(Date.class), any(Time.class))).thenReturn(testScreening);
        when(screeningRepository.save(any(Screening.class))).thenReturn(testScreening);

        // When
        int result = screeningService.bookSeats(testMovieScreening, 13);

        // Then
        assertThat(result).isEqualTo(13);
        verify(screeningRepository, times(1)).save(any(Screening.class));
    }
}
