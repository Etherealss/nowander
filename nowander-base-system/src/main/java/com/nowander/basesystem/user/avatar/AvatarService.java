package com.nowander.basesystem.user.avatar;

import com.nowander.infrastructure.file.OssFileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author wtk
 * @date 2022-04-30
 */
@Service
@Slf4j
public class AvatarService {
    private final OssFileService fileService;
    private final String avatarDir;
    private final String filePathSeparator;
    private final String DEFAULT_BOY_AVATAR;
    private final String DEFAULT_GIRL_AVATAR;
    /**
     * 用于在OSS上命名，格式 ：年月日/文件名.后缀名
     */
    private static final DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

    public AvatarService(OssFileService fileService,
                         @Value("${app.file.oss.avatar-dir}") String avatarDir,
                         @Value("${app.file.path-separator}") String filePathSeparator) {
        this.fileService = fileService;
        this.avatarDir = avatarDir;
        this.filePathSeparator = filePathSeparator;
        DEFAULT_BOY_AVATAR = avatarDir + "default-boy.png";
        DEFAULT_GIRL_AVATAR = avatarDir + "default-girl.png";
    }

    /**
     * 通过用户的头像在OSS上的访问路径，获取用户头像在外网的url地址
     * @param userAvatarPathAndName 日期文件夹+用户文件名
     * @return 可在外网访问的url地址
     */
    public String getAvatarUrl(String userAvatarPathAndName) {
        return fileService.getUrl(avatarDir + userAvatarPathAndName);
    }

    public String getDefaultAvatarUrl(boolean isBoy) {
        return fileService.getUrl(isBoy ? DEFAULT_BOY_AVATAR : DEFAULT_GIRL_AVATAR);
    }

    /**
     * 上传头像
     * @param inputStream
     * @param userId
     * @param fileExt
     * @return 头像保存路径，同时也是访问文件的url
     */
    public String uploadAvatar(InputStream inputStream, Integer userId, String fileExt) {
        String filePathAndName = getSavePath() + userId + fileExt;
        return fileService.upload(inputStream, filePathAndName, fileExt);
    }

    public String testUploal() {
        try (InputStream is = new FileInputStream("D:\\WanderFourAvatar\\19.png");) {
            String upload = fileService.upload(is, getSavePath() + "testimg.png", ".png");
            return upload;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private String getSavePath() {
        return avatarDir + DATE_FORMAT.format(new Date()) + filePathSeparator;
    }
}
