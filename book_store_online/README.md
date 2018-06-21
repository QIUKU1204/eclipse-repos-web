# book_store_online


### 项目说明
 - 一个简单的在线书城Web应用, 依旧使用了MVC设计模式;
 
 
### 功能
 - 查看图书信息: 
   - 查看图书详细信息;
   - 条件查询图书信息;
   - 图书列表翻页;
 - 把图书加入购物车;
 - 查看购物车;
 - 修改购物车:
   - 清空购物车;
   - 删除单本图书;
   - 修改单本图书数量;
 - 结账:
   - 填写用户名和账号信息;
 - 查看交易记录;


### 部署

[当当书店](http://47.106.11.169:8080/BookStore/index.jsp)
[管理员后台](http://47.106.11.169:8080/BookStore/manage/login.jsp)
 PS: 管理员账号root; 管理员密码network
 
### 更新

 - 2018-5-20: 完成网络书店的登录与注册功能;
 - 2018-5-26: 使用Filter过滤器实现 Login-Check 功能;
 - 2018-5-30: 完成查看图书信息功能;
 - 2018-6-13: 完成主体功能: 商品展示、商品查询、分页翻页、购物车、下单支付等;
 - 2018-6-15: 完善个人信息: 绑定邮箱、绑定信用卡、收货信息等;
 - 2018-6-16: 完成后台管理功能: 图书管理、用户管理及订单管理;(此处遗漏了新增图书项的功能)
 - 2018-6-18: 修复登录页面和注册页面在浏览器窗口缩小时挤压变形的问题;
 - 2018-6-20: 修复一些BUG; 完善整个WEB应用的权限系统;
 
### Goal

 - 图书管理后台: 新增图书项的功能;
 - 继续修复BUG;

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
   
 3. 记录一个异常: 关于静态实例对象的使用 ;
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
 
 1. 空字符串是""，会创建一个对象，有内存空间；而null，不会创建对象，没有内存空间
 
 2. String.trim()方法 remove 字符串的首尾空白字符 (leading and trailing whitespace);
 
 3. 重定向可以使用基于当前web应用的绝对路径，请求转发不可以使用该绝对路径;
 
 4. 在请求转发中不能使用"绝对路径"`request.getContextPath() + "/Check-Code/index.jsp"`的原因: 
    - 请求转发的  / 代表当前web应用的根目录;
    - 重定向的 / 代表当前站点的根目录;
    
 5. 关于类的静态成员(具有 static 关键字): 由类和类的多个对象共同拥有的一个属性或方法, 其在内存中只有一个拷贝;
    - 静态成员可以被类名或类实例对象所调用，但类名只可以调用静态成员，因此静态成员即类成员;
    - 静态变量: 既可以被静态方法调用，也可以被实例方法调用;
    - 静态方法: 只能调用静态变量，不能调用实例变量;
    - 静态代码块;
    - PS: 类的静态实例对象同时只能在一处被使用，多处使用可能产生空指针异常;
     
 6. 将字符串 String 转换为整型 Int 的两种方法
    - int i = Integer.parseInt(String s);
    - int i = Integer.valueOf(s).intValue();
    
 7. 将整数 int 转换成字符串 String 的三种方法
    - String s = "" + i;
	- String s = String.valueOf(i);
	- String s = Integer.toString(i);
	
 8. ThreadLocal 的原理:
 	- ThreadLocal 是如何做到为每一个线程维护 变量副本 的呢?
 	- 实现思路: 在ThreadLocal类中有一个Map, 用于存储每一个线程的变量副本, Map中元素的键为  线程对象, 而值对应线程的变量副本;

 9. 关于泛型 <T> 
 
 10. 关于事务 transaction
     - `connection.setAutoCommit(boolean);`
     - connection 的所有SQL语句将作为一个独立的事务被执行和提交;
     - `connection.commit();`
     - 使上一次commit/rollback以来的所有changes生效;
     - `connection.rollback();`
     - commit和rollback方法都只能在auto-commit模式被禁用时使用;
	
