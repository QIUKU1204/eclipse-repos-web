package com.qiuku.mvcapp.dao;

import com.qiuku.mvcapp.db.JdbcUtils;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.sql.Connection;
import java.util.List;
//dbUtils
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

/**
 * @TODO:封装了基本的CRUD方法，供子类继承使用
 * 当前DAO直接在其方法中获取数据库连接
 * @param <T>: 当前DAO处理的实体类的类型
 * @author:QIUKU
 */
public class DAO<T> {
	
	/// ???
	private QueryRunner queryRunner = new QueryRunner();
	
	private Class<T> clazz;

	public DAO() {
		Type superClass = getClass().getGenericSuperclass();
		
		if(superClass instanceof ParameterizedType) {
			ParameterizedType parameterizedType = (ParameterizedType)superClass;
			
			Type [] typeArgs = parameterizedType.getActualTypeArguments();
			if(typeArgs != null && typeArgs.length > 0 ) {
				if(typeArgs[0] instanceof Class) {
					clazz = (Class<T>)typeArgs[0];
				}
			}
		}
	}
	
	
	/**
	 * @TODO: 返回某一个字段的值
	 * 例如返回某一条记录的customerName，或返回数据表中的记录个数等
	 * @param sql
	 * @param args
	 */
	public <E> E getForValue(String sql,Object ...args) {
		Connection connection = null;
		try {
			connection = JdbcUtils.getConnection();
			// ??? !!!
			return (E) queryRunner.query(connection,sql,new ScalarHandler(),args);
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			JdbcUtils.releaseConnection(connection);
		}
		return null;
	}
	
	
	/**
	 * @TODO: 返回T所对应的List
	 * @param sql
	 * @param args
	 */
	public List<T> getForList(String sql,Object ...args){
		Connection connection = null;
		try {
			connection = JdbcUtils.getConnection();
			// ??? !!!
			return queryRunner.query(connection,sql,new BeanListHandler<>(clazz),args);
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			JdbcUtils.releaseConnection(connection);
		}
		return null;
	}
	
	
	/**
	 * @TODO: 返回T所对应的一个实例对象
	 * @param sql
	 * @param args
	 */
	public T get(String sql,Object ...args) {
		Connection connection = null;
		try {
			connection = JdbcUtils.getConnection();
			// ??? !!!
			return queryRunner.query(connection,sql,new BeanHandler<>(clazz),args);
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			JdbcUtils.releaseConnection(connection);
		}
		return null;
	}
	
	
	/**
	 * @TODO:该方法封装了INSERT、DELETE、UPDATE操作
	 * @param sql: SQL语句
	 * @param args: 填充SQL语句的占位符
	 */
	public void update(String sql,Object ...args) {
		Connection connection = null;
		try {
			connection = JdbcUtils.getConnection();
			// ??? !!!
			queryRunner.update(connection,sql,args);
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			JdbcUtils.releaseConnection(connection);
		}
		
	}


}
