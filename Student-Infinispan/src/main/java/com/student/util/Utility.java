package com.student.util;

import java.io.FileInputStream;
import java.util.Properties;

public class Utility {
	private static Utility instance;
	private Properties properties;
	private String url;
	private String username;
	private String password;
	private String infiIP;
	private String infiUserName;
	private String infiPassword;

	public static synchronized Utility getInstance() {
		if (instance == null) {
			instance = new Utility();
		}
		return instance;
	}

	public Properties getProperties() {
		return properties;
	}

	static {
		System.out.println("Loading properties file");
		Utility.getInstance().loadProperties();
	}

	public void setProperties(Properties properties) {
		this.properties = properties;
		setInfiIP(properties.getProperty("infi_host"));
		setInfiPassword(properties.getProperty("infi_password"));
		setInfiUserName(properties.getProperty("infi_username"));
		setPassword(properties.getProperty("password"));
		setUrl(properties.getProperty("dburl"));
		setUsername(properties.getProperty("username"));
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getInfiIP() {
		return infiIP;
	}

	public void setInfiIP(String infiIP) {
		this.infiIP = infiIP;
	}

	public String getInfiUserName() {
		return infiUserName;
	}

	public void setInfiUserName(String infiUserName) {
		this.infiUserName = infiUserName;
	}

	public String getInfiPassword() {
		return infiPassword;
	}

	public void setInfiPassword(String infiPassword) {
		this.infiPassword = infiPassword;
	}

	public void loadProperties() {
		properties = new Properties();
		try {
			FileInputStream file = new FileInputStream("src/main/java/com/student/dao/application.properties");
			properties.load(file);
			System.out.println("Properties : " + properties.toString());
			setProperties(properties);
		} catch (Exception e) {
			System.out.println("Exception in loadProperties : " + e.getMessage());
		}
	}

	@Override
	public String toString() {
		return "Utility [properties=" + properties + ", url=" + url + ", username=" + username + ", password="
				+ password + ", infiIP=" + infiIP + ", infiUserName=" + infiUserName + ", infiPassword=" + infiPassword
				+ "]";
	}
}
