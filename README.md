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


# 踩坑

## 多模块打包

https://blog.csdn.net/qq_36636154/article/details/109060638
https://blog.csdn.net/weixin_44066622/article/details/105916607?spm=1001.2101.3001.6650.1&utm_medium=distribute.pc_relevant.none-task-blog-2%7Edefault%7ECTRLIST%7ERate-1.pc_relevant_default&depth_1-utm_source=distribute.pc_relevant.none-task-blog-2%7Edefault%7ECTRLIST%7ERate-1.pc_relevant_default&utm_relevant_index=2
https://www.jb51.net/article/196059.htm
