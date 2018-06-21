package com.qiuku.bookstore.dao;

import java.util.List;
import java.util.Set;

import com.qiuku.bookstore.domain.User;
/**
 * @TODO: UserDAO 接口定义了用于操作 User 实体类的基本方法
 * @author:QIUKU
 */
public interface UserDAO {
	
	public Set<User> getUsers();
	
	public void save(User user);
	
	/**
	 * 根据用户名获取 User 对象
	 * @param username
	 * @return
	 */
	public User getUser(String username);
	
	public void delete(String username);
	
	public void updatePass(User user);
	
	public long getCountWithName(String username);
	
	public long getCountWithEmail(String email);
}