package com.student.schema;

import org.infinispan.protostream.GeneratedSchema;
import org.infinispan.protostream.annotations.ProtoSchema;

import com.student.pojo.StudentDetails;

@ProtoSchema(schemaPackageName = "com.student.pojo", includeClasses = { StudentDetails.class })
public interface StudentDetailsSchema extends GeneratedSchema {

}
