package com.schedular.task_scheduler_api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class TaskSchedulerApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(TaskSchedulerApiApplication.class, args);
	}

}
