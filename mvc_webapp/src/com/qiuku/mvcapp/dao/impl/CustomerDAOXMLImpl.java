package com.qiuku.mvcapp.dao.impl;

import java.util.List;

import com.qiuku.mvcapp.dao.CustomerDAO;
import com.qiuku.mvcapp.domain.CriteriaCustomer;
import com.qiuku.mvcapp.domain.Customer;
/**
 * @TODO:CustomerDAOXMLImpl.java
 * @author:QIUKU
 */
public class CustomerDAOXMLImpl implements CustomerDAO {


	@Override
	public List<Customer> getAll() {
		System.out.println("getAll");
		return null;
	}

	
	@Override
	public List<Customer> getForListWithCriteriaCustomer(CriteriaCustomer customer) {
		System.out.println("getForListWithCriteriaCustomer");
		return null;
	}

	
	@Override
	public void save(Customer customer) {
		System.out.println("sava");
		
	}

	
	@Override
	public Customer get(Integer id) {
		System.out.println("get");
		return null;
	}

	
	@Override
	public void delete(Integer id) {
		System.out.println("delete");
	}

	
	@Override
	public void update(Customer customer) {
		System.out.println("update");
	}

	
	@Override
	public long getCountWithName(String name) {
		System.out.println("getCountWithName");
		return 0;
	}

}
