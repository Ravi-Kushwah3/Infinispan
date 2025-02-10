package com.student.client;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.infinispan.client.hotrod.RemoteCache;
import org.infinispan.client.hotrod.RemoteCacheManager;

import com.student.constant.InfinispanConstant;
import com.student.pojo.StudentDetails;
import com.student.util.Utility;

import org.infinispan.client.hotrod.Search;
import org.infinispan.commons.api.query.Query;
//import org.infinispan.query.dsl.Query;
import org.infinispan.query.dsl.QueryFactory;

public class Test {
	private static final Logger LOG = LogManager.getLogger(Test.class);
	private static RemoteCacheManager remoteManager = null;

	public static void main(String[] args) {
		Test t = new Test();
		t.createConnection();
	}

	public void createConnection() {
		try {
			Properties prop = Utility.getInstance().getProperties();
			LOG.info("Properties : "+prop);
			String serverIP = prop.getProperty("infi_host");
			 Query<StudentDetails> query = null;
			RemoteCacheFactory factory = RemoteCacheFactory.getInstance();
			remoteManager = factory.createRemoteCacheManager(serverIP);
			if (null != remoteManager) {
				RemoteCache<String, StudentDetails> cache = remoteManager.getCache(InfinispanConstant.STUDENT_CACHE);
				List<StudentDetails> studentRecords = insertRecords();
				int count = 0;
				if (null != studentRecords) {
					LOG.info("Going to insert records in cache");
					for (StudentDetails records : studentRecords) {
						count++;
						LOG.info(records.toString());
						cache.putAsync(generateKey(records), records);
					}
					LOG.info("Record inserted count : " + count);
				}
				LOG.info("Get records from cache");
				QueryFactory qf = Search.getQueryFactory(cache);
				query = qf.from(StudentDetails.class).having("studentName").eq("sachin").build();
				//String queryString = "FROM com.student.pojo.StudentDetails";
			//	 query = cache.query(queryString);
				LOG.info("Query for cache : " + query);
				int recordFromCache = 0;
				List<StudentDetails> student = query.list();
				if (student.size() > 0) {
					for (StudentDetails entry : student) {
						recordFromCache++;
						LOG.info(entry.toString());
					}
					LOG.info("Total records from cache : "+recordFromCache);
				} else {
					LOG.info("Record not found");
				}

			} else {
				LOG.info("Connection not happened");
			}
		} catch (Exception e) {
			LOG.error("Exception : " + e.getMessage());
		}

	}

	public static List<StudentDetails> insertRecords() {
		StudentDetails student1 = new StudentDetails();
		List<StudentDetails> list = new ArrayList<StudentDetails>();
		// Record 1
		student1.setStudentAddress("Mumbai");
		student1.setStudentAge("21");
		student1.setStudentName("Raj");
		student1.setStudentPhoneNumber(7896541230l);
		list.add(student1);
		// Record 2
		StudentDetails student2 = new StudentDetails();
		student2.setStudentAddress("Pune");
		student2.setStudentAge("21");
		student2.setStudentName("Golu");
		student2.setStudentPhoneNumber(7896541230l);
		list.add(student2);
		// Record 3
		StudentDetails student3 = new StudentDetails();
		student3.setStudentAddress("Delhi");
		student3.setStudentAge("21");
		student3.setStudentName("Aman");
		student3.setStudentPhoneNumber(7896541230l);
		list.add(student3);
		// Record 4
		StudentDetails student4 = new StudentDetails();
		student4.setStudentAddress("Gwalior");
		student4.setStudentAge("21");
		student4.setStudentName("Ravi");
		student4.setStudentPhoneNumber(7896541230l);
		list.add(student4);
		// Record 5
		StudentDetails student5 = new StudentDetails();
		student5.setStudentAddress("Agra");
		student5.setStudentAge("21");
		student5.setStudentName("Rakesh");
		student5.setStudentPhoneNumber(7896541230l);
		list.add(student5);
		// Record 6
		StudentDetails student6 = new StudentDetails();
		student6.setStudentAddress("Hydrabad");
		student6.setStudentAge("21");
		student6.setStudentName("abdul");
		student6.setStudentPhoneNumber(7896541230l);
		list.add(student6);
		// Record 7
		StudentDetails student7 = new StudentDetails();
		student7.setStudentAddress("Indore");
		student7.setStudentAge("21");
		student7.setStudentName("Neha");
		student7.setStudentPhoneNumber(7896541230l);
		list.add(student7);
		// Record 8
		StudentDetails student8 = new StudentDetails();
		student8.setStudentAddress("Odisa");
		student8.setStudentAge("21");
		student8.setStudentName("sachin");
		student8.setStudentPhoneNumber(7896541230l);
		list.add(student8);

		return list;
	}

	public static String generateKey(StudentDetails student) {
		return student.getStudentName() + "_" + student.getStudentPhoneNumber();
	}
}
