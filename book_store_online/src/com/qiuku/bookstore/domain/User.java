package com.qiuku.bookstore.domain;

import java.util.LinkedHashSet;
import java.util.Set;

import com.sun.xml.internal.bind.v2.model.core.ID;

/**
 * @TODO: User实体类 
 * @author:QIUKU
 */
public class User {
	// 实体类属性
	private int userId;
	private String username;
	private String password;
	
	
	// 与 User 对象关联的 Trade 集合
	private Set<Trade> trades = new LinkedHashSet<Trade>();
	public void setTrades(Set<Trade> trades) {
		this.trades = trades;
	}
	public Set<Trade> getTrades() {
		return trades;
	}
	
	
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

	
	// 构造方法
	public User(String username, String password) {
		super();
		this.username = username;
		this.password = password;
	}
	public User() {}
	
}
