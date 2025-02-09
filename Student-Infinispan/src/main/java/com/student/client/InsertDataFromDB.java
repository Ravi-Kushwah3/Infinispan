package com.student.client;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.infinispan.client.hotrod.RemoteCache;
import org.infinispan.client.hotrod.RemoteCacheManager;
import org.infinispan.client.hotrod.Search;
import org.infinispan.commons.api.query.Query;
import org.infinispan.query.dsl.QueryFactory;

import com.student.constant.InfinispanConstant;
import com.student.dao.DBConnection;
import com.student.pojo.StudentDetails;
import com.student.util.Utility;

public class InsertDataFromDB {
	private RemoteCacheManager remoteManager = null;

	static {
		System.out.println("Loading properties");
	}

	public static void main(String[] args) throws SQLException {
		InsertDataFromDB insert = new InsertDataFromDB();
		insert.createConnection();
	}

	public static List<StudentDetails> insertRecords() throws SQLException {
		List<StudentDetails> studentList = new ArrayList<StudentDetails>();
		Connection conn = null;
		ResultSet resultSet = null;
		PreparedStatement statement = null;
		String query = "SELECT * FROM STUDENT";
		try {
			conn = DBConnection.getDAOInstance().getConnection();
			statement = conn.prepareStatement(query);
			resultSet = statement.executeQuery();
			while (resultSet.next()) {
				StudentDetails student = new StudentDetails();
				student.setStudentAddress(resultSet.getString("studentAddress"));
				student.setStudentAge(Float.toString(resultSet.getFloat("studentAge")));
				student.setStudentName(resultSet.getString("studentName"));
				student.setStudentPhoneNumber(resultSet.getLong("studentPhoneNumber"));
				studentList.add(student);
			}

			return studentList;
		} catch (Exception e) {
			System.out.println("Exception in insertRecords : " + e.getMessage());
		}
		return studentList;
	}

	public static String generateKey(StudentDetails student) {
		return student.getStudentName() + "_" + student.getStudentPhoneNumber();
	}

	public void createConnection() throws SQLException {

		try {
			Properties prop = Utility.getInstance().getProperties();
			String serverIP = prop.getProperty("infi_host");
			Query<StudentDetails> query = null;
			RemoteCacheFactory factory = RemoteCacheFactory.getInstance();
			remoteManager = factory.createRemoteCacheManager(serverIP);
			if (null != remoteManager) {
				System.out.println("Connecting successfully");
				RemoteCache<String, StudentDetails> cache = remoteManager.getCache(InfinispanConstant.STUDENT_CACHE);
				List<StudentDetails> studentRecords = insertRecords();
				int count = 0;
				if (null != studentRecords) {
					System.out.println("Going to insert records in cache");
					for (StudentDetails records : studentRecords) {
						count++;
						System.out.println(records.toString());
						cache.putAsync(generateKey(records), records);
					}
					System.out.println("Record inserted count : " + count);
				}
				System.out.println("Get records from cache");
				QueryFactory qf = Search.getQueryFactory(cache);
				//query = qf.from(StudentDetails.class).build();
				 String queryString = "FROM com.student.pojo.StudentDetails";
				// System.out.println("Query : "+queryString);
				 query = cache.query(queryString);
				System.out.println("================" + query.list().toString());
				List<StudentDetails> student = query.list();
				for (StudentDetails entry : student) {
					System.out.println(entry.toString());
				}

			} else {
				System.out.println("Connection not happened");
			}

		} catch (Exception e) {
			System.out.println("Exception in createConnection : " + e.getMessage());
		}
	}
}
