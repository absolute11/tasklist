package com.example.tasklist2.web.controller;

import com.example.tasklist2.domain.task.Task;
import com.example.tasklist2.domain.user.User;
import com.example.tasklist2.service.TaskService;
import com.example.tasklist2.service.UserService;
import com.example.tasklist2.web.dto.task.TaskDto;
import com.example.tasklist2.web.dto.user.UserDto;
import com.example.tasklist2.web.dto.validation.OnCreate;
import com.example.tasklist2.web.dto.validation.OnUpdate;
import com.example.tasklist2.web.mappers.TaskMapper;
import com.example.tasklist2.web.mappers.UserMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@Validated
@Tag(name = "User Controller", description = "User API")
public class UserController {

    private final UserService userService;
    private final TaskService taskService;

    private final TaskMapper taskMapper;
    private final UserMapper userMapper;

    @PutMapping
    @MutationMapping(name = "updateUser")
    @Operation(summary = "Update user")
    @PreAuthorize("@cse.canAccessUser(#dto.id)")
    public UserDto update(@Validated(OnUpdate.class)
                              @RequestBody @Argument final UserDto dto) {
        User user = userMapper.toEntity(dto);
        User updatedUser = userService.update(user);
        return userMapper.toDto(updatedUser);
    }

    @GetMapping("/{id}")
    @QueryMapping(name = "userById")
    @PreAuthorize("@cse.canAccessUser(#id)")
    @Operation(summary = "Get UserDto by id")
    public UserDto getById(@PathVariable @Argument final Long id) {
        User user = userService.getById(id);
        return userMapper.toDto(user);
    }

    @DeleteMapping("/{id}")
    @MutationMapping(name = "deleteUserById")
    @PreAuthorize("@cse.canAccessUser(#id)")
    @Operation(summary = "Delete user by id")
    public void deleteById(@PathVariable @Argument final Long id) {
        userService.delete(id);
    }

    @GetMapping("/{id}/tasks")
    @QueryMapping(name = "tasksByUserId")
    @PreAuthorize("@cse.canAccessUser(#id)")
    @Operation(summary = "Get all user tasks")
    public List<TaskDto> getTasksByUserId(@PathVariable
                                              @Argument final Long id) {
        List<Task> tasks = taskService.getAllByUserId(id);
        return taskMapper.toDto(tasks);
    }

    @PostMapping("/{id}/tasks")
    @MutationMapping(name = "createTask")
    @PreAuthorize("@cse.canAccessUser(#id)")
    @Operation(summary = "Add task to user")
    public TaskDto createTask(@PathVariable @Argument final Long id,
                              @Validated(OnCreate.class)
                              @RequestBody @Argument final TaskDto dto) {

        Task task = taskMapper.toEntity(dto);
        Task createdTask = taskService.create(task, id);
        return taskMapper.toDto(createdTask);

    }


}
