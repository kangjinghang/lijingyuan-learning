package top.lijingyuan.zookeeper.learning;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooKeeper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * DistributedServer
 *
 * @author <a href="kangjinghang@xueqiu.com">kangjinghang</a>
 * @date 2021-10-15
 * @since 1.0.0
 */
public class DistributedClient {

    private final String connectString = "localhost:2181,localhost:2182,localhost:2183";

    private final int sessionTimeout = 2000;

    private ZooKeeper zk;

    private String parentNode = "/servers";

    private void connect() throws IOException {
        zk = new ZooKeeper(connectString, sessionTimeout, event -> {
            try {
                getServerList();
            } catch (InterruptedException | KeeperException e) {
                e.printStackTrace();
            }
        });
    }

    private void getServerList() throws InterruptedException, KeeperException {
        // 1.获取服务器子节点信息，并且对父节点进行监听
        List<String> children = zk.getChildren(parentNode, true);
        // 2.存储服务器信息列表
        ArrayList<String> servers = new ArrayList<>();
        // 3.遍历所有节点，获取节点中的主机名称信息
        for (String child : children) {
            byte[] data = zk.getData(parentNode + "/" + child, false, null);
            servers.add(new String(data));
        }
        // 4.打印服务器列表信息
        System.out.println(servers);
    }

    private void business() throws InterruptedException {
        Thread.sleep(Long.MAX_VALUE);
    }

    public static void main(String[] args) throws IOException, InterruptedException, KeeperException {
        // 1.获取 zk 连接
        DistributedClient client = new DistributedClient();
        client.connect();
        // 2.获取 servers 的子节点信息，从中获取服务器信息列表
        client.getServerList();
        // 3.启动业务功能
        client.business();
    }

}
