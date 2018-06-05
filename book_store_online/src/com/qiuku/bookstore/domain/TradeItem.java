package com.qiuku.bookstore.domain;
/**
 * @TODO: TradeItem(交易项)实体类 
 * @author:QIUKU
 */
public class TradeItem {
	// 实体类属性
	private Integer tradeItemId;
	private Integer bookId;
	private int quantity;
	private Integer tradeId;
	
	
	private Book book;
	public void setBook(Book book) {
		this.book = book;
	}
	public Book getBook() {
		return book;
	}
	
	
	// 属性的getter、setter方法
	public Integer getTradeItemId() {
		return tradeItemId;
	}
	public void setTradeItemId(Integer tradeItemId) {
		this.tradeItemId = tradeItemId;
	}
	public Integer getBookId() {
		return bookId;
	}
	public void setBookId(Integer bookId) {
		this.bookId = bookId;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public Integer getTradeId() {
		return tradeId;
	}
	public void setTradeId(Integer tradeId) {
		this.tradeId = tradeId;
	}

	
	// 构造方法
	public TradeItem(Integer tradeItemId, Integer bookId, int quantity,
			Integer tradeId) {
		super();
		this.tradeItemId = tradeItemId;
		this.bookId = bookId;
		this.quantity = quantity;
		this.tradeId = tradeId;
	}
	public TradeItem() {}

	@Override
	public String toString() {
		return "TradeItem [tradeItemId=" + tradeItemId + ", quantity="
				+ quantity + ", bookId=" + bookId + "]";
	}
	
}