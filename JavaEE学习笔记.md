# JavaEE学习笔记

标签（空格分隔）： JavaEE web开发 笔记

---

##专用单词理解##

 - deploy  部署
 - undeploy  取消部署
 - reload  重载
 - load  加载
 - Middleware  中间件
 - enclosing   封装的
 - out-of-the-box  拆箱
 - wrapper class  包装类
 - Utils  工具类
 - context  上下文
 - content  内容
 - data source  数据源 -- 数据库应用程序所使用的数据库或者数据库服务器;通过提供正确的数据源名称，你可以找到相应的数据库连接;
 - Web  在Internet上通过浏览器访问的网络资源，即Internet上与HTML相关的部分
 - MVC  模型-视图-控制器
 - POJO  最简单最普通的Java类
 - DAO  数据访问对象

##中文乱码问题##

- 在JSP页面上输入中文，请求页面不出现乱码:
    - 保证`contentType="text/html; charset=UTF-8",pageEncoding="UTF-8" `
    - charset和pageEncoding的字符编码类型一致，且都支持中文即UTF-8;
    
---
- 获取中文参数值: 默认请求参数在传输过程中使用的编码为ISO-8859-1;
```
对于POST请求: 只要在获取请求参数request.getParameter()之前，调用request.setCharacterEncoding("UTF-8")方法即可;

对于GET请求: 需要修改Tomcat的server.xml文件配置,为Connector 节点添加useBodyEncodingForURI="true";但同样需要调用request.setCharacterEncoding("UTF-8")方法;
```

##错误与警告##

- 新建动态web项目(DynamicWebProject)时选择动态web模块版本2.5或3.0的区别; 
    - 2.5和3.0是指servlet的版本，是2.5的还是3.0的;  
    - web.xml文件头信息<web-app></web-app>包含版本(2.5)若与项目实际版本(3.0)不同,将可能导致java文件编译失败,引发`ClassNotFoundException`;
    
---    
- 需在用户环境配置文件/etc/profile中添加CLASSPATH等环境变量，否则编译时无法找到各种package；如`error: package javax.servlet does not exist`
     
---   
- 出现`error: package javax.servlet does not exist`情况的三种解决方法：
  - 修改~/.bashrc文件的CLASSPATH变量，添加servlet-api.jar的绝对路径；
  - 修改/etc/profile文件，做法如上;
  - 将servlet-api.jar拷贝到/jdk/jre/lib文件夹下;
    
---    
- Servlet的两种配置(web.xml和annotation注解)同时只能存在一种，否则在启动Tomcat时，会因为存在两个`url-pattern`而报错:
```
The servlets named [ListAllStudentsServlet] and [com.qiuku.javaweb.mvc.ListAllStudentsServlet] 
are both mapped to the url-pattern [/listAllStudents] which is not permitted.
```

##Servlet##

- ServletConfig对象: 对应于当前WEB应用的特定Servlet;(局部)
  ServletConfig对象代表某一个Servlet
  - `getInitParameter(String name)`: 获取指定参数名(name)的初始化参数值(value);
  - `getInitParameterNames()`: 获取参数名组成的Enumeration对象;
    
---    
- ServletContext对象：对应于当前WEB应用的所有Servlet;(全局)
  可以由ServletConfig对象获取，ServletContext对象代表当前WEB应用
  - `getInitParameter(String name)`：获取指定参数名(name)的初始化参数值(value);
  - `getInitParameterNames()`：获取参数名组成的Enumeration对象;
  - `getRealPath(String Path)`：获取当前WEB应用的某一文件部署在服务器上的绝对路径;
  - `getContextPath()`：获取当前WEB应用的名称;
  - `getResourceAsStream(String Path)`：获取当前WEB应用的某一文件对应的输入流;
    
---    
- `ServletContext`设置的初始化参数可以为WEB应用的所有Servlet所获取，而`ServletConfig`的初始化参数只由那个特定Servlet获取;

---    
- `ServletRequest`和`HttpServletRequest`接口：
  - HttpServletRequest比ServletRequest多了一些针对于Http协议的方法。例如：getHeader()，getMethod()，getSession();
  - 封装了请求信息，可以从中获取到任何的请求信息;
  - `String getParameter(String name)`: 根据请求参数的名字，返回参数值;
  - `String[] getParameterValues(String name)`:  根据请求参数的名字，返回请求参数对应的字符串数组;
  - `Enumeration getParameterNames()`: 返回参数名对应的Enumeration对象，类似于getInitParameterNames()方法;
  - `Map getParameterMap()`: 返回请求参数的键值对：key-参数名，value-参数值；String数组类型;
      
---    
- `ServletResponse`和`HttpServletResponse`接口：
  - 封装了响应信息，如果想给用户什么响应，调用该接口的方法即可实现;
  - `getWriter()`: 返回PrintWriter对象，调用该对象的print()方法，可以将print()中的参数打印到浏览器上;
  - 设置响应的内容类型: response.setContentType("application/msword"); -- 请求后,浏览器会下载一个word文档,内容为print()的参数;
  - void sendRedirect(String location): 请求的重定向;(此方法为HttpServletResponse接口中定义的.)
```java
public void service(ServletRequest request, ServletResponse response)                 
    throws ServletException, IOException{                                                  
 } 
```
 
- 以上两个(四个)接口的实现类都是服务器给予实现的，并在服务器调用service()方法时传入;

---    
- GET请求和POST请求：
  - GET请求使用于在浏览器输入URL或单击网页上的一个超链接时;
  - GET方式传送的数据量一般在1KB以下;
  - POST请求主要用于向WEB服务器提交FORM表单中的数据;
  - POST方式将表单字段元素及其数据作为HTTP消息的实体内容发送给WEB服务器;
  - POST方式传送的数据量比使用GET方式要大得多;
      
---    
- 在servlet的配置当中，<load-on-startup>5</load-on-startup>的含义是：
  - 当值为0或者大于0时，表示容器在WEB应用启动时就加载这个servlet;
  - 当是一个负数时或者没有指定时，则指示容器在该servlet被选择时才加载;
      
---    
- 抽象Servlet类GenericServlet:
  - 是一个Servlet,是Servlet接口和ServletConfig接口的实现类。
  - 是一个抽象类，其中的service()方法为抽象方法。
  - 新建的Servlet程序直接继承GenericServlet会使开发更简洁。
```
public abstract class GenericServlet extends Object
                                     implements Servlet,ServletConfig,Serialize{
}
```
      
---    
- *HttpServlet: 是一个抽象Servlet类，继承自GenericServlet。
  - 针对HTTP协议所定义的一个Servlet基类;
  - 在service()方法中把ServletRequest和ServletResponse强制转为HttpServletRequest和HttpServletResponse,并调用了重载的service(HttpServletRequest,HttpServletResponse);
  - 在service(HttpServletRequest,HttpServletResponse)中获取请求方式:request.getMethod();
  - 根据请求方式又创建了doGet()和doPost()方法;
  - 在实际开发中，直接继承HttpServlet，根据请求方式复写doGet()或doPost()方法即可;
  - 优点: 
      
--- 
- Servlet的annotation(注解)配置和web.xml配置：
  - Annotation(注解)配置：直接在Servlet源文件中添加@WebServlet("/listAllStudents")语句即可;
  - web.xml配置：
```XML
    <!-- ListAllStudentsServlet -->
    <servlet>
        <servlet-name>ListAllStudentsServlet</servlet-name>
        <servlet-class>com.qiuku.javaweb.mvc.ListAllStudentsServlet</servlet-class> 
    </servlet>
    <servlet-mapping>
        <servlet-name>ListAllStudentsServlet</servlet-name>
        <url-pattern>/listAllStudents</url-pattern>
    </servlet-mapping>
```
  - 这两种配置同时只能存在一种，否则在启动Tomcat时，会因为存在两个url-pattern而报错:
```
The servlets named [ListAllStudentsServlet] and [com.qiuku.javaweb.mvc.ListAllStudentsServlet] 
are both mapped to the url-pattern [/listAllStudents] which is not permitted.
```

##JSP概述##
`JSP是简化Servlet编写的一种技术，它将Java代码和HTML语句混合在同一个文件中编写`
`只对网页中要动态产生的内容采用Java代码来编写，静态内容则采用普通HTML语句编写`

- 可以直接使用Servlet程序输出HTML页面，但是很繁琐很臃肿;
```java
   StringBuilder result;
   result.append("<html>")
         .append("<head>")
         .append("</head>")
         .append("</html>");
   out.print(result.toString());
```
- JSP文件可以放置在WEB应用程序中的除了WEB-INF外的任何目录中;

- JSP的运行原理:JSP本质上是一个Servlet,每个JSP页面第一次被访问时,JSP引擎将它翻译成一个Servlet源程序(.java),接着将这个Servlet程序编译成class类文件,最后再由WEB容器(Servlet引擎)加载和执行这个由JSP页面翻译成的Servlet程序。

- JSP的构成和JSP语法：JSP指令+脚本元素+动作元素。
  - 静态HTML内容;
  - *JSP指令: 封装在<%@ 指令 属性名="值" %>中，最常用的有page指令和include指令;
  - *JSP表达式: 封装在<%= %>中，可以将一个Java变量或表达式的计算结果输出到客户端浏览器上;
  - *JSP脚本: 封装在<% %>中 (不需显示内容,没有输出的Java语句)
  - JSP声明: 将Java代码封装在<%!%>中，它里面的代码将被插入到JSP对应的Servlet的_jspService方法的外面,几乎从来不用;
  - JSP动作/标签:
  - *JSP注释:  <%-- JSP代码 --%>  可以阻止Java代码的执行;
     HTML注释: <!-- HTML注释 -->  提供说明性内容，不可以阻止Java代码的执行;
<br/>
- JSP页面的9个内置(隐含)对象:【没有声明就能使用的对象】;
  - request对象: HttpServletRequest类的实例对象;

  - ×response对象: HttpServletResponse类的实例对象;(在JSP页面中几乎不会调用response对象的任何方法)

  - *out对象: JspWriter类的实例对象:调用out.println()方法可以直接把字符串打印到浏览器上;

  - *session(会话)对象: HttpSession类的实例：代表浏览器和服务器的一次会话。
                            主要用于保存每个用户的信息，跟踪每个客户的操作状态;
                         
  - *application对象: ServletContext类的实例:代表当前WEB应用，其信息被一个WEB容器中的所有用户(JSP)所共享;

  - ×config对象: 当前JSP对应的Servlet的ServletConfig对象:保存了当前JSP对应的Servlet的初始化参数等信息。但若需访问这些初始化参数，必须通过映射的地址;

  - *exception(异常)对象: 只有在声明了page指令的isErrorPage="true"时，才可以使用; -- <%@ page isErrorPage="true"%>。首先创建一个能够引发错误的JSP页面errorCause.jsp,并指定其错误处理页面为errorProcess.jsp;

  - *pageContext对象: 页面的上下文，是PageContext类的实例。可以从该对象获取到其他8个对象,也可以从中获取当前页面信息;

  - ×page对象: Object基类的实例对象:指向当前JSP对应Servlet对象的引用，只能调用Object类的方法;

`使用顺序:pageContext，request，session，application(对属性的作用域范围从小到大)`

- Cookie: 
  - response.addCookie(cookie): 用于将封装好的Cookie对象传送到客户端浏览器;
  - request.getCookie(): 用于读取保存到客户端浏览器的Cookie;
<br/>
- JSP模板元素 -- JSP页面中的静态HTML内容: 定义了JSP页面的基本骨架，即定义了页面的结构和外观。

- jsp页面中System.out.println()和out.println()的区别:
  - out.println()将输出打印在网页上；
  - System.out.println()将输出打印在控制台上；

###JSP指令: page & include###