package com.qiuku.mvcapp.dao;

import java.util.List;

import com.qiuku.mvcapp.domain.CriteriaCustomer;
import com.qiuku.mvcapp.domain.Customer;
/**
 * @TODO:CustomerDAO.java
 * @author:QIUKU
 */
public interface CustomerDAO {
	
	public List<Customer> getAll();
	
	/**
	 * TODO: 返回满足查询条件的Customer List
	 */
	public List<Customer> getForListWithCriteriaCustomer(CriteriaCustomer customer);
	
	public void save(Customer customer);
	
	public Customer get(Integer id);
	
	public void delete(Integer id);
	
	public void update(Customer customer);
	
	public long getCountWithName(String name);
}