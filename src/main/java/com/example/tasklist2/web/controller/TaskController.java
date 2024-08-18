package com.example.tasklist2.web.controller;

import com.example.tasklist2.domain.task.Task;
import com.example.tasklist2.domain.task.TaskImage;
import com.example.tasklist2.service.TaskService;
import com.example.tasklist2.web.dto.task.TaskDto;
import com.example.tasklist2.web.dto.task.TaskImageDto;
import com.example.tasklist2.web.dto.validation.OnUpdate;
import com.example.tasklist2.web.mappers.TaskImageMapper;
import com.example.tasklist2.web.mappers.TaskMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/tasks")
@RequiredArgsConstructor
@Validated
@Tag(name = "Task Controller", description = "Task API")
public class TaskController {
    private final TaskService taskService;
    private final TaskMapper taskMapper;
    private final TaskImageMapper taskImageMapper;

    @PutMapping
    @Operation(summary = "Update task")
    @PreAuthorize("canAccessTask(#dto.id)")
    public TaskDto update(@Validated(OnUpdate.class) @RequestBody TaskDto dto) {
        Task task = taskMapper.toEntity(dto);
        Task updatedTask = taskService.update(task);
        return taskMapper.toDto(updatedTask);

    }

    @GetMapping("/{id}")
    @Operation(summary = "Get TaskDto by id")
    @PreAuthorize("canAccessTask(#id)")
    public TaskDto getById(@PathVariable Long id) {
        Task task = taskService.getById(id);
        return taskMapper.toDto(task);

    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete task")
    @PreAuthorize("canAccessTask(#id)")
    public void deleteById(@PathVariable Long id) {
        taskService.delete(id);
    }

    @PostMapping("/{id}/image")
    @Operation(summary = "Upload image for task")
    @PreAuthorize("canAccessTask(#id)")
    public void uploadImage(@PathVariable Long id, @Validated @ModelAttribute TaskImageDto imageDto) {
        TaskImage image = taskImageMapper.toEntity(imageDto);
        taskService.uploadImage(id, image);
    }


}
