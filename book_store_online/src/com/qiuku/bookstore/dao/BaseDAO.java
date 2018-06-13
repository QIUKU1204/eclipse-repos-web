package com.qiuku.bookstore.dao;

import com.qiuku.bookstore.db.JDBCUtils;
import com.qiuku.bookstore.web.ConnectionContext;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.omg.PortableServer.IdAssignmentPolicy;

/**
 * @TODO:DAO接口的实现类，实现了基本的CRUD方法，供子类继承使用, 用以实现操作实体类的方法; 
 * BaseDAO使用 JdbcUtils工具类获取数据库连接; 
 * BaseDAO使用 dbutils工具类执行数据库基本操作: QueryRunner.query(),QueryRunner.update(), etc.
 * @param <T>: BaseDAO实际操作的实体类的类型
 * @author:QIUKU
 */
public class BaseDAO<T> implements DAO<T> {
	
	public int Id;
	// TODO
	private QueryRunner queryRunner = new QueryRunner();

	private Class<T> clazz;

	public BaseDAO() {
		Type superClass = getClass().getGenericSuperclass();

		if (superClass instanceof ParameterizedType) {
			ParameterizedType parameterizedType = (ParameterizedType) superClass;

			Type[] typeArgs = parameterizedType.getActualTypeArguments();
			if (typeArgs != null && typeArgs.length > 0) {
				if (typeArgs[0] instanceof Class) {
					clazz = (Class<T>) typeArgs[0];
				}
			}
		}
	}

	// 接口的默认实现类(匿名类)
	// 接口不能被实例化(不能被new)
	// 此处不是new接口，而是new一个匿名类
	/*DAO<T> dao = new DAO<T>(){

		@Override
		public T get(String sql, Object... args) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public List<T> getForList(String sql, Object... args) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public <E> E getForValue(String sql, Object... args) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public void update(String sql, Object... args) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public long insert(String sql, Object... args) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public void batch(String sql, Object[]... params) {
			// TODO Auto-generated method stub
			
		}

	};*/
	
	/**
	 * @TODO: 返回T所对应的一个实例对象
	 * @param sql
	 * @param args
	 */
	@Override
 	public T get(String sql, Object... args) {
		Connection connection = null;
		try {
			// 获取数据库连接的两种方法
			// 1. 每个数据库操作都单独从 JDBCUtils 获取 connection 并最终释放;
			/*connection = JDBCUtils.getConnection();*/
			// 2. 一次请求涉及的所有数据库操作共用一个 connection , 从当前线程中获取;
			connection = ConnectionContext.getInstance().get();
			// TODO query()
			return queryRunner.query(connection, sql, new BeanHandler<>(clazz), args);
		} catch (Exception e) {
			e.printStackTrace();
		} finally { // 从当前线程中获取 connection 时删除 finally 子句
			// JDBCUtils.releaseConnection(connection);
		}
		return null;
	}

	
	/**
	 * @TODO: 返回T所对应的List
	 * @param sql
	 * @param args
	 */
	@Override
	public List<T> getForList(String sql, Object... args) {
		Connection connection = null;
		try {
			// connection = JDBCUtils.getConnection();
			connection = ConnectionContext.getInstance().get();
			// TODO query()
			return queryRunner.query(connection, sql, new BeanListHandler<>(clazz), args);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// JDBCUtils.releaseConnection(connection);
		}
		return null;
	}

	
	/**
	 * @TODO: 返回某一个字段的值 例如返回某一条记录的customerName，或返回数据表中的记录个数等
	 * @param sql
	 * @param args
	 */
	@Override
	public <E> E getForValue(String sql, Object... args) {
		Connection connection = null;
		try {
			// connection = JDBCUtils.getConnection();
			connection = ConnectionContext.getInstance().get();
			// TODO query()
			return (E) queryRunner.query(connection, sql, new ScalarHandler(), args);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// JDBCUtils.releaseConnection(connection);
		}
		return null;
	}

	
	/**
	 * @TODO:该方法封装了INSERT、DELETE、UPDATE操作
	 * @param sql:SQL语句
	 * @param args:填充SQL语句的占位符
	 */
	@Override
	public void update(String sql, Object... args) {
		Connection connection = null;
		try {
			// connection = JDBCUtils.getConnection();
			connection = ConnectionContext.getInstance().get();
			// TODO update()
			queryRunner.update(connection, sql, args);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// JDBCUtils.releaseConnection(connection);
		}

	}
	
	
	/**
	 * 执行 INSERT 操作, 返回插入后的记录的 ID
	 * @param sql: 待执行的 SQL 语句
	 * @param args: 填充占位符的可变参数
	 * @return: 插入新记录的 id
	 * 封装了PreparedStatement和ResultSet
	 */
	@Override
	public long insert(String sql, Object... args) {
		long id = 0;
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {
			// connection = JDBCUtils.getConnection();
			connection = ConnectionContext.getInstance().get();
			preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

			if (args != null) {
				for (int i = 0; i < args.length; i++) {
					preparedStatement.setObject(i + 1, args[i]);
				}
			}

			preparedStatement.executeUpdate();

			// 获取生成的主键值
			resultSet = preparedStatement.getGeneratedKeys();
			if (resultSet.next()) {
				id = resultSet.getLong(1);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// 释放数据库连接
			// JDBCUtils.releaseConnection(connection);
			// 关闭结果集和声明对象
			JDBCUtils.closeRS(resultSet, preparedStatement);
		}

		return id;
	}

		
	/**
	 * 执行批量更新操作
	 * @param sql: 待执行的 SQL 语句
	 * @param args: 填充占位符的可变参数
	 */
	@Override
	public void batch(String sql, Object[]... params) {
		Connection connection = null;	
		try {
			// connection = JDBCUtils.getConnection();
			connection = ConnectionContext.getInstance().get();
			queryRunner.batch(connection, sql, params);
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			// JDBCUtils.releaseConnection(connection);
		}
		
	}

}
