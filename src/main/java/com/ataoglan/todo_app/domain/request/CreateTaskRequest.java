package com.ataoglan.todo_app.domain.request;

import com.ataoglan.todo_app.domain.enums.TaskStatusEnum;
import lombok.NonNull;

public record CreateTaskRequest(
        @NonNull String userId,
        @NonNull String title,
        String description,
        @NonNull TaskStatusEnum status
        ) {
}
