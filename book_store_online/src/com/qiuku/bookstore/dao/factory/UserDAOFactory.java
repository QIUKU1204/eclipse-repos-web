package com.qiuku.bookstore.dao.factory;

import java.util.HashMap;
import java.util.Map;

import com.qiuku.bookstore.dao.UserDAO;
import com.qiuku.bookstore.dao.impl.UserDAOImpl;
import com.qiuku.bookstore.dao.impl.UserDAOXMLImpl;

/**
 * @TODO: UserDAO/UserDAOImpl 对应的 工厂类
 * 使用工厂模式, 以降低 UserDAO/UserDAOImpl 与 UserServlet 之间的耦合性;
 * @author:QIUKU
 */
public class UserDAOFactory {
	// TODO HashMap 集合类
	private Map<String, UserDAO> daos = new HashMap<String,UserDAO>();
	
	// ps: 静态实例对象userDAOFactory同时只能在一处被使用，否则调用对象的方法时会产生空指针异常;
	private static UserDAOFactory userDAOFactory = new UserDAOFactory(); 
	
	public static UserDAOFactory getInstance() {
		return userDAOFactory;
	}
	
	private String type = null;
	
	public void setType(String type) {
		this.type = type;
	}
	
	private UserDAOFactory(){ // 私有无参构造函数
		daos.put("jdbc", new UserDAOImpl());
		daos.put("xml", new UserDAOXMLImpl());
	}
	
	public UserDAO getUserDAO() {
		// TODO HashMap.get(key)方法: 当key==null时, 返回第一个键值对中的value;
		return daos.get(type);
	}

}
