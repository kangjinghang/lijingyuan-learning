package top.lijingyuan.guava.learning;

import com.google.common.util.concurrent.RateLimiter;
import org.junit.Test;

import java.util.concurrent.*;
import java.util.stream.IntStream;

/**
 * RateLimiterExample
 *
 * @author <a href="kangjinghang@xueqiu.com">kangjinghang</a>
 * @date 2021-05-16
 * @since 1.0.0
 */
public class RateLimiterExample {

    private final static RateLimiter LIMITER = RateLimiter.create(0.5);

    private static final Semaphore SEMAPHORE = new Semaphore(3);

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(10);
//        IntStream.range(0, 10).forEach(i -> executorService.submit(RateLimiterExample::testLimiter));
        IntStream.range(0, 10).forEach(i -> executorService.submit(RateLimiterExample::testSemaphore));
    }

    private static void testLimiter() {
        System.out.println(Thread.currentThread() + " waiting " + LIMITER.acquire());
    }

    private static void testSemaphore() {
        try {
            SEMAPHORE.acquire();
            System.out.println(Thread.currentThread() + " is coming and do work.");
            TimeUnit.SECONDS.sleep(ThreadLocalRandom.current().nextInt(10));
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            SEMAPHORE.release();
            System.out.println(Thread.currentThread() + " release the semaphore.");
        }
    }

}
