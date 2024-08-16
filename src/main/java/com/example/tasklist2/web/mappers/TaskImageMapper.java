package com.example.tasklist2.web.mappers;

import com.example.tasklist2.domain.task.TaskImage;
import com.example.tasklist2.web.dto.task.TaskImageDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TaskImageMapper extends Mappable<TaskImage, TaskImageDto>{


}
