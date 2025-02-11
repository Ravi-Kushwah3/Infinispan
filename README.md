# Infinispan
Its a cache that use to perform quick operation on data select,insert,update and delete.
Steps after clone project :
1. compile and build project with maven
2. Run infinispan server and create user take help from Infinispan_steps.txt file.
3. If you want to login infinispan without user and password comment these tags in infinispan.xml file
   <security>
         <authorization/>
      </security>
      <security>
         <security-realms>
            <security-realm name="default">
                
               <server-identities>
                  <ssl>
                     <keystore path="server.pfx"
                               password="password" alias="server"
                               generate-self-signed-certificate-host="localhost"/>
                  </ssl>
               </server-identities>
               <propertietitis-realm/>
            </security-realm>
         </security-realms>
      </security>
4. create cache in infinispan.xml file
   <local-cache name="Student" statistics="true">
	<encoding media-type="application/x-protostream"/>
	<locking concurrency-level="1000" acquire-timeout="60000" striping="true"/>
	<transaction mode="NONE"/>
	<indexing enabled="true" storage="local-heap" startup-mode="AUTO" indexing-mode="AUTO">
		<index-writer queue-size="10000" thread-pool-size="50" ram-buffer-size="1024"/>
		<indexed-entities>
			<indexed-entity>com.student.pojo.StudentDetails</indexed-entity>
		</indexed-entities>
	</indexing>
</local-cache>

5. configure credentials in application.properties file
6. If you want to store data permanent in cache use this configuration.
    <local-cache name="test">
    <encoding media-type="application/x-protostream"/>
   <persistence passivation="false">
    <file-store>
      <data path="/home/rkushwah/INFI15/infinispan-server-15.1.1.Final/server/data/infidata.data" />
    </file-store>
   </persistence>
  </local-cache>


 
