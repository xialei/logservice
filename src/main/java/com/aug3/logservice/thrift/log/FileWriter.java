package com.aug3.logservice.thrift.log;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.apache.commons.lang3.StringUtils;

import com.aug3.sys.util.DateUtil;
import com.aug3.sys.util.JSONUtil;
import com.aug3.logservice.constants.Constants;
import com.aug3.logservice.exception.LogSystemException;
import com.aug3.logservice.util.ConfigManager;

/**
 * Write the log to the file system
 * 
 */
public class FileWriter {

	private static volatile boolean logpathexist = false;

	private static final String filePath = ConfigManager.getProperty("log.file.path");
	private static final String fileName = ConfigManager.getProperty("log.file.name", "log");
	private static final boolean separate_error_log = ConfigManager.getBooleanProperty("log.error.separated", true);

	public static final String LEVEL_ERROR = "ERROR";

	private ReadWriteLock lock = new ReentrantReadWriteLock();

	public void writeLog(LogEntity entity) throws LogSystemException {

		String logFile = getLogFile();

		String content = fmtLogEntity(entity);

		writeToFile(logFile, content);

	}

	public void writeLog(String catlog, String level, String machine, List<String> messages) throws LogSystemException {

		String logFile = getLogFile(catlog, level);

		String content = fmtLogEntity(machine, messages);

		writeToFile(logFile, content);
	}

	private void writeToFile(String fileFullName, String content) throws LogSystemException {

		mkDir(filePath);

		FileOutputStream fos = null;

		lock.writeLock().lock();
		try {

			fos = new FileOutputStream(fileFullName, true);
			fos.write(content.getBytes("utf-8"));

		} catch (FileNotFoundException e) {

			throw new LogSystemException("FileNotFoundException, " + fileFullName);

		} catch (UnsupportedEncodingException e) {

			throw new LogSystemException("UnsupportedEncodingException, write to " + fileFullName);

		} catch (IOException e) {

			throw new LogSystemException("IOException, write to " + fileFullName);

		} finally {

			lock.writeLock().unlock();

			if (fos != null) {
				try {
					fos.close();
				} catch (IOException e) {

				}
			}
		}

	}

	/**
	 * @param dirPath
	 * @return void
	 */
	private void mkDir(String dirPath) {
		if (!logpathexist) {
			lock.readLock().lock();
			try {
				File file = new File(dirPath);
				if (!file.exists()) {
					lock.readLock().unlock();
					lock.writeLock().lock();
					try {
						file.mkdirs();
						logpathexist = true;
					} finally {
						lock.writeLock().unlock();
					}
					lock.readLock().lock();
				}
			} finally {
				lock.readLock().unlock();
			}
		}
	}

	private String getLogFile() {

		return new StringBuilder().append(filePath).append(File.separator).append(fileName)
				.append(Constants.LOG_FILE_SUFFIX).append(Constants.SEP_DOT).append(DateUtil.getCurrentDate())
				.toString();
	}

	private String getLogFile(String catlog, String level) {

		String suffix = separate_error_log ? (LEVEL_ERROR.equalsIgnoreCase(level) ? Constants.LOG_FILE_ERROR_SUFFIX
				: Constants.LOG_FILE_SUFFIX) : Constants.LOG_FILE_SUFFIX;

		return new StringBuilder().append(filePath).append(File.separator).append(fileName)
				.append(StringUtils.isNotBlank(catlog) ? catlog.toLowerCase() : "").append(suffix)
				.append(Constants.SEP_DOT).append(DateUtil.getCurrentDate()).toString();
	}

	private String fmtLogEntity(LogEntity entity) {
		return JSONUtil.toJson(entity);
	}

	private String fmtLogEntity(String machine, List<String> messages) {
		StringBuilder content = new StringBuilder();
		for (String m : messages) {
			content.append(machine).append(" ").append(m).append("\n");
		}
		return content.toString();
	}

}
