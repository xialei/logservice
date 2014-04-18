package com.aug3.logservice.exception;

/**
 * if the request parameter is not right, it will throw this exception
 * 
 * @date 2012-5-16
 * 
 */
public class ParameterException extends Exception {

	private static final long serialVersionUID = 1L;

	public ParameterException(String msg) {
		super(msg);
	}

}
