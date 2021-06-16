package top.lijingyuan.guava.learning;


import com.google.common.base.Stopwatch;
import com.google.common.util.concurrent.RateLimiter;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * TokenBucket
 *
 * @author <a href="kangjinghang@xueqiu.com">kangjinghang</a>
 * @date 2021-05-16
 * @since 1.0.0
 */
public class TokenBucket {

    private AtomicInteger phoneNumbers = new AtomicInteger();

    private static final int LIMIT = 100;

    private RateLimiter rateLimiter = RateLimiter.create(10);

    private final int saleLimit;

    public TokenBucket() {
        this(LIMIT);
    }

    public TokenBucket(int saleLimit) {
        this.saleLimit = saleLimit;
    }

    public int buy() {
        Stopwatch stopwatch = Stopwatch.createStarted();
        boolean success = rateLimiter.tryAcquire(10, TimeUnit.SECONDS);
        if (success) {
            if (phoneNumbers.get() >= saleLimit) {
                throw new IllegalStateException("Not any phone can be sale, please wait to next time.");
            }
            int phoneNo = phoneNumbers.getAndIncrement();
            handleOrder();
            System.out.println(Thread.currentThread() + " user get the Mi phone: " + phoneNo + ",ELT:" + stopwatch.stop());
            return phoneNo;
        } else {
            stopwatch.stop();
            throw new RuntimeException("Sorry, occur exception when buy phone");
        }
    }

    private void handleOrder() {
        try {
            TimeUnit.SECONDS.sleep(ThreadLocalRandom.current().nextInt(10));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
