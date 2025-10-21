package com.hendisantika.bookmymovie.controller;

import com.hendisantika.bookmymovie.entity.User;
import com.hendisantika.bookmymovie.service.SecurityService;
import com.hendisantika.bookmymovie.service.UserService;
import com.hendisantika.bookmymovie.validator.UserValidator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

/**
 * Unit tests for UserController
 */
@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @MockBean
    private SecurityService securityService;

    @MockBean
    private UserValidator userValidator;

    @Test
    void registration_get_shouldReturnRegistrationForm() throws Exception {
        // When & Then
        mockMvc.perform(get("/registration"))
                .andExpect(status().isOk())
                .andExpect(view().name("registration"))
                .andExpect(model().attributeExists("userForm"));
    }

    @Test
    void registration_post_shouldSaveUserAndAutoLogin() throws Exception {
        // When & Then
        mockMvc.perform(post("/registration")
                        .param("username", "testuser")
                        .param("password", "password123"))
                .andExpect(status().isOk())
                .andExpect(view().name("welcome"));

        // Verify user was saved and auto-login was called
        verify(userService, times(1)).save(any(User.class));
        verify(securityService, times(1)).autoLogin(anyString(), anyString());
    }

    @Test
    void login_get_shouldReturnLoginForm() throws Exception {
        // When & Then
        mockMvc.perform(get("/login"))
                .andExpect(status().isOk())
                .andExpect(view().name("login"));
    }

    @Test
    void login_get_withError_shouldShowErrorMessage() throws Exception {
        // When & Then
        mockMvc.perform(get("/login").param("error", "true"))
                .andExpect(status().isOk())
                .andExpect(view().name("login"))
                .andExpect(model().attributeExists("error"))
                .andExpect(model().attribute("error", "Your username and password is invalid."));
    }

    @Test
    void login_get_withLogout_shouldReturnLogoutView() throws Exception {
        // When & Then
        mockMvc.perform(get("/login").param("logout", "true"))
                .andExpect(status().isOk())
                .andExpect(view().name("logout"));
    }

    @Test
    void welcome_shouldReturnWelcomeView() throws Exception {
        // When & Then
        mockMvc.perform(get("/welcome"))
                .andExpect(status().isOk())
                .andExpect(view().name("welcome"));
    }
}
