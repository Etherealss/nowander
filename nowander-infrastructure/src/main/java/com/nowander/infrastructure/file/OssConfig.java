package com.nowander.infrastructure.file;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.BucketInfo;
import com.nowander.infrastructure.exception.BugException;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 引导类
 * @author zhangzhixiang
 * @date 2018/09/18 14:51:39
 */
@ConfigurationProperties("file.oss")
@Configuration
@Setter
@Getter
@Slf4j
public class OssConfig {
    /**
     * 文件客户端类型
     */
    private String fileClientType;
    /**
     * OSS地址（不同服务器，地址不同）
     */
    private String endPoint;
    /**
     * OSS键id（去OSS控制台获取）
     */
    private String accessKeyId;
    /**
     * OSS秘钥（去OSS控制台获取）
     */
    private String accessKeySecret;
    /**
     * OSS桶名称（这个是自己创建bucket时候的命名）
     */
    private String bucketName;
    /**
     * OSS根目录
     */
    private String rootDir;

    @Bean
    public OSS oss() {
        OSS ossClient = new OSSClientBuilder().build(endPoint, accessKeyId, accessKeySecret);

        // 判断Bucket是否存在。详细请参看“SDK手册 > Java-SDK > 管理Bucket”。
        // 链接地址是：https://help.aliyun.com/document_detail/oss/sdk/java-sdk/manage_bucket.html?spm=5176.docoss/sdk/java-sdk/init
        if (ossClient.doesBucketExist(bucketName)) {
            log.info("OSS Bucket {} 已存在", bucketName);
        } else {
            log.error("OSS Bucket {} 不存在，请检查配置", bucketName);
            throw new BugException("OSS Bucket不存在");
        }

        // 查看Bucket信息。详细请参看“SDK手册 > Java-SDK > 管理Bucket”。
        // 链接地址是：https://help.aliyun.com/document_detail/oss/sdk/java-sdk/manage_bucket.html?spm=5176.docoss/sdk/java-sdk/init
        BucketInfo info = ossClient.getBucketInfo(bucketName);
        log.info("Bucket {} 的信息如下：\n数据中心：{}\n创建时间：{}\n用户标志：{}",
                bucketName,
                info.getBucket().getLocation(),
                info.getBucket().getCreationDate(),
                info.getBucket().getOwner()
        );
        return ossClient;
    }

}

