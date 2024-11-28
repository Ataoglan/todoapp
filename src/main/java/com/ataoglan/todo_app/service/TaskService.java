package com.ataoglan.todo_app.service;

import com.ataoglan.todo_app.domain.entity.TaskEntity;
import com.ataoglan.todo_app.domain.request.CreateTaskRequest;
import com.ataoglan.todo_app.domain.request.UpdateTaskRequest;
import com.ataoglan.todo_app.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;

    public void create(CreateTaskRequest request) {
        TaskEntity task = TaskEntity.builder()
                .id(UUID.randomUUID().toString())
                .title(request.title())
                .description(request.description())
                .userId(request.userId())
                .build();

        taskRepository.save(task);
    }

    public TaskEntity getById(String taskId) {
        return taskRepository.findById(taskId).orElse(null);
    }

    public List<TaskEntity> getAllByUserId(String userId) {
        return taskRepository.findByUserId(userId);
    }

    public void update(UpdateTaskRequest updatedTask) {
        taskRepository.findById(updatedTask.id()).ifPresent(task -> {
            task.setTitle(updatedTask.title());
            task.setDescription(updatedTask.description());
            task.setUserId(updatedTask.userId());

            taskRepository.save(task);
        });
    }

    public void delete(String taskId) {
        taskRepository.deleteById(taskId);
    }
}
