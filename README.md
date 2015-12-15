# 在线迷你小说系统 MiniTxtBookSystem

------

该小说管理系统是客户端（Client）服务器端（Server）程序，主要有以下功能：

> * 用户注册
> * 用户登录
> * 显示所有小说分类
> * 根据小说分类查找该分类下所有小说
> * 小说下载
> * 小说上传


用户和小说信息都是保存在SQLite数据库中，具体的小说文件保存在服务器端具体日期文件夹中。

## 程序文件结构

> **src** 具体Java源代码  
> **config** 存放小说的配置信息，如服务器IP地址和端口号及下载路径`保存在minitxt.properties`文件中  
> **data** 存放具体的小说文件，按照文件上传的日期划分文件夹  
> **db** SQLite3数据库文件`minitxt.db`  
> **jar** 项目所需的jar包文件夹，存放一个SQLite的驱动包和JSON解析的jar包  

------

## 程序使用

先启动服务器端（`com.qf.server.MiniServer`），再启动客户端（`com.qf.client.MiniClient`），*可启动多个客户端*

### 1. 修改配置信息

```Java
socket.server.ip=127.0.0.1
socket.server.port=8888
txt.download.path=/Users/TeanWang/Documents/Test/
```
默认配置信息为上述信息，若要更改，请打开`config/minitxt.properties`文件，直接修改

### 2. 图解执行过程

```seq
Client->Server: 发送Entity（携带命令、对象、信息）
Note right of Server: 操作数据库，执行相对应的操作
Server-->Client: 发送Entity（携带是否成功、响应信息）
```

### 3. 说明

项目采用Socket+多线程实现，客户端发送entity对象给服务器端，服务器根据entity对象中command命令执行相对于的操作。

- [X] **数据** ：简单工厂模式获取与不同数据库的操作对象
- [X] **注册** ：只用正则验证 + JSON封装用户信息
- [X] **上传** ：JSON封装小说信息，字节流保存小说内容
- [X] **下载** ：接口回调获取小说信息