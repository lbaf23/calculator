package com.example.calculator.model.matrix;

import com.example.calculator.model.calculate.Calculate;
import com.example.calculator.model.exceptions.InputIllegalException;

/**
 * 矩阵
 * @author 刘禹辰
 *
 */
public class CalMatrix {
	/**
	 * 两个矩阵的运算
	 * @param in1 矩阵1
	 * @param in2 矩阵2
	 * @param module 运算模式 + - * /
	 * @return 运算结果
	 * @throws Exception
	 */
	public static String calMatrix(String in1,String in2,String module) throws Exception {
		if(module.equals("+")) {
			if(Matrix.isMatrix(in1) && Matrix.isMatrix(in2)) {
				return Matrix.mxAddition(Matrix.valueOf(in1), Matrix.valueOf(in2)).toString();
			}
			else if(Matrix.isMatrix(in1)) {
				return Matrix.add(Matrix.valueOf(in1), Double.valueOf(Calculate.calculate(in2)) ).toString();
			}
			else if(Matrix.isMatrix(in2)) {
				return Matrix.add(Matrix.valueOf(in2), Double.valueOf(Calculate.calculate(in1)) ).toString();
			}
			else {
				return Calculate.calculate(in1+"+"+in2);
			}
		}
		else if(module.equals("-")) {
			if(Matrix.isMatrix(in1) && Matrix.isMatrix(in2)) {
				return Matrix.mxSubtraction(Matrix.valueOf(in1), Matrix.valueOf(in2)).toString();
			}
			else if(Matrix.isMatrix(in1)) {
				return Matrix.sub(Matrix.valueOf(in1), Double.valueOf(Calculate.calculate(in2)) ).toString();
			}
			else if(Matrix.isMatrix(in2)) {
				return Matrix.sub(Matrix.valueOf(in2), Double.valueOf(Calculate.calculate(in1)) ).toString();
			}
			else {
				return Calculate.calculate(in1+"-"+in2);
			}
		}
		else if(module.equals("*")) {
			if(Matrix.isMatrix(in1) && Matrix.isMatrix(in2)) {
				return Matrix.mxMultiplication(Matrix.valueOf(in1), Matrix.valueOf(in2)).toString();
			}
			else if(Matrix.isMatrix(in1)) {
				return Matrix.multiply(Matrix.valueOf(in1), Double.valueOf(Calculate.calculate(in2)) ).toString();
			}
			else if(Matrix.isMatrix(in2)) {
				return Matrix.multiply(Matrix.valueOf(in2), Double.valueOf(Calculate.calculate(in1)) ).toString();
			}
			else {
				return Calculate.calculate("("+in1+")*("+in2+")");
			}
		}
		else if(module.equals("/")||module.equals("÷")) {
			if(Matrix.isMatrix(in1) && Matrix.isMatrix(in2)) {
				return Matrix.mxDivide(Matrix.valueOf(in1), Matrix.valueOf(in2)).toString();
			}
			else if(Matrix.isMatrix(in1)) {
				return Matrix.divide(Matrix.valueOf(in1), Double.valueOf(Calculate.calculate(in2)) ).toString();
			}
			else if(Matrix.isMatrix(in2)) {
				return Matrix.divide(Matrix.valueOf(in2), Double.valueOf(Calculate.calculate(in1)) ).toString();
			}
			else {
				return Calculate.calculate("("+in1+")/("+in2+")");
			}
		}
		else if(module.equals("^")) {
			if(Matrix.isMatrix(in1) && Matrix.isMatrix(in2)) {
				throw new Exception();
			}
			else if(Matrix.isMatrix(in1) ) {
				return Matrix.pow(Matrix.valueOf(in1), Integer.valueOf(Calculate.calculate(in2)) ).toString();
			}
			else if(Matrix.isMatrix(in2)) {
				throw new Exception();
			}
			else {
				return Calculate.calculate("("+in1+")^("+in2+")");
			}
		}
		else {
			throw new Exception();
		}
	}
	/**
	 * 一个矩阵的运算
	 * @param input 矩阵
	 * @param module 运算模式	1：求行最简式	2：求秩		3：求转置矩阵	4：求行列式
	 * 							5：求代数余子式	6：求逆矩阵	7：求伴随矩阵	8：求矩阵的迹
	 * @return 运算结果
	 * @throws Exception
	 */
	public static String calSingleMatrix(String input,String module) throws Exception {
		if(module.equals("1")) { // 求行最简式
			return Matrix.mxSimplification(Matrix.valueOf(input)).toString();
		}
		else if(module.equals("2")) { // 求秩
			return String.valueOf(Matrix.mxSimplification(Matrix.valueOf(input)).getRank());
		}
		else if(module.equals("3")) { // 求转置矩阵
			return Matrix.mxTransposition(Matrix.valueOf(input)).toString();
		}
		else if(module.equals("4")) { // 求行列式
			return String.valueOf(Matrix.mxDeterminant(Matrix.valueOf(input)));
		}
		else if(module.equals("6")) { // 求逆矩阵
			return Matrix.mxInversion(Matrix.valueOf(input)).toString();
		}
		else if(module.equals("7")) { // 求伴随矩阵
			return Matrix.mxAdjoin(Matrix.valueOf(input)).toString();
		}
		else if(module.equals("8")) { // 求矩阵的迹
			return String.valueOf(Matrix.mxTrace(Matrix.valueOf(input)));
		}
		throw new InputIllegalException();
	}
	/**
	 * 求代数余子式
	 * @param input
	 * @param row
	 * @param col
	 * @return
	 * @throws Exception
	 */
	public static String mxAlgebraicComplement(String input, String row,String col) throws Exception {
		return Matrix.mxAlgebraicComplement(Matrix.valueOf(input), 
				Integer.valueOf(row)-1, Integer.valueOf(col)-1).toString();
	}
}
