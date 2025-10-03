package com.schedular.task_scheduler_api.repository;
// Updated package

import com.schedular.task_scheduler_api.model.Task;
import org.springframework.stereotype.Repository;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

// NOTE: DBConnection reference is now local to this package
import com.schedular.task_scheduler_api.repository.DBConnection;
@Repository

public class TaskRepository {

    private Task mapResultSetToTask(ResultSet rs) throws SQLException {
        long id = rs.getLong("id");
        String title = rs.getString("title");
        String description = rs.getString("description");
        LocalDate date = rs.getObject("due_date", LocalDate.class);
        LocalTime time = rs.getObject("due_time", LocalTime.class);
        String status = rs.getString("status");
        return new Task(id, title, description, date, time, status);
    }

    public Task save(Task task) throws SQLException {
        String SQL = "INSERT INTO tasks (user_id, title, description, due_date, due_time, status) " +
                "VALUES (1, ?, ?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, task.getTitle());
            stmt.setString(2, task.getDescription());
            stmt.setObject(3, task.getDate());
            stmt.setObject(4, task.getTime());
            stmt.setString(5, task.getStatus());

            // ... (rest of save logic is the same)

            stmt.executeUpdate();

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    long generatedId = generatedKeys.getLong(1);
                    return new Task(generatedId, task.getTitle(), task.getDescription(),
                            task.getDate(), task.getTime(), task.getStatus());
                } else {
                    throw new SQLException("Creating task failed, no ID obtained.");
                }
            }
        }
    }

    public List<Task> findAll() throws SQLException {
        List<Task> tasks = new ArrayList<>();
        String SQL = "SELECT id, title, description, due_date, due_time, status FROM tasks WHERE user_id = 1";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SQL);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                tasks.add(mapResultSetToTask(rs));
            }
        }
        return tasks;
    }

    public Optional<Task> findById(long id) throws SQLException {
        String SQL = "SELECT id, title, description, due_date, due_time, status FROM tasks WHERE id = ? AND user_id = 1";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SQL)) {

            stmt.setLong(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapResultSetToTask(rs));
                }
            }
        }
        return Optional.empty();
    }

    public boolean updateStatus(long id, String newStatus) throws SQLException {
        String SQL = "UPDATE tasks SET status = ? WHERE id = ? AND user_id = 1";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SQL)) {

            stmt.setString(1, newStatus);
            stmt.setLong(2, id);

            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;
        }
    }

    public boolean delete(long id) throws SQLException {
        String SQL = "DELETE FROM tasks WHERE id = ? AND user_id = 1";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SQL)) {

            stmt.setLong(1, id);

            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;
        }
    }
}