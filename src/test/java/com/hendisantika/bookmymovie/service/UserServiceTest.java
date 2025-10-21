package com.hendisantika.bookmymovie.service;

import com.hendisantika.bookmymovie.entity.User;
import com.hendisantika.bookmymovie.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Unit tests for UserService
 */
@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @InjectMocks
    private UserService userService;

    private User testUser;

    @BeforeEach
    void setUp() {
        testUser = new User();
        testUser.setId(1L);
        testUser.setUsername("testuser");
        testUser.setPassword("password123");
    }

    @Test
    void save_shouldEncodePasswordAndSaveUser() {
        // Given
        String encodedPassword = "$2a$10$encodedPassword";
        when(bCryptPasswordEncoder.encode(anyString())).thenReturn(encodedPassword);
        when(userRepository.save(any(User.class))).thenReturn(testUser);

        // When
        userService.save(testUser);

        // Then
        verify(bCryptPasswordEncoder, times(1)).encode("password123");
        verify(userRepository, times(1)).save(testUser);
    }

    @Test
    void findByUsername_shouldReturnUser() {
        // Given
        when(userRepository.findByUsername("testuser")).thenReturn(testUser);

        // When
        User result = userService.findByUsername("testuser");

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getUsername()).isEqualTo("testuser");
        verify(userRepository, times(1)).findByUsername("testuser");
    }

    @Test
    void findByUsername_withNonExistentUser_shouldReturnNull() {
        // Given
        when(userRepository.findByUsername("nonexistent")).thenReturn(null);

        // When
        User result = userService.findByUsername("nonexistent");

        // Then
        assertThat(result).isNull();
    }
}
