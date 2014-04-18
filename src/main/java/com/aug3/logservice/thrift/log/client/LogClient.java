package com.aug3.logservice.thrift.log.client;

import java.util.Map;
import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;

import com.aug3.logservice.thrift.log.LogParam;
import com.aug3.logservice.thrift.log.service.LogService;
import com.aug3.logservice.util.ConfigManager;

public class LogClient {

	public static final String SERVER_IP = ConfigManager.getProperty(ConfigManager.LOG_SERVICE_IP, "localhost");
	public static final int SERVER_PORT = ConfigManager.getIntProperty(ConfigManager.LOG_SERVICE_PORT, 8888);
	public static final int TIMEOUT = ConfigManager.getIntProperty(ConfigManager.LOG_SERVICE_TIMEOUT, 30000);;

	private TTransport transport;
	private TProtocol protocol;
	LogService.Client client;

	public LogClient() throws Exception {
		transport = new TFramedTransport(new TSocket(SERVER_IP, SERVER_PORT, TIMEOUT));
		protocol = new TBinaryProtocol(transport);
		client = new LogService.Client(protocol);
		transport.open();
	}

	public void log(int component, int action, long uid, int utyp, String sessionid, String ip, String host,
			String reqpath, String qparams, String req, String cookies, String referer, String browser,
			Map<String, String> msg) throws TException {

		LogParam param = new LogParam();

		param.setAction(action);
		param.setComponent(component);
		param.setUid(uid);
		param.setUtyp(utyp);
		param.setSessionid(sessionid);
		param.setIp(ip);
		param.setHost(host);
		param.setReqpath(reqpath);
		param.setParams(qparams);
		param.setReq(req);
		param.setCookies(cookies);
		param.setReferer(referer);
		param.setBrowser(browser);
		param.setMsg(msg);

		client.log(param);
	}

	/**
	 * test case
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		LogClient client;
		try {
			client = new LogClient();
			// client.log(...)
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
