package com.qiuku.bookstore.web;

/**
 * 封装查询条件的CriteriaBook类
 */
public class CriteriaBook {
	// 实体类的属性
	private float minPrice = 0;
	private float maxPrice = Integer.MAX_VALUE;
	private int pageNo;

	
	// 有参构造方法
	public CriteriaBook(float minPrice, float maxPrice, int pageNo) {
		super();
		this.minPrice = minPrice;
		this.maxPrice = maxPrice;
		this.pageNo = pageNo;
	}

		
	// 属性的getter、setter方法
	public float getMinPrice() {
		return minPrice;
	}
	public void setMinPrice(float minPrice) {
		this.minPrice = minPrice;
	}

	public float getMaxPrice() {
		return maxPrice;
	}
	public void setMaxPrice(float maxPrice) {
		this.maxPrice = maxPrice;
	}

	public int getPageNo() {
		return pageNo;
	}
	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

}
