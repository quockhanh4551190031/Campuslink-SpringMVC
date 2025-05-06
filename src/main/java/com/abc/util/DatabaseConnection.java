package com.abc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
	private static final String URL = "jdbc:mysql://localhost:3306/jobstatistics?useSSL=false&characterEncoding=UTF-8";
    private static final String USER = "root";
    private static final String PASSWORD = "quockhanh"; // Thay bằng mật khẩu MySQL
    
    public Connection getConnection() throws SQLException {
    	try {
    		Class.forName("com.mysql.cj.jdbc.Driver");
    		return DriverManager.getConnection(URL, USER, PASSWORD);
    	} catch (ClassNotFoundException e) {
    		throw new SQLException("MySQL JDBC Driver không tìm thấy", e);
    	}
    }
}
