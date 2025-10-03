package com.schedular.task_scheduler_api.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

// NOTE: In Spring Boot, this manual JDBC approach is often replaced by Spring Data JPA.
// We keep it for now as a transition step!
public class DBConnection {

    private static final String URL = "jdbc:mysql://127.0.0.1:3306/task_scheduler_db";
    private static final String USER = "root";
    private static final String PASSWORD = "RAJ@0909";

    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException e) {
            throw new SQLException("MySQL JDBC Driver not found. Check your Maven dependencies.", e);
        }
    }
}
