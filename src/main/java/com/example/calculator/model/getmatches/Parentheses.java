package com.example.calculator.model.getmatches;

import com.example.calculator.model.exceptions.InputIllegalException;

/**
 * @author 李柯凡
 *
 */
public class Parentheses {
	/**
	 * 获取表达式前面的数值系数
	 * @param stbf 表达式
	 * @param i		位置
	 * @return		系数的值
	 * @throws Exception
	 */
	public static int getBeforeDigi(StringBuffer stbf,int i) throws Exception{
		return getBeforeDigi(stbf.toString(),i);
	}
	/**
	 * 获取表达式前面的数值系数
	 * @param str 表达式
	 * @param i 位置
	 * @return 系数的值
	 * @throws Exception
	 */
	public static int getBeforeDigi(String str,int i) throws Exception{
		if(str.charAt(i)==')') {
			return getBeforeMatch(str,i);
		}
		else if(str.charAt(i)>='0'&&str.charAt(i)<='9') {
			for(i--;i>=0;i--) {
				if(! ( (str.charAt(i)>='0'&&str.charAt(i)<='9')||str.charAt(i)=='.') ) {
					return i+1;
				}
			}
		}
		else {
			return i+1;
		}
		return 0;
	}
	/**
	 * 格式化矩阵
	 * @param s 矩阵
	 * @return 格式化后的矩阵
	 */
	public static String matrixFormat(String s) {
		s = s.replace("\n", ";");
		return "["+s+"]";
	}
	
	/**
	 * 获取前方的第一个+或-操作符的位置
	 * @param stbf 表达式
	 * @param i 起始位置
	 * @return 操作符的位置
	 * @throws Exception
	 */
	public static int getBeforePolynomialSplit(StringBuffer stbf,int i) throws Exception{
		return getBeforePolynomialSplit(stbf.toString(),i);
	}
	public static int getBeforePolynomialSplit(String str,int i) throws Exception{
		for(;i>=0;i--) {
			if(str.charAt(i)=='+'||str.charAt(i)=='-') {
				break;
			}
		}
		return i;
	}
	/**
	 * 获取后方的第一个逗号
	 * @param stbf 表达式
	 * @param i 起始位置
	 * @return 逗号的位置
	 * @throws Exception
	 */
	public static int getComma(StringBuffer stbf,int i) throws Exception{
		return getComma(stbf.toString(),i);
	}
	/**
	 * 获取后方的第一个逗号
	 * @param str 表达式
	 * @param i 起始位置
	 * @return 逗号的位置
	 * @throws Exception
	 */
	public static int getComma(String str,int i) throws Exception{
		return str.indexOf(",", i);
	}
	/**
	 * 获取某位的'('所匹配的')'的位置
	 * @param stbf 表达式
	 * @param i 起始位置
	 * @return ')'的位置
	 * @throws Exception
	 */
	public static int getMatch(StringBuffer stbf,int i) throws Exception{
		return getMatch(stbf.toString(),i);
	}
	/**
	 * 获取某位的'('所匹配的')'的位置
	 * @param str 表达式
	 * @param i 起始位置
	 * @return ')'的位置
	 * @throws Exception
	 */
	public static int getMatch(String str,int i) throws Exception {
		if(str.charAt(i)=='(') {
			int p = 1;
			for(int c=i+1; c<str.length(); c++) {
				if(str.charAt(c)=='(') {
					p = p + 1;
				}
				else if(str.charAt(c)==')') {
					p = p - 1;
					if(p==0) {
						return c;
					}
				}
			}
		}
		throw new InputIllegalException();
	}
	/**
	 * 获取某位的')'所匹配的'('的位置
	 * @param stbf 表达式
	 * @param i 起始位置
	 * @return '('的位置
	 * @throws Exception
	 */
	public static int getBeforeMatch(StringBuffer stbf,int i) throws Exception{
		return getBeforeMatch(stbf.toString(),i);
	}
	/**
	 * 获取某位的')'所匹配的'('的位置
	 * @param str 表达式
	 * @param i 起始位置
	 * @return '('的位置
	 * @throws Exception
	 */
	public static int getBeforeMatch(String str,int i) throws Exception{
		if(str.charAt(i)==')') {
			int p = 1;
			for(int c=i-1; c>=0; c--) {
				if(str.charAt(c)==')') {
					p = p + 1;
				}
				else if(str.charAt(c)=='(') {
					p = p - 1;
					if(p==0) {
						return c;
					}
				}
			}
		}
		throw new InputIllegalException();
	}
}
