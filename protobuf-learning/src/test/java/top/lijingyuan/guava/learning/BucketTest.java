package top.lijingyuan.guava.learning;

import org.checkerframework.checker.units.qual.A;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

/**
 * BucketTest
 *
 * @author <a href="kangjinghang@xueqiu.com">kangjinghang</a>
 * @date 2021-05-16
 * @since 1.0.0
 */
public class BucketTest {

    public static void main(String[] args) {
        final Bucket bucket = new Bucket();
        final AtomicInteger dataCreator = new AtomicInteger();
        IntStream.range(0, 5).forEach(i -> new Thread(() -> {
            for (; ; ) {
                int data = dataCreator.getAndIncrement();
                try {
                    bucket.submit(data);
                    TimeUnit.MILLISECONDS.sleep(200);
                } catch (Exception e) {
                    if (e instanceof IllegalStateException) {
                        System.out.println(e.getMessage());
                    }
                    e.printStackTrace();
                }
            }
        }).start());
        // 生产25 限流10

        IntStream.range(0, 5).forEach(i -> new Thread(() -> {
            for (; ; ) {
                bucket.takeThenConsume(x -> System.out.println(Thread.currentThread() + " W " + x));
            }
        }).start());
    }

}
