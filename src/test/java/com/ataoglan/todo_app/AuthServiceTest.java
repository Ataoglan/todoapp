package com.ataoglan.todo_app;

import com.ataoglan.todo_app.domain.entity.UserEntity;
import com.ataoglan.todo_app.domain.enums.UserRole;
import com.ataoglan.todo_app.domain.request.SigninRequest;
import com.ataoglan.todo_app.domain.request.SignupRequest;
import com.ataoglan.todo_app.repository.UserRepository;
import com.ataoglan.todo_app.service.AuthService;
import com.ataoglan.todo_app.utils.JwtUtil;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AuthServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JwtUtil jwtUtil;

    @InjectMocks
    private AuthService authService;

    public AuthServiceTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSignupSuccess() {
        SignupRequest request = new SignupRequest("testuser", "password", "testuser@example.com", UserRole.USER);
        UserEntity savedUser = new UserEntity();
        savedUser.setUsername("testuser");

        when(userRepository.findByUsername(request.username())).thenReturn(Optional.empty());
        when(passwordEncoder.encode(request.password())).thenReturn("encodedPassword");
        when(userRepository.save(any(UserEntity.class))).thenReturn(savedUser);

        String result = authService.signup(request);

        assertEquals("User registered successfully!", result);
        verify(userRepository).save(any(UserEntity.class));
    }

    @Test
    void testSignupFailureWhenUsernameExists() {
        SignupRequest request = new SignupRequest("testuser", "password", "testuser@example.com", UserRole.USER);
        when(userRepository.findByUsername(request.username())).thenReturn(Optional.of(new UserEntity()));

        assertThrows(RuntimeException.class, () -> authService.signup(request));
        verify(userRepository, never()).save(any(UserEntity.class));
    }

    @Test
    void testSigninSuccess() {
        SigninRequest request = new SigninRequest("testuser", "password");
        Authentication authentication = mock(Authentication.class);
        org.springframework.security.core.userdetails.User mockUser =
                new org.springframework.security.core.userdetails.User("testuser", "password", List.of());

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(mockUser);
        when(jwtUtil.generateToken(anyString(), anyString())).thenReturn("mockJwtToken");

        String result = authService.signin(request);

        assertEquals("mockJwtToken", result);
        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(jwtUtil).generateToken("testuser", "");
    }

    @Test
    void testSigninFailure() {
        SigninRequest request = new SigninRequest("testuser", "wrongpassword");
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenThrow(new RuntimeException("Invalid credentials"));

        assertThrows(RuntimeException.class, () -> authService.signin(request));
        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
    }
}

