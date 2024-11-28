package com.ataoglan.todo_app;

import com.ataoglan.todo_app.domain.entity.UserEntity;
import com.ataoglan.todo_app.domain.request.UpdateUserRequest;
import com.ataoglan.todo_app.repository.UserRepository;
import com.ataoglan.todo_app.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class UserServiceTest {
    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testUpdate_Success() {
        UpdateUserRequest updateUserRequest = new UpdateUserRequest("userId123", "newUsername", "newEmail@example.com", null);
        UserEntity userEntity = new UserEntity();
        userEntity.setId("userId123");

        when(userRepository.findById(updateUserRequest.userId())).thenReturn(Optional.of(userEntity));

        userService.update(updateUserRequest);

        verify(userRepository).findById(updateUserRequest.userId());
        verify(userRepository).save(userEntity);
        verifyNoMoreInteractions(userRepository);
    }

    @Test
    void testUpdate_UserNotFound() {
        UpdateUserRequest updateUserRequest = new UpdateUserRequest("userId123", "newUsername", "newEmail@example.com", null);

        when(userRepository.findById(updateUserRequest.userId())).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> userService.update(updateUserRequest));
        verify(userRepository).findById(updateUserRequest.userId());
        verifyNoMoreInteractions(userRepository);
    }

    @Test
    void testUpdatePassword_Success() {
        UpdateUserRequest updateUserRequest = new UpdateUserRequest("userId123", null, null, "newPassword");
        UserEntity userEntity = new UserEntity();
        userEntity.setId("userId123");

        when(userRepository.findById(updateUserRequest.userId())).thenReturn(Optional.of(userEntity));
        when(passwordEncoder.encode(updateUserRequest.password())).thenReturn("encodedPassword");

        userService.updatePassword(updateUserRequest);

        verify(userRepository).findById(updateUserRequest.userId());
        verify(passwordEncoder).encode(updateUserRequest.password());
        verify(userRepository).save(userEntity);
        verifyNoMoreInteractions(userRepository, passwordEncoder);
    }

    @Test
    void testUpdatePassword_UserNotFound() {
        UpdateUserRequest updateUserRequest = new UpdateUserRequest("userId123", null, null, "newPassword");

        when(userRepository.findById(updateUserRequest.userId())).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> userService.updatePassword(updateUserRequest));
        verify(userRepository).findById(updateUserRequest.userId());
        verifyNoInteractions(passwordEncoder);
    }

    @Test
    void testDelete_Success() {
        String userId = "userId123";
        UserEntity userEntity = new UserEntity();
        userEntity.setId(userId);

        when(userRepository.findById(userId)).thenReturn(Optional.of(userEntity));

        userService.delete(userId);

        verify(userRepository).findById(userId);
        verify(userRepository).save(userEntity);
        verifyNoMoreInteractions(userRepository);
    }

    @Test
    void testDelete_UserNotFound() {
        String userId = "userId123";

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> userService.delete(userId));
        verify(userRepository).findById(userId);
        verifyNoMoreInteractions(userRepository);
    }
}
