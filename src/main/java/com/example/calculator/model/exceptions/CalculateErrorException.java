package com.example.calculator.model.exceptions;
/**
 * 无法计算的异常类
 * @author 李柯凡
 *
 */
public class CalculateErrorException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4457040817710433357L;
	/**
	 * 无法计算的报错
	 */
	public static final String ERROR = "Error: 无法计算！";
	
	@Override
	public String getMessage() {
		return ERROR;
	}
}
