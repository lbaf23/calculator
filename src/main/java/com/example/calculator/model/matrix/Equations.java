package com.example.calculator.model.matrix;


/**
 * 计算方程组
 * @author 刘禹辰
 *
 */
public class Equations {
	private double equations[][];
	private int num;
	private String result[];
	/**
	 * 
	 */
	public static final int CAP = 10;
	/**
	 * 构建方程组
	 * @param equations 一行一个方程
	 */
	public Equations(double equations[][]){
		this.equations=equations;
		num=equations[0].length-1;
	}
	
	public double[][] getEquations() {
		return equations;
	}
	
	public void setEquations(double[][] equations) {
		this.equations = equations;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}
	/**
	 * 获取结果
	 * @return 计算结果
	 * @throws Exception 
	 */
	public String getResult() {
		StringBuffer s = new StringBuffer();
		for(int i=0; i<result.length; i++) {
			s.append(result[i]);
			s.append("\n");
		}
		return s.toString();
	}

	public void setResult(String[] result) {
		this.result = result;
	}
	/**
	 * 解方程组
	 * @param e 方程组
	 * @throws Exception
	 */
	public static void solveEquations(Equations e) throws Exception {
		Matrix m=new Matrix(e.equations);
		Matrix s=Matrix.mxSimplification(m);
		String result[]=new String[e.getNum()];
		for(int i=0;i<result.length;i++)
			result[i]="";
		for(int i=0;i<s.getWidth();i++){
			int sign=0;
			int ss=0;
			for(int j=0;j<s.getLength()-1;j++) {
				if(s.getMatrix()[i][j]!=0) {
					sign++;
					if(sign==1)
						ss=j;
				}
			}
			if(sign==1) {
				char xi=(char)('a'+ss);
				result[i]=xi+" = "+String.format("%.10f",(s.getMatrix()[i][s.getLength()-1]));
				
			}
			else if(sign>1) {
				int x=0;
				for(int j=0;j<s.getLength()-1;j++) {
					char xi=(char)('a'+j);
					if(s.getMatrix()[i][j]!=0) {
						if(result[i].isEmpty()) {
							result[i]+=xi+" = ";
							x=0;
						}
						else {
							if(s.getMatrix()[i][j]!=-1&&s.getMatrix()[i][j]!=1) {
								if(x==0)
									result[i]+=String.format("%.10f", -s.getMatrix()[i][j])+xi;
								else {
									if(s.getMatrix()[i][j]>0)
										result[i]+="-"+String.format("%.10f",s.getMatrix()[i][j])+xi;
									else
										result[i]+="+"+String.format("%.10f",-s.getMatrix()[i][j])+xi;
								}
								x++;
							}
							else {
								if(x==0) {
									if(s.getMatrix()[i][j]>0)
										result[i]+="-"+xi;
									else
										result[i]+=xi;
								}
								else {
									if(s.getMatrix()[i][j]>0)
										result[i]+="-"+xi;
									else
										result[i]+="+"+xi;
								}
								x++;
							}
						}
					}
				}
				if(s.getMatrix()[i][s.getLength()-1]>0)
					result[i]=result[i]+"+"+String.format("%.10f",s.getMatrix()[i][s.getLength()-1]);
				else if(s.getMatrix()[i][s.getLength()-1]<0)
					result[i]=result[i]+String.format("%.10f", s.getMatrix()[i][s.getLength()-1]);
			}
		}
		e.setResult(result);
	}
	
}
