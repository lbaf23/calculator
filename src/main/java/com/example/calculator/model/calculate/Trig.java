package com.example.calculator.model.calculate;

/**
 * 三角函数计算
 * @author 李柯凡
 *
 */
public class Trig {
	/**
	 * 正弦
	 * @param str
	 * @return
	 * @throws Exception
	 */
	public static String calSin(String str) throws Exception {
		if(str.endsWith("°")) {
			double d = Math.toRadians(Double.valueOf(str.substring(0,str.length()-1)) );
			return String.valueOf(Math.sin(d));
		}
		else {
			double d = Double.valueOf(str);
			return String.valueOf(Math.sin(d));
		}
	}
	/**
	 * 余弦
	 * @param str
	 * @return
	 * @throws Exception
	 */
	public static String calCos(String str) throws Exception {
		if(str.endsWith("°")) {
			double d = Math.toRadians(Double.valueOf(str.substring(0,str.length()-1)) );
			return String.valueOf(Math.cos(d));
		}
		else {
			double d = Double.valueOf(str);
			return String.valueOf(Math.cos(d));
		}
	}
	/**
	 * 正切
	 * @param str
	 * @return
	 * @throws Exception
	 */
	public static String calTan(String str) throws Exception {
		if(str.endsWith("°")) {
			double d = Math.tan(Math.toRadians(Double.valueOf(str.substring(0,str.length()-1)) ) );
			if(d>1E15)
				return String.valueOf(Double.POSITIVE_INFINITY);
			return String.valueOf(d);
		}
		else {
			double d = Math.tan(Double.valueOf(str));
			if(d>1E15)
				return String.valueOf(Double.POSITIVE_INFINITY);
			return String.valueOf(d);
		}
	}
	/**
	 * 余切
	 * @param str
	 * @return
	 * @throws Exception
	 */
	public static String calCot(String str) throws Exception {
		double d = Double.valueOf(calTan(str));
		return String.valueOf(1.0/d);
	}
	/**
	 * 正割
	 * @param str
	 * @return
	 * @throws Exception
	 */
	public static String calSec(String str) throws Exception {
		double d = Double.valueOf(calCos(str));
		return String.valueOf(1.0/d);
	}
	/**
	 * 余割
	 * @param str
	 * @return
	 * @throws Exception
	 */
	public static String calCsc(String str) throws Exception {
		double d = Double.valueOf(calSin(str));
		return String.valueOf(1.0/d);
	}
	/**
	 * 双曲函数
	 */
	/**
	 * 双曲正弦
	 * @param str
	 * @return
	 * @throws Exception
	 */
	public static String calSinh(String str) throws Exception {
		return String.valueOf(Math.sinh(Double.valueOf(str)));
	}
	/**
	 * 双曲余弦
	 * @param str
	 * @return
	 * @throws Exception
	 */
	public static String calCosh(String str) throws Exception {
		return String.valueOf(Math.cosh(Double.valueOf(str)));
	}
	/**
	 * 双曲正切
	 * @param str
	 * @return
	 * @throws Exception
	 */
	public static String calTanh(String str) throws Exception {
		return String.valueOf(Math.tanh(Double.valueOf(str)));
	}
	/**
	 * 双曲余切
	 * @param str
	 * @return
	 * @throws Exception
	 */
	public static String calCoth(String str) throws Exception {
		double ans = Double.valueOf(calCosh(str))/Double.valueOf(calSinh(str));
		return String.valueOf(ans);
	}
	/**
	 * 双曲正割
	 * @param str
	 * @return
	 * @throws Exception
	 */
	public static String calSech(String str) throws Exception {
		double d = Double.valueOf(str);
		double ans = 2.0/(Math.pow(Math.E,d)+Math.pow(Math.E,-d));
		return String.valueOf(ans);
	}
	/**
	 * 双曲余割
	 * @param str
	 * @return
	 * @throws Exception
	 */
	public static String calCsch(String str) throws Exception {
		double d = Double.valueOf(str);
		double ans = 2.0/(Math.pow(Math.E,d)+Math.pow(Math.E,-d));
		return String.valueOf(ans);
	}
	
	/**
	 * 反三角函数
	 */
	/**
	 * 反正弦
	 * @param str
	 * @return
	 * @throws Exception
	 */
	public static String calArcsin(String str) throws Exception {
		double d = Double.valueOf(str);
		return String.valueOf(Math.asin(d));
	}
	/**
	 * 反余弦
	 * @param str
	 * @return
	 * @throws Exception
	 */
	public static String calArccos(String str) throws Exception {
		double d = Double.valueOf(str);
		return String.valueOf(Math.acos(d));
	}
	/**
	 * 反正切
	 * @param str
	 * @return
	 * @throws Exception
	 */
	public static String calArctan(String str) throws Exception {
		double d = Double.valueOf(str);
		return String.valueOf(Math.atan(d));
	}
	
	/**
	 * 弃用方法
	 */
	/**
	 * 反余切
	 * @param str
	 * @return
	 * @throws Exception
	 */
	public static String calArccot(String str) throws Exception {
		double d = Double.valueOf(str);
		return String.valueOf(Math.PI/2.0 - Math.atan(d));
	}
	/**
	 * 反正割
	 * @param str
	 * @return
	 * @throws Exception
	 */
	public static String calArcsec(String str) throws Exception {
		double d = Double.valueOf(str);
		return String.valueOf(Math.acos(1.0/d));
	}
	/**
	 * 反余割
	 * @param str
	 * @return
	 * @throws Exception
	 */
	public static String calArccsc(String str) throws Exception {
		double d = Double.valueOf(str);
		return String.valueOf(Math.asin(1.0/d));
	}
}
