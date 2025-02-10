package com.student.client;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.infinispan.client.hotrod.RemoteCache;
import org.infinispan.client.hotrod.RemoteCacheManager;
import org.infinispan.client.hotrod.configuration.ConfigurationBuilder;
import org.infinispan.protostream.GeneratedSchema;
import org.infinispan.protostream.SerializationContextInitializer;
import org.infinispan.query.remote.client.ProtobufMetadataManagerConstants;

import com.student.constant.InfinispanConstant;
import com.student.constant.StringKeyMarshaller;

public class RemoteCacheFactory {
	private static final Logger LOG = LogManager.getLogger(RemoteCacheFactory.class);
	private static RemoteCacheFactory factory = null;

	public static RemoteCacheFactory getInstance() {
		if (factory == null) {
			factory = new RemoteCacheFactory();
		}
		return factory;
	}

	public RemoteCacheManager createRemoteCacheManager(String serverIp) {
		RemoteCacheManager remoteCacheManager = null;
		try {
			LOG.info("Creating remote cache manager");
			ConfigurationBuilder builder = new ConfigurationBuilder();
			builder.addServer().host(serverIp).port(11222);
			builder.security().authentication().disable();
			// builder.security().authentication().username("admin").password("admin");
			registerMarshallers(builder);
			remoteCacheManager = new RemoteCacheManager(builder.build());
			if (remoteCacheManager != null) {
				remoteCacheManager.getMarshallerRegistry().registerMarshaller(new StringKeyMarshaller());
				LOG.info("Connecting successfully with Infinispan");
			}
			registerProtoFiles(remoteCacheManager);

			return remoteCacheManager;

		} catch (Exception e) {
			LOG.error("Exception in createRemoteCache : ",e);
		}
		return remoteCacheManager;
	}

	private static void registerProtoFiles(RemoteCacheManager cacheManager) {
		try {
			// Retrieve metadata cache
			RemoteCache<String, String> metadataCache = cacheManager
					.getCache(ProtobufMetadataManagerConstants.PROTOBUF_METADATA_CACHE_NAME);
			for (Object obj : InfinispanConstant.SCHEMA_LIST) {
				GeneratedSchema schema = (GeneratedSchema) obj;
				metadataCache.put(schema.getProtoFileName(), schema.getProtoFile());
			}
			LOG.info("Register proto files");
		} catch (Exception e) {
			LOG.error("Exception in registerProtoFiles : ", e);
		}
	}

	private static ConfigurationBuilder registerMarshallers(ConfigurationBuilder clientBuilder) {
		try {
			for (Object obj : InfinispanConstant.SCHEMA_LIST) {
				clientBuilder.addContextInitializer((SerializationContextInitializer) obj);

			}
			LOG.info("Register Marshallers ");
			return clientBuilder;
		} catch (Exception e) {
			LOG.error("Exception in registerMarshallers : ", e);
		}
		return clientBuilder;
	}
}
