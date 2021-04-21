package com.example.calculator.model.draw;

import java.util.ArrayList;

import com.example.calculator.model.calculate.Calculate;
import com.example.calculator.model.exceptions.*;

/**
 * 计算出函数图像上的一系列点
 * @author 陈振涛
 *
 */
public class Draw {
	/**
	 * 默认保留小数位数
	 */
	public static final int CAP = 10;
	/**
	 * 
	 * @param input	输入函数方程
	 * @param l		区间左限
	 * @param r		区间右限
	 * @param d		精度
	 * @return
	 * @throws Exception
	 */
	public static ArrayList<Dot> drawFunction(String input,String l,String r,String d) throws Exception {
		if(input==null||input.equals("")||l==null||l.equals("")||r==null||r.equals("")||d==null||d.equals(""))
			throw new MissingArguException();
		input = input.replaceAll("\\s+", "");
		ArrayList<Dot> dot = new ArrayList<>();
		try{
			double left = Double.parseDouble(l);
			double right = Double.parseDouble(r);
			double dt = Double.parseDouble(d);
			if(dt<=0)
				throw new InputIllegalException();
			if(left>right)
				throw new InputIllegalException();
			for(double i=left; i<=right; i=i+dt) {
				String y = Calculate.retainDec(Calculate.calculate(input.replace("x", "("+i+")")),CAP);
				dot.add(new Dot(Calculate.retainDec(String.valueOf(i),CAP),y) );
			}
		}catch(NumberFormatException e) {
			throw new InputIllegalException();
		}
		return dot;
	}
}
