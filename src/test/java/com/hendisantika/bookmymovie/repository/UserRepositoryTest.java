package com.hendisantika.bookmymovie.repository;

import com.hendisantika.bookmymovie.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.TestPropertySource;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Integration tests for UserRepository
 */
@DataJpaTest(excludeAutoConfiguration = org.springframework.boot.autoconfigure.flyway.FlywayAutoConfiguration.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
@TestPropertySource(properties = {
        "spring.docker.compose.enabled=false"
})
class UserRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private UserRepository userRepository;

    private User testUser;

    @BeforeEach
    void setUp() {
        testUser = new User();
        testUser.setUsername("testuser");
        testUser.setPassword("$2a$10$encodedPassword");
    }

    @Test
    void findByUsername_shouldReturnUser() {
        // Given
        entityManager.persist(testUser);
        entityManager.flush();

        // When
        User found = userRepository.findByUsername("testuser");

        // Then
        assertThat(found).isNotNull();
        assertThat(found.getUsername()).isEqualTo("testuser");
        assertThat(found.getPassword()).isEqualTo("$2a$10$encodedPassword");
    }

    @Test
    void findByUsername_withNonExistentUser_shouldReturnNull() {
        // When
        User found = userRepository.findByUsername("nonexistent");

        // Then
        assertThat(found).isNull();
    }

    @Test
    void save_shouldPersistUser() {
        // When
        User saved = userRepository.save(testUser);

        // Then
        assertThat(saved.getId()).isNotNull();
        assertThat(saved.getUsername()).isEqualTo("testuser");
    }
}
