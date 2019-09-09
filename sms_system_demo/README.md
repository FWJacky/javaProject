简介

这个项目是一个简单的学生信息管理系统，通过这个项目希望能够熟悉SSM的整合开发

# 使用技术
IOC容器：Spring

Web框架：Spring MVC

ORM框架：MyBatis

安全框架：Shiro

数据源：C3P0

日志：log4j

前端框架：Bootstrap

# 快速上手
### 1、运行环境和所需工具
* 编译器：IntelliJ IDEA
* 项目构建工具：Maven
* 数据库：MySQL
* JDK版本：JDK1.8
* Tomcat版本：Tomcat9.x
### 2、初始化项目
* 在你的MySQL中，创建一个数据库名称为 student_manager_system 的数据库，并导入我提供的 .sql 文件,
* 进入src/main/resources修改mysql.properties配置文件,把数据库登录名和密码，改为你本地的密码
* 使用 IntelliJ IDEA 导入项目，选择Maven项目选项，一路点击next就行，导入项目后，如果src目录等，都没显示出来，别急先使用Maven构建该项目
* 在 IntelliJ IDEA 中，配置我们的 Tomcat， 然后把使用Maven构建好的项目添加到Tomcat中
* 运行
<img src="E:\project\sms\images\登录界面.png" alt="1567785055722"  />
* 登录账户
  * 管理员账户：admin
  * 老师账户：1001
  * 学生账户：10001
  * 密码均为：123
# 功能模块介绍
### 1、登录模块功能
使用Shiro权限管理框架，实现登录验证和登录信息的储存，根据不同的登录账户，分发权限角色，对不同页面url进行角色设置
### 2、管理员模块功能
管理员可对教师信息、学生信息、课程信息进行增删改查操作，管理员账户可以重置非管理员账户的密码
* 课程管理：当课程已经有学生选课成功时，将不能删除

* 学生管理：添加学生信息时，其信息也会添加到登录表中

* 教师管理：同上

* 账户密码重置

* 修改密码
  ![1567785302649](E:\project\sms\images\学生名单管理.png)
  ![1567785389595](E:\project\sms\images\修改学生信息.png)
  ![1567785429375](E:\project\sms\images\课程名单管理.png)

  ![1567785473352](E:\project\sms\images\教师信息管理.png)

  ![1567785499780](E:\project\sms\images\修改教师信息.png)

  ![1567785526190](E:\project\sms\images\重置密码.png)

  ![1567785551577](E:\project\sms\images\重置本账户密码.png)
### 3、教师模块功能
教师登陆后，可以获取其教授的课程列表，并可以给已经选择该课程的同学打分，无法对已经给完分的同学进行二次操作
* 我的课程

* 修改密码
  ![1567785618574](E:\project\sms\images\我的课程.png)
  ![1567785726256](E:\project\sms\images\已选该课程学生名单.png)
  ![1567785750604](E:\project\sms\images\学生成绩评分.png)

  ![1567785934011](E:\project\sms\images\教师端修改密码.png)
### 4、学生模块功能
学生登录后，根据学生信息，获取其已经选择的课程和已经修完的课程
* 所有课程: 在这里选修课程，选好后，将会自动跳转到已选课程选项

* 已选课程: 这里显示的是，还没修完的课程，也就是老师还没给成绩，由于还没有给成绩，所以这里可以进行退课操作

* 已修课程: 显示已经修完，老师已经给成绩的课程

* 修改密码
  ![1567785990956](E:\project\sms\images\课程列表.png)
  ![1567786017669](E:\project\sms\images\已选课程.png)
  ![1567786040157](E:\project\sms\images\已修课程.png)

  ![1567786253217](E:\project\sms\images\退课.png)