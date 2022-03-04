# 环境

# 功能

# 技术架构

## SpringSecurity

### 基于token的登录和登出

#### token黑名单

用户退出登录时，token无法立即失效。所以在退出登录时将用户名存入redis中的token黑名单中。

不存储失效的token，因为没必要，直接用username比较就行了

用户重新登录时，将username从黑名单中移除

在jwt filter中检查token时，检查username是否在黑名单中，如果有，则token失效，需要登录

### reflesh token

本质上也是一个token，它可以用来申请一个新的token，但本身并没有特别的功能

通过reflesh token获取新token的接口是开放的

### 验证码功能

## Web

### 全局异常处理器

### 全局返回值处理器

### 获取当前登录用户的参数解析器

### 路径参数检查的拦截器

## 持久层

### MyBatis自定义类型处理器 TypeHandler

可以用于将 集合 自动转为 JSON 格式存在数据库中

见 JsonSetTypeHandler


# 踩坑

## docker的使用


## 多模块打包

[参考连接1](https://blog.csdn.net/qq_36636154/article/details/109060638
)

[参考连接2](https://blog.csdn.net/weixin_44066622/article/details/105916607?spm=1001.2101.3001.6650.1&utm_medium=distribute.pc_relevant.none-task-blog-2%7Edefault%7ECTRLIST%7ERate-1.pc_relevant_default&depth_1-utm_source=distribute.pc_relevant.none-task-blog-2%7Edefault%7ECTRLIST%7ERate-1.pc_relevant_default&utm_relevant_index=2)

[参考连接3](https://www.jb51.net/article/196059.htm)


只需要对启动类模块打包。在启动类模块下使用mvn package

其他模块的pom插件：
```xml
    <build>
        <plugins>
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-compiler-plugin</artifactId>
            </plugin>
        </plugins>
    </build>
```
启动类模块的：
```xml
    <build>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
                <executions>
                    <execution>
                        <id>repackage</id>
                        <goals>
                            <goal>repackage</goal>
                        </goals>
                    </execution>
                </executions>
                <configuration>
                    <mainClass>
                        ${start-class}
                    </mainClass>
                </configuration>
            </plugin>
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-compiler-plugin</artifactId>
                <version>3.1</version>
                <configuration>
                    <source>${java.version}</source>
                    <target>${java.version}</target>
                    <encoding>${project.build.sourceEncoding}</encoding>
                    <buildDirectory>${project.build.directory}</buildDirectory>
                </configuration>
            </plugin>
        </plugins>
    </build>
```

## 服务器mysql访问权限

问题：项目运行时访问数据库，出现：java.net.ConnectException: Connection refused (Connection refused)

尝试通过idea链接远程数据库，报错：Host is not allowed to connect to this MySQL server

[参考链接1](https://www.runoob.com/w3cnote/mysql8-error-1410-42000-you-are-not-allowed-to-create-a-user-with-grant.html)

注意：mysql 8 以后不允许在 GRANT 命令后设置密码
```shell
update user set host='%' where user='nowander_user';
alter user test identified with mysql_native_password by 'xxx'; # 明文加密方式
Grant all privileges on nowander.* to 'nowander_user'@'%';
Grant all privileges on nowander.* to 'nowander_user'@'%'; # 执行两次
```

## 测试
#### 多模块测试

```java
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = com.nowander.starter.NowanderApplication.class)
@ComponentScan(basePackages = "com.nowander.blog.mapper")
```

#### Websocket测试

引入websocket 后使用junit测试保存 javax.websocket.server.ServerContainer not available
在测试类上面加上注解
```java
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
```
