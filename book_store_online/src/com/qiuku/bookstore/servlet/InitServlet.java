package com.qiuku.bookstore.servlet;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.qiuku.bookstore.dao.factory.UserDAOFactory;
/**
 * @TODO: InitServlet用于在Tomcat启动时, 设置UserDAOFactory实例的type属性的值;
 * @author:QIUKU
 */
public class InitServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	public void init() throws ServletException {
		InputStream in = getServletContext().getResourceAsStream("/WEB-INF/classes/switch.properties");
		Properties properties = new Properties();
		try {
			properties.load(in);
			String type =properties.getProperty("type");
			// 静态成员在内存中只有一个副本，由类本身和类的多个实例对象所共享
			UserDAOFactory.getInstance().setType(type);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
