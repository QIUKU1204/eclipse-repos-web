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
	private Date tradeTime;
	
	
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

	@Override
	public String toString() {
		return "Trade [tradeId=" + tradeId + ", tradeTime=" + tradeTime
				+ ", userId=" + userId + "]";
	}
}

