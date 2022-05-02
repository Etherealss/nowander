package com.nowander.basesystem.user;

import cn.hutool.core.lang.Assert;
import cn.hutool.json.JSONObject;
import cn.hutool.jwt.JWT;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nowander.basesystem.user.avatar.AvatarService;
import com.nowander.infrastructure.enums.RedisKeyPrefix;
import com.nowander.basesystem.user.security.jwt.JwtConfig;
import com.nowander.basesystem.user.security.jwt.TokenUtil;
import com.nowander.infrastructure.utils.FileUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @author wtk
 * @since 2022-01-05
 */
@Service
@Slf4j
@AllArgsConstructor
public class UserService extends ServiceImpl<UserMapper, SysUser> {

    private final UserMapper userMapper;
    private final AvatarService avatarService;
    private final RedisTemplate<String, Object> redisTemplate;
    private final JwtConfig jwtConfig;

    public UserBriefDTO getBriefById(Integer userId) {
        SysUser user = userMapper.selectById(userId);
        return user.convert(UserBriefDTO.class);
    }

    public List<UserBriefDTO> getBatchBriefsByIds(Collection<Integer> userIds) {
        List<SysUser> users = userMapper.selectBatchIds(userIds);
        return users.stream()
                .map(user -> user.convert(UserBriefDTO.class))
                .collect(Collectors.toList());
    }

    public UserDetailDTO getDetailById(Integer userId) {
        SysUser user = userMapper.selectById(userId);
        return user.convert(UserDetailDTO.class);
    }

    public void logout(HttpServletRequest request) {
        JSONObject claims = TokenUtil.parse(request.getHeader(jwtConfig.getTokenHeader()));
        Long exp = claims.get(JWT.EXPIRES_AT, Long.class);
        String username = claims.get("username", String.class);
        Assert.notNull(exp);
        Assert.notBlank(username);
        // 加入黑名单，事token失效（严格来说，黑名单是username的黑名单，用户必须再次使用账号密码登录才能才黑名单移除）
        redisTemplate.opsForValue().set(RedisKeyPrefix.USER_TOKEN_BLACKLIST + username, "",
                exp - System.currentTimeMillis(), TimeUnit.MILLISECONDS);
        // 清空
        SecurityContextHolder.getContext().setAuthentication(null);
    }

    /**
     * 上传并更新用户头像
     * @param avatarFile
     * @param user
     * @return
     * @throws IOException
     */
    public String uploadAndSetUserAvatar(MultipartFile avatarFile, SysUser user) throws IOException {
        String fileName = avatarFile.getOriginalFilename();
        Objects.requireNonNull(fileName, "文件名不能为空");
        String fileExt = FileUtil.getFileExt(fileName);
        String avatarUrl = avatarService.uploadAvatar(avatarFile.getInputStream(), user.getId(), fileExt);
        userMapper.updateAvatarById(avatarUrl, user.getId());
        return avatarUrl;
    }
}
