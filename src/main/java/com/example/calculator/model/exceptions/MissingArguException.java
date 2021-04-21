package com.example.calculator.model.exceptions;
/**
 * 输入缺少参数的异常类
 * @author 李柯凡
 *
 */
public class MissingArguException extends Exception{
	/**
	 * 
	 */
	private static final long serialVersionUID = 4457040817710433357L;
	/**
	 * 缺少参数的报错
	 */
	public static final String ERROR = "Error: 缺少输入参数！";
	
	@Override
	public String getMessage() {
		return ERROR;
	}
}
