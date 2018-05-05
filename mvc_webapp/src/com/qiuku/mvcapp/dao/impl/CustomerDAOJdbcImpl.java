package com.qiuku.mvcapp.dao.impl;

import java.util.List;

import com.qiuku.mvcapp.dao.CustomerDAO;
import com.qiuku.mvcapp.dao.DAO;
import com.qiuku.mvcapp.domain.CriteriaCustomer;
import com.qiuku.mvcapp.domain.Customer;

/**
 * @TODO: 实现类
 * @author:QIUKU
 */
public class CustomerDAOJdbcImpl extends DAO<Customer> implements CustomerDAO{
	
	@Override
	public List<Customer> getAll() {
		String sql = "SELECT id,name,address,phone FROM customers";
		return getForList(sql);
	}

	@Override
	/**
	 * TODO: 返回满足查询条件的Customer List
	 */
	public List<Customer> getForListWithCriteriaCustomer(CriteriaCustomer customer) {
		String sql = "SELECT id,name,address,phone FROM customers WHERE name LIKE ? AND address LIKE ? AND phone LIKE ?";
		// 修改了CriteriaCustomer中的getter方法: 使其返回的字符串中包含"%%"
		// 若查询条件为null, 则返回"%%"，否则返回"%" + 字段值 + "%"
		return getForList(sql,customer.getName(),customer.getAddress(),customer.getPhone());
	}

	@Override
	public void save(Customer customer) {
		String sql = "INSERT INTO customers(name,address,phone) VALUES(?,?,?)";
		update(sql, customer.getName(),customer.getAddress(),customer.getPhone());
		
	}

	@Override
	public Customer get(Integer id) {
		String sql = "SELECT id,name,address,phone FROM customers WHERE id = ?";
		return get(sql, id);
	}

	@Override
	public void delete(Integer id) {
		String sql ="DELETE FROM customers WHERE id = ?";
		update(sql, id);
	}

	@Override
	public void update(Customer customer) {
		String sql = "UPDATE customers SET name = ?, address = ?, phone = ? WHERE id = ?";
		update(sql, customer.getName(),customer.getAddress(),customer.getPhone(),customer.getId());
	}

	
	@Override
	public long getCountWithName(String name) {
		String sql = "SELECT count(id) FROM customers WHERE name = ?";
		return getForValue(sql, name);
	}

}
