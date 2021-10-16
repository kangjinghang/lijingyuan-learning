package top.lijingyuan.zookeeper.learning;

import org.apache.zookeeper.KeeperException;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * DistributedLockTest
 *
 * @author <a href="kangjinghang@xueqiu.com">kangjinghang</a>
 * @date 2021-10-16
 * @since 1.0.0
 */
public class DistributedLockTest {

    public static void main(String[] args) throws IOException, InterruptedException, KeeperException {
        DistributedLock lock1 = new DistributedLock();
        DistributedLock lock2 = new DistributedLock();
        new Thread(() -> {
            try {
                lock1.lock();
                System.out.println(Thread.currentThread().getName() + "启动，获取到锁");
                TimeUnit.SECONDS.sleep(5);
                lock1.release();
                System.out.println(Thread.currentThread().getName() + "释放锁");
            } catch (InterruptedException | KeeperException e) {
                e.printStackTrace();
            }
        }).start();
        new Thread(() -> {
            try {
                lock2.lock();
                System.out.println(Thread.currentThread().getName() + "启动，获取到锁");
                TimeUnit.SECONDS.sleep(5);
                lock2.release();
                System.out.println(Thread.currentThread().getName() + "释放锁");
            } catch (InterruptedException | KeeperException e) {
                e.printStackTrace();
            }
        }).start();
    }

}
