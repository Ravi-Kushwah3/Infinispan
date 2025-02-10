package com.student.util;

import java.io.FileInputStream;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Utility {
	private final Logger LOG = LogManager.getLogger(Utility.class);
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
			Utility.getInstance().loadProperties();
		}
		return instance;
	}

	public Properties getProperties() {
		return properties;
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

		this.properties = new Properties();
		try {
			LOG.info("Loading properties....");
			FileInputStream file = new FileInputStream("src/main/resources/application.properties");
			properties.load(file);
			LOG.info("Properties : "+properties );
			setProperties(properties);
		} catch (Exception e) {
			LOG.error("Exception in loadProperties : ", e);
		}
	}

	@Override
	public String toString() {
		return "Utility [properties=" + properties + ", url=" + url + ", username=" + username + ", password="
				+ password + ", infiIP=" + infiIP + ", infiUserName=" + infiUserName + ", infiPassword=" + infiPassword
				+ "]";
	}

	public static void main(String[] args) {
		// new Utility().loadProperties();
		String logPath = System.getProperty("user.dir");
		String osName = System.getProperty("os.name");
		System.out.println("LogPath : " + logPath + " OS : " + osName);
	}
}
