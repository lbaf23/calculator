package com.example.calculator.model.exceptions;

public class AlgebraicComplementException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7457705005384976768L;
	/**
	 * 代数余子式的错误
	 */
	public static String ERROR = "Error: 需要输入一个方阵。";
	/**
	 * 获取报错信息
	 */
	@Override
	public String getMessage() {
		return ERROR;
	}
}
