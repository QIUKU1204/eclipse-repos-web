package com.qiuku.bookstore.dao.factory;

import java.util.HashMap;
import java.util.Map;

import com.qiuku.bookstore.dao.UserDAO;
import com.qiuku.bookstore.dao.impl.UserDAOJdbcImpl;
import com.qiuku.bookstore.dao.impl.UserDAOXMLImpl;

/**
 * @TODO:CustomerDAOFactory.java
 * @author:QIUKU
 */
public class UserDAOFactory {
	// TODO HashMap 集合类
	private Map<String, UserDAO> daos = new HashMap<String,UserDAO>();
	
	private static UserDAOFactory userDAOFactory = new UserDAOFactory(); // 对应下面的无参构造函数
	
	public static UserDAOFactory getInstance() {
		return userDAOFactory;
	}
	
	private String type = null;
	
	public void setType(String type) {
		this.type = type;
	}
	
	private UserDAOFactory(){ // 无参构造函数
		daos.put("jdbc", new UserDAOJdbcImpl());
		daos.put("xml", new UserDAOXMLImpl());
	}
	
	public UserDAO getUserDAO() {
		// TODO HashMap.get(key)方法: 当key==null时, 返回第一个键值对中的value;
		return daos.get(type);
	}

}
