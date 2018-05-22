package com.qiuku.mvcapp.dao.impl;

import java.util.List;

import com.qiuku.mvcapp.dao.CustomerDAO;
import com.qiuku.mvcapp.dao.UserDAO;
import com.qiuku.mvcapp.domain.CriteriaCustomer;
import com.qiuku.mvcapp.domain.Customer;
import com.qiuku.mvcapp.domain.User;
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
