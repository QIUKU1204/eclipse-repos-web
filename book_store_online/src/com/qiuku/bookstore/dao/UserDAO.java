package com.qiuku.bookstore.dao;

import java.util.List;

import com.qiuku.bookstore.domain.User;
/**
 * @TODO: UserDAO 接口定义了用于操作 User 实体类的基本方法
 * @author:QIUKU
 */
public interface UserDAO {
	
	public List<User> getUsers();
	
	public void save(User user);
	
	/**
	 * 根据用户名获取 User 对象
	 * @param username
	 * @return
	 */
	public User getUser(String username);
	
	public void delete(String username);
	
	public void update(User user);
	
	public long getCountWithName(String username);
}