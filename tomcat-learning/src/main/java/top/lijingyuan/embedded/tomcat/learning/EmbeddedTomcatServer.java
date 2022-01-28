package top.lijingyuan.embedded.tomcat.learning;

import org.apache.catalina.Host;
import org.apache.catalina.connector.Connector;
import org.apache.catalina.startup.Tomcat;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

/**
 * 手写嵌入式Tomcat
 *
 * @author <a href="kangjinghang@xueqiu.com">kangjinghang</a>
 * @date 2022-01-20
 * @since 1.0.0
 */
public class EmbeddedTomcatServer {

    private static File baseDirectory;

    private static final int PORT = 9090;

    private static final String PROTOCOL = "org.apache.coyote.http11.Http11NioProtocol";


    public static void main(String[] args) throws Exception {
        // /Users/kangjinghang/workspace/lijingyuan-learning
        String classpath = System.getProperty("user.dir");
//        System.out.println(classpath);


        Tomcat tomcat = new Tomcat();
        File baseDir = (baseDirectory != null) ? baseDirectory : createTempDir("tomcat");
        System.out.println(baseDir.getAbsolutePath());
        tomcat.setBaseDir(baseDir.getAbsolutePath());
        Connector connector = new Connector(PROTOCOL);
        connector.setThrowOnFailure(true);
        tomcat.getService().addConnector(connector);
//        customizeConnector(connector);
        tomcat.setConnector(connector);
        final Host host = tomcat.getHost();
        host.setName("localhost");
        host.setAppBase("webapps");

        tomcat.addContext(host, "/hello", baseDir.getAbsolutePath());

//        configureEngine(tomcat.getEngine());
//        for (Connector additionalConnector : this.additionalTomcatConnectors) {
//            tomcat.getService().addConnector(additionalConnector);
//        }
        tomcat.getServer().await();
    }


    protected static final File createTempDir(String prefix) {
        try {
            File tempDir = Files.createTempDirectory(prefix + "." + getPort() + ".").toFile();
            tempDir.deleteOnExit();
            return tempDir;
        } catch (IOException ex) {
            throw new RuntimeException(
                    "Unable to create tempDir. java.io.tmpdir is set to " + System.getProperty("java.io.tmpdir"), ex);
        }
    }

    public static int getPort() {
        return PORT;
    }

}
