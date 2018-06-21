package com.qiuku.bookstore.service;

import com.qiuku.bookstore.dao.AccountDAO;
import com.qiuku.bookstore.dao.impl.AccountDAOIml;

import com.qiuku.bookstore.domain.Account;

public class AccountService {
	
	private AccountDAO accountDAO = new AccountDAOIml();
	
	public Account getAccount(int accountId){
		return accountDAO.getAccount(accountId);
	}
	
	public void recharge(int accountId, float increment) {
		accountDAO.recharge(accountId, increment);
	}
}
