package com.qiuku.bookstore.dao.impl;

import java.util.List;


import com.qiuku.bookstore.dao.UserDAO;

import com.qiuku.bookstore.domain.User;
/**
 * @TODO:CustomerDAOXMLImpl.java
 * @author:QIUKU
 */
public class UserDAOXMLImpl implements UserDAO {

	
	@Override
	public List<User> getAll() {
		System.out.println("getAll");
		return null;
	}

	
	@Override
	public void save(User user) {
		System.out.println("save");
		
	}

	
	@Override
	public User get(String name) {
		System.out.println("get");
		return null;
	}

	
	@Override
	public void delete(String name) {
		System.out.println("delete");
		
	}

	
	@Override
	public void update(User user) {
		System.out.println("update");
		
	}

	
	@Override
	public long getCountWithName(String name) {
		System.out.println("getCountWithName");
		return 0;
	}


}
