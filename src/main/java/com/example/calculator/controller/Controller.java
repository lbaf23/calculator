package com.example.calculator.controller;


import java.util.*;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.calculator.model.base.CalBase;
import com.example.calculator.model.calculate.*;
import com.example.calculator.model.complex.CalComplex;
import com.example.calculator.model.derivatives.Derivatives;
import com.example.calculator.model.draw.Dot;
import com.example.calculator.model.draw.Draw;
import com.example.calculator.model.equation.Equation;
import com.example.calculator.model.exceptions.*;
import com.example.calculator.model.getmatches.Parentheses;
import com.example.calculator.model.integral.Integral;
import com.example.calculator.model.matrix.CalEquations;
import com.example.calculator.model.matrix.CalMatrix;

/**
 * 返回前端的类
 * @author 李柯凡
 *
 */
class Ret{
	boolean success;
	String result;
	String message;
	
	public Ret(boolean success,String result,String message) {
		this.success = success;
		this.result = result;
		this.message = message;
	}
	public Ret() {
		this.success = false;
		this.result = "";
		this.message = "";
	}
	
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
}
/**
 * 方程计算的返回前端的类
 * @author 李柯凡
 *
 */
class EquationRet extends Ret{
	private List<String> res;
	public EquationRet(){
		super();
		this.res = new ArrayList<>();
	}
	public void addRes(String s) {
		res.add(s);
	}
	public void addRes(String[] str) {
		for(String s:str) {
			addRes(s);
		}
	}
	
	public List<String> getRes() {
		return res;
	}
	public void setRes(List<String> res) {
		this.res = res;
	}
}
/**
 * 求导数的计算返回前端的类
 * @author 李柯凡
 *
 */
class DerRet extends Ret{
	private double x;
	private double y;
	public DerRet() {
		super();
		x = 0;
		y = 0;
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
}
/**
 * 函数图像绘制的返回前端的类
 * @author 李柯凡
 *
 */
class FunctionRet extends Ret{
	private ArrayList<Dot> point = new ArrayList<>();
	public void addPoint(Dot d) {
		this.point.add(d);
	}
	public void addFunction(List<Dot> d) {
		for(Dot dt:d) {
			addPoint(dt);
		}
	}
	public ArrayList<Dot> getPoint() {
		return point;
	}
	public void setPoint(ArrayList<Dot> point) {
		this.point = point;
	}
}
/*
 * 前端控制器
 * http://localhost:8000
 * 
 */
/**
 * 和前端的交互类
 * @author 李柯凡
 *
 */
@RestController
public class Controller {
	/**
	 * 其它情况报错信息
	 */
	public static final String ERROR = "Error: 请检查您的输入。";
	/**
	 * 计算算式的值
	 * @param input
	 * @return
	 */
	@RequestMapping(value = "/calculate")
	@ResponseBody
	@CrossOrigin
	public Object calculate(@RequestParam("input") String input) {
		Ret r = new Ret();
		System.out.println("calculating "+input+"......");
		try {
			r.setSuccess(true);
			r.setResult(Calculate.calculate(input));
		}catch(InputIllegalException e) {
			r.setSuccess(false);
			r.setMessage(e.getMessage());
		}catch(MissingArguException e) {
			r.setSuccess(false);
			r.setMessage(e.getMessage());
		}catch(CalculateErrorException e) {
			r.setSuccess(false);
			r.setMessage(e.getMessage());
		}catch(Exception e) {
			r.setSuccess(false);
			r.setMessage(ERROR);
		}
		return r;
	}
	/**
	 * 解方程
	 * @param input1 4次方系数
	 * @param input2 3次方系数
	 * @param input3 2次方系数
	 * @param input4 1次方系数
	 * @param input5 常数项系数
	 * @return
	 */
	@RequestMapping(value = "/equation")
	@ResponseBody
	@CrossOrigin
	public Object solveEquation(@RequestParam("input1") String input1,
			@RequestParam("input2") String input2,
			@RequestParam("input3") String input3,
			@RequestParam("input4") String input4,
			@RequestParam("input5") String input5) {
		EquationRet r = new EquationRet();
		System.out.println("solve equation "+input1+"x4+"+input2+"x3+"+input3+"x2+"+input4+"x+"+input5+"="+"......");
		try {
			Equation eq = new Equation();
			String s[] = eq.calEquation(Double.valueOf(Calculate.calculate(input1)),
					Double.valueOf(Calculate.calculate(input2)),
					Double.valueOf(Calculate.calculate(input3)),
					Double.valueOf(Calculate.calculate(input4)),
					Double.valueOf(Calculate.calculate(input5)) );
			r.setSuccess(true);
			r.addRes(s);
		}catch(InputIllegalException e) {
			r.setSuccess(false);
			r.setMessage(e.getMessage());
		}catch(MissingArguException e) {
			r.setSuccess(false);
			r.setMessage(e.getMessage());
		}catch(CalculateErrorException e) {
			r.setSuccess(false);
			r.setMessage(e.getMessage());
		}catch(Exception e) {
			r.setSuccess(false);
			r.setMessage(ERROR);
		}
		return r;
	}
	/**
	 * 计算复数表达式
	 * @param input
	 * @return
	 */
	@RequestMapping(value = "/calculatecomplex")
	@ResponseBody
	@CrossOrigin
	public Object calculateComplex(@RequestParam("input") String input) {
		input = input.replaceAll("\\s+", "");
		Ret r = new Ret();
		System.out.println("calculating complex "+input+"......");
		try {
			r.setSuccess(true);
			r.setResult(CalComplex.calculate(input));
		}catch(InputIllegalException e) {
			r.setSuccess(false);
			r.setMessage(e.getMessage());
		}catch(MissingArguException e) {
			r.setSuccess(false);
			r.setMessage(e.getMessage());
		}catch(CalculateErrorException e) {
			r.setSuccess(false);
			r.setMessage(e.getMessage());
		}catch(Exception e) {
			r.setSuccess(false);
			r.setMessage(ERROR);
		}
		return r;
	}
	
	/**
	 * 计算矩阵
	 * @param input1
	 * @param input2
	 * @param module
	 * @param input3
	 * @param input4
	 * @return
	 */
	@RequestMapping(value = "/calculatematrix")
	@ResponseBody
	@CrossOrigin
	public Object calculateMatrix(@RequestParam("input1") String input1,
			@RequestParam(value="input2",required = false) String input2, // 可以不传值
			@RequestParam("module") String module,
			@RequestParam(value="input3",required = false) String input3,
			@RequestParam(value="input4",required = false) String input4) {
		if(input1.contains(" "))
			input1 = Parentheses.matrixFormat(input1);
		if(input2!=null && input2.contains(" ") )
			input2 = Parentheses.matrixFormat(input2);
		
		System.out.println("calculating Matrix "+input1+" "+module+" "+input2+" ......"+input3+" "+input4);
		Ret mr = new Ret();
		if(input2!=null) {
			try {
				mr.setResult(CalMatrix.calMatrix(input1,input2,module) );
				if(mr.result.startsWith("E")) {
					mr.setSuccess(false);
					mr.setMessage(mr.result);
				}
				else
					mr.setSuccess(true);
			}catch(InputIllegalException e) {
				mr.setSuccess(false);
				mr.setMessage(e.getMessage());
			}catch(MissingArguException e) {
				mr.setSuccess(false);
				mr.setMessage(e.getMessage());
			}catch(CalculateErrorException e) {
				mr.setSuccess(false);
				mr.setMessage(e.getMessage());
			}catch(Exception e) {
				mr.setSuccess(false);
				mr.setMessage(ERROR);
			}
		}
		else {
			try {
				if(input1==null||input1.equals("")) {
					mr.setSuccess(false);
					mr.setMessage(MissingArguException.ERROR);
					return mr;
				}
				if(module.equals("5")) {
					if(input3==null||input4==null||input3.equals("")||input4.equals("")) {
						mr.setSuccess(false);
						mr.setMessage(MissingArguException.ERROR);
						return mr;
					}
					mr.setResult(CalMatrix.mxAlgebraicComplement(input1,input3,input4));
				}
				else
					mr.setResult(CalMatrix.calSingleMatrix(input1,module) );
				mr.setSuccess(true);
			}catch(InputIllegalException e) {
				mr.setSuccess(false);
				mr.setMessage(e.getMessage());
			}catch(MissingArguException e) {
				mr.setSuccess(false);
				mr.setMessage(e.getMessage());
			}catch(CalculateErrorException e) {
				mr.setSuccess(false);
				mr.setMessage(e.getMessage());
			}catch(DeterminantException e) {
				mr.setSuccess(false);
				mr.setMessage(e.getMessage());
			}catch(AlgebraicComplementException e) {
				mr.setSuccess(false);
				mr.setMessage(e.getMessage());
			}catch(Exception e) {
				mr.setSuccess(false);
				mr.setMessage(ERROR);
			}
		}
		
		return mr;
	}
	
	/**
	 * 向方程组中添加方程，检验方程的合法性
	 * @param input
	 * @return
	 */
	@RequestMapping(value = "/addnewequation")
	@ResponseBody
	@CrossOrigin
	public Object addNewEquation(@RequestParam("input") String input) {
		System.out.println("add equation "+input);
		try {
			CalEquations cal = new CalEquations();
			return cal.formatEquation(cal.checkEquation(input));
		}catch(Exception e) {
			return false;
		}
	}
	/**
	 * 计算方程组
	 * @param input
	 * @return
	 */
	@RequestMapping(value = "/solveequations")
	@ResponseBody
	@CrossOrigin
	public Object solveEquations(@RequestParam("input") String input) {
		System.out.println("solve "+input);
		try {
			String in[] = input.trim().split("\n");
			CalEquations ce = new CalEquations();
			for(String s:in) {
				ce.addEquation(s);
			}
			String ans = ce.solveEquations();
			System.out.println(ans);
			return ans;
		}catch(InputIllegalException e) {
			return e.getMessage();
		}catch(MissingArguException e) {
			return e.getMessage();
		}catch(CalculateErrorException e) {
			return e.getMessage();
		}catch(Exception e) {
			return ERROR;
		}
	}
	
	/**
	 * 求和
	 * @param input
	 * @param max
	 * @param min
	 * @return
	 */
	@RequestMapping(value = "/summation")
	@ResponseBody
	@CrossOrigin
	public Object calSummation(@RequestParam("input") String input,
			@RequestParam("max") String max,
			@RequestParam("min") String min) {
		input = input.replaceAll("\\s+", "");
		System.out.println("cal sum "+min+" - "+max+" "+input);
		Ret r = new Ret();
		try{
			String ans = Sigma.calSigma(min, max, input);
			r.setSuccess(true);
			r.setResult(ans);
		}catch(InputIllegalException e) {
			r.setSuccess(false);
			r.setMessage(e.getMessage());
		}catch(MissingArguException e) {
			r.setSuccess(false);
			r.setMessage(e.getMessage());
		}catch(CalculateErrorException e) {
			r.setSuccess(false);
			r.setMessage(e.getMessage());
		}catch(Exception e) {
			r.setSuccess(false);
			r.setMessage(ERROR);
		}
		return r;
	}
	
	/**
	 * 求导数
	 * @param input	求导函数
	 * @param x		求导的点的横坐标
	 * @return		导数值
	 */
	@RequestMapping(value = "/derivatives")
	@ResponseBody
	@CrossOrigin
	public Object calDerivatives(@RequestParam("input") String input,
			@RequestParam("x") String x) {
		input = input.replaceAll("\\s+", "");
		System.out.println("cal derivatives "+input+" at x="+x);
		DerRet r = new DerRet();
		try {
			String rp = "("+Double.valueOf(x)+")";
			r.setY(Double.valueOf(Calculate.calculate(input.replace("x", rp))) );
			r.setX(Double.valueOf(x));
			Derivatives d = new Derivatives(input,Double.valueOf(x));
			Derivatives.derivation(d);
			r.setResult(d.getDvalue());
			r.setSuccess(true);
			
		}catch(InputIllegalException e) {
			r.setSuccess(false);
			r.setMessage(e.getMessage());
		}catch(MissingArguException e) {
			r.setSuccess(false);
			r.setMessage(e.getMessage());
		}catch(CalculateErrorException e) {
			r.setSuccess(false);
			r.setMessage(e.getMessage());
		}catch(Exception e) {
			r.setSuccess(false);
			r.setMessage(ERROR);
		}
		return r;
	}
	/**
	 * 求积分
	 * @param input	积分函数
	 * @param left	积分下限
	 * @param right	积分上限
	 * @return		积分值
	 */
	@RequestMapping(value = "/integral")
	@ResponseBody
	@CrossOrigin
	public Object calIntegral(@RequestParam("input") String input,
			@RequestParam("left") String left,
			@RequestParam("right") String right) {
		input = input.replaceAll("\\s+", "");
		System.out.println("cal integral "+input+" between "+left+" to "+right);
		Ret r = new Ret();
		try {
			Integral i = new Integral(input,Double.valueOf(left),Double.valueOf(right));
			Integral.Integration(i);
			r.setSuccess(true);
			r.setResult(i.getSum());
		}catch(InputIllegalException e) {
			r.setSuccess(false);
			r.setMessage(e.getMessage());
		}catch(MissingArguException e) {
			r.setSuccess(false);
			r.setMessage(e.getMessage());
		}catch(CalculateErrorException e) {
			r.setSuccess(false);
			r.setMessage(e.getMessage());
		}catch(Exception e) {
			r.setSuccess(false);
			r.setMessage(ERROR);
		}
		return r;
	}
	
	/**
	 * 计算进制数
	 * @param input		输入的计算式
	 * @param base		输入进制
	 * @param outbase	输出进制
	 * @return			计算结果
	 */
	@RequestMapping(value = "/base")
	@ResponseBody
	@CrossOrigin
	public Object calBase(@RequestParam("input") String input,
			@RequestParam("base") String base,
			@RequestParam("outbase") String outbase) {
		input = input.replaceAll("\\s+", "");
		System.out.println("cal base "+input+" "+base);
		Ret r = new Ret();
		try {
			r.setSuccess(true);
			r.setResult(CalBase.calculate(input, Integer.valueOf(base), Integer.valueOf(outbase)) );
		}catch(InputIllegalException e) {
			r.setSuccess(false);
			r.setMessage(e.getMessage());
		}catch(MissingArguException e) {
			r.setMessage(e.getMessage());
			r.setSuccess(false);
		}catch(Exception e) {
			r.setMessage(ERROR);
			r.setSuccess(false);
		}
		return r;
	}
	
	/**
	 * 绘制函数图像
	 * @param input	输入的方程
	 * @param left	区间左限
	 * @param right	区间右限
	 * @param dt	精度
	 * @return		函数上的点集
	 */
	@RequestMapping(value = "/drawfunction")
	@ResponseBody
	@CrossOrigin
	public Object drawFunction(@RequestParam(value="input",required = false) String input,
			@RequestParam("left") String left,
			@RequestParam("right") String right,
			@RequestParam("dt") String dt) {
		System.out.println("Draw function "+input+" between "+left+" to "+right);
		FunctionRet r = new FunctionRet();
		try {
			input = input.replaceAll("\\s+", "");
			r.setSuccess(true);
			r.addFunction(Draw.drawFunction(input, left, right,dt));
		}catch(InputIllegalException e) {
			r.setSuccess(false);
			r.setMessage(e.getMessage());
		}catch(MissingArguException e) {
			r.setMessage(e.getMessage());
			r.setSuccess(false);
		}catch(Exception e) {
			r.setMessage("Error");
			r.setSuccess(false);
		}
		System.out.println(r.isSuccess());
		return r;
	}
}
