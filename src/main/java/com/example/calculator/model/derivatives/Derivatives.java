package com.example.calculator.model.derivatives;

import com.example.calculator.model.calculate.Calculate;
/**
 * 计算导数值
 * @author 刘禹辰
 *
 */
public class Derivatives {	
	private static double dx=0.000001;
	private String pfunction;//原函数
	private String dvalue;//导数值
	private double spot;
	/**
	 * 构造一个导数实例
	 * @param pfunction
	 * @param spot
	 */
	public Derivatives(String pfunction,double spot) {
		this.pfunction=pfunction;
		this.spot=spot;
	}
	public String getPfunction() {
		return pfunction;
	}
	public void setPfunction(String pfunction) {
		this.pfunction = pfunction;
	}
	
	public String getDvalue() {
		return dvalue;
	}
	public void setDvalue(String dvalue) {
		this.dvalue = dvalue;
	}
	public double getSpot() {
		return spot;
	}
	public void setSpot(double spot) {
		this.spot = spot;
	}
	/**
	 * 求某点的导数值
	 * @param d
	 * @throws Exception
	 */
	public static void derivation(Derivatives d) throws Exception {
		if(d.getSpot()>1000) {
			dx*=10;
		}
		if(d.getSpot()>10000) {
			dx*=10;
		}
		char c[]=d.getPfunction().toCharArray();
		String x1="";
		String x2="";
		for(int i=0;i<c.length;i++) {
			if(c[i]!='x') {
				x1+=c[i];
				x2+=c[i];
			}
			else {
				x1+="("+d.getSpot()+")";
				x2+="("+(d.getSpot()+dx)+")";
			}
		}
		String r1=Calculate.calculate(x1);
		String r2=Calculate.calculate(x2);
		double dvalue=(Double.valueOf(r2)-Double.valueOf(r1))/dx;
		String sd=String.format("%.0f", dvalue);
		d.setDvalue(sd);
	}
}
