package com.example.calculator.model.base;

import com.example.calculator.model.exceptions.InputIllegalException;

/**
 * 进制数
 * @author 刘禹辰
 *
 */
public class Base {
	private String baseNumber;	// 进制数
	private int decimalNumber;	// 十进制
	private int base;			// 进制
	/**
	 * 0的进制数实例
	 */
	public static final Base zero = new Base(0);
	/**
	 * 构建一个进制数
	 * @param baseNumber 进制数字符串
	 * @param base 进制
	 * @throws Exception
	 */
	public Base(String baseNumber,int base) throws Exception {
		if(base>36||base<2) {
			throw new InputIllegalException();
		}
		this.baseNumber=baseNumber;
		this.base=base;
		this.decimalNumber=Integer.valueOf(baseNumber,base);
	}
	/**
	 * 通过一个十进制整数构建一个进制数
	 * @param x 整数
	 */
	public Base(int x) {
		this.baseNumber = String.valueOf(x);
		this.decimalNumber = x;
		this.base = 10;
	}
	//get和set方法
	public String getBaseNumber() {
		return baseNumber;
	}
	public void setBaseNumber(String baseNumber) {
		this.baseNumber = baseNumber;
	}
	public int getDecimalNumber() {
		return decimalNumber;
	}
	public void setDecimalNumber(int decimalNumber) {
		this.decimalNumber = decimalNumber;
	}
	public int getBase() {
		return base;
	}
	public void setBase(int base) {
		this.base = base;
	}
	
	public String toString() {
		return this.baseNumber;
	}
	/**
	 * 进制转换
	 * @param base 进制数
	 * @param toBase 转换进制
	 * @return 进制数
	 * @throws Exception
	 */
	public static Base baseConversion(Base base,int toBase) throws Exception{
		if(toBase>36||toBase<2)
			throw new Exception();
		int decimalNumber=base.decimalNumber;
		String str= "";
		int remainder ;
		while (decimalNumber>0) {
			remainder = decimalNumber%toBase;
			decimalNumber = decimalNumber/toBase;   
			if(remainder>9) {
				str =(char)('a'+(remainder-10)) + str;      
			}
			else {
				str = remainder+str;
			}
		}
		return new Base(str,toBase);
	}
	/**
	 * 两个进制数相加
	 * @param a 进制数1
	 * @param b 进制数2
	 * @return 计算结果的进制数
	 * @throws Exception
	 */
	public static Base baseadd(Base a,Base b) throws Exception {
		int c=a.getDecimalNumber()+b.getDecimalNumber();
		return new Base(String.valueOf(c),10);
	}
	/**
	 * 两个进制数相减
	 * @param a 进制数1
	 * @param b 进制数2
	 * @return 计算结果的进制数
	 * @throws Exception
	 */
	public static Base basedec(Base a,Base b) throws Exception {
		int c=a.getDecimalNumber()-b.getDecimalNumber();
		return new Base(String.valueOf(c),10);
	}
	/**
	 * 两个进制数相乘
	 * @param a 进制数1
	 * @param b 进制数2
	 * @return 计算结果的进制数
	 * @throws Exception
	 */
	public static Base basemul(Base a,Base b) throws Exception {
		int c=a.getDecimalNumber()*b.getDecimalNumber();
		return new Base(String.valueOf(c),10);
	}
	/**
	 * 两个进制数相除
	 * @param a 进制数1
	 * @param b 进制数2
	 * @return 计算结果的进制数
	 * @throws Exception
	 */
	public static Base basedev(Base a,Base b) throws Exception {
		int c=a.getDecimalNumber()/b.getDecimalNumber();
		return new Base(String.valueOf(c),10);
	}
	/**
	 * 两个进制数相乘
	 * @param a 进制数1
	 * @param b 进制数2
	 * @return 计算结果的进制数
	 * @throws Exception
	 */
	public static Base basepow(Base a,Base b) throws Exception {
		int c=(int)Math.pow(a.getDecimalNumber(),b.getDecimalNumber());
		return new Base(String.valueOf(c),10);
	}
}
