package com.ataoglan.todo_app.controller;

import com.ataoglan.todo_app.domain.request.UpdateUserRequest;
import com.ataoglan.todo_app.domain.response.ApiResponse;
import com.ataoglan.todo_app.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("v1/update")
    public ApiResponse update(@RequestBody UpdateUserRequest updateUserRequest) {
        userService.update(updateUserRequest);
        return ApiResponse.success();
    }

    @PostMapping("v1/update-password")
    public ApiResponse updatePassword(@RequestBody UpdateUserRequest updateUserRequest) {
        userService.updatePassword(updateUserRequest);
        return ApiResponse.success();
    }

    @PostMapping("v1/delete/{id}")
    public ApiResponse updatePassword(@PathVariable String id) {
        userService.delete(id);
        return ApiResponse.success();
    }
}
