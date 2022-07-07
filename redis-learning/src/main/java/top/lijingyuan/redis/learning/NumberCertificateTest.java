package top.lijingyuan.redis.learning;

import org.apache.commons.codec.binary.Base64;

import java.io.FileInputStream;
import java.security.PublicKey;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;

/**
 * 从数字证书（a的公钥+签名（a的公钥+ca的私钥））中提取公钥
 *
 * @author <a href="kangjinghang@gmail.com">kangjinghang</a>
 * @date 2021-11-10
 * @since 1.0.0
 */
public class NumberCertificateTest {

    public static void main(String[] args) throws Exception {
        CertificateFactory cf = CertificateFactory.getInstance("X.509");
        // 证书是使用 JDK 自带的keytool工具生成的
        String path = NumberCertificateTest.class.getClassLoader().getResource("certificate.pem").getPath();
        FileInputStream fis = new FileInputStream(path);
        // 生成一个证书对象并使用从输入流 fis 中读取的数据对它进行初始化。
        Certificate certificate = cf.generateCertificate(fis);
        PublicKey publicKey = certificate.getPublicKey();
        String key = Base64.encodeBase64String(publicKey.getEncoded());
        System.out.println(key);
    }

}
