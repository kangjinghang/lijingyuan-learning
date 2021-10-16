package top.lijingyuan.zookeeper.learning;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * ZkClientDemo
 *
 * @author <a href="kangjinghang@xueqiu.com">kangjinghang</a>
 * @date 2021-10-11
 * @since 1.0.0
 */
public class ZkClientDemo {

    private final String connectString = "localhost:2181,localhost:2182,localhost:2183";

    private final int sessionTimeout = 2000;

    private ZooKeeper zkClient;

    @Before
    public void init() throws IOException {
        zkClient = new ZooKeeper(connectString, sessionTimeout, event -> {
            // 收到事件通知后的回调函数（用户的业务逻辑）
            System.out.println(event.getType() + "--" + event.getPath());
            // 再次启动监听
            try {
                List<String> children = zkClient.getChildren("/", true);
                for (String child : children) {
                    System.out.println(child);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    @Test
    public void testStart() {

    }

    @Test
    public void create() throws InterruptedException, KeeperException {
        // 参数 1：要创建的节点的路径； 参数 2：节点数据 ； 参数 3：节点权限 ； 参数 4：节点的类型
        String nodeCreated = zkClient.create("/lijingyuan", "ss.avi".getBytes(StandardCharsets.UTF_8),
                ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        System.out.println(nodeCreated);
    }

    @Test
    public void getChildren() throws InterruptedException, KeeperException {
        zkClient.getChildren("/", true);
        // 延时阻塞
        Thread.sleep(Long.MAX_VALUE);
    }

    @Test
    public void exists() throws InterruptedException, KeeperException {
        Stat stat = zkClient.exists("/lijingyuan", false);
        System.out.println(stat == null ? "not exist" : "exist");
    }

}
