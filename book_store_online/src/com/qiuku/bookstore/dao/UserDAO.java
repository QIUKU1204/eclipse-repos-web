package com.qiuku.bookstore.dao;

import java.util.List;

import com.qiuku.bookstore.domain.User;
/**
 * @TODO: UserDAO 接口定义了用于操作 User 实体类的基本方法
 * @author:QIUKU
 */
public interface UserDAO {
	
	public List<User> getAll();
	
	public void save(User user);
	
	public User get(String name);
	
	public void delete(String name);
	
	public void update(User user);
	
	public long getCountWithName(String name);
}