# JavaEE学习笔记

标签（空格分隔）： JavaEE web开发 笔记

---

[TOC]

## 专用单词

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

---
## 中文乱码问题

- 在JSP页面上输入中文，请求页面不出现乱码:
    - 保证`contentType="text/html; charset=UTF-8",pageEncoding="UTF-8" `
    - charset和pageEncoding的字符编码类型一致，且都支持中文即UTF-8;
<br/>
- 获取中文参数值: 默认请求参数在传输过程中使用的编码为ISO-8859-1;
```
对于POST请求: 只要在获取请求参数request.getParameter()之前，调用request.setCharacterEncoding("UTF-8")方法即可;

对于GET请求: 需要修改Tomcat的server.xml文件配置,为Connector 节点添加useBodyEncodingForURI="true";但同样需要调用request.setCharacterEncoding("UTF-8")方法;
```

---
## 错误与警告

- 新建动态web项目(DynamicWebProject)时选择动态web模块版本2.5或3.0的区别; 
    - 2.5和3.0是指servlet的版本，是2.5的还是3.0的;  
    - web.xml文件头信息<web-app></web-app>包含版本(2.5)若与项目实际版本(3.0)不同,将可能导致java文件编译失败,引发`ClassNotFoundException`;
<br/>   
- 需在用户环境配置文件/etc/profile中添加CLASSPATH等环境变量，否则编译时无法找到各种package；如`error: package javax.servlet does not exist`
<br/> 
- 出现`error: package javax.servlet does not exist`情况的三种解决方法：
  - 修改~/.bashrc文件的CLASSPATH变量，添加servlet-api.jar的绝对路径；
  - 修改/etc/profile文件，做法如上;
  - 将servlet-api.jar拷贝到/jdk/jre/lib文件夹下;
<br/>
- Servlet的两种配置(web.xml和annotation注解)同时只能存在一种，否则在启动Tomcat时，会因为存在两个`url-pattern`而报错:
```
The servlets named [ListAllStudentsServlet] and [com.qiuku.javaweb.mvc.ListAllStudentsServlet] 
are both mapped to the url-pattern [/listAllStudents] which is not permitted.
```

---
## Servlet

- ServletConfig对象: 对应于当前WEB应用的特定Servlet;(局部)
  ServletConfig对象代表某一个Servlet
  - `getInitParameter(String name)`: 获取指定参数名(name)的初始化参数值(value);
  - `getInitParameterNames()`: 获取参数名组成的Enumeration对象;
<br/>   
- ServletContext对象：对应于当前WEB应用的所有Servlet;(全局)
  可以由ServletConfig对象获取，ServletContext对象代表当前WEB应用
  - `getInitParameter(String name)`：获取指定参数名(name)的初始化参数值(value);
  - `getInitParameterNames()`：获取参数名组成的Enumeration对象;
  - `getRealPath(String Path)`：获取当前WEB应用的某一文件部署在服务器上的绝对路径;
  - `getContextPath()`：获取当前WEB应用的名称;
  - `getResourceAsStream(String Path)`：获取当前WEB应用的某一文件对应的输入流;
<br/>  
- `ServletContext`设置的初始化参数可以为WEB应用的所有Servlet所获取，而`ServletConfig`的初始化参数只由那个特定Servlet获取;
<br/>  
- `ServletRequest`和`HttpServletRequest`接口：
  - HttpServletRequest比ServletRequest多了一些针对于Http协议的方法。例如：getHeader()，getMethod()，getSession();
  - 封装了请求信息，可以从中获取到任何的请求信息;
  - `String getParameter(String name)`: 根据请求参数的名字，返回参数值;
  - `String[] getParameterValues(String name)`:  根据请求参数的名字，返回请求参数对应的字符串数组;
  - `Enumeration getParameterNames()`: 返回参数名对应的Enumeration对象，类似于getInitParameterNames()方法;
  - `Map getParameterMap()`: 返回请求参数的键值对：key-参数名，value-参数值；String数组类型;
<br/>  
- `ServletResponse`和`HttpServletResponse`接口：
  - 封装了响应信息，如果想给用户什么响应，调用该接口的方法即可实现;
  - `getWriter()`: 返回PrintWriter对象，调用该对象的print()方法，可以将print()中的参数打印到浏览器上;
  - 设置响应的内容类型: response.setContentType("application/msword"); -- 请求后,浏览器会下载一个word文档,内容为print()的参数;
  - `void sendRedirect(String location)`: 请求的重定向;(此方法为HttpServletResponse接口中定义的.)
```java
public void service(ServletRequest request, ServletResponse response)                 
    throws ServletException, IOException{                                                  
 } 
```

- 以上两个(四个)接口的实现类都是服务器给予实现的，并在服务器调用`service()`方法时传入;
<br/>  
- `GET`请求和`POST`请求：
  - GET请求使用于在浏览器输入URL或单击网页上的一个超链接时;
  - GET方式传送的数据量一般在1KB以下;
  - POST请求主要用于向WEB服务器提交FORM表单中的数据;
  - POST方式将表单字段元素及其数据作为HTTP消息的实体内容发送给WEB服务器;
  - POST方式传送的数据量比使用GET方式要大得多;
<br/>  
- 在servlet的配置当中，`<load-on-startup>5</load-on-startup>`的含义是：
  - 当值为0或者大于0时，表示在WEB应用启动时就加载这个servlet;
  - 当是一个负数时或者没有指定时，则指示容器在该servlet被选择时才加载;
<br/>  
- 抽象Servlet类`GenericServlet`:
  - 是一个Servlet,是Servlet接口和ServletConfig接口的实现类。
  - 是一个抽象类，其中的service()方法为抽象方法。
  - 新建的Servlet程序直接继承GenericServlet会使开发更简洁。
```
public abstract class GenericServlet extends Object
                                     implements Servlet,ServletConfig,Serialize{
}
```
 
- *`HttpServlet`: 是一个抽象Servlet类，继承自GenericServlet。
  - 针对HTTP协议所定义的一个Servlet基类;
  - 在`service()`方法中把ServletRequest和ServletResponse强制转为HttpServletRequest和HttpServletResponse,并调用了重载的`service(HttpServletRequest,HttpServletResponse)`;
  - 在`service(HttpServletRequest,HttpServletResponse)`中获取请求方式:`request.getMethod()`;
  - 根据请求方式又创建了`doGet()`和`doPost()`方法;
  - 在实际开发中，直接继承HttpServlet，根据请求方式复写`doGet()`或`doPost()`方法即可;
  - 优点: 
<br/>
- Servlet的`annotation`(注解)配置和`web.xml`配置：
  - Annotation(注解)配置：直接在Servlet源文件中添加`@WebServlet("/listAllStudents")`语句即可;
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

---

## Servlet注解技术
- `@WebServlet`       取代servlet配置
- `@WebFilter`        取代filter配置
- `@WebInitParam`     取代初始化参数配置（servlet、filter）
- `WebListener`       取代listener配置

---

## JSP概述
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
- JSP文件可以放置在WEB应用程序中的除了`WEB-INF`外的任何目录中;
<br/>
- JSP的运行原理:JSP本质上是一个Servlet,每个JSP页面第一次被访问时,`JSP引擎`将它翻译成一个Servlet源程序(.java),接着将这个Servlet程序编译成class类文件,最后再由`WEB容器(Servlet引擎)`加载和执行这个由JSP页面翻译成的Servlet程序。
<br/>
- JSP的构成和JSP语法：JSP指令+脚本元素+动作元素。
  - `静态HTML内容`;
  - `JSP指令`: 封装在`<%@ 指令 属性名="值" %>`中，最常用的有page指令和include指令;
  - `JSP表达式`: 封装在`<%= %>`中，可以将一个Java变量或表达式的计算结果输出到客户端浏览器上;
  - `JSP脚本`: 封装在`<% %>`中 (不需显示内容,没有输出的Java语句)
  - `JSP声明`: 将Java代码封装在<%!%>中，它里面的代码将被插入到JSP对应的Servlet的_jspService方法的外面,几乎从来不用;
  - `JSP动作/标签`:
  - `*JSP注释`: `<%-- JSP代码 --%>`  可以阻止Java代码的执行;
     HTML注释: `<!-- HTML注释 -->`  提供说明性内容，不可以阻止Java代码的执行;
<br/>
- JSP页面的9个内置(隐含)对象:【没有声明就能使用的对象】;
  - `request对象`: HttpServletRequest类的实例对象;

  - ×response对象: HttpServletResponse类的实例对象;(在JSP页面中几乎不会调用response对象的任何方法)

  - *`out对象`: JspWriter类的实例对象:调用`out.println()`方法可以直接把字符串打印到浏览器上;

  - *`session对象`: HttpSession类的实例：代表浏览器和服务器的一次会话。
                            主要用于保存每个用户的信息，跟踪每个客户的操作状态;
                         
  - *`application对象`: ServletContext类的实例:代表当前WEB应用，其信息被一个WEB容器中的所有用户(JSP)所共享;

  - ×config对象: 当前JSP对应的Servlet的ServletConfig对象:保存了当前JSP对应的Servlet的初始化参数等信息。但若需访问这些初始化参数，必须通过映射的地址;

  - *`exception对象`: 只有在声明了page指令的isErrorPage="true"时，才可以使用; `<%@ page isErrorPage="true"%>`；首先创建一个能够引发错误的JSP页面errorCause.jsp,并指定其错误处理页面为errorProcess.jsp;

  - *`pageContext对象`: 页面的上下文，是PageContext类的实例。可以从该对象获取到其他8个对象,也可以从中获取当前页面信息;

  - ×page对象: Object基类的实例对象:指向当前JSP对应Servlet对象的引用，只能调用Object类的方法;

`使用顺序:pageContext，request，session，application(对属性的作用域范围从小到大)`

- Cookie: 
  - `response.addCookie(cookie)`: 用于将封装好的Cookie对象传送到客户端浏览器;
  - `request.getCookie()`: 用于读取保存到客户端浏览器的Cookie;
<br/>
- `JSP模板元素` 
  - JSP页面中的静态HTML内容: 定义了JSP页面的基本骨架，即定义了页面的结构和外观。
<br/>
- jsp页面中`System.out.println()`和`out.println()`的区别:
  - `out.println()`将输出打印在网页上；
  - `System.out.println()`将输出打印在控制台上；

---
### JSP指令: page & include

- JSP指令简介:JSP指令是为JSP引擎而设计的，它们并不直接产生任何可见输出，只是告诉引擎如何处理JSP页面中的其余部分;
<br/>
- JSP2.0中定义了page、include和taglib这三种指令，每种指令又定义了一些各自的属性(properties);

 `<%@page %>`
- page指令: 格式为<%@page 属性名="值" %>，如`<%@ page contentType="text/html;charset=gb2312" %>`;
  - page指令用于定义JSP页面的各种属性，它的作用范围是整个JSP页面，一般放置于JSP页面的起始位置;
  - 属性名是大小敏感的;
  - 常用属性:
```
language="java"
extends="package.class"
import="java.xxx.xxx"      指定当前JSP页面对应的Servlet程序需要导入的类;
session="true | false"     指定当前JSP页面的session隐含对象是否可用; 
errorPage="relative_url"   指定当前JSP页面出现错误时的实际响应页面是什么,其中 / 表示的是当前WEB应用的根目录;
isErrorPage="true | false" 指定当前JSP页面是否为错误处理页面，并指定当前页面是否可以使用exception隐含对象;
contentType="text/html; charset=UTF-8" 指定当前JSP页面的响应类型(text/html)和返回页面的字符编码类型(UTF-8);
contentType="application/msword; charset=UTF-8"  实际调用的是response.setContentType()方法;
pageEncoding="characterSet | UTF-8"  指定当前JSP页面的字符编码,通常情况下和contentType的一致;
isELIgnored="true | false"  指定当前JSP是否可以使用EL表达式,通常取值为true;
```

- `errorPage`和`isErrorPage`:
  - `errorPage` 指定当前JSP页面出现错误时的实际响应页面是什么，其中 / 表示的是当前WEB应用的根目录;
  - `<%@ page errorPage="/error.jsp" %>` 在响应error.jsp时，JSP引擎使用的是请求转发的方式;
  - `isErrorPage` 指定当前JSP页面是否为错误处理页面，指定当前页面是否可以使用exception隐含对象;
    一般情况下，不建议能够直接访问该错误处理页面;
  - 如何使客户不能直接访问某一个页面呢？对于Tomcat服务器而言，`WEB-INF`下的文件是不能直接在浏览器中输入地址来访问的，但通过请求的转发是可以的！
  - 还可以在web.xml文件中配置错误页面: 错误页面.PNG;

 <br/>`<%@include %>`
- include指令: `<%@include file="relative_url" %>`;
  - `静态引入`: 当前JSP页面与静态引入的页面紧密结合为一个Servlet;
  - 引入文件和被引入文件在被JSP引擎翻译成Servlet的过程中进行合并，而不是先合并源文件后再对合并结果进行翻译;
  - file属性的设置值必须使用相对路径; 
  - / 表示当前WEB应用的根目录，而不是WEB站点根目录;

---
### JSP标签/动作

- Action元素(动作元素)可以完成各种`通用的JSP页面功能`，也可以实现一些`处理复杂业务逻辑的专业功能`;
<br/>
- Action元素采用XML语法格式，即每个Action元素在JSP页面中都以XML标签的形式出现;
<br/>
- 标准格式:如`<jsp:include />`、`<jsp:forward />`等;
<br/>
- `<jsp:include>`标签:
  - 用于把另外一个资源的输出内容插入到当前JSP页面的输出内容之中;
  - 这种在JSP页面执行时的引入方式称之为`动态引入`;
<br/>
- `<jsp:include page="url" />`标签和`<%@ include file="url" %>`指令的区别:
  - 标签为`动态引入`，生成两个Servlet源文件;
  - 指令为`静态引入`，只生成一个Servlet源文件;
  - 被动态引入的资源必须是一个能被WEB容器独立调用和执行的资源;
  - include指令只能引入遵循JSP格式的文件，被引入文件和当前JSP文件共同被转译为一个Servlet源文件;

- `<jsp:forward>`标签:
  - `<jsp:forward page="/b.jsp">...</jsp:forward>`
 
    相当于
  
  - `request.getRequestDispatcher("/b.jsp").forward(request,response);`

  - 区别: `<jsp:forward>`标签可以使用`<jsp:param>`子标签传入一些参数，同理`<jsp:include>`标签也是可以的;

---
### 和属性相关的方法

`attrName`    `attrValue`

- `void serAttribute(String name ,Object o)`: 为对象设置属性;

- `Object getAttribute(String name)`: 根据属性名，获取指定的属性;

- `Enumeration getAttributeNames()`: 获取所有属性的名字组成的`Enumeration`对象;

- `removeAttribute(String name)`: 根据属性名，移除指定的属性;

- `pageContext,request,session,application`对象都有这些方法，这四个对象也称之为`域对象`;

- 四个`域对象`的作用域范围:
  - pageContext: 属性的作用范围仅限于`当前JSP页面`及其对应Servlet程序;

  - request: 属性的作用范围仅限于`同一个请求`;(*在有转发的情况下可以跨页面获取属性值)

  - session: 属性的作用范围限于`一次会话` , 浏览器打开直到关闭称为一次会话;

  - application: 属性的作用范围限于`当前WEB应用`。只要在一次设置属性，则在该WEB应用的其他JSP或Servlet中都可以获取这个属性;

---
## 请求转发和请求重定向

- `RequestDispatcher`接口: 请求转发器接口;
<br/>
- 使用forward方法实现请求转发:
```java
request.getRequestDispatcher("/"+path).forward(request,response);
```

- 使用sendRedirect方法实现请求重定向:
```java
response.sendRedirect(path);
```
- 两者的运行流程
  - 请求的转发: 请求从浏览器发出后，`直接在中转页面转发到最终页面`，无需返回浏览器;
  - 请求重定向: 请求从浏览器发出后，`需要从中转页面返回到浏览器`，再由浏览器发二次请求到最终页面;
<br/>
- 两者的本质区别:请求转发只发出了一次请求，而请求重定向则发出了两次请求;
  - `forward()`向服务器发了一个请求 -- 所以地址栏是初次请求的地址;
  - `sendRedirect()`向服务器发了两个请求 -- 所以地址栏不再是初次请求的地址,而是最后响应的地址;

  - 请求的转发: 最终Servlet中的request与中转Servlet的request是同一个对象;
    请求重定向: 最终Servlet中的request与中转Servlet的request不是同一个对象;

  - 请求的转发: 只能转发给当前WEB应用的资源;
    请求重定向: 可以重定向到任何资源;

  - 请求的转发: / 代表的是`当前Web应用`(如`http://localhost:8080/webapp`)的根目录;
    请求重定向: / 代表的是`当前Web站点`(如`http://localhost:8080`)的根目录;

---
## JavaBean

- JavaBean是一个Java类，最简单的JavaBean是一个只包含属性及其`get/set方法`的类；
<br/>
- JavaBean的作用：能够自动封装JSP页面提交时所包含的全部表单参数信息，可以方便JSP页面之间的参数传递和业务封装；
<br/>
- 在JSP中使用JavaBean即`在JSP中使用Java类`:
  - 创建JavaBean实例对象:
  - 使用该实例对象封装`前一页面`提交的表单参数:
  - 读取JavaBean实例中的属性值:
<br/>
- JavaBean的四个属性:
  - `Simple属性`: 一个Simple属性表示一个伴随有一对set/get方法的变量；
  - `Index属性`: 一个Index属性表示一个数组值。使用对应的set/get方法可获取数组中的数值；
  - `Bound属性`: 当该种属性的值发生改变时，要通知其他的对象；
  - `Constrained属性`: 当这个属性的值要发生改变时,与这个属性级联的其他Java对象可否决属性值的改变；
<br/>
- 企业级JavaBean(EJB):
  EJB是一个服务器端组件模型，最大的用处是部署分布式应用程序，用EJB技术部署的分布式系统可以跨平台；

---
## MySQL
`一种流行的关系数据库`

- Windows下MySQL免安装版的配置方法:
  - 添加环境变量`MYSQL_HOME`,变量值为`F:\mysql-5.7.21-winx64`;
  - 修改环境变量`PATH`，添加新值`F:\mysql-5.7.21-winx64\bin``
  - 在安装目录下新建配置文件`my.ini`；
  - 将MySQL安装为Windows服务: `mysqld install MySQL --defaults-file="%MYSQL_HOME%\my.ini"`;
  - 创建data文件夹和默认的数据库: `mysqld --initialize-insecure --user=mysql`;
  - 启动MySQL服务:`net start mysql`;
<br/>
- `MySQL-Front`: MySQL客户端工具

---

## JDBC
`Java数据库连接`

- JDBC的定义:
  - JDBC是Java程序中与数据库通信的应用;
  - JDBC是一种用于执行SQL语句的Java API,可以为多种关系数据库提供统一访问;
  - JDBC是一组用Java语言编写的类和接口;
<br/>
- JDBC的驱动程序: 有四种模式，选择哪一种取决于程序的应用范围。
  - 模式1`JDBC-ODBC驱动`: JDBC-ODBC桥接器提供了了经由ODBC驱动进行访问的JDBC接口。实验用途或没有其他JDBC驱动可用;

  - 模式2`网络库`: 类似于JDBC-ODBC桥接器，需要load到客户机，是一个部分用Java实现的驱动接口。它将JDBC调用转换为对数据库客户端接口的调用;
 
  - 模式3`纯Java驱动程序`: 由数据库厂商实现的基于本地协议的驱动，直接调用DBMS使用的网络协议。适用于企业内网;

  - 模式4`通过中间件服务器`: 纯Java驱动，将JDBC调用转换为中间网络协议，中间网络协议层起到一个读取数据库的中间件的作用，能连接多种类型的数据库。适用于企业内网;
<br/>
- JDBC的主要对象:
  - 连接对象`Connection`: `Connection对象`代表与数据库的连接。连接过程包括执行的SQL语句和返回的结果；

  - 声明对象`Statement`: JDBC提供了三个类`Statement、PrepareStatement、CallableStatement`，用于向数据库发送SQL语句并执行。如果声明对象执行的是Select语句，则将返回一个结果集(ResultSet)对象；

  - 结果集对象`ResultSet`: `ResultSet`是一个存储查询结果的对象，同时具有操纵数据的功能；
<br/>
- JDBC的工作步骤:
  - register、load一个`driver`; (在连接数据库之前，首先要将该数据库的驱动程序加载到JVM)
  - 创建数据库连接；
  - 创建三个类`Statement、PrepareStatement、CallableStatement`的实例`语句对象`，发送SQL语句，并执行SQL语句；
  - 处理SQL结果集`ResultSet`;
  - 关闭`Statement`;
  - 关闭连接`Connection`;
<br/>
- `语句对象`:
  - `Statement的实例`：通过语句对象执行SQL语句，语句对象有以下三种；
  - `Statement对象`：执行简单的、无参数的SQL的语句；
  - `PrepareStatement对象`：预编译语句对象；
  - `CallableStatement对象`：用来执行存储过程；
<br/>
- `数据库连接池`:服务器提供的一个拥有很多物理连接的缓冲池`connection pooling`。
  - 减少了连接、断开连接和重新连接数据库的开销；
  - 通过设定连接池最大连接数防止系统无尽的与数据库连接；
  - 通过连接池管理机制监视数据库的连接的数量、使用情况；
<br/>
- `元数据`:
  - 元数据用于描述数据库或数据库一部分的数据，是关于数据的数据；
  - 元数据被用于动态地调节数据库的内容和结果集，因此元数据分为数据库元数据和结果集元数据两类；
  - 数据库元数据`DatabaseMetaData`: 从数据库连接对象中获取，提供关于特定数据库的信息；
  - 结果集元数据`ResultSetMetaData`: 从结果集ResultSet中获取，提供关于该结果集的相关信息；

---
## 关于JavaEE打包

- jar包和war包: web所有包都是用jar打的，只是目标文件的扩展名不一样。
  - war是一个WEB模块，其中包含了`WEB-INF`，因此是可以直接运行的WEB模块；
  - jar一般只是包括一些class文件，若声明了`Main_class`则可以用java命令运行；
  - 将war包放置在Tomcat的`\webapps`目录下。启动Tomcat后，这个war包会自动解压成`web应用文件夹`，相当于部署；
<br/>
- 总结:
  - war包:做好一个web应用(如网站)后，打成包部署到web容器(如Tomcat)中；
  - jar包:通常是开发时要引用各种通用类(class)，打成包便于使用和管理；
  - ear包:企业级应用，通常是EJB打成ear包；
<br/>
- Ubuntu下实时监控Tomcat日志:
  - 先切换到Tomcat的日志目录`logs/`；
  - 键入`tail -f catalina.out`即可实时查看运行日志；
  
---

## **MVC**

### MVC设计模式

- MVC设计模型将应用程序分为三个核心模块(模型、视图、控制器)，它们各自处理自己的任务;
<br/>
- JavaEE组件通过配置(业务逻辑)组装成web应用;
<br/>
- *模型M*:
  - `业务模型`：业务逻辑+业务数据;
  - 一个模型能为多个视图提供数据;
  - 模型代码可以被多个视图重用;
  - 每一个业务都会去操作单独的数据库的数据表，整个放在一起，算一个业务操作;
<br/>
- *视图V*:
  - `JSP页面`：用户看到并与之交互的界面;
  - 视图接受用户的输入;
  - 视图向用户显示相关的数据;
  - 不进行任何实际的业务操作;
<br/>
- *控制器C*：
  - `Servlet`: 接收浏览器端请求的服务器端程序`Server Applet`;
  - 控制器接受用户的输入并调用模型和视图去完成用户的需求;
  - 控制器接收请求并决定调用哪个`模型组件`去处理请求，然后决定调用哪个`视图`显示处理后返回的数据;
<br/>
- 总结：
  - M： Model .  Dao基类;
  - V： View  .  JSP，在JSP页面上编写Java代码实现显示;
  - C： Controller  .  Servlet:
    > 接收请求request
      获取请求request参数
      调用DAO的方法
      把DAO方法的返回值放入request中
      转发/重定向JSP页面
  - 当目标响应页面不需要从request中读取任何值时，则可以使用重定向;(如此还可以防止表单的重复提交）

### MVC设计模式的具体案例

- 关于这个MVC案例：
  - 没有业务层，直接由Servlet调用DAO方法,所以也没有业务操作,所以可以在DAO中直接获取Connection对象;
  - 采取MVC设计模式;
  - 使用到的技术：
    > MVC设计模式: JSP + Servlet + POJO/DAO ;
      MySQL数据库;
      C3P0数据库连接池;
      JDBC工具为DBUtils;
      页面上的提示操作使用jQuery;
  - 技术难点:
    ```多个请求如何使用同一个Servlet;
       模糊查询;
       在创建或修改的情况下，验证用户名是否已经被使用，并给出提示;```
<br/>
- 架构分析
  - JSP:
  - Servlet:
  - DAO:
  - MySQL:
  - 注意事项: 不能跨层访问; 只能自上向下依赖，而不能自下向上依赖;
```graphLR
    A[JSP] -->|Request| B(Servlet)
    B -->|Response| A
    B -->|Call| C[DAO]
    C -->|Value| B
    C -->|CRUD| D[MySQL]
    D -->|ResultSet| C
```

- DAO层的设计与实现:
<br/>
- 多个Request使用同一个Servlet：
<br/>
- 模糊查询：
<br/>
- 验证用户名并提示:
  