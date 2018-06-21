package com.qiuku.bookstore.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.qiuku.bookstore.domain.Book;
import com.qiuku.bookstore.domain.ShoppingCart;

public class BookStoreWebUtils {
	
	/**
	 * 获取购物车对象: 从 session 中获取, 若有, 则直接返回;
	 * 若 session 中没有该对象, 则创建一个新的购物车对象, 放入到 session 中;
	 * @param request
	 * @return ShoppingCart
	 */
	// 静态方法(或称之为类方法), 可由类名直接调用
	public static ShoppingCart getShoppingCart(HttpServletRequest request){
		HttpSession session = request.getSession();
		ShoppingCart sc = (ShoppingCart) session.getAttribute("ShoppingCart");
		if(sc == null){
			sc = new ShoppingCart();
			session.setAttribute("ShoppingCart", sc);
		}
		return sc;
	}
	
	public static Book getBook(HttpServletRequest request) {
		HttpSession session = request.getSession();
		Book book = (Book) session.getAttribute("book");
		if(book == null){
			book = new Book();
			session.setAttribute("book", book);
		}
		return book;
	}
	
}
