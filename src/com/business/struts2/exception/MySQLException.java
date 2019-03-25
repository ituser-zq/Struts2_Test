package com.business.struts2.exception;

/**
 * 自定义异常，代表SQL运行出错
 * 
 * 
 * 
 */
public class MySQLException extends RuntimeException {
	public MySQLException(Throwable throwable) {
		super(throwable);
	}

	public MySQLException(String message) {
		super(message);
	}

	public MySQLException() {
		super();
	}
}
