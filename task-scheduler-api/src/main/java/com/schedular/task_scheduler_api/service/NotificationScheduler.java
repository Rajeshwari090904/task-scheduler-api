package com.schedular.task_scheduler_api.service;

// File: src/main/java/com/scheduler/taskschedulerapi/service/NotificationScheduler.java



import com.schedular.task_scheduler_api.model.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled; // NEW IMPORT
import org.springframework.stereotype.Component; // NEW IMPORT

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

@Component // Tells Spring to manage this class
public class NotificationScheduler {

    private final TaskManager taskService;

    @Autowired
    public NotificationScheduler(TaskManager taskService) {
        this.taskService = taskService;
    }

    // This method will run every 60 seconds (60000 milliseconds)
    @Scheduled(fixedRate = 60000)
    public void checkDueTasks() {
        try {
            // 1. Get all tasks from the database
            List<Task> tasks = taskService.getAllTasks();
            LocalDateTime now = LocalDateTime.now();

            System.out.println("--- Scheduler Check: Checking " + tasks.size() + " tasks at " + now.toLocalTime());

            for (Task task : tasks) {
                // 2. Combine the date and time for comparison
                LocalDateTime taskDateTime = LocalDateTime.of(task.getDate(), task.getTime());

                // 3. Logic: If the task is PENDING and due in the next 1 minute (or is overdue)
                if (task.getStatus().equals("PENDING") && taskDateTime.isBefore(now.plusMinutes(1))) {

                    if (taskDateTime.isBefore(now)) {
                        // Task is overdue
                        System.out.println("!! ⏰ OVERDUE: " + task.getTitle() + " (ID: " + task.getId() + ")");
                    } else {
                        // Task is due soon (within the next minute)
                        System.out.println("!! ⭐ REMINDER: " + task.getTitle() + " is due at " + task.getTime());
                    }

                    // Optional: Update status to 'ALERTED' in the DB here
                }
            }
        } catch (SQLException e) {
            System.err.println("Scheduler Error: Database access failed: " + e.getMessage());
        }
    }
}