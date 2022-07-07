package top.lijingyuan.zookeeper.learning;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * zookeeper分布式锁
 *
 * @author <a href="kangjinghang@gmail.com">kangjinghang</a>
 * @date 2021-10-16
 * @since 1.0.0
 */
public class DistributedLock {

    private final String connectString = "localhost:2181,localhost:2182,localhost:2183";

    private final int sessionTimeout = 2000;

    private ZooKeeper zk;

    private CountDownLatch connectLatch = new CountDownLatch(1);

    private CountDownLatch waitLatch = new CountDownLatch(1);

    private String rootPath = "/locks";

    private String waitPath;

    private String current;

    public DistributedLock() throws IOException, InterruptedException, KeeperException {
        // 获取连接
        zk = new ZooKeeper(connectString, sessionTimeout, event -> {
            if (event.getState() == Watcher.Event.KeeperState.SyncConnected) {
                connectLatch.countDown();
            }
            // waitPath需要释放
            if (event.getType() == Watcher.Event.EventType.NodeDeleted && event.getPath().equals(waitPath)) {
                waitLatch.countDown();
            }
        });
        // 等待zk正常连接后，往下运行程序
        connectLatch.await();
        // 判断根节点/locks是否存在
        Stat stat = zk.exists(rootPath, false);
        if (stat == null) {
            // 创建根节点
            zk.create(rootPath, rootPath.getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        }
    }

    /**
     * 加锁
     */
    public void lock() throws InterruptedException, KeeperException {
        // 创建对应的临时带序号节点
        current = zk.create(rootPath + "/seq-", null, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
        // 判断创建的节点是否是最小的节点，如果是，则获取到锁；如果不是，监听前面的那一个节点
        List<String> children = zk.getChildren(rootPath, false);
        // 如果children只有一个值，那就直接获取锁，如果有多个节点，需要判断，谁最小
        if (children.size() == 1) {
            return;
        } else {
            Collections.sort(children);
            // 获取节点名称，seq-00000000
            String thisNode = current.substring((rootPath + "/").length());
            // 通过 seq-00000000 获取该节点在children集合的位置
            int index = children.indexOf(thisNode);
            if (index == -1) {
                System.out.println("数据异常");
            } else if (index == 0) {
                // 获取到锁
                return;
            } else {
                // 需要判断前一个节点的变化
                waitPath = rootPath + "/" + children.get(index - 1);
                zk.getData(waitPath, true, null);
                // 等待监听
                waitLatch.await();
                return;
            }
        }
    }

    /**
     * 释放锁
     */
    public void release() throws InterruptedException, KeeperException {
        // 删除节点
        zk.delete(current, -1);
    }

}
