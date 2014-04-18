package com.aug3.logservice.thrift.log;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.log4j.Logger;

import com.aug3.logservice.exception.LogSystemException;

/**
 * @author roger.xia
 * @date 2012-5-11
 * 
 */
public class LogProcessor {

	private static final Logger logger = Logger.getLogger(LogProcessor.class);
	private FileWriter fileWriter;
	private DBWriter dbWriter;

	private ExecutorService executor = Executors.newCachedThreadPool();

	public void dealLog(final LogEntity entity) {
		executor.execute(new Runnable() {
			@Override
			public void run() {
				try {
					dbWriter.saveLog(entity);
				} catch (Exception ex) {
					logger.error("dealLog", ex);
					try {
						fileWriter.writeLog(entity);
					} catch (Exception e) {
						logger.error("dealLogList", e);
					}
				}
			}
		});

	}

	public void dealLogFile(final String catlog, final String level, final String machine, final List<String> messages) {

		if (messages != null) {
			executor.execute(new Runnable() {

				@Override
				public void run() {
					try {
						fileWriter.writeLog(catlog, level, machine, messages);
					} catch (LogSystemException e) {
						logger.error("dealLogFile", e);
					}
				}
			});
		}

	}

	public DBWriter getDbWriter() {
		return dbWriter;
	}

	public void setDbWriter(DBWriter dbWriter) {
		this.dbWriter = dbWriter;
	}

	public FileWriter getFileWriter() {
		return fileWriter;
	}

	public void setFileWriter(FileWriter fileWriter) {
		this.fileWriter = fileWriter;
	}

}
