package com.student.pojo;

import org.infinispan.api.annotations.indexing.Indexed;
import org.infinispan.api.annotations.indexing.Keyword;
import org.infinispan.protostream.annotations.Proto;
import org.infinispan.protostream.annotations.ProtoField;

@Indexed
@Proto
public class StudentDetails {
	@Keyword(projectable = true, norms = false)
	@ProtoField(number = 1)
	protected String studentName = "";
	@Keyword(projectable = true, norms = false)
	@ProtoField(number = 2)
	protected String studentAge = "";
	@Keyword(projectable = true, norms = false)
	@ProtoField(number = 3)
	protected String studentAddress = "";
	@ProtoField(number = 4)
	protected Long studentPhoneNumber = null;
	public String getStudentName() {
		return studentName;
	}
	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}
	public String getStudentAge() {
		return studentAge;
	}
	public void setStudentAge(String studentAge) {
		this.studentAge = studentAge;
	}
	public String getStudentAddress() {
		return studentAddress;
	}
	public void setStudentAddress(String studentAddress) {
		this.studentAddress = studentAddress;
	}
	public Long getStudentPhoneNumber() {
		return studentPhoneNumber;
	}
	public void setStudentPhoneNumber(Long studentPhoneNumber) {
		this.studentPhoneNumber = studentPhoneNumber;
	}
	@Override
	public String toString() {
		return "StudentDetails [studentName=" + studentName + ", studentAge=" + studentAge + ", studentAddress="
				+ studentAddress + ", studentPhoneNumber=" + studentPhoneNumber + "]";
	}

}
