package top.lijingyuan.guava.learning;

import com.google.common.util.concurrent.RateLimiter;

import java.text.DecimalFormat;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

public class RateLimiterTest {

    public static void main(String[] args) throws InterruptedException {
        // qps设置为5，代表一秒钟只允许处理五个并发请求
        RateLimiter rateLimiter = RateLimiter.create(5);
        ExecutorService executorService = Executors.newFixedThreadPool(5);
        int nTasks = 10;
        CountDownLatch countDownLatch = new CountDownLatch(nTasks);
        long start = System.currentTimeMillis();
        for (int i = 0; i < nTasks; i++) {
            final int j = i;
            executorService.submit(() -> {
                rateLimiter.acquire(1);
                System.out.println(Thread.currentThread().getName() + " gets job " + j + " doing");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                }
                System.out.println(Thread.currentThread().getName() + " gets job " + j + " done");
                countDownLatch.countDown();
            });
        }
        executorService.shutdown();
        countDownLatch.await();
        long end = System.currentTimeMillis();
        System.out.println("10 jobs gets done by 5 threads concurrently in " + (end - start) + " milliseconds");

        System.out.println("=========================");

        compareBurstyRequest();
    }

    /**
     * 突发请求情况对比
     */
    public static void compareBurstyRequest() {
        RateLimiter burstyLimiter = RateLimiter.create(5);
        RateLimiter warmingUpLimiter = RateLimiter.create(5, 2, TimeUnit.SECONDS);

        DecimalFormat df = new DecimalFormat("0.00");
        // 积攒1秒
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("稳定限流器");
        IntStream.range(0, 10).forEach(a -> {
            double acquire = burstyLimiter.acquire();
            System.out.println("第" + a + "次请求等待时间：" + df.format(acquire));
        });

        System.out.println("预热限流器");
        IntStream.range(0, 10).forEach(a -> {
            double acquire = warmingUpLimiter.acquire();
            System.out.println("第" + a + "次请求等待时间：" + df.format(acquire));
        });
    }

}

