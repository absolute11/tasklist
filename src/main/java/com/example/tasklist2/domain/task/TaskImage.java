package com.example.tasklist2.domain.task;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data

public class TaskImage {

    private MultipartFile file;
}
