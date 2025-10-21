package com.hendisantika.bookmymovie.repository;

import com.hendisantika.bookmymovie.entity.Screening;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.TestPropertySource;

import java.sql.Date;
import java.sql.Time;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Integration tests for ScreeningRepository
 */
@DataJpaTest(excludeAutoConfiguration = org.springframework.boot.autoconfigure.flyway.FlywayAutoConfiguration.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
@TestPropertySource(properties = {
        "spring.docker.compose.enabled=false"
})
class ScreeningRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private ScreeningRepository screeningRepository;

    private Screening testScreening;

    @BeforeEach
    void setUp() {
        testScreening = new Screening();
        testScreening.setMovieId(1);
        testScreening.setTheatreId(1L);
        testScreening.setScreenId(1L);
        testScreening.setScreeningDate(Date.valueOf("2025-10-21"));
        testScreening.setScreeningTime(Time.valueOf("10:00:00"));
        testScreening.setBookedTickets(10);
    }

    @Test
    void findByScreeningDate_shouldReturnScreenings() {
        // Given
        entityManager.persist(testScreening);
        entityManager.flush();

        // When
        List<Screening> found = screeningRepository.findByScreeningDate(Date.valueOf("2025-10-21"));

        // Then
        assertThat(found).isNotEmpty();
        assertThat(found.get(0).getMovieId()).isEqualTo(1);
        assertThat(found.get(0).getBookedTickets()).isEqualTo(10);
    }

    @Test
    void findByMovieId_shouldReturnScreeningsForMovie() {
        // Given
        entityManager.persist(testScreening);
        entityManager.flush();

        // When
        List<Screening> found = screeningRepository.findByMovieId(1);

        // Then
        assertThat(found).isNotEmpty();
        assertThat(found.get(0).getMovieId()).isEqualTo(1);
    }

    @Test
    void findByMovieIdAndTheatreIdAndScreeningDateAndScreeningTime_shouldReturnSpecificScreening() {
        // Given
        entityManager.persist(testScreening);
        entityManager.flush();

        // When
        Screening found = screeningRepository.findByMovieIdAndTheatreIdAndScreeningDateAndScreeningTime(
                1, 1L, Date.valueOf("2025-10-21"), Time.valueOf("10:00:00")
        );

        // Then
        assertThat(found).isNotNull();
        assertThat(found.getMovieId()).isEqualTo(1);
        assertThat(found.getTheatreId()).isEqualTo(1L);
        assertThat(found.getBookedTickets()).isEqualTo(10);
    }

    @Test
    void save_shouldUpdateBookedTickets() {
        // Given
        Screening saved = entityManager.persist(testScreening);
        entityManager.flush();

        // When
        saved.setBookedTickets(15);
        Screening updated = screeningRepository.save(saved);

        // Then
        assertThat(updated.getBookedTickets()).isEqualTo(15);
    }
}
