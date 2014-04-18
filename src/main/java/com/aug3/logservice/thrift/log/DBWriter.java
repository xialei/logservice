package com.aug3.logservice.thrift.log;

import java.util.List;

import com.aug3.storage.mongoclient.MongoFactory;
import com.aug3.logservice.util.ConfigManager;
import com.mongodb.DB;
import com.mongodb.DBCollection;

public class DBWriter {

	private static String dbAddress = ConfigManager.getProperty("log.db.address");
	private static String dbName = ConfigManager.getProperty(ConfigManager.LOG_DB_NAME, "ada");
	private static String collectionName = ConfigManager.getProperty(ConfigManager.LOG_DB_COLLECTION_NAME,
			"ops_visit_log");

	private DB db = new MongoFactory(dbAddress).newMongoInstance().getDB(dbName);

	private DBCollection collection = db.getCollection(collectionName);

	public void saveLog(LogEntity entity) throws Exception {
		collection.save(entity);
	}

	public void saveLogList(List<LogEntity> list) throws Exception {
		collection.insert(list.toArray(new LogEntity[list.size()]));
	}

}
