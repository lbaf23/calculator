package com.example.calculator.model.calculate;

/**
 * 求和
 * @author 李柯凡
 *
 */
public class Sigma {
	/**
	 * 求和
	 * @param down	求和下限
	 * @param up	求和上限
	 * @param str	求和表达式
	 * @return
	 * @throws Exception
	 */
	public static String calSigma(String down,String up,String str) throws Exception {
		if(up.equals("+∞")) {
		}
		String ans = "", line = "";
		int d = Integer.valueOf(down), u = Integer.valueOf(up);
		for(int i=d; i<=u; i++) {
			line = str.replaceAll("x", "("+String.valueOf(i)+")");
			line = Calculate.calculate(line);
			if(ans!="" && line!="")
				ans = Calculate.calculate(ans+"+"+line);
			else if(line!="")
				ans = Calculate.calculate(line);
		}
		return ans;
	}
}
