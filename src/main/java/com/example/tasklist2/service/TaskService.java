package com.example.tasklist2.service;

import com.example.tasklist2.domain.task.Task;
import com.example.tasklist2.domain.task.TaskImage;

import java.util.List;

public interface TaskService {

    Task getById(Long id);

    List<Task> getAllByUserId(Long id);

    Task update(Task task);


    Task create(Task task, Long userId);

    void delete(Long id);

    void uploadImage(Long id, TaskImage image);
}
