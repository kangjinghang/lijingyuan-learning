package top.lijingyuan.embedded.tomcat.learning.classloader;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Arrays;

/**
 * MyClassLoader
 *
 * @author <a href="kangjinghang@gmail.com">kangjinghang</a>
 * @date 2022-01-25
 * @since 1.0.0
 */
public class MyClassLoader extends ClassLoader {

    private String name;

    public MyClassLoader(String name, ClassLoader parent) {
//        super(name, parent);
        this.name = name;
    }

    @Override
    public Class<?> loadClass(String name) throws ClassNotFoundException {
        final ClassLoader systemClassloader = getSystemClassLoader();
        final ClassLoader extClassloader = systemClassloader.getParent();
        // 如果是仍然先由systemClassloader加载，那么 clazz1.equals(clazz2) = true
//        final Class<?> clazz = systemClassloader.loadClass(name);
        // 如果直接先由extClassloader加载，那么 clazz1.equals(clazz2) = false
        try {
            final Class<?> clazz = extClassloader.loadClass(name);
            if (clazz != null) {
                return clazz;
            }
        } catch (ClassNotFoundException ex) {
            // ignore
        }
        return this.findClass(name);
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        byte[] data = getBytes("/Users/kangjinghang/workspace/lijingyuan-learning/tomcat-learning/target/classes/top/lijingyuan/embedded/tomcat/learning/classloader/Test.class");
        return this.defineClass(name, data, 0, data.length);
    }

    private byte[] getBytes(String path) {
        ByteBuffer dst = ByteBuffer.allocate(1024);
        try (FileInputStream fis = new FileInputStream(path)) {
            final int read = fis.getChannel().read(dst);
            System.out.println(read);
//            final int position = dst.position();
//            System.out.println(position);
            final byte[] data = dst.array();
            return Arrays.copyOf(data, read);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new byte[1024];
    }

    public static void main(String[] args) throws Exception {
        MyClassLoader loader1 = new MyClassLoader("MyClassLoader", MyClassLoader.class.getClassLoader());
        final Class<?> clazz1 = loader1.loadClass("top.lijingyuan.embedded.tomcat.learning.classloader.Test");

        MyClassLoader loader2 = new MyClassLoader("MyClassLoader", MyClassLoader.class.getClassLoader());
        final Class<?> clazz2 = loader2.loadClass("top.lijingyuan.embedded.tomcat.learning.classloader.Test");
        // 如果不重写loadClass/findClass，clazz1.equals(clazz2) = true，因为都是由AppClassLoader加载的
        // 如果重写loadClass，clazz1.equals(clazz2) = false，因为是由两个不同的ClassLoader加载的
        System.out.println(clazz1.equals(clazz2));
    }

}
