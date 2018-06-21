package com.qiuku.bookstore.filter;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.qiuku.bookstore.db.JDBCUtils;
import com.qiuku.bookstore.web.ConnectionContext;

/**
 * Servlet Filter implementation class TranactionFilter
 */
public class TransactionFilter implements Filter {

    /**
     * Default constructor. 
     */
    public TransactionFilter() {
        // TODO Auto-generated constructor stub
    }

    private FilterConfig filterConfig = null;
    
    /**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
		this.filterConfig = fConfig;
	}

	/**
	 * TODO 负责处理一次请求所涉及的数据库事务(transaction):
	 * 1. 使用 ThreadLocal + Filter 的模式处理事务;
	 * 2. ThreadLocal 负责将 数据库连接 和 当前线程 绑定, 如此可以共用一个 connection;
	 * 3. TransactionFilter 负责拦截与 数据库事务 相关的请求, 并执行一些必要的操作;
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		
		Connection connection = null;
		try {
			// 1. 获取连接(默认是 auto-commit 模式)
			connection = JDBCUtils.getConnection();
			// 2. 禁用 auto-commit 模式
			connection.setAutoCommit(false);		
			// 3. 将 connection 和 当前线程 绑定
			ConnectionContext.getInstance().bind(connection);
			// 4. 把请求转给目标 Servlet
			chain.doFilter(request, response);		
			// 5. 提交事务
			connection.commit();
		} catch (Exception e) {
			e.printStackTrace();
			// 6. 当发生异常时回滚事务
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			// 7. 并重定向到错误页面
			// HttpServletResponse resp = (HttpServletResponse) response;
			HttpServletRequest req = (HttpServletRequest) request;
			req.getRequestDispatcher("/WEB-INF/error_404.jsp").forward(request, response);
			
		} finally{
			// 8. 解除绑定
			ConnectionContext.getInstance().remove();
			// 9. 关闭连接
			JDBCUtils.releaseConnection(connection);	
		}	
	}

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}
}
