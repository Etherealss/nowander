package com.nowander.infrastructure.file;

import com.aliyun.oss.OSS;
import com.aliyun.oss.model.CannedAccessControlList;
import com.aliyun.oss.model.OSSObject;
import com.aliyun.oss.model.ObjectMetadata;
import com.nowander.infrastructure.exception.service.NotFoundException;
import com.nowander.infrastructure.utils.FileUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author wang tengkun
 * @date 2022/4/29
 */
@Component
@Slf4j
public class OssFileService {
    private final OSS ossClient;
    private final String bucketName;
    private final String filePathSeparator;
    /**
     * 用于在OSS上命名，格式 ：年月日/文件名.后缀名
     */
    private static final DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

    public OssFileService(OSS ossClient,
                          OssConfig ossConfig,
                          @Value("${app.file.path-separator}") String filePathSeparator) {
        this.ossClient = ossClient;
        this.bucketName = ossConfig.getBucketName();
        this.filePathSeparator = filePathSeparator;
    }

    /**
     * 获取文件流
     * @param filePath
     * @param fileName
     */
    public InputStream getFileStream(String filePath, String fileName) {
        OSSObject ossObject = ossClient.getObject(bucketName, filePath + filePath);
        return ossObject.getObjectContent();
    }

    /**
     * 上传文件
     * @param key 文件目录和文件名
     * @param fileExt 文件扩展符
     * @return 文件访问url
     */
    public String upload(InputStream inputStream, String key, String fileExt) {
        // 公共读
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setObjectAcl(CannedAccessControlList.Private);
        objectMetadata.setContentType(FileUtil.getContentType(fileExt));
        ossClient.putObject(bucketName, key, inputStream, objectMetadata);
        // 返回文件访问路径
        URL url = getUrlByKeyWithExpiration(key);
        return buildUrl(url);
    }

    /**
     * 下载文件
     */
    public void download(HttpServletResponse response, String filePath, String fileName) throws Exception {
        OSSObject ossObject = ossClient.getObject(bucketName, filePath + fileName);
        try (InputStream fis = ossObject.getObjectContent();
             OutputStream fos = response.getOutputStream();
             BufferedOutputStream bos = new BufferedOutputStream(fos)) {
            response.setContentType("application/octet-stream; charset=UTF-8");
            fileName = new String(fileName.getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1);
            response.setHeader("Content-disposition",
                    "attachment;filename=" + fileName);
            int bytesRead = 0;
            byte[] buffer = new byte[1024];
            while ((bytesRead = fis.read(buffer, 0, buffer.length)) != -1) {
                bos.write(buffer, 0, bytesRead);
            }
            bos.flush();
        }
    }

    /**
     * 删除文件
     * @param filePath
     * @param fileName
     */
    public void delete(String filePath, String fileName) {
        String key = filePath + fileName;
        boolean flag = ossClient.doesObjectExist(bucketName, key);
        if (flag) {
            ossClient.deleteObject(bucketName, key);
        } else {
            throw new NotFoundException("OSS文件 " + key + " 不存在");
        }
    }

    /**
     * 获得url地址
     * @param filePathAndPath
     * @return 可在外网访问的url地址
     */
    public String getUrl(String filePathAndPath) {
        URL url = getUrlByKeyWithExpiration(filePathAndPath);
        if (url == null) {
            throw new NotFoundException("无法从OSS获取 " + filePathAndPath + " 的url");
        }
        return buildUrl(url);
    }

    /**
     * 设置URL过期时间为1年
     * @param key
     * @return
     */
    private URL getUrlByKeyWithExpiration(String key) {
        Date expiration = new Date(System.currentTimeMillis() + 1000L * 3600 * 24 * 365);
        URL url = ossClient.generatePresignedUrl(bucketName, key, expiration);
        return url;
    }

    private String buildUrl(URL fileUrl) {
        // url = "https://" + bucketName + ".oss-cn-guangzhou.aliyuncs.com/" + bucketName+"/"+ 文件访问路径 + 请求参数/签名;
        return "http://" +
                fileUrl.getHost() +
                filePathSeparator +
                fileUrl.getFile();
    }
}
