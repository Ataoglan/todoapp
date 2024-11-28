package com.ataoglan.todo_app.service;

import com.ataoglan.todo_app.domain.entity.UserEntity;
import com.ataoglan.todo_app.domain.enums.UserRole;
import com.ataoglan.todo_app.domain.request.SigninRequest;
import com.ataoglan.todo_app.domain.request.SignupRequest;
import com.ataoglan.todo_app.repository.UserRepository;
import com.ataoglan.todo_app.utils.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    public String signup(SignupRequest request) {
        if (userRepository.findByUsername(request.username()).isPresent()) {
            throw new RuntimeException("Username already exists!");
        }

        UserEntity user = new UserEntity();

        user.setUsername(request.username());
        user.setPassword(passwordEncoder.encode(request.password()));
        user.setEmail(request.email());
        user.setRole(request.userRole());
        user.setEnabled(true);

        userRepository.save(user);

        return "User registered successfully!";
    }

    public String signin(SigninRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.username(), request.password())
        );

        User user = (User) authentication.getPrincipal();
        return jwtUtil.generateToken(user.getUsername(), String.join(",", user.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority).toArray(String[]::new)));
    }
}
