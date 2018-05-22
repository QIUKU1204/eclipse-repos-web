package com.qiuku.mvcapp.domain;

import com.sun.xml.internal.bind.v2.model.core.ID;

/**
 * @TODO:Customer.java
 * @author:QIUKU
 */
public class User {
	
	private int id;
	
	private String username;
	
	private String password;
	
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
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
	
	public String toString() {
		return "Customer [id=" + id + ", username=" + username + ", password=" + password  + "]";
	}
	public User(String username, String password) {
		super();
		this.username = username;
		this.password = password;
	}
	public User() {
	}
}
