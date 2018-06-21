package com.qiuku.bookstore.dao.impl;

import com.qiuku.bookstore.dao.AccountDAO;
import com.qiuku.bookstore.dao.BaseDAO;

import com.qiuku.bookstore.domain.Account;

public class AccountDAOIml extends BaseDAO<Account> implements AccountDAO {

	@Override
	public Account getAccount(Integer accountId) {
		String sql = "SELECT accountId, balance FROM accounts WHERE accountId = ?";
		return get(sql, accountId); 
	}

	@Override
	public void updateBalance(Integer accountId, float amount) {
		String sql = "UPDATE accounts SET balance = balance - ? WHERE accountId = ?";
		update(sql, amount, accountId); 
	}

	@Override
	public void recharge(Integer accountId, float increment) {
		String sql = "UPDATE accounts SET balance = balance + ? WHERE accountId = ?";
		update(sql, increment, accountId); 
	}

}
