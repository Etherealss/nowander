package com.nowander.infrastructure.file;

import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;

/**
 * @author wang tengkun
 * @date 2022/4/29
 */
public interface IFileService {
    /**
     * 获取文件流
     */
    InputStream getFileStream(String filePath);

    /**
     * 上传文件
     * @return key
     */
    String upload(InputStream inputStream, String filePath, String ext) throws Exception;

    /**
     * 下载文件
     */
    void download(HttpServletResponse response, String filePath, String name) throws Exception;

    /**
     * 删除文件
     */
    void delete(String dirPath);

    /**
     * 获得url地址
     */
    String getUrl(String key);
}
