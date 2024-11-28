package com.ataoglan.todo_app.controller;

import com.ataoglan.todo_app.domain.request.CreateTaskRequest;
import com.ataoglan.todo_app.domain.request.UpdateTaskRequest;
import com.ataoglan.todo_app.domain.response.ApiResponse;
import com.ataoglan.todo_app.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/task")
@RequiredArgsConstructor
public class TaskController {
    private final TaskService taskService;

    @PostMapping("v1/create")
    public ApiResponse create(@RequestBody CreateTaskRequest createTaskRequest) {
        taskService.create(createTaskRequest);
        return ApiResponse.success();
    }

    @PostMapping("v1/get/{id}")
    public ApiResponse get(@PathVariable String id) {
        return ApiResponse.success(taskService.getById(id));
    }

    @PostMapping("v1/tasks/{userId}")
    public ApiResponse getAll(@PathVariable String userId) {
        return ApiResponse.success(taskService.getAllByUserId(userId));
    }

    @PostMapping("v1/update")
    public ApiResponse update(@RequestBody UpdateTaskRequest updateTaskRequest) {
        taskService.update(updateTaskRequest);
        return ApiResponse.success();
    }

    @PostMapping("v1/delete/{id}")
    public ApiResponse delete(@PathVariable String id) {
        taskService.delete(id);
        return ApiResponse.success();
    }
}
