package com.example.calculator.model.exceptions;

/**
 * 
 * @author 刘禹辰
 *
 */
public class DeterminantException extends Exception{
	/**
	 * 
	 */
	private static final long serialVersionUID = -4662575721130870356L;
	/**
	 * 报错信息
	 */
	private String message="Error: 行列式的值为0，逆矩阵不存在！";
	public String getMessage() {
		return message;
	}
}
