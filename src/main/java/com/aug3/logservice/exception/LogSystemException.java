package com.aug3.logservice.exception;

/**
 * if the system is not work correctly,this exception will generate
 * 
 * @date 2012-5-16
 * 
 */
public class LogSystemException extends Exception {
	private static final long serialVersionUID = 1L;

	public LogSystemException(String msg) {
		super(msg);
	}

}
