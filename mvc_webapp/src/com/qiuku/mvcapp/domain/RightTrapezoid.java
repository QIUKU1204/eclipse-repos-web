package com.qiuku.mvcapp.domain;

/**
 * @TODO:RightTrapezoid.java
 * @author:QIUKU
 */
public class RightTrapezoid {
	// 直角梯形的上底
	public float H1;
	// 直角梯形的下底
	public float H2;
	// 直角梯形的高
	public float L;
	// 直角梯形内小梯形的高
	public float Ln;
	// 需要计算的小梯形的下底,即所求柱子的高度
	public float Hn;
	
	
	public float getH1() {
		return H1;
	}
	public void setH1(float H1) {
		this.H1 = H1;
	}
	
	
	public float getH2() {
		return H2;
	}
	public void setH2(float H2) {
		this.H2 = H2;
	}
	
	
	public float getL() {
		return L;
	}
	public void setL(float L) {
		this.L = L;
	}
	
	
	public float getLn() {
		return Ln;
	}
	public void setLn(float Ln) {
		this.Ln = Ln;
	}
	
	
	public float getHn() {
		return Hn = (H1*L - H1*Ln + H2*Ln) / L;
	}
	
	
	public RightTrapezoid(float H1, float H2, float L) {
		this.H1 = H1;
		this.H2 = H2;
		this.L = L;
	}

}
