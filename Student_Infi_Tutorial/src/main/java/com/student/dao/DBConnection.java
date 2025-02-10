package com.student.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.student.util.Utility;

public class DBConnection {
	private static final Logger LOG = LogManager.getLogger(DBConnection.class);
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
				LOG.info("Connected to database successfully.");
			} else {
				LOG.info("Connection not happened with database.");
			}
			return conn;
		} catch (Exception e) {
			LOG.error("Exception in getConnection : ",e);
		}
		return conn;
	}

	public static void main(String[] args) {
		new DBConnection().getConnection();
	}

}
