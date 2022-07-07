package top.lijingyuan.guava;

import com.google.common.io.ByteSource;
import com.google.common.io.Closer;
import com.google.common.io.Files;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * CloserTest
 *
 * @author <a href="kangjinghang@gmail.com">kangjinghang</a>
 * @date 2021-06-21
 * @since 1.0.0
 */
public class CloserTest {

    @Test
    public void testCloser() throws IOException {
        ByteSource byteSource = Files.asByteSource(new File("/Users/kangjinghang/Desktop/20210520093203.jpg"));
        Closer closer = Closer.create();
        try {
            InputStream in = closer.register(byteSource.openStream());
//            OutputStream out = closer.register(openOutputStream());
            // do stuff
        } catch (Throwable e) {
            // ensure that any checked exception types other than IOException that could be thrown are
            // provided here, e.g. throw closer.rethrow(e, CheckedException.class);
            throw closer.rethrow(e);
        } finally {
            closer.close();
        }
    }

    @Test
    public void testTryCatch() {
        Throwable t = null;
        try {
            throw new RuntimeException("1");
        } catch (Exception e) {
            t = e;
            throw e;
        } finally {
            // close
            // 第一次：
//            throw new RuntimeException("2");
            // 第二次：对比
            RuntimeException runtimeException = new RuntimeException("2");
            runtimeException.addSuppressed(t);
            throw runtimeException;
        }

    }

}
