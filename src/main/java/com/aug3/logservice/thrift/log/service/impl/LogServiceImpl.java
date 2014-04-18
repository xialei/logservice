package com.aug3.logservice.thrift.log.service.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.apache.thrift.TException;

import com.aug3.logservice.thrift.log.DBWriter;
import com.aug3.logservice.thrift.log.FileWriter;
import com.aug3.logservice.thrift.log.LogEntity;
import com.aug3.logservice.thrift.log.LogParam;
import com.aug3.logservice.thrift.log.LogProcessor;
import com.aug3.logservice.thrift.log.service.LogService.Iface;

public class LogServiceImpl implements Iface {

	private static final Logger logger = Logger.getLogger(LogServiceImpl.class);

	private LogProcessor logProcessor;

	public void setLogProcessor(LogProcessor logger) {
		this.logProcessor = logger;
	}

	public LogServiceImpl() {
		logProcessor = new LogProcessor();
		logProcessor.setDbWriter(new DBWriter());
		logProcessor.setFileWriter(new FileWriter());
	}

	@Override
	public void log(LogParam param) throws TException {
		try {
			LogEntity entity = LogEntity.buildEntity(param);
			logProcessor.dealLog(entity);
		} catch (Exception ex) {
			logger.error("", ex);
		}
	}

	@Override
	public void logFile(String catlog, String level, String machine, List<String> message) throws TException {
		logProcessor.dealLogFile(catlog, level, machine, message);
	}

}
