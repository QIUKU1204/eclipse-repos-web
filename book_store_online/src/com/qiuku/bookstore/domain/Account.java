package com.qiuku.bookstore.domain;
/**
 * @TODO: Account实体类 
 * @author:QIUKU
 */
public class Account {
	// 账户ID
	private Integer accountId;
	// 账户余额
	private int balance;

	
	// 属性的getter、setter方法
	public Integer getAccountId() {
		return accountId;
	}
	public void setAccountId(Integer accountId) {
		this.accountId = accountId;
	}
	public int getBalance() {
		return balance;
	}
	public void setBalance(int balance) {
		this.balance = balance;
	}

	
	// 构造方法
	public Account(Integer accountId, int balance) {
		super();
		this.accountId = accountId;
		this.balance = balance;
	}
	public Account() {}

	@Override
	public String toString() {
		return "Account [accountId=" + accountId + ", balance=" + balance + "]";
	}

}
