package com.qiuku.bookstore.service;

import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import com.qiuku.bookstore.dao.BookDAO;
import com.qiuku.bookstore.dao.TradeDAO;
import com.qiuku.bookstore.dao.TradeItemDAO;
import com.qiuku.bookstore.dao.UserDAO;
import com.qiuku.bookstore.dao.impl.BookDAOImpl;
import com.qiuku.bookstore.dao.impl.TradeDAOImpl;
import com.qiuku.bookstore.dao.impl.TradeItemDAOImpl;
import com.qiuku.bookstore.dao.impl.UserDAOImpl;

import com.qiuku.bookstore.domain.Trade;
import com.qiuku.bookstore.domain.TradeItem;
import com.qiuku.bookstore.domain.User;

public class UserService {

	private UserDAO userDAO = new UserDAOImpl();
	// 根据用户名获取 user 对象
	public User getUser(String username){
		return userDAO.getUser(username);
	}
	// 获取所有用户记录
	public Set<User> getUsers() {
		Set<User> userSet = userDAO.getUsers();
		return userSet;
	}
	// 保存新创建的合法用户
	public void save(User user) {
		userDAO.save(user);
	}
	// 修改指定用户的密码
	public void updatePass(User user) {
		userDAO.updatePass(user);
	}
	// 统计相同用户名的记录数
	public long getCountWithName(String username) {
		return userDAO.getCountWithName(username);
	}
	// 统计相同用户名的记录数
	public long getCountWithEmail(String email) {
		return userDAO.getCountWithEmail(email);
	}
	
	private TradeDAO tradeDAO = new TradeDAOImpl();
	private TradeItemDAO tradeItemDAO = new TradeItemDAOImpl();
	private BookDAO bookDAO = new BookDAOImpl();
	
	/**
	 * 根据 username 返回指定的 User 对象, 这个 User 对象装配了 trades 属性, 同时 trades 中的每一个 
	 * Trade 对象又装配了 items 属性, 而 items 中的每一个 TradeItem 对象则装配了 book 属性;
	 */
	public User getUserWithTrades(String username){
		// 调用 UserDAO 的 getUser 方法获取指定 User 对象
		User user = userDAO.getUser(username);
		if(user == null){
			return null;
		}
		
		int userId = user.getUserId();
		// 调用 TradeDAO 的方法获取 Trade 的集合
		Set<Trade> tradeSet = tradeDAO.getTradesWithUserId(userId);
		// 调用 TradeItemDAO 的方法获取每一个 Trade 中的 TradeItem 的集合，并把其装配为 Trade 的属性
		if(tradeSet != null){
			// 获取覆盖了 tradeSet 中所有元素的迭代器
			Iterator<Trade> tradeIterator = tradeSet.iterator();
			// 迭代集合的每一个 Trade 对象
			while(tradeIterator.hasNext()) {
				// 从迭代器中获取元素
				Trade trade = tradeIterator.next();
				int tradeId = trade.getTradeId();
				// 根据 tradeId 获取关联的 TradeItem 的集合
				Set<TradeItem> tradeItemSet = tradeItemDAO.getTradeItemsWithTradeId(tradeId);
				// 若交易项集合不为空
				if(tradeItemSet != null){
					// TODO 为 TradeItem 对象设置其 book 属性
					for(TradeItem item: tradeItemSet){
						item.setBook(bookDAO.getBook(item.getBookId())); 
					}
					// TODO 为 Trade 对象设置其 items 属性 及 其他属性
					if(tradeItemSet != null && tradeItemSet.size() != 0){
						trade.setItems(tradeItemSet);
					}
				}
				
				// 若交易项集合为空, 说明该交易无效, 移除该交易;
				if(tradeItemSet == null || tradeItemSet.size() == 0){
					tradeIterator.remove();	
				}
			}
		}
		// TODO 为 User 对象设置其 trades 属性
		if(tradeSet != null && tradeSet.size() != 0){
			user.setTrades(tradeSet);			
		}
		
		return user;
	}
	
	/**
	 * 根据 tradeId 获取对应的 Trade 对象
	 */
	public Trade getTrade(Integer tradeId) {
		Trade trade = tradeDAO.getTrade(tradeId);
		Set<TradeItem> tradeItemSet = tradeItemDAO.getTradeItemsWithTradeId(tradeId);
		if(tradeItemSet != null){
			// TODO 为 TradeItem 对象设置其 book 属性
			for(TradeItem item: tradeItemSet){
				item.setBook(bookDAO.getBook(item.getBookId())); 
			}
			// TODO 为 Trade 对象设置其 items 属性 及 其他属性
			if(tradeItemSet != null && tradeItemSet.size() != 0){
				trade.setItems(tradeItemSet);
			}
		}
		return trade;
	}
	
	/**
	 * 获取trades表中的所有记录
	 */
	public Set<Trade> getTrades() {
		Set<Trade> tradeSet = tradeDAO.getTrades();
		return tradeSet;
	}
	
	/**
	 * 修改指定tradeId的订单的交易状态
	 */
	public void updateTradeStatus(Integer tradeId, String status) {
		tradeDAO.updateTradeStatus(tradeId, status);
	}
}
