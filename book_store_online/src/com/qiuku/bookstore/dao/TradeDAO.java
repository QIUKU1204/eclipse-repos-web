package com.qiuku.bookstore.dao;

import java.util.Set;

import com.qiuku.bookstore.domain.Trade;

public interface TradeDAO {

	/**
	 * 向数据表中插入 Trade 对象
	 * @param trade
	 */
	public abstract void insert(Trade trade);

	/**
	 * 根据 userId 获取与其关联的 Trade 的集合
	 * @param userId
	 * @return
	 */
	public abstract Set<Trade> getTradesWithUserId(Integer userId);

}