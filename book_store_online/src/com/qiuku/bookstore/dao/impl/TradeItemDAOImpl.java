package com.qiuku.bookstore.dao.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.qiuku.bookstore.dao.BaseDAO;
import com.qiuku.bookstore.dao.TradeItemDAO;

import com.qiuku.bookstore.domain.TradeItem;

public class TradeItemDAOImpl extends BaseDAO<TradeItem> implements TradeItemDAO {

	@Override
	public void batchSave(Collection<TradeItem> items) {
		String sql = "INSERT INTO tradeitems(bookId, quantity, tradeId) VALUES(?,?,?)";
		Object [][] params = new Object[items.size()][3];
		// 集合类: Collection父接口 + List、Set子接口
		List<TradeItem> tradeItemList = new ArrayList<>(items);
		for(int i = 0; i < tradeItemList.size(); i++){
			params[i][0] = tradeItemList.get(i).getBookId();
			params[i][1] = tradeItemList.get(i).getQuantity();
			params[i][2] = tradeItemList.get(i).getTradeId(); 
		}
		
		batch(sql, params);
	}

	@Override
	public Set<TradeItem> getTradeItemsWithTradeId(Integer tradeId) {
		String sql = "SELECT itemId tradeItemId, bookId, " +
				"quantity, tradeId FROM tradeitems WHERE tradeid = ?";
		Set<TradeItem> tradeItemSet = new HashSet<>(getForList(sql, tradeId));
		return tradeItemSet;
	}

}
