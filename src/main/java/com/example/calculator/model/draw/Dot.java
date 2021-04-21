package com.example.calculator.model.draw;
/**
 * 点
 * @author 陈振涛
 *
 */
public class Dot {
	private double x;
	private double y;
	/**
	 * 
	 * @param x 横坐标
	 * @param y 纵坐标
	 */
	public Dot(double x,double y) {
		this.x = x; this.y = y;
	}
	/**
	 * 
	 * @param x 横坐标
	 * @param y 纵坐标
	 * @throws Exception
	 */
	public Dot(String x,String y) throws Exception{
		this.x = Double.parseDouble(x);
		this.y = Double.parseDouble(y);
	}
	public double getX() {
		return x;
	}
	public void setX(double x) {
		this.x = x;
	}
	public double getY() {
		return y;
	}
	public void setY(double y) {
		this.y = y;
	}
	/**
	 * 返回格式化的点
	 */
	@Override
	public String toString() {
		return "("+x+","+y+")";
	}
}
