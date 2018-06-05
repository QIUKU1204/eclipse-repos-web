package com.qiuku.javaweb;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * @TODO:LoginServlet
 * @author:QIUKU
 */
public class LoginServlet implements Servlet {


	public ServletConfig getServletConfig() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getServletInfo() {
		// TODO Auto-generated method stub
		return null;
	}

	private ServletConfig servletConfig;	
	
	public void init(ServletConfig servletConfig) throws ServletException {
		// TODO Auto-generated method stub
		System.out.println("init...");
        this.servletConfig = servletConfig;
	}
	
	public void service(ServletRequest request, ServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.println("service...");
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        
        ServletContext servletContext = servletConfig.getServletContext();
        String init_user = servletContext.getInitParameter("user");
        String init_pass = servletContext.getInitParameter("password");
        
        PrintWriter out = response.getWriter();
        if(init_user.equals(username)&&init_pass.equals(password)) {
        	out.print("Hello:"+username);
        }else {
        	out.print("Sorry:"+username);
        }
        
	}
	
	public void destroy() {
		// TODO Auto-generated method stub
		System.out.println("destroy...");
	}	
	
	public LoginServlet() {
		System.out.println("LoginServlet's constructor.");
	}

}
