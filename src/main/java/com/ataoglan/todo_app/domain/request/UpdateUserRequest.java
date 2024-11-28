package com.ataoglan.todo_app.domain.request;

import lombok.NonNull;

public record UpdateUserRequest(
        @NonNull String userId,
        String username,
        String email,
        String password
) {
}
