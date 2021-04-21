package com.example.calculator.model.calculate;
import java.math.BigInteger;
import java.text.DecimalFormat;
import java.util.Stack;

import com.example.calculator.model.exceptions.InputIllegalException;

/**
 * 计算一个算式的值
 * @author 李柯凡
 *
 */
public class Calculate {
	/**
	 *  π的值
	 */
	public static final double PAI = Math.PI;
	/**
	 *  角度转换为弧度需要乘的系数 
	 */
	public static final String TORADIAN = "0.017453292519943";
	/**
	 *  默认保留的小数位数 
	 */
	public static final int CAP = 10;
	/**
	 * 自然数e的值
	 */
	public static final double E = Math.E;
	/**
	 * 近似无穷小
	 */
	public static final double INFINITY = 0.0000000001;
	/**
	 * 保留相应的小数位数
	 * @param str 数字
	 * @param len 保留小数位数
	 * @return 保留后的数字
	 * @throws Exception
	 */
	public static String retainDec(String str,int len) throws Exception {
		StringBuffer format = new StringBuffer("0.");
		for(int i=0; i<len; i++)
			format.append("0");
		DecimalFormat d = new DecimalFormat(format.toString());
		String ans = String.format(d.format(Double.valueOf(str)));
		if(len<=0)
			return ans.substring(0,ans.length()-1);
		return ans;
	}
	/**
	 * 保留一个x=1.01格式的等号右边的位数
	 * @param str 输入表达式
	 * @param len 保留位数
	 * @return 计算结果
	 * @throws Exception
	 */
	public static String retainLine(String str,int len) throws Exception {
		int i = str.indexOf("=");
		if(i<0)
			return str;
		for(;i<str.length();i++)
			if(str.charAt(i)>='0'&&str.charAt(i)<='9')
				break;
		
		String b = str.substring(0,i-1);
		String s = str.substring(i);
		
		StringBuffer format = new StringBuffer("0.");
		for(i=0; i<len; i++)
			format.append("0");
		DecimalFormat d = new DecimalFormat(format.toString());
		String ans = String.format(d.format(Double.valueOf(s)));
		if(len<=0)
			return b + ans.substring(0,ans.length()-1);
		return b + ans;
	}
	/**
	 * 判断一个字符串是否是浮点数，返回true或false
	 * @param str 字符串
	 * @return 返回结果
	 */
	public static boolean isDigital(String str) {
		try {
			Double.valueOf(str);
			return true;
		}catch(Exception e) {
			return false;
		}
	}
	
	/*
	 * 计算复杂表达式
	 * 可以计算的类型：
	 * log(n,x), ln(x), lg(x), arccos(), arcsin(), arctan(), sin(), cos(), tan()
	 * e, π
	 */
	/**
	 * 调用方法计算一个复杂的算式的值
	 * @param str 计算式
	 * @return 结果
	 * @throws Exception
	 */
	public static String calculate(String str) throws Exception {
		String solu = InputFilter.filter(str,1,0);
		if(isInteger(solu)) {
			return retainDec(solu, 0);
		}
		else {
			return retainDec(solu, CAP+2);
		}
	}
	/**
	 * 计算阶乘
	 * @param str 数字
	 * @return 阶乘结果
	 * @throws Exception
	 */
	public static String calFactorial(String str) throws Exception {
		BigInteger x,i,ans;
		if(isInteger(str)) {
			x = new BigInteger(retainDec(str,0));
			i = new BigInteger("2");
			ans = new BigInteger("1");
		}
		else {
			throw new InputIllegalException();
		}
		while(i.compareTo(x) <= 0) {
			ans = ans.multiply(i);
			i = i.add(new BigInteger("1"));
		}
		return ans.toString();
	}
	/**
	 * 判断一个字符串在设定的精度条件下是否是整数
	 * @param str 字符串
	 * @return 判断结果
	 * @throws Exception
	 */
	public static boolean isInteger(String str) throws Exception {
		str = String.valueOf(Double.valueOf(str)+INFINITY);
		int i = str.indexOf('.');
		
		for(i++;i<str.length()&&i<=CAP;i++) {
			if(str.charAt(i)!='0')
				return false;
		}
		return true;
	}
	
	private static boolean isCalOp(char c) {
		return c=='+'||c=='-'||c=='*'||c=='/'||c=='^'||c=='(';
	}
	/**
	 * 计算一个简单算式的值，只包括加减乘除幂次
	 * @param str 计算式
	 * @return 计算结果
	 * @throws Exception
	 */
	public static String solution(String str) throws Exception {
		str = str.replaceAll("E", "*10^");
		str = str.replaceAll("÷", "/");
		Stack<Double> numStack = new Stack<>();
		Stack<Character> signalStack = new Stack<>();
		int index = 0;
		while (index < str.length()) {
			char c = str.charAt(index),last='+';
			if(index>0)
				last=str.charAt(index-1);
			if(c == '(') {
				signalStack.push(c);
			}
			else if(c == '+' || (c == '-' && !isCalOp(last) ) || c == '*' || c == '/'|| c == '^') {
				int r = getOpRank(c);
				while(true) {
					int stRank = 0;
					if (signalStack.isEmpty() == false) {
						Object obj = signalStack.peek();
						stRank = getOpRank((char) obj);
					}
					if (r > stRank) {
						signalStack.push(c);
						break;
					}
					else {
						char optemp = '0';
						double num1 = 0;
						double num2 = 0;
						if(!signalStack.isEmpty()) {
							optemp = (char) signalStack.pop();
						}
						if(!numStack.isEmpty()) {
							num1 = (double)numStack.pop();
							num2 = (double)numStack.pop();
						}
						numStack.push(cal(optemp, num2, num1));
					}
				}
			}
			else if (c == ')') {
				while (true) {
					char theop = (char) signalStack.pop();
					if (theop == '(') {
						break;
					}
					else {
						double num1 = (double) numStack.pop();
						double num2 = (double) numStack.pop();
						numStack.push(cal(theop, num2, num1));// 运算括号内的内容
					}
				}
			}
			else if ((c >= '0' && c <= '9')||(isCalOp(last)&&c=='-')) {
				int tempIndex = index + 1;
				boolean havePercent = false, haveAngle = false;
				while (tempIndex < str.length()) {
					char temp = str.charAt(tempIndex);
					if( (temp >= '0' && temp <= '9')||(temp == '.') ) {
						tempIndex++;
					}
					else if(temp == '%') {
						havePercent = true;
						tempIndex++;
					}
					else if(temp == '°') {
						haveAngle = true;
						tempIndex++;
					}
					else {
						break;
					}
				}
				String numstr;
				if(havePercent || haveAngle ) {
					numstr = str.substring(index, tempIndex-1);
				}
				else {
					numstr = str.substring(index, tempIndex);
				}
				double numnum = Double.valueOf(numstr);
				if(havePercent)
					numnum /= 100.0;
				if(haveAngle)
					numnum = numnum/180.0*Math.PI;
				numStack.push(numnum);
				index = tempIndex - 1;
			}
			index++;
			
		}
		while (true) {
			Object obj = null;
			if (signalStack.isEmpty() == false) {
				obj = signalStack.pop();
			}
			
			if(obj == null) {
				break;
			}
			else {
				char opterTemp = (char)obj;
				double num1 = (double)numStack.pop();
				double num2 = (double)numStack.pop();
				numStack.push(cal(opterTemp, num2, num1));
			}
		}
		double result = 0;
		result = (double) numStack.pop();
		return String.valueOf(result);
	}
	/**
	 * 计算加减乘除余
	 * @param optemp 计算符号
	 * @param num1 数字1
	 * @param num2 数字2
	 * @return 计算结果
	 */
	private static Double cal(char optemp, double num1, double num2) {
		switch (optemp) {
		case '+':
			return num1 + num2;
		case '-':
			return num1 - num2;
		case '*':
			return num1 * num2;
		case '/':
			return num1 / num2;
		case '^':
			return Math.pow(num1, num2);
		}
		return 0.0;
	}
	/*
	 * 返回计算符的优先级
	 */
	private static int getOpRank(char c) {
		switch (c) {
		case '(':
			return 0;
		case '+':
		case '-':
			return 1;
		case '*':
		case '/':
			return 2;
		case '^':
			return 3;
		default:
			return 0;
		}
	}
}
