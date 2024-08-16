package com.example.tasklist2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableTransactionManagement
@SpringBootApplication
@EnableCaching
public class TaskList2Application {

	public static void main(String[] args) {
		SpringApplication.run(TaskList2Application.class, args);
	}

}
