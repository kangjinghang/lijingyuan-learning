package top.lijingyuan.guava.learning;

import com.google.common.io.BaseEncoding;
import org.junit.Test;
import top.lijingyuan.guava.io.Base64;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

/**
 * BaseEncodingTest
 *
 * @author <a href="kangjinghang@gmail.com">kangjinghang</a>
 * @date 2021-06-21
 * @since 1.0.0
 */
public class BaseEncodingTest {

    @Test
    public void testBase64Encode() {
        BaseEncoding baseEncoding = BaseEncoding.base64();
        System.out.println(baseEncoding.encode("hello".getBytes()));
    }

    @Test
    public void testBase64Decoding() {
        BaseEncoding baseEncoding = BaseEncoding.base64();
        System.out.println(new String(baseEncoding.decode("aGVsbG8=")));
    }

    @Test
    public void testMyBase64Encode() {
        System.out.println(Base64.encode("hello"));
    }

    @Test
    public void testMyBase64Decoding() {
        System.out.println(Base64.decode("aGVsbG8="));
    }

    @Test
    public void test() {
        long l = System.currentTimeMillis();
        LocalDateTime localDateTime = Instant.ofEpochMilli(l).atZone(ZoneOffset.ofHours(8)).toLocalDateTime();
        System.out.println(localDateTime);
        System.out.println(localDateTime.format(DateTimeFormatter.ofPattern("MM-dd")));
        System.out.println(localDateTime.getYear());
        System.out.println(localDateTime.getMonthValue());
        System.out.println(localDateTime.getDayOfMonth());
    }

}
