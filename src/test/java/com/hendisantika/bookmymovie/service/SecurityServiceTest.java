package com.hendisantika.bookmymovie.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Unit tests for SecurityService
 */
@ExtendWith(MockitoExtension.class)
class SecurityServiceTest {

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private UserDetailsService userDetailsService;

    @InjectMocks
    private SecurityService securityService;

    private UserDetails testUserDetails;

    @BeforeEach
    void setUp() {
        testUserDetails = new User("testuser", "encodedPassword", new ArrayList<>());
    }

    @Test
    void autoLogin_shouldAuthenticateUser() {
        // Given
        when(userDetailsService.loadUserByUsername("testuser")).thenReturn(testUserDetails);

        // Create authenticated token with authorities (this makes it trusted)
        UsernamePasswordAuthenticationToken authToken =
                new UsernamePasswordAuthenticationToken(testUserDetails, "password123", testUserDetails.getAuthorities());

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(authToken);

        // When
        securityService.autoLogin("testuser", "password123");

        // Then
        verify(userDetailsService, times(1)).loadUserByUsername("testuser");
        verify(authenticationManager, times(1)).authenticate(any(UsernamePasswordAuthenticationToken.class));

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        assertThat(auth).isNotNull();
        assertThat(auth.isAuthenticated()).isTrue();
    }

    @Test
    void findLoggedInUsername_withUserDetails_shouldReturnNull() {
        // Note: In the current implementation, findLoggedInUsername returns null
        // because it checks if details instanceof UserDetails, but details is typically
        // not UserDetails but rather other authentication details

        // Given
        SecurityContext securityContext = mock(SecurityContext.class);
        Authentication authentication = mock(Authentication.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getDetails()).thenReturn("someDetails");
        SecurityContextHolder.setContext(securityContext);

        // When
        String result = securityService.findLoggedInUsername();

        // Then
        assertThat(result).isNull();
    }
}
