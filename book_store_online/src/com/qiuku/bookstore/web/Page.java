package com.qiuku.bookstore.web;

import java.util.List;
/**
 * 封装翻页信息的Page类
 */
public class Page<T> {

	// 当前页的页码
	private int pageNo;
	// 当前页需要显示的Book List
	private List<T> list;
	// 每页显示多少条记录
	private int pageSize = 3;
	// 总记录数
	private long totalItemNumber;
	
	
	// 构造器：初始化pageNo
	public Page(int pageNo) {
		super();
		this.pageNo = pageNo;
	}
	
	
	// 需要校验一下
	public int getPageNo() {
		if(pageNo <= 0)
			pageNo = 1;	
		if(pageNo > getTotalPageNumber()){
			pageNo = getTotalPageNumber();
		}
		return pageNo;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setList(List<T> list) {
		this.list = list;
	}
	public List<T> getList() {
		return list;
	}
	public void setTotalItemNumber(long totalItemNumber) {
		this.totalItemNumber = totalItemNumber;
	}
	
	
	// 获取总页码数
	public int getTotalPageNumber(){
		int totalPageNumber = (int)totalItemNumber / pageSize;	
		if(totalItemNumber % pageSize != 0){
			totalPageNumber++;
		}	
		return totalPageNumber;
	}
	
	// 与上一页下一页相关的方法
	public boolean isHasNext(){
		if(getPageNo() < getTotalPageNumber()){
			return true;
		}	
		return false;
	}
	
	public boolean isHasPrev(){
		if(getPageNo() > 1){
			return true;
		}	
		return false;
	}
	
	public int getPrevPage(){
		if(isHasPrev()){
			return getPageNo() - 1;
		}
		return getPageNo();
	}
	
	public int getNextPage(){
		if(isHasNext()){
			return getPageNo() + 1;
		}
		return getPageNo();
	}
}
