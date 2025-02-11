package com.student.client;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.infinispan.client.hotrod.RemoteCache;
import org.infinispan.client.hotrod.RemoteCacheManager;
import org.infinispan.client.hotrod.Search;
import org.infinispan.commons.api.query.Query;
//import org.infinispan.query.dsl.Query;
import org.infinispan.query.dsl.QueryFactory;

import com.student.constant.InfinispanConstant;
import com.student.dao.DBConnection;
import com.student.pojo.StudentDetails;
import com.student.util.Utility;

public class InsertDataFromDB {
	private static final Logger LOG = LogManager.getLogger(InsertDataFromDB.class);
	private RemoteCacheManager remoteManager = null;

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
		LOG.info("Query for loading data in cache: " + query);
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
			LOG.error("Exception in insertRecords : ", e);
		}finally {
			if(resultSet!=null) {
				resultSet.close();
			}
			if(statement!=null) {
				statement.close();
			}
			if(conn!=null) {
				conn.close();
			}
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
				RemoteCache<String, StudentDetails> cache = remoteManager.getCache(InfinispanConstant.STUDENT_CACHE);
				List<StudentDetails> studentRecords = insertRecords();
				int count = 0;
				if (studentRecords.size() > 0) {
					LOG.info("Going to insert records in cache");
					for (StudentDetails records : studentRecords) {
						count++;
						LOG.info(records.toString());
						cache.putAsync(generateKey(records), records);
					}
					LOG.info("Record inserted count : " + count);

					LOG.info("Get records from cache");
					QueryFactory qf = Search.getQueryFactory(cache);
					// query = qf.from(StudentDetails.class).build();
					String queryString = "FROM com.student.pojo.StudentDetails";
					LOG.info("Query for cache : " + queryString);
					// query = cache.query(queryString);
					int recordFromCache = 0;
					List<StudentDetails> student = query.list();
					if (student.size() > 0) {
						for (StudentDetails entry : student) {
							recordFromCache++;
							LOG.info(entry.toString());
						}
						LOG.info("Total records from cache : " + recordFromCache);
					} else {
                                               LOG.info("Record not found in cache");
					}
					 
				} else {
					LOG.info("Records are not available in database");
				}

			} else {
				LOG.info("Connection not happened with infinispan.");
			}

		} catch (Exception e) {
			LOG.error("Exception in createConnection : ", e);
		}
	} 
}
