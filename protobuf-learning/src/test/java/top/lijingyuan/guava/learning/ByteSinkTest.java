package top.lijingyuan.guava.learning;

import com.google.common.io.ByteSink;
import com.google.common.io.Files;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * ByteSinkTest
 *
 * @author <a href="kangjinghang@gmail.com">kangjinghang</a>
 * @date 2021-06-20
 * @since 1.0.0
 */
public class ByteSinkTest {

    private static final String FILE_PATH = "/Users/kangjinghang/Desktop/20210520093203 2.jpg";

    @Test
    public void testByteSink() throws IOException {
        File file = new File(FILE_PATH);
        file.deleteOnExit();
        ByteSink byteSink = Files.asByteSink(file);
        byte[] bytes = {0x01, 0x02};
        byteSink.write(bytes);

        byte[] expected = Files.toByteArray(file);
        assertThat(expected, is(bytes));
    }

}
