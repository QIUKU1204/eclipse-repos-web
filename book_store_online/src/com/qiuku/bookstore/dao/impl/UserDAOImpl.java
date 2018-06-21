package com.qiuku.bookstore.dao.impl;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import com.qiuku.bookstore.dao.UserDAO;
import com.qiuku.bookstore.dao.BaseDAO;
import com.qiuku.bookstore.domain.Trade;
import com.qiuku.bookstore.domain.User;

/**
 * @TODO: 实现类
 * @author:QIUKU
 */
public class UserDAOImpl extends BaseDAO<User> implements UserDAO{
	/**
	 * 获取 users 表中的所有用户记录
	 * @param username
	 * @return
	 */
	@Override
	public Set<User> getUsers() {
		String sql = "SELECT userId,username,password,email,creditCard FROM users";
		Set<User> userSet = new LinkedHashSet<User>(getForList(sql));
		return userSet;
	}


	@Override
	public void save(User user) {
		String sql = "INSERT INTO users(username,password,email,creditCard) VALUES(?,?,?,?)";
		update(sql, user.getUsername(), user.getPassword(), user.getEmail(), user.getCreditCard());
		
	}
	
	/**
	 * 根据用户名获取 User 对象
	 * @param username
	 * @return
	 */
	@Override
	public User getUser(String username) {
		String sql = "SELECT userId,username,password,email,creditCard FROM users WHERE username = ?";
		return get(sql, username);
	}

	@Override
	public void delete(String username) {
		String sql ="DELETE FROM users WHERE username = ?";
		update(sql, username);
	}

	@Override
	public void updatePass(User user) {
		String sql = "UPDATE users SET password = ? WHERE username = ?";
		update(sql, user.getPassword(), user.getUsername());
	}

	
	@Override
	public long getCountWithName(String username) {
		String sql = "SELECT count(userId) FROM users WHERE username = ?";
		return getForValue(sql, username);
	}


	@Override
	public long getCountWithEmail(String email) {
		String sql = "SELECT count(userId) FROM users WHERE email = ?";
		return getForValue(sql, email);
	}

}
