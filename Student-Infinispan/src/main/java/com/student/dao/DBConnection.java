package com.student.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

import com.student.util.Utility;

public class DBConnection {
	private Connection conn;

	public static DBConnection getDAOInstance() {
		return new DBConnection();
	}

	public Connection getConnection() {
		try {
			Properties properties = Utility.getInstance().getProperties();
			String url = properties.getProperty("dburl");
			String username = properties.getProperty("username");
			String password = properties.getProperty("password");
			conn = DriverManager.getConnection(url, username, password);
			if (null != conn) {
				System.out.println("Connected to database");
			} else {
				System.out.println("Connection not happened");
			}
			return conn;
		} catch (Exception e) {
			System.out.println("Exception in getConnection : " + e.getMessage());
		}
		return conn;
	}

	public static void main(String[] args) {
		new DBConnection().getConnection();
	}

}
