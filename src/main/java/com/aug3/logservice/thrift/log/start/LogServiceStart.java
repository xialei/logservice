package com.aug3.logservice.thrift.log.start;

import org.apache.log4j.Logger;
import org.apache.thrift.TProcessor;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.server.TNonblockingServer;
import org.apache.thrift.server.TServer;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TNonblockingServerSocket;

import com.aug3.logservice.thrift.log.service.LogService;
import com.aug3.logservice.thrift.log.service.impl.LogServiceImpl;
import com.aug3.logservice.util.ConfigManager;

public class LogServiceStart {

	private static final Logger logger = Logger.getLogger(LogServiceStart.class);
	public final int LOG_SERVICE_PORT = ConfigManager.getIntProperty(ConfigManager.LOG_SERVICE_PORT, 17060);

	public void start() throws Exception {
		logger.info("+++++++++++++++++++++++logservice start ..... ");
		LogServiceImpl logservice = new LogServiceImpl();
		TProcessor tprocessor = new LogService.Processor<LogService.Iface>(logservice);
		TNonblockingServerSocket tnbSocketTransport = new TNonblockingServerSocket(LOG_SERVICE_PORT);
		TNonblockingServer.Args tnbArgs = new TNonblockingServer.Args(tnbSocketTransport);
		tnbArgs.processor(tprocessor);
		tnbArgs.transportFactory(new TFramedTransport.Factory());
		tnbArgs.protocolFactory(new TBinaryProtocol.Factory());
		TServer server = new TNonblockingServer(tnbArgs);
		server.serve();
		logger.info("+++++++++++++++++++++++successfully started.... ");
	}

	public static void main(String[] args) throws Exception {
		LogServiceStart logServiceStart = new LogServiceStart();
		logServiceStart.start();
	}

}
