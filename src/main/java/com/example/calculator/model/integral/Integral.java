package com.example.calculator.model.integral;

import com.example.calculator.model.calculate.Calculate;


/**
 * 求积分
 * @author 刘禹辰
 *
 */
public class Integral {
	private String ifunction;//积分函数
	private double a,b;//积分的两个端点
	private String sum;
	private int op;
	/**
	 * 构建积分实例
	 * @param ifunction	积分函数
	 * @param a			积分下限
	 * @param b			积分上限
	 */
	public Integral(String ifunction,double a,double b) {
		this.ifunction=ifunction;
		if(a<b) {
			this.a=a;
			this.b=b;
			this.op = 1;
		}
		else {
			this.b=a;
			this.a=b;
			this.op = -1;
		}
	}
	public String getIfunction() {
		return ifunction;
	}
	public void setIfunction(String ifunction) {
		this.ifunction = ifunction;
	}
	public double getA() {
		return a;
	}
	public void setA(double a) {
		this.a = a;
	}
	public double getB() {
		return b;
	}
	public void setB(double b) {
		this.b = b;
	}
	/**
	 * @return 积分的结果
	 */
	public String getSum() {
		return sum;
	}
	public void setSum(String sum) {
		this.sum = sum;
	}
	/**
	 * 求积分
	 * @param t
	 * @throws Exception
	 */
	public static void Integration(Integral t) throws Exception {
		char c[]=t.getIfunction().toCharArray();
		double sum=0;
		for(int i=0;i<50000;i++) {
			String sx="";
			double x=min(t.a,t.b,50000,i);
			for(int j=0;j<c.length;j++) {
				if(c[j]!='x') {
					sx+=c[j];
				}
				else {
					sx=sx+"("+x+")";
				}
			}
			sum+=Double.valueOf(Calculate.calculate(sx));
		}
		sum=sum*(t.getB()-t.getA())/50000.0 * t.op;
		String sd=String.format("%.1f", sum);
		t.setSum(sd);
	}
	private static double min(double a,double b,double n,int i) {
		return a+i*(b-a)/n;
	}
}
