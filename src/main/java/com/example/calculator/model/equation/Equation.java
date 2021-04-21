package com.example.calculator.model.equation;

import java.util.Arrays;

import com.example.calculator.model.calculate.Calculate;

/**
 * 计算一元四次一下的方程
 * @author 李柯凡
 *
 */
public class Equation {
	/**
	 * 近似无穷小
	 */
	public static final Double INFINITY = new Double(1E-10);
	/**
	 * 默认保留位数
	 */
	public static final int CAP = 10;
	private String[] error = new String[1];
	private String[] linear = new String[1];
	private String[] quadratic = new String[2];
	private String[] cubic = new String[3];
	private String[] quartic = new String[4];
	public Equation() {
		error[0] = "Error";
	}
	/**
	 * 计算一次到四次方程，系数不存在则输入0
	 * @param a	四次方的系数
	 * @param b 三次方的系数
	 * @param c 二次方的系数
	 * @param d 一次方的系数
	 * @param e 常数项系数
	 * @return
	 * @throws Exception
	 */
	public String[] calEquation(double a, double b, double c, double d,double e) throws Exception {
		int num = solveQuartic(a,b,c,d,e);
		if(num == 0)
			return error;
		else if(num == 1) {
			return linear;
		}
		else if(num == 2) {
			return quadratic;
		}
		else if(num == 3) {
			return cubic;
		}
		else {
			return quartic;
		}
	}
	
	private double value(double d) {
		if(Math.abs(d) < INFINITY)
			return 0;
		return d;
	}
	/**
	 * 解四次方程
	 * @param a
	 * @param b
	 * @param c
	 * @param d
	 * @param e
	 * @return
	 * @throws Exception
	 */
	public int solveQuartic(double a,double b,double c,double d,double e) throws Exception{
		if(a != 0) {
			Double D = 3*b*b-8*a*c, F = 3*Math.pow(b, 4)+16*a*a*c*c-16*a*b*b*c+16*a*a*b*d-64*a*a*a*e,
					E = -Math.pow(b, 3) + 4*a*b*c - 8*a*a*d;
			Double A = D*D-3*F, B = D*F-9*E*E, C=F*F-3*D*E*E, dt = B*B-4*A*C;
			
			System.out.println("D = "+D+", E = "+E+", F = "+F+", A = "+A+", B = "+B+", C = "+C+", DT = "+dt);
			
			if( D.equals(E) && D.equals(F) && D.equals(new Double(0))) {
				double t = -b/(4*a);
				t = value(t);
				quartic[0] = Calculate.retainDec(String.valueOf(t),CAP);
				quartic[1] = quartic[0];
				quartic[2] = quartic[0];
				quartic[3] = quartic[0];
			}
			else if( A.equals(B) && A.equals(C) && A.equals(new Double(0)) && !((Double)(D*E*F)).equals(new Double(0)) ) {
				double t1 = (-b*D+9*E)/(4*a*D), t2 = (-b*D-3*E)/(4*a*D);
				t1 = value(t1);
				t2 = value(t2);
				quartic[0] = Calculate.retainDec(String.valueOf(t1),CAP);
				quartic[1] = Calculate.retainDec(String.valueOf(t2),CAP);
				quartic[2] = quartic[1];
				quartic[3] = quartic[1];
			}
			else if(E.equals(F) && E.equals(new Double(0)) && !D.equals(new Double(0)) ) {
				double t1, t2;
				if(D > 0) {
					t1 = (-b+Math.pow(D,0.5))/(4*a);
					t2 = (-b-Math.pow(D,0.5))/(4*a);
					t1 = value(t1);
					t2 = value(t2);
					quartic[0] = Calculate.retainDec(String.valueOf(t1),CAP);
					quartic[1] = quartic[0];
					quartic[2] = Calculate.retainDec(String.valueOf(t2),CAP);
					quartic[3] = quartic[2];
				}
				else {
					double  ti1 = Math.pow(-D,0.5)/(4*a), ti2 = -ti1;
					t1 = (-b)/(4*a);
					t1 = value(t1);
					ti1 = value(ti1);
					ti2 = value(ti2);
					
					String op1 = ti1>=0 ? "+":"", op2 = ti2>=0 ? "+":"";
					quartic[0] = Calculate.retainDec(String.valueOf(t1),CAP)+op1+
							Calculate.retainDec(String.valueOf(ti1),CAP)+"i";
					quartic[1] = quartic[0];
					quartic[2] = Calculate.retainDec(String.valueOf(t1),CAP)+op2+
							Calculate.retainDec(String.valueOf(ti2),CAP)+"i";
					quartic[3] = quartic[2];
				}
			}
			else if(dt.equals(new Double(0)) && !((Double)(A*B*C)).equals(new Double(0)) ) {
				double t1, t2, t3, ti3, t4, ti4;
				String op3 = "", op4 = "";
				t1 = (-b - Math.signum(A*B*E)*Math.sqrt(D-B/A))/(4*a);
				t2 = t1;
				t1 = value(t1);
				t2 = value(t2);
				quartic[0] = Calculate.retainDec(String.valueOf(t1),CAP);
				quartic[1] = Calculate.retainDec(String.valueOf(t2),CAP);
				if(A*B > 0) {
					t3 = (-b + Math.signum(A*B*E)*Math.sqrt(D-B/A)+Math.sqrt(2*B/A) )/(4*a);
					t4 = (-b + Math.signum(A*B*E)*Math.sqrt(D-B/A)-Math.sqrt(2*B/A) )/(4*a);
					t3 = value(t3);
					t4 = value(t4);
					quartic[2] = Calculate.retainDec(String.valueOf(t3),CAP);
					quartic[3] = Calculate.retainDec(String.valueOf(t4),CAP);
				}
				else {
					t3 = (-b + Math.signum(A*B*E)*Math.sqrt(D-B/A) )/(4*a); ti3 = Math.sqrt(-2*B/A); op3 = ti3>=0 ? "+":"";
					t4 = (-b + Math.signum(A*B*E)*Math.sqrt(D-B/A) )/(4*a); ti4 = -Math.sqrt(-2*B/A); op4 = ti4>=0 ? "+":"";
					t3 = value(t3); ti3 = value(ti3);
					t4 = value(t4); ti4 = value(ti4);
					
					quartic[2] = Calculate.retainDec(String.valueOf(t3),CAP)+op3+
							Calculate.retainDec(String.valueOf(ti3),CAP)+"i";
					quartic[3] = Calculate.retainDec(String.valueOf(t4),CAP)+op4+
							Calculate.retainDec(String.valueOf(ti4),CAP)+"i";
				}
			}
			else if(dt < 0) {
				if( E == 0 ) {
					if( D > 0 && F > 0 ) {
						double t1 = (-b + Math.sqrt(D + 2*Math.sqrt(F)))/(4*a);
						double t2 = (-b - Math.sqrt(D + 2*Math.sqrt(F)))/(4*a);
						double t3 = (-b + Math.sqrt(D - 2*Math.sqrt(F)))/(4*a);
						double t4 = (-b - Math.sqrt(D - 2*Math.sqrt(F)))/(4*a);
						t1 = value(t1);
						t2 = value(t2);
						t3 = value(t3);
						t4 = value(t4);
						quartic[0] = Calculate.retainDec(String.valueOf(t1),CAP);
						quartic[1] = Calculate.retainDec(String.valueOf(t2),CAP);
						quartic[2] = Calculate.retainDec(String.valueOf(t3),CAP);
						quartic[3] = Calculate.retainDec(String.valueOf(t4),CAP);
					}
					else {
						double t1 = (-b)/(4*a);
						double ti1 = Math.sqrt( -(D + 2*Math.sqrt(F)) )/(4*a);
						double ti2 = -ti1;
						double ti3 = Math.sqrt( -(D - 2*Math.sqrt(F)) )/(4*a);
						double ti4 = -ti3;
						t1 = value(t1);
						ti1 = value(ti1);
						ti2 = value(ti2);
						ti3 = value(ti3);
						ti4 = value(ti4);
						
						String op1 = ti1>=0 ? "+":"", op2 = ti2>=0 ? "+":"", op3 = ti3>=0 ? "+":"", op4 = ti4>=0 ? "+":"";
						quartic[0] = Calculate.retainDec(String.valueOf(t1),CAP)+op1+
								Calculate.retainDec(String.valueOf(ti1),CAP)+"i";
						quartic[1] = Calculate.retainDec(String.valueOf(t1),CAP)+op2+
								Calculate.retainDec(String.valueOf(ti2),CAP)+"i";
						quartic[2] = Calculate.retainDec(String.valueOf(t1),CAP)+op3+
								Calculate.retainDec(String.valueOf(ti3),CAP)+"i";
						quartic[3] = Calculate.retainDec(String.valueOf(t1),CAP)+op4+
								Calculate.retainDec(String.valueOf(ti4),CAP)+"i";
					}
				}
				else {
					double k = Math.acos((3*B-2*A*D)/(2*A*Math.sqrt(A)));
					double y1 = (D - 2*Math.sqrt(A)*Math.cos(k/3.0))/3.0;
					double y2 = (D + Math.sqrt(A)*(Math.cos(k/3.0) + Math.sqrt(3)*Math.sin(k/3.0)))/3.0;
					double y3 = (D + Math.sqrt(A) * (Math.cos(k / 3.0) - Math.sqrt(3)*Math.sin(k/3.0)))/3.0;
					
					if(D>0 && F>0) {
						double t1 = (-b + Math.signum(E)*Math.sqrt(y1) + (Math.sqrt(y2) + Math.sqrt(y3)) )/(4*a);
						double t2 = (-b + Math.signum(E)*Math.sqrt(y1) - (Math.sqrt(y2) + Math.sqrt(y3)) )/(4*a);
						double t3 = (-b - Math.signum(E)*Math.sqrt(y1) + (Math.sqrt(y2) - Math.sqrt(y3)) )/(4*a);
						double t4 = (-b - Math.signum(E)*Math.sqrt(y1) - (Math.sqrt(y2) - Math.sqrt(y3)) )/(4*a);
						t1 = value(t1);
						t2 = value(t2);
						t3 = value(t3);
						t4 = value(t4);
						quartic[0] = Calculate.retainDec(String.valueOf(t1),CAP);
						quartic[1] = Calculate.retainDec(String.valueOf(t2),CAP);
						quartic[2] = Calculate.retainDec(String.valueOf(t3),CAP);
						quartic[3] = Calculate.retainDec(String.valueOf(t4),CAP);
					}
					else {
						double t1 = (-b + Math.sqrt(y2))/(4*a), t2 = t1;
						double ti1 = (Math.signum(E)*Math.sqrt(-y1) - Math.sqrt(-y3))/(4*a);
						double ti2 = -ti1;
						double t3 = (-b - Math.sqrt(y2))/(4*a), t4 = t3;
						double ti3 = (Math.signum(E)*Math.sqrt(-y1) + Math.sqrt(-y3))/(4*a);
						double ti4 = -ti3;
						t1 = value(t1); t2 = value(t2); t3 = value(t3); t4 = value(t4);
						ti1 = value(ti1);
						ti2 = value(ti2);
						ti3 = value(ti3);
						ti4 = value(ti4);
						String op1 = ti1>=0 ? "+":"", op2 = ti2>=0 ? "+":"", op3 = ti3>=0 ? "+":"", op4 = ti4>=0 ? "+":"";
						quartic[0] = Calculate.retainDec(String.valueOf(t1),CAP)+op1+
								Calculate.retainDec(String.valueOf(ti1),CAP)+"i";
						quartic[1] = Calculate.retainDec(String.valueOf(t2),CAP)+op2+
								Calculate.retainDec(String.valueOf(ti2),CAP)+"i";
						quartic[2] = Calculate.retainDec(String.valueOf(t3),CAP)+op3+
								Calculate.retainDec(String.valueOf(ti3),CAP)+"i";
						quartic[3] = Calculate.retainDec(String.valueOf(t4),CAP)+op4+
								Calculate.retainDec(String.valueOf(ti4),CAP)+"i";
					}
				}
			}
			else if(dt > 0) {
				double z1 = A*D+3*((-B+Math.sqrt(dt))/2.0), z2 = A*D+3*((-B-Math.sqrt(dt))/2.0);
				
				double z = D*D - D*(Math.signum(z1)*Math.pow(Math.abs(z1), 1.0/3.0) + Math.signum(z2)*Math.pow(Math.abs(z2), 1.0/3.0)) +
						Math.pow((Math.signum(z1)*Math.pow(Math.abs(z1), 1.0/3.0) + Math.signum(z2)*Math.pow(Math.abs(z2), 1.0/3.0)), 2) - 3*A;
				double t1 = (-b + Math.signum(E)*Math.sqrt((D + Math.signum(z1)*Math.pow(Math.abs(z1), 1.0/3.0) + Math.signum(z2)*Math.pow(Math.abs(z2), 1.0/3.0))/3.0)
						+ Math.sqrt((2*D - Math.signum(z1)*Math.pow(Math.abs(z1), 1.0/3.0) - Math.signum(z2)*Math.pow(Math.abs(z2), 1.0/3.0)
								+ 2*Math.sqrt(z))/3.0))/(4*a);
				double t2 = (-b + Math.signum(E)*Math.sqrt((D + Math.signum(z1)*Math.pow(Math.abs(z1), 1.0/3.0) + Math.signum(z2)*Math.pow(Math.abs(z2), 1.0/3.0))/3.0)
						- Math.sqrt((2*D - Math.signum(z1)*Math.pow(Math.abs(z1), 1.0/3.0) - Math.signum(z2)*Math.pow(Math.abs(z2), 1.0/3.0)
								+ 2*Math.sqrt(z))/3.0))/(4*a);
				
				double t3 = (-b - Math.signum(E)*Math.sqrt((D + Math.signum(z1)*Math.pow(Math.abs(z1), 1.0/3.0) + Math.signum(z2)*Math.pow(Math.abs(z2), 1.0/3.0))/3.0) )/(4*a);
				
				double ti3 = ( Math.sqrt( (-2*D + Math.signum(z1)*Math.pow(Math.abs(z1), 1.0/3.0) + Math.signum(z2)*Math.pow(Math.abs(z2), 1.0/3.0) + 2*Math.sqrt(z))/3.0) )
						/(4*a);
				double ti4 = -ti3;
				t1 = value(t1);
				t2 = value(t2);
				t3 = value(t3); ti3 = value(ti3); ti4 = value(ti4);
				String op3 = ti3>=0 ? "+":"", op4 = ti4>=0 ? "+":"";
				quartic[0] = Calculate.retainDec(String.valueOf(t1),CAP);
				quartic[1] = Calculate.retainDec(String.valueOf(t2),CAP);
				quartic[2] = Calculate.retainDec(String.valueOf(t3),CAP)+op3+
						Calculate.retainDec(String.valueOf(ti3),CAP)+"i";
				quartic[3] = Calculate.retainDec(String.valueOf(t3),CAP)+op4+
						Calculate.retainDec(String.valueOf(ti4),CAP)+"i";
			}
			else {
				return 0;
			}
			return 4;
		}
		
		else {
			return solveCubic(b,c,d,e);
		}
	}
	/**
	 * 三次方程
	 * @param a
	 * @param b
	 * @param c
	 * @param d
	 * @return
	 * @throws ArithmeticException
	 */
	public int solveCubic(double a, double b, double c, double d) throws Exception{
		String t1 = "", t2 = "", t3 = "";
		if (a != 0) {
			double A = b * b - 3 * a * c;
			double B = b * c - 9 * a * d;
			double C = c * c - 3 * b * d;
			double D = B * B - 4 * A * C;
			if(A == 0 && B == 0) {
				double t=0;
				if(b!=0)
					t = -c/b;
				else if(a!=0)
					t = -b/(3*a);
				else if(c!=0)
					t = -3*d/c;
				t = value(t);
				t1 = String.valueOf(t);
				t2 = t1;
				t3 = t1;
			}
			else {
				if(D > 0) {
					double Y1 = A*b+3*a*(-B + Math.sqrt(D))/2.0;
					double Y2 = A*b+3*a*(-B - Math.sqrt(D))/2.0;
					if(Y1 < 0) {
						Y1 = -Math.pow(Math.abs(Y1), 1.0/3.0);
					}
					else {
						Y1 = Math.pow(Math.abs(Y1), 1.0/3.0);
					}
					if(Y2 < 0) {
						Y2 = -Math.pow(Math.abs(Y2), 1.0/3.0);
					}
					else {
						Y2 = Math.pow(Math.abs(Y2), 1.0/3.0);
					}
					double p =Y1 + Y2;
					double x1 = (-2*b+p)/(6*a);
					double x2 = Math.pow(3, 0.5)*(Y1-Y2)/(6*a);
					String op1 = x2>=0 ? "+":"", op2 = x2>=0 ? "":"+";
					t1 = Calculate.retainDec(String.valueOf(value((-b-p)/(3*a))),CAP);
					t2 = Calculate.retainDec(String.valueOf(value(x1)),CAP)+op1+
							Calculate.retainDec(String.valueOf(value(x2)),CAP)+"i";
					t3 = Calculate.retainDec(String.valueOf(value(x1)),CAP)+op2+
							Calculate.retainDec(String.valueOf(value(-x2)),CAP)+"i";
				}
				else if (D == 0) {
					double K = B / A;
					t1 = String.valueOf(value(-b / a + K));
					t2 = String.valueOf(value(-K / 2));
					t3 = t2;
				}
				else{
					double T = (2 * A * b - 3 * a * B) / (2 * Math.sqrt(A * A * A));
					double q = Math.acos(T);
					double q3 = q / 3.0;
					t1 = Calculate.retainDec(String.valueOf( value((-b - 2 * Math.sqrt(A) * Math.cos(q3)) / (3 * a)) ),CAP);
					t2 = Calculate.retainDec(String.valueOf( value((-b + Math.sqrt(A) * Math.cos(q3) - Math.sqrt(A) * Math.sqrt(3) * Math.sin(q3)) / (3 * a)) ),CAP);
					t3 = Calculate.retainDec(String.valueOf( value((-b + Math.sqrt(A) * Math.cos(q3) + Math.sqrt(A) * Math.sqrt(3) * Math.sin(q3)) / (3 * a)) ),CAP);
				}
			}
			
			cubic[0] = t1;
			cubic[1] = t2;
			cubic[2] = t3;
			
			return 3;
		}
		else{
			return solveQuadratic(b,c,d);
		}
	}

	/**
	 * 二次方程
	 * @param a
	 * @param b
	 * @param c
	 * @return
	 * @throws ArithmeticException
	 */
	public int solveQuadratic(double a,double b,double c) throws Exception{
		double t1=0,t2=0;
		if(a != 0){
			double dt = b*b - 4*a*c;
			if(dt < 0){
				dt = -dt;
				double x1 = -b/(2*a);
				double x2 = Math.pow(dt, 1.0/2)/(2*a);
				double x3 = -x2;				
				String op1 = x2>=0 ? "+":"", op2 = x3>=0 ? "+":"";
				quadratic[0] = Calculate.retainDec(String.valueOf(value(x1)),CAP)+op1+
						Calculate.retainDec(String.valueOf(value(x2)),CAP)+"i";
				quadratic[1] = Calculate.retainDec(String.valueOf(value(x1)),CAP)+op2+
						Calculate.retainDec(String.valueOf(value(x3)),CAP)+"i";
				return 2;
			}
			else if(dt == 0){
				t1 = t2 = -b/(2*a);
			}
			else{
				t1= (-b + Math.pow(dt, 1.0/2))/(2*a);
				t2= (-b - Math.pow(dt, 1.0/2))/(2*a);
			}
			double jg[]={t1,t2};
			Arrays.sort(jg);
			quadratic[0] = Calculate.retainDec(String.valueOf(value(jg[0])),CAP);
			quadratic[1] = Calculate.retainDec(String.valueOf(value(jg[1])),CAP);
			return 2;
		}
		else{
			return solveLinear(b,c);
		}
	}
    
	/**
	 * 一次方程
	 * @param a
	 * @param b
	 * @return
	 * @throws ArithmeticException
	 */
	public int solveLinear(double a,double b) throws Exception{
		double t1=0;
		if(b == 0) {
			linear[0] = "0";
			return 1;
		}
		else {
			if(a != 0) {
				t1=-b/a;
				linear[0] = Calculate.retainDec(String.valueOf(value(t1)),CAP);
				return 1;
			}
			else {
				return 0;
			}
		}
	}

	public String[] getError() {
		return error;
	}

	public void setError(String[] error) {
		this.error = error;
	}

	public String[] getLinear() {
		return linear;
	}

	public void setLinear(String[] linear) {
		this.linear = linear;
	}

	public String[] getQuadratic() {
		return quadratic;
	}

	public void setQuadratic(String[] quadratic) {
		this.quadratic = quadratic;
	}

	public String[] getCubic() {
		return cubic;
	}

	public void setCubic(String[] cubic) {
		this.cubic = cubic;
	}

	public String[] getQuartic() {
		return quartic;
	}

	public void setQuartic(String[] quartic) {
		this.quartic = quartic;
	}
    
    
}
