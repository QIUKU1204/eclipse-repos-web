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

### issues

 - 中文乱码问题: 
   - 在addCustomer.jsp页面中输入中文信息后，这些中文参数经request传到CustomerServlet中的add()方法，再由CustomerDAO的实现类的save()方法插入到数据表中;
   - update.jsp同理;
   - 中文数据在传到Servlet中并被request.getParameter()读取时就已经变成乱码: `æµ?ç?°ç??å¤®` 显然这是由网络传输所导致的;
   - 解决方法: 与jsp中文乱码的相同 `request.setCharacterEncoding("UTF-8")`;
   - 使用save()方法将中文数据写入数据表时也会出现乱码问题: `每个中文将变为1个?`;
   - 解决方法: `jdbc:mysql://localhost:3306/mvc_database?characterEncoding=utf8`;
 
   
 - 在index.jsp页面单击Query后，应使查询条件停留在对应的输入文本框内;
 