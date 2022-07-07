package top.lijingyuan.guava.learning;

import com.google.common.io.ByteSource;
import com.google.common.io.Files;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * ByteSourceTest
 *
 * @author <a href="kangjinghang@gmail.com">kangjinghang</a>
 * @date 2021-06-20
 * @since 1.0.0
 */
public class ByteSourceTest {

    private static final String FILE_PATH = "/Users/kangjinghang/Desktop/20210520093203.jpg";

    @Test
    public void testByteSourceWrap() throws IOException {
        File file = new File(FILE_PATH);
        ByteSource byteSource = Files.asByteSource(file);
        byte[] bytes = byteSource.read();
        assertThat(bytes, is(Files.toByteArray(file)));
    }

    @Test
    public void testSliceByteSource() throws IOException {
        ByteSource byteSource = ByteSource.wrap(
                new byte[]{0x00, 0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x08, 0x09}
        );
        ByteSource slicedByteSource = byteSource.slice(5, 5);
        byte[] bytes = slicedByteSource.read();
        for (byte aByte : bytes) {
            System.out.println(aByte);
        }
    }

}
