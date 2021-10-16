package top.lijingyuan.zookeeper.learning;

import org.apache.zookeeper.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * DistributedServer
 *
 * @author <a href="kangjinghang@xueqiu.com">kangjinghang</a>
 * @date 2021-10-15
 * @since 1.0.0
 */
public class DistributedServer {

    private final String connectString = "localhost:2181,localhost:2182,localhost:2183";

    private final int sessionTimeout = 2000;

    private ZooKeeper zk;

    private String parentNode = "/servers";

    private void connect() throws IOException {
        zk = new ZooKeeper(connectString, sessionTimeout, event -> {

        });
    }

    private void register(String hostname) throws InterruptedException, KeeperException {
        String path = zk.create( parentNode + "/server", hostname.getBytes(StandardCharsets.UTF_8), ZooDefs.Ids.OPEN_ACL_UNSAFE,
                CreateMode.EPHEMERAL_SEQUENTIAL);
        System.out.println(hostname + " is online " + path);
    }

    private void business(String hostname) throws InterruptedException {
        System.out.println(hostname + " is working ...");
        Thread.sleep(Long.MAX_VALUE);
    }

    public static void main(String[] args) throws IOException, InterruptedException, KeeperException {
        // 1.获取 zk 连接
        DistributedServer server = new DistributedServer();
        server.connect();
        // 2.利用 zk 连接注册服务器信息
        server.register(args[0]);
        // 3.启动业务功能
        server.business(args[0]);
    }

}
