package com.qiuku.bookstore.dao.impl;

import java.util.List;

import com.qiuku.bookstore.dao.UserDAO;
import com.qiuku.bookstore.dao.DAO;
import com.qiuku.bookstore.domain.User;

/**
 * @TODO: 实现类
 * @author:QIUKU
 */
public class UserDAOJdbcImpl extends DAO<User> implements UserDAO{
	
	@Override
	public List<User> getAll() {
		String sql = "SELECT Id,username,password FROM users";
		return getForList(sql);
	}


	@Override
	public void save(User user) {
		String sql = "INSERT INTO users(username,password) VALUES(?,?)";
		update(sql, user.getUsername(), user.getPassword());
		
	}

	@Override
	public User get(String name) {
		String sql = "SELECT Id,username,password FROM users WHERE username = ?";
		return get(sql, name);
	}

	@Override
	public void delete(String name) {
		String sql ="DELETE FROM users WHERE username = ?";
		update(sql, name);
	}

	@Override
	public void update(User user) {
		String sql = "UPDATE users SET username = ?, password = ? WHERE username = ?";
		update(sql, user.getUsername(),user.getPassword(),user.getUsername());
	}

	
	@Override
	public long getCountWithName(String name) {
		String sql = "SELECT count(Id) FROM users WHERE username = ?";
		return getForValue(sql, name);
	}

}
