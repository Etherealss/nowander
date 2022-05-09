package test;

import com.nowander.starter.NowanderApplication;
import org.jasypt.encryption.StringEncryptor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author wtk
 * @date 2022-04-30
 */
@SpringBootTest(classes = NowanderApplication.class)
public class JasyptTest {
    @Autowired
    StringEncryptor stringEncryptor;

    @Test
    public void encryptorTest() {
        /*你的密码*/
        String passwd = stringEncryptor.encrypt("123");
        System.out.println("加密密码是：" + passwd);
        System.out.println("解密密码是：" + stringEncryptor.decrypt(passwd));

        passwd = stringEncryptor.encrypt("234");
        System.out.println("加密密码是：" + passwd);
        System.out.println("解密密码是：" + stringEncryptor.decrypt(passwd));
    }
}