package com.aug3.logservice.util;

import com.aug3.sys.props.ConfigProperties;

public class ConfigManager {

	private static final String[] propertyFiles = { "/logservice.properties" };
	
	public static final String LOG_DB_NAME = "log.db.name";
	
	public static final String LOG_DB_COLLECTION_NAME = "log.collection.name";
	
	public static final String LOG_SERVICE_PORT = "log.service.port";
	
	public static final String LOG_SERVICE_IP = "log.service.ip";
	
	public static final String LOG_SERVICE_TIMEOUT = "log.service.timeout";
	
	public static final String LOG_FILE_PATH = "log.file.path";
	
	public static final String LOG_FILE_NAME = "log.file.name";
	
	public static final String LOG_ERROR_SEPARATED = "log.error.separated";

	private static ConfigProperties props = new ConfigProperties(propertyFiles);

	public static String getProperty(String key, String defaultValue) {
		return props.getProperty(key, defaultValue);
	}

	public static String getProperty(String key) {
		return props.getProperty(key);
	}

	public static int getIntProperty(String key, int defaultValue) {
		return props.getIntProperty(key, defaultValue);
	}

	public static int getIntProperty(String key) {
		return props.getIntProperty(key);
	}

	public static boolean getBooleanProperty(String key) {
		return props.getBooleanProperty(key);
	}

	public static boolean getBooleanProperty(String key, boolean defaultValue) {
		return props.getBooleanProperty(key, defaultValue);
	}

	public static ConfigProperties getConfigProperties() {
		return props;
	}

}
