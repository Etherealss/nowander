package com.nowander.common.utils;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.nowander.common.enums.ApiInfo;
import com.nowander.common.exception.TokenException;
import com.nowander.common.pojo.po.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.context.request.NativeWebRequest;

import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.util.Date;

/**
 * @author wtk
 * @date 2021-10-28
 */
@Slf4j
public class TokenUtil {

    public static final byte[] JWT_SIGN_KEY = "yun_ji".getBytes(StandardCharsets.UTF_8);

    /**
     * token 存放的请求头的位置
     */
    public final static String TOKEN_HEADER = "Authorization";
    /**
     * token类型
     */
    public final static String TOKEN_TYPE = "Bearer";
    /**
     * token类型的字符串长度，用于截取字符串
     */
    public final static int TOKEN_TYPE_LENGTH = TOKEN_TYPE.length() + 1;

    public static boolean existToken(HttpServletRequest request) {
        String authorization = request.getHeader(TOKEN_HEADER);
        return !StringUtils.isBlank(authorization);
    }

    /**
     * 从request中获取token
     * @param request
     * @return
     */
    public static String getToken(HttpServletRequest request) {
        String authorization = request.getHeader(TOKEN_HEADER);
        if (StringUtils.isBlank(authorization)) {
            return null;
        }
        String token = authorization.substring(authorization.indexOf(TOKEN_TYPE) + TOKEN_TYPE_LENGTH);
        return token;
    }

    public static Claims getTokenClaims(HttpServletRequest request) {
        return getTokenClaims(getToken(request));
    }

    public static Claims getTokenClaims(String token) {
        if (StrUtil.isBlank(token)) {
            return null;
        }
        return Jwts.parser().setSigningKey(JWT_SIGN_KEY).parseClaimsJws(token).getBody();
    }

    public static User getUserByToken(HttpServletRequest request) throws TokenException {
        Claims tokenClaims = getTokenClaims(request);
        if (tokenClaims == null) {
            throw new TokenException(ApiInfo.TOKEN_MISSING, "获取不到token");
        }
        User user = new User();
        user.setId(tokenClaims.get("id", Integer.class));
        user.setAvatar(tokenClaims.get("avatar", String.class));
        user.setEmail(tokenClaims.get("username", String.class));
        user.setCreateTime(tokenClaims.get("createTime", Date.class));
        return user;
    }
}
