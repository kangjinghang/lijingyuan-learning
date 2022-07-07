package top.lijingyuan.redis.learning;

import org.apache.commons.codec.binary.Hex;

import java.nio.charset.StandardCharsets;
import java.security.PrivateKey;
import java.security.Signature;

/**
 * SignatureTest
 *
 * @author <a href="kangjinghang@gmail.com">kangjinghang</a>
 * @date 2021-11-10
 * @since 1.0.0
 */
public class SignatureTest {

    private static final String SIGNATURE_ALGORITHM = "sha256withrsa";

    public static void main(String[] args) throws Exception {
        String content = "今晚8点行动";
        byte[] contentBytes = content.getBytes(StandardCharsets.UTF_8);
        // 自己签名
        String sign = sign(contentBytes);
        System.out.println("签名：" + sign);
        // 他人验签
        boolean status = verify(content, sign);
        System.out.println("验签结果：" + status);
    }

    /**
     * 验签
     */
    private static boolean verify(String originalContent, String sign) throws Exception {
        Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
        signature.initVerify(new RsaTest().getPublicKey());
        signature.update(originalContent.getBytes(StandardCharsets.UTF_8));
        return signature.verify(Hex.decodeHex(sign));
    }

    /**
     * 签名
     */
    private static String sign(byte[] data) throws Exception {
        // 获取签名对象
        PrivateKey privateKey = new RsaTest().getPrivateKey();
        // 用指定的算法初始化签名对象：先进行摘要再进行加密
        Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
        signature.initSign(privateKey);
        signature.update(data);
        return Hex.encodeHexString(signature.sign());
    }

}
