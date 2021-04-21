package com.example.calculator.model.exceptions;
/**
 * 输入存在错误的异常类
 * @author 李柯凡
 *
 */
public class InputIllegalException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = -773298606345571411L;
	/**
	 * 输入存在错误的报错信息
	 */
	public static final String ERROR = "Error: 请检查您的输入。";
	
	@Override
	public String getMessage() {
		return ERROR;
	}
}
