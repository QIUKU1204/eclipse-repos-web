package com.qiuku.bookstore.dao.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import com.qiuku.bookstore.dao.BookDAO;
import com.qiuku.bookstore.domain.Book;
import com.qiuku.bookstore.domain.ShoppingCartItem;
import com.qiuku.bookstore.web.CriteriaBook;
import com.qiuku.bookstore.web.Page;

import sun.print.resources.serviceui;

import com.qiuku.bookstore.dao.BaseDAO;

public class BookDAOImpl extends BaseDAO<Book> implements BookDAO {

	@Override
	public Book getBook(int id) {
		String sql = "SELECT id, author, title, price, publishingDate, " +
				"salesAmount, storeNumber, remark FROM books WHERE id = ?";
		return get(sql, id);
	}

	@Override
	public Page<Book> getPage(CriteriaBook cb) {
		Page<Book> page = new Page<>(cb.getPageNo());
		
		page.setTotalItemNumber(getTotalBookNumber(cb));
		// 使用 getPageNo 方法校验 pageNo 的合法性
		// 若查询的 pageNo 超出合法范围, 则自动重新设置
		cb.setPageNo(page.getPageNo());
		page.setList(getPageList(cb, 4));
		
		return page;
	}

	@Override
	public long getTotalBookNumber(CriteriaBook cb) {
		String sql = "SELECT count(id) FROM books WHERE price >= ? AND price <= ?";
		return getForValue(sql, cb.getMinPrice(), cb.getMaxPrice()); 
	}

	/**
	 * 根据传入的 CriteriaBook 和 pageSize 返回当前页对应的 List
	 * MySQL 分页使用 LIMIT 函数, 其中 fromIndex 从 0 开始。 
	 */
	@Override
	public List<Book> getPageList(CriteriaBook cb, int pageSize) {
		String sql = "SELECT id, author, title, price, publishingDate, " +
				"salesAmount, storeNumber, remark FROM books " +
				"WHERE price >= ? AND price <= ? " +
				"LIMIT ?, ?";
		
		return getForList(sql, cb.getMinPrice(), cb.getMaxPrice(), 
				(cb.getPageNo() - 1) * pageSize, pageSize);
	}

	@Override
	public int getStoreNumber(Integer id) {
		String sql = "SELECT storeNumber FROM books WHERE id = ?";
		return getForValue(sql, id);
	}

	@Override
	public void batchUpdateStoreNumberAndSalesAmount(
			Collection<ShoppingCartItem> items) {
		String sql = "UPDATE books SET salesAmount = salesAmount + ?, " +
				"storeNumber = storeNumber - ? " +
				"WHERE id = ?";
		// 定义并初始化 params 数组的大小
		Object [][] params = null;
		params = new Object[items.size()][3];
		// Collection 接口没有get()方法来取得集合中的某个元素
		// 但是通过 iterator 方法可以返回一个覆盖所有元素的迭代器
		Iterator<ShoppingCartItem> itemIterator = items.iterator();
		// Collection 的子接口 List 拥有 get(index) 方法
		List<ShoppingCartItem> scis = new ArrayList<>(items);
		for(int i = 0; i < items.size(); i++){
			/*ShoppingCartItem sci = itemIterator.next();
			params[i][0] = sci.getQuantity();
			params[i][1] = sci.getQuantity();
			params[i][2] = sci.getBook().getId();*/
			params[i][0] = scis.get(i).getQuantity();
			params[i][1] = scis.get(i).getQuantity();
			params[i][2] = scis.get(i).getBook().getId();
		}
		batch(sql, params);
	}

}
