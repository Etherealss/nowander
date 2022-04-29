package com.nowander.infrastructure.file;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.OSSObject;
import com.aliyun.oss.model.PutObjectResult;
import com.nowander.infrastructure.exception.service.NotFoundException;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.Date;

/**
 * @author wang tengkun
 * @date 2022/4/29
 */
@Component
@Slf4j
public class OssService implements IFileService {
    private final OSS ossClient;
    private final String fileClientType;
    private final String endPoint;
    private final String bucketName;
    private final String rootDir;
    private final String filePathSeparator;

    public OssService(OSS ossClient, OssConfig ossConfig, @Value("file.path-separator") String filePathSeparator) {
        this.ossClient = ossClient;
        this.fileClientType = ossConfig.getFileClientType();
        this.endPoint = ossConfig.getEndPoint();
        this.bucketName = ossConfig.getBucketName();
        this.rootDir = ossConfig.getRootDir();
        this.filePathSeparator = filePathSeparator;
    }

    @Override
    public InputStream getFileStream(String filePath) {
        OSSObject ossObject = ossClient.getObject(bucketName, rootDir + filePathSeparator + filePath);
        return ossObject.getObjectContent();
    }

    @Override
    public String upload(InputStream inputStream, String filePath, String ext) throws Exception {
        PutObjectResult putResult = ossClient.putObject(bucketName, rootDir + filePathSeparator + filePath + ext, inputStream);
        return StringUtils.isNotEmpty(putResult.getETag()) ? rootDir + filePathSeparator + filePath + ext : null;
    }

    @Override
    public void download(HttpServletResponse response, String filePath, String name) throws Exception {
        OSSObject ossObject = ossClient.getObject(bucketName, rootDir + filePathSeparator + filePath);
        try (InputStream fis = ossObject.getObjectContent(); OutputStream fos = response.getOutputStream(); BufferedOutputStream bos = new BufferedOutputStream(fos)) {
            response.setContentType("application/octet-stream; charset=UTF-8");
            response.setHeader("Content-disposition", "attachment;filename=" + new String(name.getBytes("UTF-8"), "ISO-8859-1"));
            int bytesRead = 0;
            byte[] buffer = new byte[1024];
            while ((bytesRead = fis.read(buffer, 0, buffer.length)) != -1) {
                bos.write(buffer, 0, bytesRead);
            }
            bos.flush();
        }
    }

    @Override
    public void delete(String dirPath) {
        boolean flag = ossClient.doesObjectExist(bucketName, dirPath);
        if (flag) {
            ossClient.deleteObject(bucketName, dirPath);
        } else {
            throw new NotFoundException("OSS文件 " + dirPath + " 不存在");
        }
    }

    @Override
    public String getUrl(String key) {
        // 设置URL过期时间为10年 3600l* 1000*24*365*10
        Date expiration = new Date(System.currentTimeMillis() + 3600L * 1000 * 24 * 365 * 10);
        URL url = ossClient.generatePresignedUrl(bucketName, key, expiration);
        // url = "https://" + bucketName + ".oss-cn-beijing.aliyuncs.com/" + bucketName+"/"+ key;
        if (url != null) {
            return url.getPath();
        }
        throw new NotFoundException("无法从OSS获取 " + key + " 的url");
    }
}
