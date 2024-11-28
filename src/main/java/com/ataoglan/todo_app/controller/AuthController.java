package com.ataoglan.todo_app.controller;


import com.ataoglan.todo_app.domain.request.SigninRequest;
import com.ataoglan.todo_app.domain.request.SignupRequest;
import com.ataoglan.todo_app.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signup")
    public String signup(@RequestBody SignupRequest signupRequest) {
        return authService.signup(signupRequest);
    }

    @PostMapping("/signin")
    public String signin(@RequestBody SigninRequest signinRequest) {
        return authService.signin(signinRequest);
    }
}
