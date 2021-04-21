package com.example.calculator.model.complex;

import java.util.Stack;

import com.example.calculator.model.calculate.InputFilter;
import com.example.calculator.model.exceptions.InputIllegalException;
/**
 * 计算复数
 * @author 李柯凡
 *
 */
public class CalComplex {
	/**
	 * 计算一个复杂复数算式的值
	 * @param str 计算式
	 * @return 计算结果
	 * @throws Exception
	 */
	public static String calculate(String str) throws Exception {
		String solu = InputFilter.filter(str,2,0);
		return solu;
	}
	/**
	 * 计算带有复数的加减乘除幂次的算式的值
	 * @param str 计算式
	 * @return 计算结果
	 * @throws Exception
	 */
	public static String solution(String str) throws Exception {
		
		str = addMultiply(str);
		str = str.replaceAll("E", "*10^");
		str = str.replaceAll("÷", "/");
		Stack<Complex> numStack = new Stack<>();
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
						Complex num1 = Complex.ZERO;
						Complex num2 = Complex.ZERO;
						if(!signalStack.isEmpty()) {
							optemp = (char) signalStack.pop();
						}
						if(!numStack.isEmpty()) {
							num1 = (Complex)numStack.pop();
							num2 = (Complex)numStack.pop();
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
						Complex num1 = (Complex) numStack.pop();
						Complex num2 = (Complex) numStack.pop();
						numStack.push(cal(theop, num2, num1));
					}
				}
			}
			else if ((c >= '0' && c <= '9')||(isCalOp(last)&&c=='-')||c=='i') {
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
					else if(temp == 'i') {
						tempIndex ++;
						break;
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
				Complex numnum = Complex.valueOf(numstr);
				if(havePercent)
					numnum = Complex.divide(numnum, new Complex(100,0));
				if(haveAngle)
					numnum = Complex.divide(numnum, new Complex(180.0*Math.PI,0));
				numStack.push(numnum);
				index = tempIndex - 1;
			}
			else {
				throw new InputIllegalException();
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
				Complex num1 = (Complex)numStack.pop();
				Complex num2 = (Complex)numStack.pop();
				numStack.push(cal(opterTemp, num2, num1));
			}
		}
		Complex result = Complex.ZERO;
		result = (Complex) numStack.pop();
		return result.toString();
	}
	/**
	 * 计算加减乘除余
	 * @param optemp
	 * @param num1
	 * @param num2
	 * @return
	 * @throws Exception
	 */
	private static Complex cal(char optemp, Complex num1, Complex num2) throws Exception {
		switch (optemp) {
		case '+':
			return Complex.add(num1, num2);
		case '-':
			return Complex.subtract(num1, num2);
		case '*':
			return Complex.multiply(num1, num2);
		case '/':
			return Complex.divide(num1, num2);
		case '^':
			return Complex.pow(num1, num2);
		}
		return Complex.ZERO;
	}
	/**
	 * 获取符号的优先级
	 * @param c
	 * @return
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
	/**
	 * 添加乘号
	 * @param s
	 * @return
	 */
	private static String addMultiply(String s) {
		StringBuffer stbf = new StringBuffer(s);
		for(int i=0; i<stbf.length(); i++) {
			if(i>0 && stbf.charAt(i-1)==')' && stbf.charAt(i)=='(') {
				stbf.insert(i, '*');
			}
		}
		return stbf.toString();
	}
	/**
	 * 是否是简单运算符
	 * @param c
	 * @return
	 */
	private static boolean isCalOp(char c) {
		return c=='+'||c=='-'||c=='*'||c=='/'||c=='^'||c=='(';
	}
}
