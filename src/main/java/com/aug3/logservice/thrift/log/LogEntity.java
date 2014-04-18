package com.aug3.logservice.thrift.log;

import java.util.Date;

import com.aug3.sys.util.DateUtil;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

@SuppressWarnings("serial")
public class LogEntity extends BasicDBObject implements DBObject {

	private static final String FIELD_C = "c";
	private static final String FIELD_A = "a";
	private static final String FIELD_U = "u";
	private static final String FIELD_USERTYPE = "t";
	private static final String FIELD_SID = "sid";
	private static final String FIELD_IP = "ip";
	private static final String FIELD_H = "h";
	private static final String FIELD_PATH = "p";
	private static final String FIELD_QPARAM = "q";
	private static final String FIELD_REQ = "req";
	private static final String FIELD_COOKIES = "ck";
	private static final String FIELD_REFERER = "rf";
	private static final String FIELD_BROWSER = "br";
	private static final String FIELD_MSG = "m";
	private static final String FIELD_TS = "ts";

	public static LogEntity buildEntity(LogParam param) {
		if (null == param)
			return null;
		LogEntity entity = new LogEntity();
		entity.append(FIELD_C, param.getComponent())
				.append(FIELD_A, param.getAction())
				.append(FIELD_U, param.getUid())
				.append(FIELD_USERTYPE, param.getUtyp())
				.append(FIELD_SID, param.getSessionid())
				.append(FIELD_IP, param.getIp())
				.append(FIELD_H, param.getHost())
				.append(FIELD_PATH, param.getReqpath())
				.append(FIELD_QPARAM, param.getParams())
				.append(FIELD_REQ, param.getReq())
				.append(FIELD_COOKIES, param.getCookies())
				.append(FIELD_REFERER, param.getReferer())
				.append(FIELD_BROWSER, param.getBrowser())
				.append(FIELD_MSG, param.getMsg())
				.append(FIELD_TS,
						DateUtil.convertTimestampLocal2UTC(DateUtil.formatDate(new Date(),
								DateUtil.ISO_TIMESTAMP_PATTERN)));

		return entity;
	}
}
