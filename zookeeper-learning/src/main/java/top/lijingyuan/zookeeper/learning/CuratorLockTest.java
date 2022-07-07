package top.lijingyuan.zookeeper.learning;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.locks.InterProcessMultiLock;
import org.apache.curator.retry.ExponentialBackoffRetry;

import java.util.Collections;
import java.util.concurrent.TimeUnit;

/**
 * CuratorLockTest
 *
 * @author <a href="kangjinghang@gmail.com">kangjinghang</a>
 * @date 2021-10-16
 * @since 1.0.0
 */
public class CuratorLockTest {

    private static final String rootNode = "/locks";

    private static final String connectString = "localhost:2181,localhost:2182,localhost:2183";

    private static final int sessionTimeout = 2000;

    public static void main(String[] args) {
        InterProcessMultiLock lock1 = new InterProcessMultiLock(getCuratorFramework(), Collections.singletonList(rootNode));
        InterProcessMultiLock lock2 = new InterProcessMultiLock(getCuratorFramework(), Collections.singletonList(rootNode));

        new Thread(() -> {
            try {
                lock1.acquire();
                System.out.println(Thread.currentThread().getName() + "启动，获取到锁");
                lock1.acquire();
                System.out.println(Thread.currentThread().getName() + "再次获取到锁");
                TimeUnit.SECONDS.sleep(5);
                lock1.release();
                System.out.println(Thread.currentThread().getName() + "释放锁");
                lock1.release();
                System.out.println(Thread.currentThread().getName() + "再次释放锁");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
        new Thread(() -> {
            try {
                lock2.acquire();
                System.out.println(Thread.currentThread().getName() + "启动，获取到锁");
                lock2.acquire();
                System.out.println(Thread.currentThread().getName() + "再次获取到锁");
                TimeUnit.SECONDS.sleep(5);
                lock2.release();
                System.out.println(Thread.currentThread().getName() + "释放锁");
                lock2.release();
                System.out.println(Thread.currentThread().getName() + "再次释放锁");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    public static CuratorFramework getCuratorFramework() {
        RetryPolicy policy = new ExponentialBackoffRetry(3000, 3);
        CuratorFramework client = CuratorFrameworkFactory.builder()
                .connectString(connectString).connectionTimeoutMs(sessionTimeout)
                .sessionTimeoutMs(sessionTimeout)
                .retryPolicy(policy).build();
        // 启动客户端
        client.start();
        System.out.println("zookeeper 启动成功");
        return client;
    }

}
