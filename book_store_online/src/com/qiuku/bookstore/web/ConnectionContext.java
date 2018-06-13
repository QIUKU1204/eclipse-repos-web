package com.qiuku.bookstore.web;

import java.sql.Connection;

import sun.security.jca.GetInstance.Instance;

import java.lang.ThreadLocal;
/**
 * @TODO: 使用 ThreadLocal 实现数据库连接与当前进程绑定、从当前进程中获取连接等方法;
 * @author:QIUKU
 */
public class ConnectionContext {
	// 私有构造方法
	private ConnectionContext(){}
	// 创建一个静态 ConnectionContext 实例
	private static ConnectionContext instance = new ConnectionContext();
	// 返回这个静态 ConnectionContext 实例
	public static ConnectionContext getInstance() {
		return instance;
	}
	
	// 创建一个 ThreadLocal 对象(Creates a thread local variable)
	private ThreadLocal<Connection> connectionThreadLocal = new ThreadLocal<>();
	
	// 将 数据库连接 保存到当前线程的 connectionThreadLocal 变量副本中
	public void bind(Connection connection){
		connectionThreadLocal.set(connection);
	}
	
	// 从 变量副本 中获取 connection
	public Connection get(){
		return connectionThreadLocal.get();
	}
	
	// 从 变量副本 中移除 connection
	public void remove(){
		connectionThreadLocal.remove();
	}
	
}
