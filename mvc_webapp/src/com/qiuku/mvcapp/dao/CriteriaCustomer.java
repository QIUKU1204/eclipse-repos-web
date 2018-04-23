package com.qiuku.mvcapp.dao;

/**
 * @TODO:CriteriaCustomer.java
 * @author:QIUKU
 */
public class CriteriaCustomer {
	
    private String name;
	
	private String address;
	
	private String phone;

	/**
	 * Constructor
	 * @param name
	 * @param address
	 * @param phone
	 */
	public CriteriaCustomer(String name, String address, String phone) {
		super();
		this.name = name;
		this.address = address;
		this.phone = phone;
	}

	public String getName() {
		if(name == null)
			name = "%%";
		else
			name = "%" + name + "%";
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		if(address == null)
			address = "%%";
		else
			address = "%" + address + "%";
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}


	public String getPhone() {
		if(phone == null)
			phone = "%%";
		else
			phone = "%" + phone + "%";
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	

}
