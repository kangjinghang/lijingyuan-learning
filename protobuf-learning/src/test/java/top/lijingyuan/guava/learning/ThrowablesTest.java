package top.lijingyuan.guava.learning;

import com.google.common.base.Throwables;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class ThrowablesTest {

    @Test
    public void testThrowables() {
        try {
            throw new Exception();
        } catch (Throwable t) {
            String ss = Throwables.getStackTraceAsString(t);
            System.out.println("ss:" + ss);
            Throwables.propagate(t);
        }
    }

    @Test
    public void call() throws IOException {
        try {
            throw new IOException();
        } catch (Throwable t) {
            Throwables.propagateIfInstanceOf(t, IOException.class);
            throw Throwables.propagate(t);
        }
    }

    @Test
    public void testCheckException() {
        try {
            URL url = new URL("http://ociweb.com");
            final InputStream in = url.openStream();
            // read from the input stream
            in.close();
        } catch (Throwable t) {
            throw Throwables.propagate(t);
        }
    }

    @Test
    public void testPropagateIfPossible() throws Exception {
        try {
            throw new Exception();
        } catch (Throwable t) {
            Throwables.propagateIfPossible(t, Exception.class);
            Throwables.propagate(t);
        }
    }

}