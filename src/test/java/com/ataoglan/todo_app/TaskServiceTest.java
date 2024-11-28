package com.ataoglan.todo_app;

import com.ataoglan.todo_app.domain.entity.TaskEntity;
import com.ataoglan.todo_app.domain.enums.TaskStatusEnum;
import com.ataoglan.todo_app.domain.request.CreateTaskRequest;
import com.ataoglan.todo_app.domain.request.UpdateTaskRequest;
import com.ataoglan.todo_app.repository.TaskRepository;
import com.ataoglan.todo_app.service.TaskService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private TaskService taskService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateTask() {
        CreateTaskRequest request = new CreateTaskRequest("userId123","Task Title", "Task Description", TaskStatusEnum.IN_PROGRESS);
        TaskEntity taskEntity = TaskEntity.builder()
                .id(UUID.randomUUID().toString())
                .title(request.title())
                .description(request.description())
                .userId(request.userId())
                .build();

        when(taskRepository.save(any(TaskEntity.class))).thenReturn(taskEntity);

        taskService.create(request);

        verify(taskRepository).save(any(TaskEntity.class));
    }

    @Test
    void testGetTaskById_Found() {
        String taskId = "taskId123";
        TaskEntity taskEntity = new TaskEntity();
        taskEntity.setId(taskId);

        when(taskRepository.findById(taskId)).thenReturn(Optional.of(taskEntity));

        TaskEntity result = taskService.getById(taskId);

        assertNotNull(result);
        assertEquals(taskId, result.getId());
        verify(taskRepository).findById(taskId);
    }

    @Test
    void testGetTaskById_NotFound() {
        String taskId = "taskId123";

        when(taskRepository.findById(taskId)).thenReturn(Optional.empty());

        TaskEntity result = taskService.getById(taskId);

        assertNull(result);
        verify(taskRepository).findById(taskId);
    }

    @Test
    void testGetAllByUserId() {
        String userId = "userId123";
        List<TaskEntity> tasks = Arrays.asList(
                new TaskEntity("taskId1", "Task 1", "Description 1", userId, TaskStatusEnum.IN_PROGRESS, null, null),
                new TaskEntity("taskId2", "Task 2", "Description 2", userId, TaskStatusEnum.IN_PROGRESS,null , null)
        );

        when(taskRepository.findByUserId(userId)).thenReturn(tasks);

        List<TaskEntity> result = taskService.getAllByUserId(userId);

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(taskRepository).findByUserId(userId);
    }

    @Test
    void testUpdateTask() {
        UpdateTaskRequest updateRequest = new UpdateTaskRequest("taskId123", "Updated Title", "Updated Description", "userId123", TaskStatusEnum.IN_PROGRESS);
        TaskEntity taskEntity = new TaskEntity();
        taskEntity.setId(updateRequest.id());

        when(taskRepository.findById(updateRequest.id())).thenReturn(Optional.of(taskEntity));

        taskService.update(updateRequest);

        verify(taskRepository).findById(updateRequest.id());
        verify(taskRepository).save(taskEntity);
        assertEquals(updateRequest.title(), taskEntity.getTitle());
        assertEquals(updateRequest.description(), taskEntity.getDescription());
        assertEquals(updateRequest.userId(), taskEntity.getUserId());
    }

    @Test
    void testDeleteTask() {
        String taskId = "taskId123";

        doNothing().when(taskRepository).deleteById(taskId);

        taskService.delete(taskId);

        verify(taskRepository).deleteById(taskId);
    }
}

