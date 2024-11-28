package com.ataoglan.todo_app.domain.request;

import com.ataoglan.todo_app.domain.enums.UserRole;

public record SignupRequest(String username, String password, String email, UserRole userRole) {
}
