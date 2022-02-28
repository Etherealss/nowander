# 环境

# 功能

# 技术架构

## SpringSecurity

### 基于tokenf的登录和登出

#### token黑名单

用户退出登录时，token无法立即失效。所以在退出登录时将用户名存入redis中的token黑名单中。

不存储失效的token，因为没必要，直接用username比较就行了

用户重新登录时，将username从黑名单中移除

在jwt filter中检查token时，检查username是否在黑名单中，如果有，则token失效，需要登录

### reflect token