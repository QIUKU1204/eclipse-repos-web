package com.qiuku.bookstore.dao;

import java.util.List;

/**
 * @TODO:DAO接口
 * 定义了基本的CRUD方法以操作数据库
 * 具体由BaseDAO提供实现
 * @param <T>: DAO实际操作的实体类的类型
 * @author:QIUKU
 */
public interface DAO<T> {
	
	public static final int ID = 10;
	String SSS = "";

	/**
	 * 执行单条记录的查询操作, 返回与记录对应的类的一个对象
	 * @param sql: 待执行的 SQL 语句
	 * @param args: 填充占位符的可变参数
	 * @return: 与记录对应的类的一个对象
	 */
	public abstract T get(String sql, Object... args);
	
	
	/**
	 * 执行多条记录的查询操作, 返回与记录对应的类的一个 List
	 * @param sql: 待执行的 SQL 语句
	 * @param args: 填充占位符的可变参数
	 * @return: 与记录对应的类的一个 List
	 */
	List<T> getForList(String sql, Object... args);
	
	
	/**
	 * 执行一个属性或值的查询操作, 例如查询某一条记录的一个字段, 或查询某个统计信息, 返回要查询的值
	 * @param sql: 待执行的 SQL 语句
	 * @param args: 填充占位符的可变参数
	 * @return: 执行一个属性或值的查询操作, 例如查询某一条记录的一个字段, 或查询某个统计信息, 返回要查询的值
	 */
	<E> E getForValue(String sql, Object... args);
	
	
	/**
	 * 执行 UPDATE 操作, 包括 INSERT(但没有返回值), UPDATE, DELETE
	 * @param sql: 待执行的 SQL 语句
	 * @param args: 填充占位符的可变参数
	 */
	void update(String sql, Object... args);
	
	
	/**
	 * 执行 INSERT 操作, 返回插入后的记录的 ID
	 * @param sql: 待执行的 SQL 语句
	 * @param args: 填充占位符的可变参数
	 * @return: 插入新记录的 id
	 */
	long insert(String sql, Object... args);
	
	
	/**
	 * 执行批量更新操作
	 * @param sql: 待执行的 SQL 语句
	 * @param args: 填充占位符的可变参数
	 */
	public void batch(String sql, Object[]... params);
}
