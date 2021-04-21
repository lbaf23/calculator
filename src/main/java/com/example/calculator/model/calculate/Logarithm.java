package com.example.calculator.model.calculate;

/**
 * 计算对数
 * @author 李柯凡
 *
 */
public class Logarithm {
	/**
	 * 任意底数指数的对数
	 * @param n 底数
	 * @param x	指数
	 * @return	对数值
	 * @throws Exception
	 */
	public static String calLog(String n,String x) throws Exception {
		double d1 = Double.valueOf(n);
		double d2 = Double.valueOf(x);
		double ans = Math.log(d2)/Math.log(d1);
		return String.valueOf(ans);
	}
	/**
	 * 自然底数的对数
	 * @param d 指数 doulbe类型
	 * @return	对数值
	 * @throws Exception
	 */
	public static double calLn(double d) throws Exception {
		return Math.log(d)/Math.log(Math.E);
	}
	/**
	 * 
	 * @param x 指数 String类型
	 * @return
	 * @throws Exception
	 */
	public static String calLn(String x) throws Exception {
		double d = Double.valueOf(x);
		double ans = Math.log(d)/Math.log(Math.E);
		return String.valueOf(ans);
	}
	/**
	 * 计算以10为底的对数值
	 * @param x 指数
	 * @return
	 * @throws Exception
	 */
	public static String calLg(String x) throws Exception {
		double d = Double.valueOf(x);
		double ans = Math.log10(d);
		return String.valueOf(ans);
	}
}
