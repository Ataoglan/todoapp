package com.ataoglan.todo_app.domain.request;

import com.ataoglan.todo_app.domain.enums.TaskStatusEnum;
import lombok.NonNull;

public record UpdateTaskRequest(
        @NonNull String id,
        @NonNull String userId,
        @NonNull String title,
        String description,
        @NonNull TaskStatusEnum status
) {
}
