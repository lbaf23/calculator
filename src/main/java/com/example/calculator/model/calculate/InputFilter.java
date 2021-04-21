package com.example.calculator.model.calculate;

import com.example.calculator.model.base.CalBase;
import com.example.calculator.model.complex.CalComplex;
import com.example.calculator.model.complex.Complex;
import com.example.calculator.model.exceptions.CalculateErrorException;
import com.example.calculator.model.exceptions.InputIllegalException;
import com.example.calculator.model.getmatches.Parentheses;
/**
 * 过滤输入表达式
 * 
 * @author 李柯凡
 *
 */
public class InputFilter {
	/**
	 * 过滤输入的表达式
	 * 模式：module 1: 计算器模式  2:过滤器模式(复数) 3:过滤器模式(进制数)
	 * @param str
	 * @param module
	 * @param b
	 * @return
	 * @throws Exception
	 */
	public static String filter(String str,int module,int b) throws Exception {
		String f = "";
		if(module==1)
			f = "^[0-9|°|π|e|%|+|\\-|*|/|÷|(|)|.|E|$|^]";
		else if(module==2)
			f = "^[0-9|°|π|e|%|+|\\-|*|/|÷|(|)|.|E|$|^|i]";
		else if(module==3)
			f = "^[0-9|°|π|e|%|+|\\-|*|/|÷|(|)|E|$|^|a-z]";
		else
			return "";
		
		StringBuffer stbf = new StringBuffer(str);
		int len = stbf.length();
		for(int i=0; i<len; i++) {
			String s1=String.valueOf(stbf.charAt(i)),s2="",s3="",s4="",s6="";
			if(i<len-1) {
				s2 = stbf.substring(i,i+2);
			}
			if(i<len-2) {
				s3 = stbf.substring(i,i+3);
			}
			if(i<len-3) {
				s4 = stbf.substring(i,i+4);
			}
			if(i<len-6) {
				s6 = stbf.substring(i,i+6);
			}
			
			if(s1.equals("√")) {
				int end = Parentheses.getMatch(stbf,i+1);
				int beg = Parentheses.getBeforeDigi(stbf,i-1);
				String p;
				if(beg == i) {
					p = "1/2";
				}
				else {
					if(module==1)
						p = Calculate.calculate("1/"+stbf.substring(beg,i));
					else if(module==2)
						p = CalComplex.calculate("1/"+stbf.substring(beg,i));
					else
						throw new CalculateErrorException();
				}
				stbf.insert(end+1, "^("+p+")");
				stbf.delete(beg, i+1);
				len = stbf.length();
				i = i - (i+1-beg);
			}
			else if(s1.equals("!")) {
				int beg = Parentheses.getBeforeDigi(stbf,i-1);
				String s = stbf.substring(beg,i);	
				String ans;
				if(module==1)
					ans = Calculate.calFactorial(filter(s,module,b));
				else
					throw new CalculateErrorException();
				stbf.insert(i+1,ans);
				stbf.delete(beg,i+1);
				len = stbf.length();
				i = i - (i+1-beg);
			}
			else if(s2.matches("^\\)[0-9|π|e|i]$") || s2.matches("^[i|π][0-9]$") || s2.matches("^[e|i][0-9]$")) {
				throw new InputIllegalException();
			}
			else if(s2.matches("^[0-9|π|e]\\($") || s2.matches("^[0-9|e|π]π$") || s2.matches("^[0-9|π|e]e$")) {
				stbf = stbf.insert(i+1, "*");
				i = i + 1;
				len = len + 1;
			}
			else if(s2.equals("-(")) {
				stbf = stbf.insert(i+1, "1*");
				i = i + 2;
				len = len + 2;
			}
			else if(s2.equals("lg")) {
				if(i>0 && stbf.charAt(i-1)>='0' && stbf.charAt(i-1)<='9') {
					stbf.insert(i, "*");
					len = len + 1;
					i = i + 1;
				}
				int end = Parentheses.getMatch(stbf,i+2);
				String ans;
				if(module==1)
					ans = Logarithm.calLg(filter(stbf.substring(i+3,end),module,b) );
				else if(module==2)
					ans = Complex.lg(Complex.valueOf(filter(stbf.substring(i+3,end),module,b)) ).toString();
				else
					throw new CalculateErrorException();
				
				stbf = stbf.delete(i, i+2);
				stbf = stbf.delete(i+1, end-2);
				stbf = stbf.insert(i+1, ans);
				len = stbf.length();
			}
			else if(s2.equals("ln")) {
				if(i>0 && stbf.charAt(i-1)>='0' && stbf.charAt(i-1)<='9') {
					stbf.insert(i, "*");
					i = i + 1;
					len = len + 1;
				}
				int end = Parentheses.getMatch(stbf,i+2);
				String ans;
				if(module==1)
					ans = Logarithm.calLn(filter(stbf.substring(i+3,end),module,b) );
				else if(module==2)
					ans = Complex.ln(Complex.valueOf(filter(stbf.substring(i+3,end),module,b)) ).toString();
				else
					throw new CalculateErrorException();
				
				stbf = stbf.delete(i, i+2);
				stbf = stbf.delete(i+1, end-2);
				stbf = stbf.insert(i+1, ans);
				len = stbf.length();
			}
			else if(s4.equals("sinh")) {
				if(i>0 && stbf.charAt(i-1)>='0' && stbf.charAt(i-1)<='9') {
					stbf.insert(i, "*");
					i = i + 1;
					len = len + 1;
				}
				int end = Parentheses.getMatch(stbf,i+4);
				String ans;
				if(module==1)
					ans = Trig.calSinh(filter(stbf.substring(i+5,end),module,b) );
				else
					throw new CalculateErrorException();
				stbf = stbf.delete(i, i+4);
				stbf = stbf.delete(i+1, end-4);
				stbf = stbf.insert(i+1, ans);
				len = stbf.length();
			}
			else if(s4.equals("cosh")) {
				if(i>0 && stbf.charAt(i-1)>='0' && stbf.charAt(i-1)<='9') {
					stbf.insert(i, "*");
					i = i + 1;
					len = len + 1;
				}
				int end = Parentheses.getMatch(stbf,i+4);
				String ans;
				if(module==1)
					ans = Trig.calCosh(filter(stbf.substring(i+5,end),module,b) );
				else
					throw new CalculateErrorException();
				stbf = stbf.delete(i, i+4);
				stbf = stbf.delete(i+1, end-4);
				stbf = stbf.insert(i+1, ans);
				len = stbf.length();
			}
			else if(s4.equals("tanh")) {
				if(i>0 && stbf.charAt(i-1)>='0' && stbf.charAt(i-1)<='9') {
					stbf.insert(i, "*");
					i = i + 1;
					len = len + 1;
				}
				int end = Parentheses.getMatch(stbf,i+4);
				String ans;
				if(module==1)
					ans = Trig.calTanh(filter(stbf.substring(i+5,end),module,b) );
				else
					throw new CalculateErrorException();
				stbf = stbf.delete(i, i+4);
				stbf = stbf.delete(i+1, end-4);
				stbf = stbf.insert(i+1, ans);
				len = stbf.length();
			}
			else if(s4.equals("coth")) {
				if(i>0 && stbf.charAt(i-1)>='0' && stbf.charAt(i-1)<='9') {
					stbf.insert(i, "*");
					i = i + 1;
					len = len + 1;
				}
				int end = Parentheses.getMatch(stbf,i+4);
				String ans;
				if(module==1)
					ans = Trig.calCoth(filter(stbf.substring(i+5,end),module,b) );
				else
					throw new CalculateErrorException();
				stbf = stbf.delete(i, i+4);
				stbf = stbf.delete(i+1, end-4);
				stbf = stbf.insert(i+1, ans);
				len = stbf.length();
			}
			else if(s4.equals("sech")) {
				if(i>0 && stbf.charAt(i-1)>='0' && stbf.charAt(i-1)<='9') {
					stbf.insert(i, "*");
					i = i + 1;
					len = len + 1;
				}
				int end = Parentheses.getMatch(stbf,i+4);
				String ans;
				if(module==1)
					ans = Trig.calSech(filter(stbf.substring(i+5,end),module,b) );
				else
					throw new CalculateErrorException();
				stbf = stbf.delete(i, i+4);
				stbf = stbf.delete(i+1, end-4);
				stbf = stbf.insert(i+1, ans);
				len = stbf.length();
			}
			else if(s4.equals("csch")) {
				if(i>0 && stbf.charAt(i-1)>='0' && stbf.charAt(i-1)<='9') {
					stbf.insert(i, "*");
					i = i + 1;
					len = len + 1;
				}
				int end = Parentheses.getMatch(stbf,i+4);
				String ans;
				if(module==1)
					ans = Trig.calCsch(filter(stbf.substring(i+5,end),module,b) );
				else
					throw new CalculateErrorException();
				stbf = stbf.delete(i, i+4);
				stbf = stbf.delete(i+1, end-4);
				stbf = stbf.insert(i+1, ans);
				len = stbf.length();
			}
			else if(s3.equals("sin")) {
				if(i>0 && stbf.charAt(i-1)>='0' && stbf.charAt(i-1)<='9') {
					stbf.insert(i, "*");
					i = i + 1;
					len = len + 1;
				}
				int end = Parentheses.getMatch(stbf,i+3);
				String ans;
				if(module==1)
					ans = Trig.calSin(filter(stbf.substring(i+4,end),module,b) );
				else if(module==2)
					ans = Complex.sin(Complex.valueOf(filter(stbf.substring(i+4,end),module,b))).toString();
				else
					throw new CalculateErrorException();
				stbf = stbf.delete(i, i+3);
				stbf = stbf.delete(i+1, end-3);
				stbf = stbf.insert(i+1, ans);
				len = stbf.length();
				//i = i + ans.length();
			}
			else if(s3.equals("cos")) {
				if(i>0 && stbf.charAt(i-1)>='0' && stbf.charAt(i-1)<='9') {
					stbf.insert(i, "*");
					i = i + 1;
					len = len + 1;
				}
				int end = Parentheses.getMatch(stbf,i+3);
				String ans;
				if(module==1)
					ans = Trig.calCos(filter(stbf.substring(i+4,end),module,b) );
				else if(module==2)
					ans = Complex.cos(Complex.valueOf(filter(stbf.substring(i+4,end),module,b))).toString();
				else
					throw new CalculateErrorException();
				stbf = stbf.delete(i, i+3);
				stbf = stbf.delete(i+1, end-3);
				stbf = stbf.insert(i+1, ans);
				len = stbf.length();
			}
			else if(s3.equals("tan")) {
				if(i>0 && stbf.charAt(i-1)>='0' && stbf.charAt(i-1)<='9') {
					stbf.insert(i, "*");
					i = i + 1;
					len = len + 1;
				}
				int end = Parentheses.getMatch(stbf,i+3);
				String ans;
				if(module==1)
					ans = Trig.calTan(filter(stbf.substring(i+4,end),module,b) );
				else if(module==2)
					ans = Complex.tan(Complex.valueOf(filter(stbf.substring(i+4,end),module,b))).toString();
				else
					throw new CalculateErrorException();
				stbf = stbf.delete(i, i+3);
				stbf = stbf.delete(i+1, end-3);
				stbf = stbf.insert(i+1, ans);
				len = stbf.length();
			}
			else if(s3.equals("cot")) {
				if(i>0 && stbf.charAt(i-1)>='0' && stbf.charAt(i-1)<='9') {
					stbf.insert(i, "*");
					i = i + 1;
					len = len + 1;
				}
				int end = Parentheses.getMatch(stbf,i+3);
				String ans;
				if(module==1)
					ans = Trig.calCot(filter(stbf.substring(i+4,end),module,b) );
				else if(module==2)
					ans = Complex.cot(Complex.valueOf(filter(stbf.substring(i+4,end),module,b))).toString();
				else
					throw new CalculateErrorException();
				stbf = stbf.delete(i, i+3);
				stbf = stbf.delete(i+1, end-3);
				stbf = stbf.insert(i+1, ans);
				len = stbf.length();
			}
			else if(s3.equals("sec")) {
				if(i>0 && stbf.charAt(i-1)>='0' && stbf.charAt(i-1)<='9') {
					stbf.insert(i, "*");
					i = i + 1;
					len = len + 1;
				}
				int end = Parentheses.getMatch(stbf,i+3);
				String ans;
				if(module==1)
					ans = Trig.calSec(filter(stbf.substring(i+4,end),module,b) );
				else if(module==2)
					ans = Complex.sec(Complex.valueOf(filter(stbf.substring(i+4,end),module,b))).toString();
				else
					throw new CalculateErrorException();
				stbf = stbf.delete(i, i+3);
				stbf = stbf.delete(i+1, end-3);
				stbf = stbf.insert(i+1, ans);
				len = stbf.length();
			}
			else if(s3.equals("csc")) {
				if(i>0 && stbf.charAt(i-1)>='0' && stbf.charAt(i-1)<='9') {
					stbf.insert(i, "*");
					i = i + 1;
					len = len + 1;
				}
				int end = Parentheses.getMatch(stbf,i+3);
				String ans;
				if(module==1)
					ans = Trig.calCsc(filter(stbf.substring(i+4,end),module,b) );
				else if(module==2)
					ans = Complex.csc(Complex.valueOf(filter(stbf.substring(i+4,end),module,b))).toString();
				else
					throw new CalculateErrorException();
				stbf = stbf.delete(i, i+3);
				stbf = stbf.delete(i+1, end-3);
				stbf = stbf.insert(i+1, ans);
				len = stbf.length();
			}
			else if(s3.equals("log")) {
				if(i>0 && stbf.charAt(i-1)>='0' && stbf.charAt(i-1)<='9') {
					stbf.insert(i, "*");
					i = i + 1;
					len = len + 1;
				}
				int end = Parentheses.getMatch(stbf,i+3);
				int com = Parentheses.getComma(stbf,i+3);
				String ans;
				if(module==1)
					ans = Logarithm.calLog(filter(stbf.substring(i+4,com),module,b),filter(stbf.substring(com+1,end),module,b) );
				else if(module==2)
					ans = Complex.log(Complex.valueOf(filter(stbf.substring(i+4,com),module,b)), Complex.valueOf(filter(stbf.substring(com+1,end),module,b)) ).toString();
				else
					throw new CalculateErrorException();
				stbf = stbf.delete(i, i+3);
				stbf = stbf.delete(i+1, end-3);
				stbf = stbf.insert(i+1, ans);
				len = stbf.length();
			}
			else if(s6.equals("arcsin")) {
				if(i>0 && stbf.charAt(i-1)>='0' && stbf.charAt(i-1)<='9') {
					stbf.insert(i, "*");
					i = i + 1;
					len = len + 1;
				}
				int end = Parentheses.getMatch(stbf,i+6);
				String ans;
				if(module==1)
					ans = Trig.calArcsin(filter(stbf.substring(i+7,end),module,b) );
				else
					throw new CalculateErrorException();
				stbf = stbf.delete(i, i+6);
				stbf = stbf.delete(i+1, end-6);
				stbf = stbf.insert(i+1, ans);
				len = stbf.length();
			}
			else if(s6.equals("arccos")) {
				if(i>0 && stbf.charAt(i-1)>='0' && stbf.charAt(i-1)<='9') {
					stbf.insert(i, "*");
					i = i + 1;
					len = len + 1;
				}
				int end = Parentheses.getMatch(stbf,i+6);
				String ans;
				if(module==1)
					ans = Trig.calArccos(filter(stbf.substring(i+7,end),module,b) );
				else
					throw new CalculateErrorException();
				stbf = stbf.delete(i, i+6);
				stbf = stbf.delete(i+1, end-6);
				stbf = stbf.insert(i+1, ans);
				len = stbf.length();
			}
			else if(s6.equals("arctan")) {
				if(i>0 && stbf.charAt(i-1)>='0' && stbf.charAt(i-1)<='9') {
					stbf.insert(i, "*");
					i = i + 1;
					len = len + 1;
				}
				int end = Parentheses.getMatch(stbf,i+6);
				String ans;
				if(module==1)
					ans = Trig.calArctan(filter(stbf.substring(i+7,end),module,b) );
				else
					throw new CalculateErrorException();
				stbf = stbf.delete(i, i+6);
				stbf = stbf.delete(i+1, end-6);
				stbf = stbf.insert(i+1, ans);
				len = stbf.length();
			}
			else if(s1.matches(f)) {
			}
			else {
				System.out.println(stbf.charAt(i) +" "+stbf+" "+ String.valueOf(i));
				throw new InputIllegalException();
			}
		}
		
		String ret = stbf.toString();
		
		ret = ret.replaceAll("π",String.valueOf(Calculate.PAI));
		ret = ret.replaceAll("e", String.valueOf(Calculate.E));
		
		if(module == 1)
			return Calculate.solution(ret);
		else if(module == 2)
			return CalComplex.solution(ret);
		else if(module == 3)
			return CalBase.solution(ret,b);
		else
			throw new InputIllegalException();
	}
}
