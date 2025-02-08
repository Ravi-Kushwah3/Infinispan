package com.student.client;

import org.infinispan.client.hotrod.RemoteCache;
import org.infinispan.client.hotrod.RemoteCacheManager;
import org.infinispan.client.hotrod.configuration.ConfigurationBuilder;
import org.infinispan.protostream.GeneratedSchema;
import org.infinispan.protostream.SerializationContextInitializer;
import org.infinispan.query.remote.client.ProtobufMetadataManagerConstants;

import com.student.constant.InfinispanConstant;

public class RemoteCacheFactory {
	private static RemoteCacheFactory factory = null;
	public static RemoteCacheFactory getInstance() {
    if(factory == null) {
    	factory = new RemoteCacheFactory();
    }
    return factory;
	}

	public RemoteCacheManager createRemoteCacheManager(String serverIp) {
		RemoteCacheManager remoteCacheManager = null;
		try {
			System.out.println("Creating remote cache manager");
			ConfigurationBuilder builder = new ConfigurationBuilder();
			builder.addServer().host(serverIp).port(11222);
			 builder.security().authentication().disable();
		//	builder.security().authentication().username("admin").password("admin");
			registerMarshallers(builder);
			remoteCacheManager = new RemoteCacheManager(builder.build());
			 registerProtoFiles(remoteCacheManager);
			
			return remoteCacheManager;

		} catch (Exception e) {
			System.out.println("Exception in createRemoteCache : " + e.getMessage());
		}
		return remoteCacheManager;
	}

	private static void registerProtoFiles(RemoteCacheManager cacheManager) {
		// Retrieve metadata cache
		RemoteCache<String, String> metadataCache = cacheManager
				.getCache(ProtobufMetadataManagerConstants.PROTOBUF_METADATA_CACHE_NAME);
		for (Object obj : InfinispanConstant.SCHEMA_LIST) {
			GeneratedSchema schema = (GeneratedSchema) obj;
			metadataCache.put(schema.getProtoFileName(), schema.getProtoFile());
		}
		System.out.println("Register proto files");
	}

	private static ConfigurationBuilder registerMarshallers(ConfigurationBuilder clientBuilder) {
		for (Object obj : InfinispanConstant.SCHEMA_LIST) {
			clientBuilder.addContextInitializer((SerializationContextInitializer) obj);

		}
		System.out.println("Register Marshallers ");
		return clientBuilder;
	}
}
