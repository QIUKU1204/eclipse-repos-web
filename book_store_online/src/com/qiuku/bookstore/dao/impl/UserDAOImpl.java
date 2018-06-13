package com.qiuku.bookstore.dao.impl;

import java.util.List;

import com.qiuku.bookstore.dao.UserDAO;
import com.qiuku.bookstore.dao.BaseDAO;

import com.qiuku.bookstore.domain.User;

/**
 * @TODO: 实现类
 * @author:QIUKU
 */
public class UserDAOImpl extends BaseDAO<User> implements UserDAO{
	
	@Override
	public List<User> getUsers() {
		String sql = "SELECT userId,username,password,accountId FROM users";
		return getForList(sql);
	}


	@Override
	public void save(User user) {
		String sql = "INSERT INTO users(username,password) VALUES(?,?)";
		update(sql, user.getUsername(), user.getPassword());
		
	}
	
	/**
	 * 根据用户名获取 User 对象
	 * @param username
	 * @return
	 */
	@Override
	public User getUser(String username) {
		String sql = "SELECT userId,username,password FROM users WHERE username = ?";
		return get(sql, username);
	}

	@Override
	public void delete(String username) {
		String sql ="DELETE FROM users WHERE username = ?";
		update(sql, username);
	}

	@Override
	public void update(User user) {
		String sql = "UPDATE users SET username = ?, password = ? WHERE username = ?";
		update(sql, user.getUsername(),user.getPassword(),user.getUsername());
	}

	
	@Override
	public long getCountWithName(String username) {
		String sql = "SELECT count(userId) FROM users WHERE username = ?";
		return getForValue(sql, username);
	}

}
