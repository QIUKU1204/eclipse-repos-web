package com.qiuku.bookstore.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;

import com.qiuku.bookstore.exception.DBException;

import com.mchange.v2.c3p0.ComboPooledDataSource;

/**
 * @TODO: JDBC的工具类
 * 用于简化JDBC操作的工具类
 * JDBC - Java数据库连接
 * @author:QIUKU
 */
public class JDBCUtils {
	
	private static DataSource dataSource =null;
	
	static {
		// ps:数据源只能被创建一次;
		/**
		 * 参数值需与c3p0-config.xml中的<named-config name="mvcapp">一致
		 * 否则会出现如下exception、warning及error:
		 * java.sql.SQLException: Connections could not be acquired from the underlying database!
		 * java.sql.SQLException: No suitable driver
		 */
		dataSource = new ComboPooledDataSource("mvcapp");
	}
	
	/**
	 * @throws SQLException 
	 * @TODO: 返回数据源连接池的一个Connection对象
	 */
	public static Connection getConnection() throws SQLException {
		try {
			return dataSource.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DBException("数据库连接错误!");
		}
	}
	
	/**
	 * @TODO: 释放Connection连接
	 */
	public static void releaseConnection(Connection connection) {
		try {
			if(connection != null)
				connection.close();
		}catch(Exception e) {
			e.printStackTrace();
			throw new DBException("数据库连接错误!");
		}
	}
	
	/**
	 * @TODO: 关闭结果集和声明对象
	 */
	public static void closeRS(ResultSet rs, Statement statement) {
		try { // 关闭结果集
			if(rs != null){
				rs.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DBException("数据库连接错误!");
		}
		
		try { // 关闭声明对象
			if(statement != null){
				statement.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DBException("数据库连接错误!");
		}
	}

}
