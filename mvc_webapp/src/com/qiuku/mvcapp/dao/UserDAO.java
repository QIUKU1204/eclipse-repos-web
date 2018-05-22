package com.qiuku.mvcapp.dao;

import java.util.List;

import com.qiuku.mvcapp.domain.User;
/**
 * @TODO:CustomerDAO.java
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