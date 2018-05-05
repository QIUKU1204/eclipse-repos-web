package com.qiuku.mvcapp.servlet;

import java.io.IOException;
import java.lang.String;
import java.lang.reflect.Method;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.qiuku.mvcapp.domain.RightTriangle;
import com.qiuku.mvcapp.domain.RightTrapezoid;

public class ColumnServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 1.获取ServletPath: /rightTriangle.do
		request.setCharacterEncoding("UTF-8");
		String servletPath = request.getServletPath();
		// 2.去除 / 和 .do, 得到rightTriangle或rightTrapezoid这样的字符串
		String methodName = servletPath.substring(1);
		methodName = methodName.substring(0,methodName.length()-3);
				
		try {
			// 3.利用反射技术获取methodName对应的方法
			Method method = getClass().getDeclaredMethod(methodName,HttpServletRequest.class,HttpServletResponse.class);
			// 4.利用反射调用对应的方法
			method.invoke(this, request,response);
		} catch (Exception e) {
			// e.printStackTrace();
			// java.lang.reflect.InvocationTargetException
			System.out.println(e);
			response.sendRedirect("/WEB-INF/error_404.jsp");
		}
	}
	
	private void rightTriangle(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		
		String LStr = request.getParameter("L");
		String HStr = request.getParameter("H");
		String LnStr = request.getParameter("Ln");
		String[] LnArrayStr = LnStr.split(","); 
		
		try {
			float L = Float.parseFloat(LStr);
			float H = Float.parseFloat(HStr);
			float[] LnArray = new float[LnArrayStr.length];
			float[] HnArray = new float[LnArrayStr.length];
			RightTriangle rightTriangle = new RightTriangle(L,H);
			for(int i=0;i<LnArrayStr.length;i++) {
				LnArray[i] = Float.parseFloat(LnArrayStr[i]);
				if(LnArray[i] > L) {
					request.setAttribute("message", "Ln > L,请检查输入！");
					request.getRequestDispatcher("rightTriangle.jsp").forward(request, response);
				}
				rightTriangle.setLn(LnArray[i]);
				HnArray[i] = rightTriangle.getHn();
			}
			System.out.println("HnArray: " + HnArray + ";" + "LnArray: " + LnArray);
			request.setAttribute("HnArray", HnArray);
			request.getRequestDispatcher("rightTriangle.jsp").forward(request, response);
			
		} catch (Exception e) {
			e.getLocalizedMessage();
		}

	}
	
	private void rightTrapezoid(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		
		String H1Str = request.getParameter("H1");
		String H2Str = request.getParameter("H2");
		String LStr = request.getParameter("L");
		String LnStr = request.getParameter("Ln");
		String[] LnArrayStr = LnStr.split(","); 
		
		try {
			float H1 = Float.parseFloat(H1Str);
			float H2 = Float.parseFloat(H2Str);
			float L = Float.parseFloat(LStr);
			float[] LnArray = new float[LnArrayStr.length];
			float[] HnArray = new float[LnArrayStr.length];
			RightTrapezoid rightTrapezoid = new RightTrapezoid(H1, H2, L);
			for(int i=0;i<LnArrayStr.length;i++) {
				LnArray[i] = Float.parseFloat(LnArrayStr[i]);
				if(LnArray[i] > L) {
					request.setAttribute("message", "Ln > L,请检查输入！");
					request.getRequestDispatcher("rightTrapezoid.jsp").forward(request, response);
				}
				rightTrapezoid.setLn(LnArray[i]);
				HnArray[i] = rightTrapezoid.getHn();
			}
			System.out.println("HnArray: " + HnArray + ";" + "LnArray: " + LnArray);
			request.setAttribute("HnArray", HnArray);
			request.getRequestDispatcher("rightTrapezoid.jsp").forward(request, response);
			
		} catch (Exception e) {
			e.getLocalizedMessage();
		}
		
	}

}
