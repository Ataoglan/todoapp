package com.ataoglan.todo_app.service;

import com.ataoglan.todo_app.domain.request.UpdateUserRequest;
import com.ataoglan.todo_app.repository.UserRepository;
import com.ataoglan.todo_app.utils.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public void update(UpdateUserRequest updateUserRequest) {
        userRepository.findById(updateUserRequest.userId()).ifPresentOrElse(userEntity -> {
                    userEntity.setUsername(updateUserRequest.username());
                    userEntity.setEmail(updateUserRequest.email());
                    userRepository.save(userEntity);
                }, () -> {
                    throw new RuntimeException("User not found!");
                }
        );
    }

    public void updatePassword(UpdateUserRequest updateUserRequest) {
        userRepository.findById(updateUserRequest.userId()).ifPresentOrElse(userEntity -> {
                    userEntity.setPassword(passwordEncoder.encode(updateUserRequest.password()));
                    userRepository.save(userEntity);
                }, () -> {
                    throw new RuntimeException("User not found!");
                }
        );
    }

    public void delete(String userId) {
        userRepository.findById(userId).ifPresentOrElse(userEntity -> {
            userEntity.setEnabled(false);
            userRepository.save(userEntity);
        }, () -> {
            throw new RuntimeException("User not found!");
        });
    }
}
