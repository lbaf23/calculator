package com.example.calculator.model.base;

import java.util.Stack;

import com.example.calculator.model.calculate.InputFilter;
import com.example.calculator.model.exceptions.InputIllegalException;

/**
 * 
 * @author 刘禹辰
 *
 */
public class CalBase {
	/**
	 * 计算进制数
	 * @param str 进制数表达式
	 * @param b 进制
	 * @param out 输出进制
	 * @return 运算结果
	 * @throws Exception 
	 */
	public static String calculate(String str,int b,int out) throws Exception {
		str = str.replaceAll("\\s+", "");
		String solu;
		if(b>=2 && b<=36)
			solu = InputFilter.filter(str,3,b);
		else
			throw new InputIllegalException();
		
		System.out.println(solu);
		
		Base res = Base.baseConversion(new Base(solu,10), out);
		return res.getBaseNumber();
	}
	
	private static boolean isCalOp(char c) {
		return c=='+'||c=='-'||c=='*'||c=='/'||c=='^'||c=='(';
	}
	/**
	 * 计算加减乘除，括号，幂次
	 * @param str
	 * @param b
	 * @return
	 * @throws Exception
	 */
	public static String solution(String str,int b) throws Exception {
		str = str.replaceAll("E", "*10^");
		str = str.replaceAll("÷", "/");
		Stack<Base> numStack = new Stack<>();
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
						Base num1 = Base.zero;
						Base num2 = Base.zero;
						if(!signalStack.isEmpty()) {
							optemp = (char) signalStack.pop();
						}
						if(!numStack.isEmpty()) {
							num1 = (Base)numStack.pop();
							num2 = (Base)numStack.pop();
						}
						numStack.push(cal(optemp, num2, num1, b));
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
						Base num1 = (Base) numStack.pop();
						Base num2 = (Base) numStack.pop();
						numStack.push(cal(theop, num2, num1, b));// 运算括号内的内容
					}
				}
			}
			else if ((c >= '0' && c <= '9')||(isCalOp(last)&&c=='-')||(c >= 'a' && c <= 'z') ) {
				int tempIndex = index + 1;
				boolean havePercent = false;
				while (tempIndex < str.length()) {
					char temp = str.charAt(tempIndex);
					if( (temp >= '0' && temp <= '9')||(temp >= 'a' && temp <= 'z') ) {
						tempIndex++;
					}
					else if(temp == '%') {
						havePercent = true;
						tempIndex++;
					}
					else {
						break;
					}
				}
				String numstr;
				if(havePercent) {
					numstr = str.substring(index, tempIndex-1);
				}
				else {
					numstr = str.substring(index, tempIndex);
				}
				Base numnum = new Base(numstr,b);
				if(havePercent)
					numnum = Base.basedev(numnum, new Base(100));
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
				Base num1 = (Base)numStack.pop();
				Base num2 = (Base)numStack.pop();
				numStack.push(cal(opterTemp, num2, num1, b));
			}
		}
		Base result = Base.zero;
		result = (Base) numStack.pop();
		return String.valueOf(result);
	}
	/**
	 * 计算加减乘除余，两个进制数相同进制
	 * @param optemp 运算符号
	 * @param num1 进制数1
	 * @param num2 进制数2
	 * @param base 进制
	 * @return 计算结果的进制数
	 * @throws Exception
	 */
	private static Base cal(char optemp, Base num1, Base num2, int base) throws Exception {
		switch (optemp) {
		case '+':
			return Base.baseadd(num1, num2);
		case '-':
			return Base.basedec(num1, num2);
		case '*':
			return Base.basemul(num1, num2);
		case '/':
			return Base.basedev(num1, num2);
		case '^':
			return Base.basepow(num1, num2);
		}
		return Base.zero;
	}
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
