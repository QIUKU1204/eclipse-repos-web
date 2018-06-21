package com.qiuku.bookstore.dao;

import com.qiuku.bookstore.domain.Account;

public interface AccountDAO {

	/**
	 * 根据 accountId 获取对应的 Account 对象
	 * @param accountId
	 * @return
	 */
	public abstract Account getAccount(Integer accountId);

	/**
	 * 根据传入的 accountId, amount 更新指定账户的余额: 扣除 amount 指定的钱数
	 * @param accountId
	 * @param amount
	 */
	public abstract void updateBalance(Integer accountId, float amount);

	/**
	 * 根据传入的增量给指定账户充值
	 * @TODO:recharge
	 * @return:void
	 * @param accountId
	 * @param increment
	 */
	public abstract void recharge(Integer accountId, float increment);
}