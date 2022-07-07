package top.lijingyuan.guava.learning.cache;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * ConcurrentTest
 *
 * @author <a href="kangjinghang@gmail.com">kangjinghang</a>
 * @date 2021-06-02
 * @since 1.0.0
 */
@Slf4j
public class ConcurrentTest {

    // 并发数
    private static final int CONCURRENT_NUM = 10;

    private volatile static int value = 1;

    private static LoadingCache<String, String> cache = CacheBuilder.newBuilder()
            .maximumSize(1000)
            .expireAfterWrite(5, TimeUnit.SECONDS)
            .refreshAfterWrite(1, TimeUnit.SECONDS)
            .build(new CacheLoader<String, String>() {
                       @Override
                       public String load(String key) throws InterruptedException {
//                           System.out.println("load by " + Thread.currentThread().getName());
                           log.info("load by {}", Thread.currentThread().getName());
                           return createValue(key);
                       }


                       @Override
                       public ListenableFuture<String> reload(String key, String oldValue) throws Exception {
//                           System.out.println("reload by " + Thread.currentThread().getName());
                           log.info("reload by {}", Thread.currentThread().getName());
                           return Futures.immediateFuture(createValue(key));
                       }
                   }
            );

    // 创建value
    private static String createValue(String key) throws InterruptedException {
        // 让当前线程sleep 1秒，是为了测试load和reload时候的并发特性
        Thread.sleep(1000L);
        return String.valueOf(value++);
    }

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        CyclicBarrier barrier = new CyclicBarrier(CONCURRENT_NUM);
        CountDownLatch latch = new CountDownLatch(CONCURRENT_NUM);
        for (int i = 0; i < CONCURRENT_NUM; i++) {
            final ClientRunnable runnable = new ClientRunnable(barrier, latch);
            Thread thread = new Thread(runnable, "client-" + i);
            thread.start();
        }

        // 测试一段时间不访问后是否执行expire而不是refresh
        latch.await();
        Thread.sleep(5100L);
//        System.out.println("\n超过expire时间未读之后...");
        log.info("超过expire时间未读之后...");
        log.info("{} ,val:{}", Thread.currentThread().getName(), cache.get("key"));

//        System.out.println(Thread.currentThread().getName() + ",val:" + cache.get("key"));
    }

    static class ClientRunnable implements Runnable {

        CyclicBarrier barrier;
        CountDownLatch latch;

        public ClientRunnable(CyclicBarrier barrier, CountDownLatch latch) {
            this.barrier = barrier;
            this.latch = latch;
        }

        @Override
        public void run() {
            try {
                barrier.await();
                // 每个client随机睡眠，为了充分测试refresh和load
                double time = Math.random() * 4000;
                Thread.sleep((long) time);
                log.info("{} slept {}ms", Thread.currentThread().getName(), (long) time);
                log.info("{} ,val:{}", Thread.currentThread().getName(), cache.get("key"));

//                System.out.println(Thread.currentThread().getName() + " slept " + (long) time + "ms,val:" + cache.get("key"));
//                log.info("{} slept {}ms,val:{}", Thread.currentThread().getName(), (long) time, cache.get("key"));
                latch.countDown();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

}
