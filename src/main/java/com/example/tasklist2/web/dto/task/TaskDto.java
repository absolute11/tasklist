package com.example.tasklist2.web.dto.task;

import com.example.tasklist2.domain.task.Status;
import com.example.tasklist2.web.dto.validation.OnCreate;
import com.example.tasklist2.web.dto.validation.OnUpdate;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class TaskDto {
    @NotNull(message = "id must be not null.",groups = OnUpdate.class)
    private Long id;
    @NotNull(message = "Title must be not null.",groups = OnCreate.class)
    @Length(max = 255,message = "Title length must be smaller than 255 symbols",groups = {OnCreate.class, OnUpdate.class})
    private String title;
    @Length(max = 255,message = "Title length must be smaller than 255 symbols",groups = {OnCreate.class, OnUpdate.class})
    private String description;

    private Status status;
    @DateTimeFormat(iso= DateTimeFormat.ISO.TIME)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime expirationDate;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private List<String> images;

}
