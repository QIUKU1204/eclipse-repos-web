package com.qiuku.bookstore.domain;

import java.sql.Date;
import java.util.LinkedHashSet;
import java.util.Set;
/**
 * @TODO: Trade(交易)实体类 
 * @author:QIUKU
 */
public class Trade {
	// 实体类属性
	private Integer tradeId;
	private Integer userId;
	private String name;
	private String address;
	private String telephone;
	private String status;
	private Date tradeTime;
	
	/**
	 * 无参构造器
	 */
	public Trade() {
	}

	/**
	 * 有参构造器
	*/
	public Trade(String name, String telephone, String address) {
		super();
		this.name = name;
		this.telephone = telephone;
		this.address = address;
	}
	
	// TODO ${trade.totalBook }
	public int getTotalBook() {
		int totalBook = 0;
		for(TradeItem item: items) {
			totalBook += item.getQuantity();
		}
		return totalBook;
	}
	// TODO ${trade.totalMoney }
	public float getTotalMoney() {
		float totalMoney = 0;
		for(TradeItem item: items) {
			totalMoney += item.getBook().getPrice();
		}
		return totalMoney;
	}	
	
	// Trade (包含)关联的 TradeItem 集合, 非数据表属性
	private Set<TradeItem> items = new LinkedHashSet<TradeItem>();
	public void setItems(Set<TradeItem> items) {
		this.items = items;
	}
	public Set<TradeItem> getItems() {
		return items;
	}
	
	// 属性的getter、setter方法
	public Integer getTradeId() {
		return tradeId;
	}
	public void setTradeId(Integer tradeId) {
		this.tradeId = tradeId;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public Date getTradeTime() {
		return tradeTime;
	}
	public void setTradeTime(Date tradeTime) {
		this.tradeTime = tradeTime;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getStatus() {
		return status;
	}
	// 修改交易订单状态: 已付款 -> 已发货
	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "Trade [tradeId=" + tradeId + ", tradeTime=" + tradeTime
				+ ", userId=" + userId + "]";
	}
}

