package com.student.constant;

import java.util.List;

import com.student.schema.StudentDetailsSchemaImpl;

public class InfinispanConstant {
	public static final String STUDENT_CACHE = "Student";
	public static final List<Object> SCHEMA_LIST = List.of(new StudentDetailsSchemaImpl());
	 
 
	

}
