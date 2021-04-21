package com.example.calculator.model.matrix;

import com.example.calculator.model.calculate.Calculate;
import com.example.calculator.model.exceptions.DeterminantException;

public class Matrix {
	private int length;
	private int width;
	private int rank;
	private double matrix[][];
	public Matrix(double[][] matrix) throws Exception{
		int max=matrix[0].length;
		for(int i=1;i<matrix.length;i++) {
			if(matrix[i].length!=max)
				throw new Exception();
		}
		this.matrix=matrix;
		this.length=matrix[0].length;
		this.width=matrix.length;
		this.rank=-1;
	}
	public Matrix (String matrix) throws Exception{
		char ch[]=matrix.toCharArray();
		int max=0,sign=0,width=0;
		for(int i=0;i<ch.length;i++) {
			if(ch[i]==' ')
				sign++;
			else if(ch[i]==';') {
				width++;
				if(max==0)
					max=sign;
				else {
					if(max!=sign)
						throw new Exception();
				}
				sign=0;
			}
		}
		String s[]=matrix.substring(0,matrix.length()).split("[\\s+ ， ;]");
		double d[] = new double[s.length];
		for(int i=0;i<s.length;i++) {
			try {
				d[i]=Double.valueOf(s[i]);
			}catch(NumberFormatException e) {
				throw new Exception();
			}
		}
		double mx[][]=new double[width+1][max+1];
		int k=0;
		for(int i=0;i<width+1;i++) {
			for(int j=0;j<max+1;j++) {
				mx[i][j]=d[k++];
			}
		}
		this.matrix=mx;
		length=mx[0].length;
		this.width=mx.length;
		this.rank=-1;
	}
	public int getLength() {
		return length;
	}
	public void setLength(int length) {
		this.length = length;
	}
	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
		this.width = width;
	}
	public double[][] getMatrix() {
		return matrix;
	}
	public void setMatrix(double[][] matrix) {
		this.matrix = matrix;
	}
	public int getRank() {
		return rank;
	}
	public void setRank(int rank) {
		this.rank = rank;
	}
	@Override
	public String toString() {
		StringBuffer st = new StringBuffer();
		for(int i=0; i<width; i++) {
			for(int j=0; j<length; j++) {
				matrix[i][j] = Math.round(matrix[i][j]*10000000000.0)/10000000000.0;
				st.append(String.valueOf(matrix[i][j]));
				if(j==length-1)
					st.append("\n");
				else
					st.append("  \t");
			}
		}
		return st.toString();
	}
	public static Matrix E(int l) throws Exception{
		double[][] d = new double[l][l];
		for(int i=0; i<l; i++) {
			d[i][i] = 1;
		}
		return new Matrix(d);
	}
	//矩阵加法
	public static Matrix mxAddition(Matrix a,Matrix b) throws Exception{
		if(a.width!=b.width||a.length!=b.length) {
			throw new Exception();
		}
		for(int i=0;i<a.width;i++) {
			for(int j=0;j<a.length;j++) {
				a.matrix[i][j]+=b.matrix[i][j];
			}
		}
		return a;
	}
	// 矩阵加数字
	public static Matrix add(Matrix a,double b) throws Exception {
		Matrix ret = new Matrix(a.matrix);
		for(int i=0; i<a.width; i++) {
			for(int j=0; j<a.length; j++) {
				ret.matrix[i][j] = a.matrix[i][j] + b;
			}
		}
		return ret;
	}
	//矩阵减法
	public static Matrix mxSubtraction(Matrix a,Matrix b) throws Exception{
		if(a.width!=b.width||a.length!=b.length) {
			throw new Exception();
		}
		for(int i=0;i<a.width;i++) {
			for(int j=0;j<a.length;j++) {
				a.matrix[i][j]-=b.matrix[i][j];
			}
		}
		return a;
	}
	// 矩阵减数字
		public static Matrix sub(Matrix a,double b) throws Exception {
			Matrix ret = new Matrix(a.matrix);
			for(int i=0; i<a.width; i++) {
				for(int j=0; j<a.length; j++) {
					ret.matrix[i][j] = a.matrix[i][j] - b;
				}
			}
			return ret;
		}
	// 矩阵乘法
	public static Matrix mxMultiplication(Matrix a,Matrix b) throws Exception{
		if(a.width!=b.length) {
			throw new Exception();
		}
		double c[][]=new double[a.width][b.length];
		for(int i=0;i<a.width;i++) {
			for(int j=0;j<b.length;j++) {
				for(int k=0;k<a.length;k++) {
					c[i][j]+=a.matrix[i][k]*b.matrix[k][j];
				}
			}
		}
		return new Matrix(c);
	}
	// 矩阵乘数字
	public static Matrix multiply(Matrix a,double b) throws Exception {
		Matrix ret = new Matrix(a.matrix);
		for(int i=0; i<a.width; i++) {
			for(int j=0; j<a.length; j++) {
				ret.matrix[i][j] = a.matrix[i][j] * b;
			}
		}
		return ret;
	}
	// 矩阵除法
	public static Matrix mxDivide(Matrix a,Matrix b) throws Exception{
		return mxMultiplication(a,mxTransposition(b));
	}
	// 矩阵除以数字
	public static Matrix divide(Matrix a,double b) throws Exception{
		return multiply(a,1.0/b);
	}
	// 矩阵的数字次幂
	public static Matrix pow(Matrix a,int b) throws Exception{
		if(b==0)
			return E(Math.max(a.length, a.width));
		if(b==1)
			return a;
		Matrix ret = Matrix.mxMultiplication(a, a);
		for(int i=1; i<b; i++) {
			ret = Matrix.mxMultiplication(ret, a);
		}
		return ret;
	}
	//矩阵转置
	public static Matrix mxTransposition(Matrix a) throws Exception{
		double b[][]=new double[a.length][a.width];
		for(int i=0;i<b.length;i++) {
			for(int j=0;j<b[0].length;j++) {
				b[i][j]=a.matrix[j][i];
			}
		}
		return new Matrix(b);
	}
	// 求行列式
	public static double mxDeterminant(Matrix a) throws Exception {
		if (a.length !=a.width) { 
			throw new Exception();
		}			
        if (a.length == 1)                                                      
            return a.matrix[0][0];
        if (a.length==2){
            return a.matrix[a.length-1][a.length-1]*a.matrix[a.length-2][a.length-2] - a.matrix[a.length-1][a.length-2]*a.matrix[a.length-2][a.length-1];   // 如果是二阶行列式,计算后返回
        }else{                                                      
            int sum = 0;
            for (int i = 0;i<a.length ; i++) {
                if (a.matrix[0][i]!=0) {                                     
                    sum += a.matrix[0][i] * mxDeterminant(mxAlgebraicComplement(a,0,i)) * Math.pow(-1, i);
                }
            }
            return sum;
        }
	}
	// 求代数余子式
	public static Matrix mxAlgebraicComplement(Matrix a,int row,int col) throws Exception{
		if (a.length !=a.width) { 
			throw new Exception();
		}
        double[][] b = new double[a.length-1][a.width-1];
        for (int i = 0; i <row ;i++){
            for (int j = 0; j < col; j++){
                b[i][j] = a.matrix[i][j];
            }
            for (int j = col; j < a.length-1; j++){
                b[i][j] = a.matrix[i][j+1];
            }
        }
        for (int i = row; i < a.length-1;i++){
            for (int j = 0; j < col; j++){
                b[i][j] = a.matrix[i+1][j];
            }
            for (int j = col; j < a.length-1; j++){
                b[i][j] = a.matrix[i+1][j+1];
            }
        }
        return new Matrix(b);
    }
	// 求逆矩阵
	public static Matrix mxInversion(Matrix a) throws Exception{
		if (a.length !=a.width) { 
			throw new Exception();
		}
		double[][] b = mxAdjoin(a).matrix;
        double value =  mxDeterminant(a);
        if(value==0) {
        	throw new DeterminantException();
        }
        for (int i = 0; i < a.length; i++){
            for (int j = 0; j < a.length; j++){
                b[i][j] /= value;
            }
        }
        return new Matrix(b);
	}
	// 求伴随矩阵
	public static Matrix mxAdjoin(Matrix a) throws Exception{
		if (a.length !=a.width) { 
			throw new Exception();
		}
        double[][] b = new double[a.length][a.length];
        for (int i = 0; i < a.length; i++){
            for (int j = 0; j < a.length; j++){
               b[j][i] = mxDeterminant(mxAlgebraicComplement(a,i,j))*(double)Math.pow(-1,i+j+2);
            }
        }
        return new Matrix(b);
	}
	// 化成阶梯型&&求秩
	public static Matrix mxStep(Matrix a,int row) throws Exception{
		int num1=0,m,n=a.width;
		for(m=row;m<n;m++) {
			num1=0;
			for(int i=0;i<a.length;i++) {
				if(a.matrix[m][i]==0) {
					num1++;
				}
				else {
					break;
				}
			}
			if(num1>m) {
				int num2=0,max=0,i;
				for(i=m+1;i<a.width;i++) {
					for(int j=0;j<a.length;j++) {
						if(a.matrix[i][j]==0)
							num2++;
						else 
							break;
					}
					if(num2<num1) {
						max=i;
						num1=num2;
					}
					num2=0;
				}
				if(max!=0) {
					double change;
					for(int k=0;k<a.length;k++) {
						change=a.matrix[max][k];
						a.matrix[max][k]=a.matrix[m][k];
						a.matrix[m][k]=change;
					}
					
				}
			}
		}
		int sign2=0;
		for(int i=1;i<a.width;i++) {
			for(int j=0;j<i&&j<a.length;j++) {
				if(a.matrix[i][j]!=0) {
					sign2=1;
					break;
				}
			}
			if(sign2==1)
				break;
		}
		if(sign2==0) {
			for(int i=0;i<a.width;i++) {
				for(int j=i;j<a.length;j++) {
					if(a.matrix[i][j]!=0) {
						double dev=a.matrix[i][j];
						for(int k=j;k<a.length;k++) {
							a.matrix[i][k]=a.matrix[i][k]/dev;
						}
						break;
					}
				}
			}
			int num0=0,rank=0;
			for(int i=0;i<a.width;i++) {
				for(int j=0;j<a.length;j++) {
					if(a.matrix[i][j]==0)
						num0++;
					if(a.matrix[i][j]==-0)
						a.matrix[i][j]=0;
				}
				if(num0!=a.length) {
					rank++;
				}
				num0=0;
			}
			a.rank=rank;
			return a;
		}
		else {
			double b[][]=new double[a.width][a.length];
			double c[][]=new double[a.width][a.length];
			for(int i=0;i<a.width;i++) {
				for(int j=0;j<a.length;j++) {
					b[i][j]=a.matrix[i][j];
					c[i][j]=a.matrix[i][j];
				}
			}
			for(int i=row+1;i<a.width;i++) {
				for(int j=row;j<a.length;j++) {
					if(a.matrix[i][row]!=0) {
						b[i-1][j]=a.matrix[i-1][j]*a.matrix[i][row];
						b[i][j]=a.matrix[i][j]*a.matrix[i-1][row]-b[i-1][j];
						c[i][j]=b[i][j];
					}
				}
			}
			return mxStep(new Matrix(c),row+1);
		}
	}
	// 化成行最简&&求秩
	public static Matrix mxSimplification(Matrix a) throws Exception {
		Matrix d= mxStep(a,0);
		double b[][]=d.matrix;
		double c[]=new double[a.length];
		int i,j;
		for(i=0;i<a.width;i++) {
			for(j=0;j<a.length;j++) {
				if(b[i][j]!=0)
					break;
			}
			for(int m=0;m<a.length;m++)
				c[m]=0;
			for(int l=0;l<a.width;l++) {
				if(l!=i) {
					for(int k=j;k<a.length;k++) {
						c[k]=b[i][k]*b[l][j];
					}
					for(int m=0;m<a.length;m++) {
						b[l][m]-=c[m];
					}
				}
			}
		}
		for(i=0;i<d.width;i++) {
			for(j=0;j<d.length;j++) {
				if(b[i][j]==-0)
					b[i][j]=0;
			}
		}
		d.setMatrix(b);
		return d;
	}
	
	public static boolean isMatrix(String input) {
		try {
			valueOf(input);
			return true;
		}catch(Exception e) {
			return false;
		}
	}
	
	// 输入格式：[1 2 3;3  3 4;3 4 4]
	public static Matrix valueOf(String input) throws Exception {
		if(input.charAt(0)!='[' || input.charAt(input.length()-1)!=']' ) {
			throw new Exception();
		}
		input = input.substring(1,input.length()-1).trim();
		String[] in = input.split(";");
		int w = in[0].split("\\s+").length;
		double[][] m = new double[in.length][w];
		for(int i=0; i<in.length; i++) {
			in[i] = in[i].trim();
			String[] line = in[i].split("\\s+");
			if(line.length!=w)
				throw new Exception();
			for(int j=0; j<line.length; j++) {
				String str = Calculate.calculate(line[j].trim());
				m[i][j] = Double.valueOf(str);
			}
		}
		return new Matrix(m);
	}
	//计算矩阵的迹
	public static double mxTrace(Matrix a) throws Exception {
		if (a.length !=a.width) { 
			throw new Exception();
		}
		double tr=0;
		for(int i=0;i<a.length;i++) {
			tr+=a.getMatrix()[i][i];
		}
		return tr;
	}
	
	
	
}