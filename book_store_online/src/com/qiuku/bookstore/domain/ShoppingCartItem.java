package com.qiuku.bookstore.domain;

/**
 * 封装了购物车中的商品, 包含对商品的引用以及购物车中该商品的数量
 * 对应于购物车中商品项的实体类
 */
public class ShoppingCartItem {
	// 实体类的属性
	private Book book;
	private int quantity;
	
	
	// 有参构造方法
	public ShoppingCartItem(Book book) {
		this.book = book;
		this.quantity = 1;
	}
	
	
	// 只读
	public Book getBook() {
		return book;
	}
	// 可读可写
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
	/**
	 * 返回该商品的价格 * 数量
	 * @return
	 */
	public float getItemMoney(){
		return book.getPrice() * quantity;
	}
	
	/**
	 * 使商品数量 + 1
	 */
	public void increment(){
		quantity++;
	}
	
}
