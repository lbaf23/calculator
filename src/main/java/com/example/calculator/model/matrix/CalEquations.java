package com.example.calculator.model.matrix;

import java.util.ArrayList;

import com.example.calculator.model.exceptions.InputIllegalException;
import com.example.calculator.model.getmatches.Parentheses;

/**
 * 计算方程组
 * @author 李柯凡
 *
 */
public class CalEquations {
	private static final int size = 27;
	private int min = 26;
	private ArrayList<double[]> in = new ArrayList<double[]>();
	/**
	 * 解方程租
	 * @return 格式化的解
	 * @throws Exception
	 */
	public String solveEquations() throws Exception {
		double d[][] = new double[in.size()][size];
		int p=0;
		for(double db[]:in) {
			for(int j=0; j<size; j++) {
				d[p][j] = db[j];
			}
			p++;
		}
		
		Equations e = new Equations(d);
		Equations.solveEquations(e);
		String ans = e.getResult().trim();
		char c = 'a';
		int j=min;
		System.out.println(min);
		for(int i=0; i<size; i++) {
			if((char)(c+i+min) <= 'z') {
				String r = String.valueOf((char)(c+i));
				String rp = String.valueOf((char)(c+i+min));
				System.out.println((char)(c+i+min));
				ans = ans.replace(r, rp );
			}
			j++;
		}
		return ans;
	}
	/**
	 * 添加方程
	 * @param s 一个方程
	 * @throws Exception
	 */
	public void addEquation(String s) throws Exception {
		double []line = this.checkEquation(s);
		in.add(line);
	}
	/**
	 * 检查方程合法性
	 * @param s 一个方程
	 * @return	返回格式化的方程
	 * @throws Exception
	 */
	public double[] checkEquation(String s) throws Exception {
		if(!s.contains("=")) {
			throw new InputIllegalException();
		}
		double[] var = new double[size];
		s = s.toLowerCase();
		s = s.replaceAll("\\s+", "");
		s = s.replace("\n", "");
		char c = 'a';
		int j=0;
		for(int i=0; i<26; i++) {
			int p = s.indexOf((char)(c+i));
			if(p >= 0) {
				min = Math.min(min, i);
				int b=0;
				if(p==0)
					b=0;
				else
					b = Parentheses.getBeforeDigi(s, p-1);
				if(b==p) {
					if(b==0)
						var[j] = 1;
					else if(s.charAt(b-1)=='-')
						var[j] = -1;
					else if(s.charAt(b-1)=='+')
						var[j] = 1;
					else
						throw new InputIllegalException();
					j++;
				}
				else {
					double v = Double.valueOf(s.substring(b,p));
					if(b-1>=0 && s.charAt(b-1)=='-')
						var[j] = -v;
					else
						var[j] = v;
					j++;
				}
			}
			else if(j>0)
				var[j++] = 0;
		}
		int b = Parentheses.getBeforeDigi(s, s.length()-1);
		if(b-1>0 && s.charAt(b-1)=='-')
			var[26] = -Double.valueOf(s.substring(b));
		else
			var[26] = Double.valueOf(s.substring(b));
		return var;
	}
	/**
	 * 格式化方程
	 * @param d 方程的系数
	 * @return 格式化后的方程
	 */
	public String formatEquation(double[] d) {
		StringBuffer s = new StringBuffer();
		char c = (char)('a'+min);
		for(int i=0; i<size-1; i++) {
			if(d[i]!=0) {
				if(d[i]>0 && s.length()!=0) {
					s.append("  +  ");
				}
				if(d[i]<0) {
					s.append("  -  ");
				}
				s.append(Math.abs(d[i]));
				s.append((char)(c+i));
			}
		}
		s.append("  =  "+d[size-1]);
		return s.toString();
	}
}
