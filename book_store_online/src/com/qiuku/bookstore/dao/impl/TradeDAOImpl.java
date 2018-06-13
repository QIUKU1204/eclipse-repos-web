package com.qiuku.bookstore.dao.impl;

import java.util.LinkedHashSet;
import java.util.Set;

import com.qiuku.bookstore.dao.BaseDAO;
import com.qiuku.bookstore.dao.TradeDAO;
import com.qiuku.bookstore.domain.Trade;

public class TradeDAOImpl extends BaseDAO<Trade> implements TradeDAO {

	@Override
	public void insert(Trade trade) {
		String sql = "INSERT INTO trades(userid, tradetime) VALUES(?, ?)";
		long tradeId = insert(sql, trade.getUserId(), trade.getTradeTime());
		trade.setTradeId((int)tradeId);
	}

	@Override
	public Set<Trade> getTradesWithUserId(Integer userId) {
		String sql = "SELECT tradeId, userId, tradeTime FROM trades " +
				"WHERE userId = ? ORDER BY tradeTime DESC";
		Set<Trade> tradeSet = new LinkedHashSet<Trade>(getForList(sql, userId));
		return tradeSet;
	}


	@Override
	public Trade geTrade(Integer tradeId) {
		String sql = "SELECT tradeId, userId, tradeTime FROM trades " +
					"WHERE tradeId = ? ";
		Trade trade = get(sql, tradeId);
		return trade;
	}

}
