package com.example.tasklist2.web.mappers;

import com.example.tasklist2.domain.task.Task;
import com.example.tasklist2.web.dto.task.TaskDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TaskMapper extends Mappable<Task, TaskDto> {


}
