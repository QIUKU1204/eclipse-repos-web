package com.qiuku.bookstore.service;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import com.qiuku.bookstore.dao.AccountDAO;
import com.qiuku.bookstore.dao.BookDAO;
import com.qiuku.bookstore.dao.TradeDAO;
import com.qiuku.bookstore.dao.TradeItemDAO;
import com.qiuku.bookstore.dao.UserDAO;
import com.qiuku.bookstore.dao.impl.AccountDAOIml;
import com.qiuku.bookstore.dao.impl.BookDAOImpl;
import com.qiuku.bookstore.dao.impl.TradeDAOImpl;
import com.qiuku.bookstore.dao.impl.TradeItemDAOImpl;
import com.qiuku.bookstore.dao.impl.UserDAOImpl;

import com.qiuku.bookstore.db.JDBCUtils;
import com.qiuku.bookstore.domain.Account;
import com.qiuku.bookstore.domain.Book;
import com.qiuku.bookstore.domain.ShoppingCart;
import com.qiuku.bookstore.domain.ShoppingCartItem;
import com.qiuku.bookstore.domain.Trade;
import com.qiuku.bookstore.domain.TradeItem;

import com.qiuku.bookstore.web.CriteriaBook;
import com.qiuku.bookstore.web.Page;

public class BookService {
	
	private BookDAO bookDAO = new BookDAOImpl();
	
	public Page<Book> getPage(CriteriaBook criteriaBook){
		return bookDAO.getPage(criteriaBook);
	}

	public Book getBook(int id) {
		return bookDAO.getBook(id);
	}
	
	public void updateBook(Book book) {
		bookDAO.updateBook(book);
	}

	public boolean addToCart(int id, ShoppingCart sc) {
		Book book = bookDAO.getBook(id);
		if(book != null){
			sc.addBook(book);
			return true;
		}
		return false;
	}

	public void removeItemFromShoppingCart(ShoppingCart sc, int id) {
		sc.removeItem(id);
	}

	public void clearShoppingCart(ShoppingCart sc) {
		sc.clear();
	}

	public void updateItemQuantity(ShoppingCart sc, int id, int quantity) {
		sc.updateItemQuantity(id, quantity);
	}
	
	private AccountDAO accountDAO = new AccountDAOIml();
	private TradeDAO tradeDAO = new TradeDAOImpl();
	private UserDAO userDAO = new UserDAOImpl();
	private TradeItemDAO tradeItemDAO = new TradeItemDAOImpl();

	/**
	 * TODO 结账的具体业务流程
	 */
	public void cash(ShoppingCart shoppingCart, Trade seTrade,String username, 
			int accountId) {
		
		//1. 更新 books 数据表相关记录的 salesamount 和 storenumber
		bookDAO.batchUpdateStoreNumberAndSalesAmount(shoppingCart.getItems());
		
		//2. 更新 account 数据表的 balance
		accountDAO.updateBalance(accountId, shoppingCart.getTotalMoney());
		
		//3. 向 trade 数据表插入一条记录
		Trade trade = new Trade();
		trade.setUserId(userDAO.getUser(username).getUserId());
		trade.setTradeTime(new Date(new java.util.Date().getTime()));
		trade.setName(seTrade.getName());
		trade.setTelephone(seTrade.getTelephone());
		trade.setAddress(seTrade.getAddress());
		trade.setStatus("已付款");
		tradeDAO.insert(trade);
		
		//4. 向 tradeitem 数据表插入 n 条记录
		Collection<TradeItem> items = new ArrayList<>();
		for(ShoppingCartItem sci: shoppingCart.getItems()){
			TradeItem tradeItem = new TradeItem();
			
			tradeItem.setBookId(sci.getBook().getId());
			tradeItem.setQuantity(sci.getQuantity());
			tradeItem.setTradeId(trade.getTradeId());
			
			items.add(tradeItem);
		}
		tradeItemDAO.batchSave(items);
		
		//5. 清空购物车
		shoppingCart.clear();
	}
	
}
