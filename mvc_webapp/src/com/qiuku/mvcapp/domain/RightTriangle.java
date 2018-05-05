package com.qiuku.mvcapp.domain;

/**
 * @TODO:RightTriangle.java
 * @author:QIUKU
 */
public class RightTriangle {
	
	// 直角三角形的底长
	public float L;
	// 直角三角形的高
	public float H;
	// 直角三角形内相似三角形的底长
	public float Ln;
	// 需要计算的相似三角形的高,即所求柱子的高度
	public float Hn;



	public float getL() {
		return L;
	}
	public void setL(float L) {
		this.L = L;
	}


	public float getH() {
		return H;
	}
	public void setH(float H) {
		this.H = H;
	}


	public float getLn() {
		return Ln;
	}
	public void setLn(float Ln) {
		this.Ln = Ln;
	}


	public float getHn() {
		return Hn = H * Ln / L;
	}
	

	/**
	 * Constructor
	 * @param l2
	 * @param h2
	 */
	public RightTriangle(float L, float H) {
		// TODO Auto-generated constructor stub
		this.L = L;
		this.H = H;
	}

}
