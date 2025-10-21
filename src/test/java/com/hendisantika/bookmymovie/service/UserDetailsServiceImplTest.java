package com.hendisantika.bookmymovie.service;

import com.hendisantika.bookmymovie.entity.User;
import com.hendisantika.bookmymovie.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

/**
 * Unit tests for UserDetailsServiceImpl
 */
@ExtendWith(MockitoExtension.class)
class UserDetailsServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserDetailsServiceImpl userDetailsService;

    private User testUser;

    @BeforeEach
    void setUp() {
        testUser = new User();
        testUser.setId(1L);
        testUser.setUsername("testuser");
        testUser.setPassword("$2a$10$encodedPassword");
    }

    @Test
    void loadUserByUsername_withValidUsername_shouldReturnUserDetails() {
        // Given
        when(userRepository.findByUsername("testuser")).thenReturn(testUser);

        // When
        UserDetails result = userDetailsService.loadUserByUsername("testuser");

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getUsername()).isEqualTo("testuser");
        assertThat(result.getPassword()).isEqualTo("$2a$10$encodedPassword");
        assertThat(result.getAuthorities()).isEmpty();
    }

    @Test
    void loadUserByUsername_withNullUser_shouldThrowException() {
        // Given
        when(userRepository.findByUsername("nonexistent")).thenReturn(null);

        // When & Then
        assertThatThrownBy(() -> userDetailsService.loadUserByUsername("nonexistent"))
                .isInstanceOf(NullPointerException.class);
    }
}
