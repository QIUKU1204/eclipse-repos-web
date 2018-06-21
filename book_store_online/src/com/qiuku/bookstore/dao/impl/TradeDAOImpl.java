package com.qiuku.bookstore.dao.impl;

import java.util.LinkedHashSet;
import java.util.Set;

import com.qiuku.bookstore.dao.BaseDAO;
import com.qiuku.bookstore.dao.TradeDAO;
import com.qiuku.bookstore.domain.Trade;

public class TradeDAOImpl extends BaseDAO<Trade> implements TradeDAO {

	@Override
	public void insert(Trade trade) {
		String sql = "INSERT INTO trades(userid, tradetime, name, telephone, address, status)" + 
				" VALUES(?, ?, ?, ?, ?, ?)";
		long tradeId = insert(sql, trade.getUserId(), trade.getTradeTime(), trade.getName(),
				trade.getTelephone(), trade.getAddress(), trade.getStatus());
		trade.setTradeId((int)tradeId);
	}

	@Override
	public Set<Trade> getTradesWithUserId(Integer userId) {
		String sql = "SELECT tradeId, userId, tradeTime, status FROM trades " +
				"WHERE userId = ? ORDER BY tradeTime DESC";
		Set<Trade> tradeSet = new LinkedHashSet<Trade>(getForList(sql, userId));
		return tradeSet;
	}


	@Override
	public Trade getTrade(Integer tradeId) {
		String sql = "SELECT tradeId, userId, tradeTime, name, telephone, address, status FROM trades " +
					"WHERE tradeId = ? ";
		Trade trade = get(sql, tradeId);
		return trade;
	}

	@Override
	public Set<Trade> getTrades() {
		String sql = "SELECT tradeId, tradeTime, name, telephone, address, status FROM trades ";
		Set<Trade> tradeSet = new LinkedHashSet<Trade>(getForList(sql));
		return tradeSet;
	}
	
	@Override
	public void updateTradeStatus(Integer tradeId, String status) {
		System.out.println(status);
		System.out.println(tradeId);
		String sql = "UPDATE trades SET status = ? WHERE tradeId = ?";
		update(sql, status, tradeId);
	}

}
