package com.schedular.task_scheduler_api.controller;


import com.schedular.task_scheduler_api.model.Task;
import com.schedular.task_scheduler_api.service.TaskManager;
import com.schedular.task_scheduler_api.service.InvalidTaskInputException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    private final TaskManager taskService;

    @Autowired
    public TaskController(TaskManager taskService) {
        this.taskService = taskService;
    }

    // --- READ ALL: GET http://localhost:8080/api/tasks ---
    @GetMapping
    public ResponseEntity<List<Task>> getAllTasks() {
        try {
            List<Task> tasks = taskService.getAllTasks();
            return new ResponseEntity<>(tasks, HttpStatus.OK);
        } catch (SQLException e) {
            // Handle database connection errors gracefully
            return new ResponseEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR); // 500
        }
    }

    // --- READ BY ID: GET http://localhost:8080/api/tasks/{id} ---
    @GetMapping("/{id}")
    public ResponseEntity<Task> getTaskById(@PathVariable Long id) {
        try {
            Optional<Task> task = taskService.getTaskById(id);
            return task.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                    .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
        } catch (SQLException e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // --- CREATE: POST http://localhost:8080/api/tasks ---
    @PostMapping
    public ResponseEntity<?> addTask(@RequestBody Task task) {
        try {
            Task savedTask = taskService.addTask(
                    task.getTitle(),
                    task.getDescription(),
                    task.getDate().toString(),
                    task.getTime().toString()
            );
            return new ResponseEntity<>(savedTask, HttpStatus.CREATED); // 201 Created
        } catch (InvalidTaskInputException e) {
            // Bad user input (past date, bad format)
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST); // 400
        } catch (SQLException e) {
            return new ResponseEntity<>("Database Error: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // --- DELETE: DELETE http://localhost:8080/api/tasks/{id} ---
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
        try {
            if (taskService.deleteTask(id)) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT); // 204 Success, no body
            }
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // 404 Not Found
        } catch (SQLException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
