package com.student.constant;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;

import org.infinispan.commons.dataconversion.MediaType;
import org.infinispan.commons.io.ByteBuffer;
import org.infinispan.commons.marshall.BufferSizePredictor;
import org.infinispan.commons.marshall.Marshaller;

public class StringKeyMarshaller implements Marshaller {

	public byte[] objectToByteBuffer(String key) throws IOException, InterruptedException {
		return key.getBytes();
	}

	@Override
	public String objectFromByteBuffer(byte[] bytes) throws IOException, ClassNotFoundException {
		return new String(bytes);
	}

	@Override
	public boolean isMarshallable(Object o) {
		return o instanceof String;
	}

	@Override
	public byte[] objectToByteBuffer(Object obj, int estimatedSize) throws IOException, InterruptedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public byte[] objectToByteBuffer(Object obj) throws IOException, InterruptedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object objectFromByteBuffer(byte[] buf, int offset, int length) throws IOException, ClassNotFoundException {
		Object obj = null;
		ObjectInput in = null;
		ByteArrayInputStream bis = null;
		try {
			bis = new ByteArrayInputStream(buf, offset, length);
			in = new ObjectInputStream(bis);
			obj = in.readObject();
		} catch (ClassNotFoundException e) {

			e.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();
		}
		finally {
			in.close();
			bis.close();
		}
		return obj;
	}

	@Override
	public ByteBuffer objectToBuffer(Object o) throws IOException, InterruptedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BufferSizePredictor getBufferSizePredictor(Object o) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public MediaType mediaType() {
		// TODO Auto-generated method stub
		return MediaType.APPLICATION_OBJECT;
	}
}
