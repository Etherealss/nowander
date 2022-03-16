# 环境

# 功能

## 缓存

### 草稿缓存

网上看到的思路：

前端保存"上一次更新时间"，redis缓存文章的key是draft_userId:articleId:updateTime，生存时间为10分钟。
再使用draft_userId:articleId缓存最后的更新时间。
获取最新的草稿时，先获取最后的更新时间，再拼接key获取。而之前的缓存还没过期之前也不会被访问到

### 页面侧栏缓存

每一行都是一个缓存项popular_article，整个侧栏合起来是一个大的缓存项all_popular_articles

当有一个popular_article更新时，移除all_popular_articles

### 页面分层缓存

一个大页面（比如首页）会由多个板块组成，这些板块又由更小的部分组成。

举例：文章页面 - 文章、评论区 - 评论区的一条评论及其回复 - 每一行回复

可以将这些数据进行分层缓存。缓存key的设计是：article_level_index_userId:articleId

level区分不同三层级，index区分同一层的不同行

存json，读json，传json。

当下层数据发生更新时，要删除上层缓存。

当某一块缓存缺失时，需要获取缓存并且拼接json，所以要使用hash缓存页面数据及其key，key可以用于拼接json

article_0_0_userId:articleId page data
article_1_0_userId:articleId article data
article_1_1_userId:articleId comments data
article_2_0_userId:articleId comment:

## SpringSecurity

### 基于token的登录和登出

token黑名单

用户退出登录时，token无法立即失效。所以在退出登录时将用户名存入redis中的token黑名单中。

不存储失效的token，因为没必要，直接用username比较就行了

用户重新登录时，将username从黑名单中移除

在jwt filter中检查token时，检查username是否在黑名单中，如果有，则token失效，需要登录

### reflesh token

本质上也是一个token，它可以用来申请一个新的token，但本身并没有特别的功能

通过reflesh token获取新token的接口是开放的

### 验证码功能

##Web

### 全局异常处理器


### 全局返回值处理器

### 获取当前登录用户的参数解析器

### 路径参数检查的拦截器

## 持久层

### MyBatis自定义类型处理器 TypeHandler

可以用于将 集合 自动转为 JSON 格式存在数据库中

见 JsonSetTypeHandler

## 多线程

### 检查是否已点赞

模拟行级锁 LikeServiceImpl#likeOrUnlike

## 消息通知

### 消息通用要素

1. 是否已读
2. 消息模板
3. 消息其他属性（json）

### 添加好友

要素：
1. 申请人
2. 申请对象
3. 申请信息
4. 是否通过

### 点赞

要素
1. 点赞用户
2. 点赞目标id
3. 点赞目标类型（哪个表的id）
4. 附加属性（比如，点赞的文章的url以及文章标题）

### 评论

要素
1. 评论用户
2. 评论内容
3. 评论目标id
4. 评论目标类型
5. 附加属性（比如，评论的文章的url以及文章标题）

### 关注

要素
1. 关注用户
2. 附加属性（从哪里关注的）

### 私信

要素
1. 发送者
2. 接收者
3. 内容


# 踩坑

## docker的使用

## 多模块打包

参考连接1

参考连接2

参考连接3

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
服务器mysql访问权限

问题：项目运行时访问数据库，出现：java.net.ConnectException: Connection refused (Connection refused)

尝试通过idea链接远程数据库，报错：Host is not allowed to connect to this MySQL server

参考链接1

注意：mysql 8 以后不允许在 GRANT 命令后设置密码
```shell
    update user set host='%' where user='nowander_user';
    alter user nowander_user identified with mysql_native_password by 'baotamysql123456'; # 明文加密方式
    Grant all privileges on nowander.* to 'nowander_user'@'%';
    Grant all privileges on nowander.* to 'nowander_user'@'%'; # 执行两次
```
测试

多模块测试
```java
    @SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = com.nowander.starter.NowanderApplication.class)
    @ComponentScan(basePackages = "com.nowander.blog.mapper")
```
Websocket测试

引入websocket 后使用junit测试保存 javax.websocket.server.ServerContainer not available
在测试类上面加上注解
```java
    @SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
```