package com.example.tasklist2.service.impl;

import com.example.tasklist2.domain.exception.ResourceNotFoundException;
import com.example.tasklist2.domain.task.Status;
import com.example.tasklist2.domain.task.Task;
import com.example.tasklist2.domain.task.TaskImage;
import com.example.tasklist2.domain.user.User;
import com.example.tasklist2.repository.TaskRepository;
import com.example.tasklist2.service.ImageService;
import com.example.tasklist2.service.TaskService;
import com.example.tasklist2.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {
    private final TaskRepository taskRepository;
    private final UserService userService;
    private final ImageService imageService;

    @Override
    @Transactional(readOnly = true)
    @Cacheable(value = "TaskService::getById", key = "#id")
    public Task getById(final Long id) {
        return taskRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Task not found."));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Task> getAllByUserId(final Long id) {
        return taskRepository.findAllByUserId(id);
    }

    @Override
    @Transactional
    @CachePut(value = "TaskService::getById", key = "#task.id")
    public Task update(final Task task) {
        if (task.getStatus() == null) {
            task.setStatus(Status.TODO);
        }
        taskRepository.save(task);
        return task;
    }

    @Override
    @Transactional
    @Cacheable(value = "TaskService::getById",
            condition = "#task.id!=null",
            key = "#task.id")
    public Task create(final Task task, final Long userId) {
        User user = userService.getById(userId);
        task.setStatus(Status.TODO);
        taskRepository.save(task);
        user.getTasks().add(task);
        userService.update(user);
        return task;
    }

    @Override
    @Transactional
    @CacheEvict(value = "TaskService::getById", key = "#id")
    public void delete(final Long id) {
        taskRepository.deleteById(id);

    }

    @Override
    @Transactional
    @CacheEvict(value = "TaskService::getById", key = "#id")
    public void uploadImage(final Long id, final TaskImage image) {
        Task task = getById(id);
        String filename = imageService.upload(image);
        task.getImages().add(filename);
        taskRepository.save(task);
    }
}
