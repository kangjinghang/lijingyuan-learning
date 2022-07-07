package top.lijingyuan.guava.learning;

import com.google.common.util.concurrent.Monitor;
import com.google.common.util.concurrent.RateLimiter;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.function.Consumer;

/**
 * Bucket 漏桶算法，只关注处理速率
 *
 * @author <a href="kangjinghang@gmail.com">kangjinghang</a>
 * @date 2021-05-16
 * @since 1.0.0
 */
public class Bucket {

    private final ConcurrentLinkedQueue<Integer> containers = new ConcurrentLinkedQueue<>();

    private static final int BUCKET_LIMIT = 1000;

    private final RateLimiter limiter = RateLimiter.create(10);

    private final Monitor offerMonitor = new Monitor();

    public void submit(Integer data) {
        if (offerMonitor.enterIf(offerMonitor.newGuard(() -> containers.size() < BUCKET_LIMIT))) {
            try {
                containers.offer(data);
                System.out.println(Thread.currentThread() + " submit data " + data + ",current size: " + containers.size());
            } finally {
                offerMonitor.leave();
            }
        } else {
            throw new IllegalStateException("The bucket is full.");
        }
    }

    public void takeThenConsume(Consumer<Integer> consumer) {
        if (offerMonitor.enterIf(offerMonitor.newGuard(() -> !containers.isEmpty()))) {
            try {
                System.out.println(Thread.currentThread() + " waiting " + limiter.acquire());
                consumer.accept(containers.poll());
            } finally {
                offerMonitor.leave();
            }
        }
    }

}
