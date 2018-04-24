package com.qiuku.mvcapp.dao.factory;

import java.util.HashMap;
import java.util.Map;

import com.qiuku.mvcapp.dao.CustomerDAO;
import com.qiuku.mvcapp.dao.impl.CustomerDAOJdbcImpl;
import com.qiuku.mvcapp.dao.impl.CustomerDAOXMLImpl;

/**
 * @TODO:CustomerDAOFactory.java
 * @author:QIUKU
 */
public class CustomerDAOFactory {
	private Map<String, CustomerDAO> daos = new HashMap<String,CustomerDAO>();
	
	private static CustomerDAOFactory customerDAOFactory = new CustomerDAOFactory();
	
	public static CustomerDAOFactory getInstance() {
		return customerDAOFactory;
	}
	
	private String type = null;
	
	public void setType(String type) {
		this.type = type;
	}
	
	private CustomerDAOFactory(){
		daos.put("jdbc", new CustomerDAOJdbcImpl());
		daos.put("xml", new CustomerDAOXMLImpl());
	}
	
	public CustomerDAO getCustomerDAO() {
		return daos.get(type);
	}

}
