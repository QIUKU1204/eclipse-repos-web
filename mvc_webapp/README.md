# mvc_webapp


### 项目说明
 - 一个应用了mvc设计模式的简单mvc案例
 
 
### 功能
 - 数据库查询、插入、删除、修改;
 - 模糊查询;
 - 删除提示、验证提示;
 - 验证用户名是否可用;


### 部署

[mvc案例](http://47.106.11.169:8080/mvcapp/index.jsp)
 
 
### 更新

 - 2018-4-19: 新建动态Java工程`一个mvc案例`;
 - 2018-4-23: 在本mvc案例中新增查询、删除及模糊查询功能，且实现多请求使用同一Servlet;
 - 2018-4-24: 增加新建用户功能，用户信息修改功能，用户名验证功能;
 - 2018-4-30: 测试了Cookie相关的API，实现自动登录和显示最近浏览商品;
 - 2018-5-20: 完成了网络书店的登录与注册功能;
 - 2018-5-26: 使用Filter过滤器实现了 Login-Check 功能;


### issues

 1. 中文乱码问题: 
    - 在addCustomer.jsp页面输入中文信息后，这些中文参数通过request传到CustomerServlet中的add()方法，再由CustomerDAO的实现类的save()方法插入到数据表中;
    - `JSP -> Servlet -> MySQL`
    - 中文数据传到Servlet中并被request.getParameter()读取时变成乱码: `æµ?ç?°ç??å¤®` 显然这是由网络传输所导致的;
    - 解决方法: `request.setCharacterEncoding("UTF-8")`;
    - 将中文数据写入数据表时也会出现乱码问题: `每个中文将变为1个?`;
    - 解决方法: `jdbc:mysql://localhost:3306/mvc_database?characterEncoding=utf8`;
    - 总结: 浏览器到Tomcat的URL要使用utf-8, Tomcat到数据库的URL同样要使用utf-8;
 
 2. 当存入数据的编码与MySQL数据库的编码不一致时，可能会出现如下异常:
    - `java.sql.SQLException: Illegal mix of collations (latin1_swedish_ci,IMPLICIT) and (utf8_general_ci,COERCIBLE) for operation '=' Query: SELECT count(id) FROM customers WHERE name = ? Parameters: [西瓜]`;
    - `java.sql.SQLException: Incorrect string value: '\xE6\x88\x90\xE9\x83\xBD' for column 'address' at row 1 Query: UPDATE customers SET name = ?, address = ?, phone = ? WHERE id = ? Parameters: [xigua, 成都, 15789456325, 1]`;
   
 3. 记录一个异常:
    - `private UserDAO userDAO = UserDAOFactory.getInstance().getUserDAO();`
    -  该静态实例对象同时只能在一处被使用，否则调用对象的方法时会产生空指针异常;
   
 4. Java的异常处理:
    - 被调用的内层方法若发生异常，会将异常往上抛，由外层方法的try...catch块捕获并处理；
    - 若发生异常的内层方法自带try...catch块，则由内层方法捕获并处理异常，不会再往外层抛；
    - 若一段代码前有异常抛出，且这个异常没有被捕获处理，则这段代码将无法继续执行；
    - 若一段代码前有异常抛出，且这个异常被try...catch捕获处理，则这段代码可以被执行；
   
 5. Java中的异常: 
    - 运行时异常(RuntimeException): 运行时异常是RuntimeException类及其子类的异常，是非受检异常，如NullPointerException、IndexOutOfBoundsException等;
      - JVM必须停止运行以改正这种错误，所以运行时异常可以不进行处理(当然也可以处理)，而由JVM自行处理;
      - Java Runtime会自动catch到程序throw的RuntimeException，然后停止线程，打印异常;
    - 非运行时异常(Exception): 非运行时异常是RuntimeException以外的异常，类型上都属于Exception类及其子类，是受检异常;
      - 非运行时异常必须进行处理（捕获或向上抛出），如果不处理，程序将出现编译错误;
      - 一般情况下，API中写了throws的Exception都不是RuntimeException;  
   
### note
 
 0. 简单工厂模式;

 1. 在index.jsp页面单击Query后，应使查询条件停留在对应的输入文本框内(回显);
 
 2. 在addCustomer.jsp页面单击Add后，输入文本框的内容应不变(回显);
 
 3. 新建用户成功时，在addCustomer.jsp页面中应显示红色警告信息; 失败时则提示绿色提示信息;
 
 4. 空字符串是""，会创建一个对象，有内存空间；而null，不会创建对象，没有内存空间
 
 5. String.trim()方法remove字符串的首尾空白字符 (leading and trailing whitespace);
 
 6. 利用 Cookie 实现自动登录;
 
 7. 利用 Cookie 显示最近浏览商品;
 
 8. 测试 Cookie 的作用范围和路径问题;
 
 9. 关于 HttpSession 对象的生命周期;
 
 10. 测试 HttpSession 接口的常用方法;
 
 11. 使用 UR L重写技术实现 Session 会话追踪管理;
 
 12. 使用 Session 实现简易购物车功能: 三个jsp页面 + 两个Servlet + 一个实体类 ; 使用 HttpSession 对象在请求重定向的情况下传递各种参数与属性;
 
 13. Web应用中的绝对路径与相对路径问题: 写相对路径可能会有问题, 写绝对路径则肯定没有问题; 在开发中建议使用绝对路径而不是相对路径; request.getContextPath();
 
 14. 表单的重复提交: 重复提交的原因; 重复提交的危害; 如何避免重复提交;
 
 15. 利用Session实现验证码功能: 两个Servlet: ValidateColorServlet 负责生成验证码图片 + CheckCodeServlet 负责验证是否一致及后续处理;
 
 16. 在原MVC案例中增加验证码功能: 重定向可以使用基于当前web应用的绝对路径，请求转发不可以使用该绝对路径;
 
 17. 在请求转发中不能使用"绝对路径"`request.getContextPath() + "/Check-Code/index.jsp"`的原因: 
     - 请求转发的  / 代表当前web应用的根目录;
     - 重定向的 / 代表当前站点的根目录;
     