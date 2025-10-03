package com.schedular.task_scheduler_api.service;
 // Updated package

import com.schedular.task_scheduler_api.model.Task;
import com.schedular.task_scheduler_api.repository.TaskRepository; // Use local repository package
import org.springframework.stereotype.Service; // NEW Spring annotation
import org.springframework.beans.factory.annotation.Autowired; // NEW Spring annotation

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service // Tells Spring: "Manage this class as a business component."
public class TaskManager {

    private final TaskRepository repository;

    @Autowired // Spring automatically injects the TaskRepository instance
    public TaskManager(TaskRepository repository) {
        this.repository = repository;
    }

    // --- FINAL CREATE: Calls repository.save() ---
    public Task addTask(String title, String description, String dateStr, String timeStr)
            throws InvalidTaskInputException, SQLException
    {
        try {
            LocalDate date = LocalDate.parse(dateStr);
            LocalTime time = LocalTime.parse(timeStr);

            LocalDateTime scheduledDateTime = LocalDateTime.of(date, time);

            if (scheduledDateTime.isBefore(LocalDateTime.now())) {
                throw new InvalidTaskInputException("Task cannot be scheduled in the past. Choose a future date/time.");
            }

            Task newTask = new Task(0, title, description, date, time, "PENDING");

            // Returns the task with the DB-generated ID
            return repository.save(newTask);

        } catch (DateTimeParseException e) {
            throw new InvalidTaskInputException("Invalid date or time format. Please use YYYY-MM-DD and HH:MM.");
        }
    }

    // --- READ: Calls repository.findAll() ---
    public List<Task> getAllTasks() throws SQLException {
        return repository.findAll();
    }

    // --- READ BY ID: Calls repository.findById() ---
    public Optional<Task> getTaskById(long taskId) throws SQLException {
        return repository.findById(taskId);
    }

    // --- UPDATE: Calls repository.updateStatus() ---
    public boolean updateTaskStatus(long taskId, String newStatus) throws SQLException {
        return repository.updateStatus(taskId, newStatus);
    }

    // --- DELETE: Calls repository.delete() ---
    public boolean deleteTask(long taskId) throws SQLException {
        return repository.delete(taskId);
    }

    // --- SORTING: Fetches from DB and sorts ---
    public List<Task> getTasksSortedByTime() throws SQLException {
        List<Task> sortedList = repository.findAll();

        Collections.sort(sortedList, (t1, t2) -> {
            LocalDateTime dt1 = LocalDateTime.of(t1.getDate(), t1.getTime());
            LocalDateTime dt2 = LocalDateTime.of(t2.getDate(), t2.getTime());
            return dt1.compareTo(dt2);
        });

        return sortedList;
    }
}