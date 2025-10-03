package com.schedular.task_scheduler_api.model;

import java.time.LocalDate;
import java.time.LocalTime;

public class Task {
    private long id;
    private String title;
    private String description;
    private LocalDate date;
    private LocalTime time;
    private String status;

    // Constructors
    public Task(long id, String title, String description, LocalDate date, LocalTime time, String status) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.date = date;
        this.time = time;
        this.status = status;
    }

    // Default constructor is required by some JSON frameworks (like Jackson)
    public Task() {}

    // Getters and Setters (omitted for brevity, ensure they are all present)
    public long getId() { return id; }
    public void setId(long id) { this.id = id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }
    public LocalTime getTime() { return time; }
    public void setTime(LocalTime time) { this.time = time; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    @Override
    public String toString() {
        return "Task [ID=" + id + ", Title=" + title +
                ", Due=" + date + " at " + time +
                ", Status=" + status + "]";
    }
}