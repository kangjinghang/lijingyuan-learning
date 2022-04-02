package top.lijingyuan.apollo.learning;

import org.jasypt.encryption.StringEncryptor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class JasyptTest {

    @Autowired
    private StringEncryptor encryptor;

    @Test
    public void encode() {
        String applicationName = "apollo-learning";
        System.out.println(encryptor.encrypt(applicationName));
    }

    @Value("${spring.application.name}")
    private String applicationName;

    @Test
    public void print() {
        System.out.println(applicationName);
    }

}