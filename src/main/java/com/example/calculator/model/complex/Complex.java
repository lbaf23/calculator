package com.example.calculator.model.complex;

import com.example.calculator.model.calculate.Calculate;
import com.example.calculator.model.calculate.Logarithm;

/**
 * 复数类
 * @author 李柯凡
 *
 */
public class Complex {
	/**
	 * i的复数实例
	 */
	public static final Complex i = new Complex(0,1);
	/**
	 * e的复数实例的
	 */
	public static final Complex e = new Complex(Math.E,0);
	/**
	 * π的复数实例
	 */
	public static final Complex pi = new Complex(Math.PI,0);
	/**
	 * 0的复数实例
	 */
	public static final Complex ZERO = new Complex(0,0);
	/**
	 * 默认保留小数位数
	 */
	public static int CAP = 10;
	
	public static Complex varpow;
	private double x;
	private double y;
	
	/**
	 * 
	 * @param x 复数的实部
	 * @param y 复数的虚部
	 */
	public Complex(double x,double y) {
		this.x = x;
		this.y = y;
	}
	/**
	 * 相加
	 * @param a
	 * @param b
	 * @return
	 */
	public static Complex add(Complex a, Complex b) {
		return new Complex(a.x + b.x, a.y + b.y);
	}
	/**
	 * 相减
	 * @param a
	 * @param b
	 * @return
	 */
	public static Complex subtract(Complex a, Complex b) {
		return new Complex(a.x - b.x, a.y - b.y);
	}
	/**
	 * 相乘
	 * @param a
	 * @param b
	 * @return
	 */
	public static Complex multiply(Complex a, Complex b) {
		return new Complex(a.x*b.x - a.y*b.y, a.x*b.y + a.y*b.x);
	}
	/**
	 * 相除
	 * @param a
	 * @param b
	 * @return
	 */
	public static Complex divide(Complex a, Complex b) {
		double x1 = (a.x*b.x + a.y*b.y)/(b.x*b.x + b.y*b.y);
		double y1 = (b.x*a.y - b.y*a.x)/(b.x*b.x + b.y*b.y);
		return new Complex(x1, y1);
	}
	/**
	 * 幂次
	 * @param x
	 * @param a
	 * @return
	 * @throws Exception
	 */
	public static Complex pow(Complex x, Complex a) throws Exception {
		Complex pw1 = Complex.multiply(a, new Complex(Logarithm.calLn(x.getLength()), 0));
		Complex pw2 =  Complex.multiply(Complex.multiply(i, a), new Complex(x.getArgument(),0) );
		varpow = Complex.add(pw1, pw2);
		return exp(varpow);
	}
	/**
	 * 以10为底的复对数
	 * @param x
	 * @return
	 */
	public static Complex lg(Complex x) {
		return new Complex(Math.log(x.getLength()),Math.atan(x.y/x.x));
	}
	/**
	 * 自然数e为底的对数
	 * @param x
	 * @return
	 */
	public static Complex ln(Complex x) {
		return Complex.divide(lg(x), lg(e));
	}
	/**
	 * 任意底数和指数的对数
	 * @param n 底数
	 * @param x 指数
	 * @return
	 */
	public static Complex log(Complex n,Complex x) {
		return Complex.divide(lg(x), lg(n));
	}
	// 复三角函数
	/**
	 * cos()
	 * @param x
	 * @return
	 */
	public static Complex cos(Complex x) {
		Complex x1 = Complex.multiply(new Complex(Math.cos(x.x),0), Complex.cosni(x.y));
		Complex x2 = Complex.multiply(new Complex(Math.sin(x.x),0), Complex.sinni(x.y));
		return Complex.subtract(x1, x2);
	}
	/**
	 * sin()
	 * @param x
	 * @return
	 */
	public static Complex sin(Complex x) {
		Complex x1 = Complex.multiply(new Complex(Math.sin(x.x),0), Complex.cosni(x.y));
		Complex x2 = Complex.multiply(new Complex(Math.cos(x.x),0), Complex.sinni(x.y));
		return Complex.add(x1, x2);
	}
	/**
	 * tan()
	 * @param x
	 * @return
	 */
	public static Complex tan(Complex x) {
		if(Complex.cos(x).equals(new Complex(0,0)) ) {
			return null;
		}
		return Complex.divide(Complex.sin(x), Complex.cos(x));
	}
	/**
	 * cot()
	 * @param x
	 * @return
	 */
	public static Complex cot(Complex x) {
		return Complex.divide(new Complex(1,0), tan(x));
	}
	/**
	 * sec()
	 * @param x
	 * @return
	 */
	public static Complex sec(Complex x) {
		return Complex.divide(new Complex(1,0), cos(x));
	}
	/**
	 * csc()
	 * @param x
	 * @return
	 */
	public static Complex csc(Complex x) {
		return Complex.divide(new Complex(1,0), sin(x));
	}
	/**
	 * 判断两个复数是否相等
	 * @param x
	 * @return
	 */
	public boolean equals(Complex x) {
		return this.x == x.x && this.y == x.y;
	}
	/**
	 * 计算虚数的余弦
	 * @param n
	 * @return
	 */
	public static Complex cosni(double n) {
		return new Complex(Math.cosh(n), 0);
	}
	/**
	 * 计算虚数的正弦
	 * @param n
	 * @return
	 */
	public static Complex sinni(double n) {
		return new Complex(0,Math.sinh(n));
	}
	/**
	 * 计算e的复数次方
	 * @param c
	 * @return
	 */
	public static Complex exp(Complex c) {
		return new Complex(Math.cos(c.y)*Math.exp(c.x), Math.exp(c.x)*Math.sin(c.y));
	}
	/**
	 * 求Arg() 幅角主值
	 * @return
	 */
	public double getArgument() {
		if(this.x != 0)
			return Math.atan(this.y/this.x);
		else
			return this.y>0 ? Math.PI/2:-Math.PI/2 + Math.PI*2;
	}
	/**
	 * 计算复变函数
	 * @param z
	 * @return
	 */
	public static Complex calArg(Complex z) {
		Complex r = new Complex(z.getLength(),0);
		Complex costh = new Complex(Math.cos(z.getArgument()),0);
		Complex sinth = new Complex(Math.sin(z.getArgument()),0);
		return Complex.multiply(r, Complex.add(costh, Complex.multiply(i,sinth)) );
	}
	/**
	 * 获取模长
	 * @return
	 */
	public double getLength() {
		return Math.sqrt(x*x + y*y);
	}
	/**
	 * 获取实部
	 * @return
	 */
	public double toDouble() {
		return this.x;
	}
	/**
	 * 格式化一个复数
	 */
	@Override
	public String toString() {
		try {
			String a = Calculate.retainDec(String.valueOf(this.x), CAP),
					b = Calculate.retainDec(String.valueOf(this.y), CAP);
			if(b.matches("^[0|.]$")) {
				return String.valueOf(a);
			}
			else if(a.matches("^[0|.]$")) {
				return b+"i";
			}
			String op = b.startsWith("-") ? "":"+";
			return a + op + b + "i";
			
		}catch(Exception e) {
			return "";
		}
	}
	/**
	 * 将字符串转换为一个复数
	 * @param str 字符串
	 * @return 计算结果
	 * @throws Exception
	 */
	public static Complex valueOf(String str) throws Exception {
		if(str.endsWith("i")) {
			double x = 0, y=0;
			if((str.charAt(0)=='-'||str.charAt(0)=='+')&&(str.substring(1).contains("+")||str.substring(1).contains("-"))) {
				int op = str.substring(1).indexOf("+");
				op = Math.max(op, str.substring(1).indexOf("-"));
				x = Double.valueOf(str.substring(0,op));
				y = Double.valueOf(str.substring(op+1,str.length()-1));
			}
			else if(str.contains("+")||str.contains("-")) {
				int op = str.indexOf("+");
				op = Math.max(op, str.indexOf("-"));
				x = Double.valueOf(str.substring(0,op));
				y = Double.valueOf(str.substring(op+1,str.length()-1));
			}
			else {
				if(str.length()==1)
					return new Complex(0,1);
				else if(str.length()==2&&str.charAt(0)=='-')
					return new Complex(0,-1);
				return new Complex(0,Double.valueOf(str.substring(0,str.length()-1)) );
			}
			return new Complex(x,y);
		}
		else {
			return new Complex(Double.valueOf(str), 0);
		}
	}
	
	public double getX() {
		x=Math.round(x*10000000000.0)/10000000000.0;
		return x;
	}
	public void setX(double x) {
		this.x = x;
	}
	public double getY() {
		y=Math.round(y*10000000000.0)/10000000000.0;
		return y;
	}
	
}
