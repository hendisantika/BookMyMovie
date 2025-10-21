package com.hendisantika.bookmymovie.repository;

import com.hendisantika.bookmymovie.entity.Movie;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Integration tests for MovieRepository
 */
@DataJpaTest
class MovieRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private MovieRepository movieRepository;

    private Movie testMovie;

    @BeforeEach
    void setUp() {
        testMovie = new Movie(0, "The Shawshank Redemption",
                "https://image.tmdb.org/t/p/w500/9cqNxx0GxF0bflZmeSMuL5tnGzr.jpg", "Drama");
    }

    @Test
    void findByMovieName_shouldReturnMovie() {
        // Given
        entityManager.persist(testMovie);
        entityManager.flush();

        // When
        Movie found = movieRepository.findByMovieName("The Shawshank Redemption");

        // Then
        assertThat(found).isNotNull();
        assertThat(found.getMovieName()).isEqualTo("The Shawshank Redemption");
        assertThat(found.getMovieTags()).isEqualTo("Drama");
    }

    @Test
    void findByMovieName_withNonExistentMovie_shouldReturnNull() {
        // When
        Movie found = movieRepository.findByMovieName("NonExistent Movie");

        // Then
        assertThat(found).isNull();
    }

    @Test
    void findByMovieId_shouldReturnMovie() {
        // Given
        Movie saved = entityManager.persist(testMovie);
        entityManager.flush();

        // When
        Movie found = movieRepository.findByMovieId(saved.getMovieId());

        // Then
        assertThat(found).isNotNull();
        assertThat(found.getMovieId()).isEqualTo(saved.getMovieId());
        assertThat(found.getMovieName()).isEqualTo("The Shawshank Redemption");
    }
}
