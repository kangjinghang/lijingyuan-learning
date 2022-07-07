package top.lijingyuan.redis.learning;

import org.apache.commons.io.FileUtils;

import javax.crypto.Cipher;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

/**
 * RasTest
 *
 * @author <a href="kangjinghang@gmail.com">kangjinghang</a>
 * @date 2021-11-10
 * @since 1.0.0
 */
public class RsaTest {

    private static final String ALGORITHM = "RSA";

    private static final String UTF8 = StandardCharsets.UTF_8.name();

    // rsa单次最大的加密的明文的字节数组大小
    private static final int MAX_ENCRYPT_BLOCK = 117;

    // rsa单次最大的解密的密文的字节数组大小
    private static final int MAX_DECRYPT_BLOCK = 128;

    private static String publicKeyPath;

    private static String privateKeyPath;

    static {
        ClassLoader classLoader = RsaTest.class.getClassLoader();
        publicKeyPath = classLoader.getResource("rsa.pub").getPath();
        privateKeyPath = classLoader.getResource("rsa.pri").getPath();
    }

    /**
     * 每次生成都是不一样的
     */
    private void writeKey2File() throws Exception {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(ALGORITHM);
        keyPairGenerator.initialize(1024);
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        PublicKey publicKey = keyPair.getPublic();
        String publicKeyStr = Base64.getEncoder().encodeToString(publicKey.getEncoded());

        PrivateKey privateKey = keyPair.getPrivate();
        String privateKeyStr = Base64.getEncoder().encodeToString(privateKey.getEncoded());
        FileUtils.writeByteArrayToFile(new File(publicKeyPath), publicKeyStr.getBytes(UTF8));
        FileUtils.writeByteArrayToFile(new File(privateKeyPath), privateKeyStr.getBytes(UTF8));
    }

    public PublicKey getPublicKey() throws Exception {
        String publicKeyStr = FileUtils.readFileToString(new File(publicKeyPath), UTF8);
        byte[] decodeBase64 = Base64.getDecoder().decode(publicKeyStr);
        // 公钥规则：X059
        X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(decodeBase64);
        KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM);
        return keyFactory.generatePublic(x509EncodedKeySpec);
    }

    public PrivateKey getPrivateKey() throws Exception {
        String privateKeyStr = FileUtils.readFileToString(new File(privateKeyPath), UTF8);
        byte[] decodeBase64 = Base64.getDecoder().decode(privateKeyStr);
        // 私钥的规则就是 PKCS8
        PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(decodeBase64);
        KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM);
        return keyFactory.generatePrivate(pkcs8EncodedKeySpec);
    }

    public static void main(String[] args) throws Exception {
        RsaTest test = new RsaTest();
//        test.writeKey2File();
        String str = "乐之者Java";
        // 公钥加密
        String encrypt = test.encrypt(str, test.getPublicKey());
        // 私钥解密
        System.out.println(test.decrypt(encrypt, test.getPrivateKey()));

        // 私钥加密
        encrypt = test.encrypt(str, test.getPrivateKey());
        // 公钥解密
        System.out.println(test.decrypt(encrypt, test.getPublicKey()));

    }

    public String encrypt(String originalCont, Key key) throws Exception {
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] bytes = doCodec(cipher, originalCont.getBytes(UTF8), MAX_ENCRYPT_BLOCK);
        return Base64.getEncoder().encodeToString(bytes);
    }

    public String decrypt(String encryptedStr, Key key) throws Exception {
        byte[] bytes = Base64.getDecoder().decode(encryptedStr);
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, key);
        byte[] decryptedBytes = doCodec(cipher, bytes, MAX_DECRYPT_BLOCK);
        return new String(decryptedBytes, UTF8);
    }

    private byte[] doCodec(Cipher cipher, byte[] bytes, int maxBlockSize) throws Exception {
        int inputLen = bytes.length;
        int offset = 0;
        byte[] cache;
        int i = 0;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        while ((inputLen - offset) > 0) {
            // 循环分段处理
            if ((inputLen - offset) > maxBlockSize) {
                cache = cipher.doFinal(bytes, offset, maxBlockSize);
            } else {
                cache = cipher.doFinal(bytes, offset, inputLen - offset);
            }
            bos.write(cache, 0, cache.length);
            i++;
            offset = i * maxBlockSize;
        }
        byte[] result = bos.toByteArray();
        bos.close();
        return result;
    }


}
