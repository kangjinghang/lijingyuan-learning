package top.lijingyuan.metrics.counters;

import com.codahale.metrics.ConsoleReporter;
import com.codahale.metrics.Counter;
import com.codahale.metrics.MetricRegistry;

import java.util.Queue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

/**
 * example of {@link Counter}
 * <p>
 * 就是一个计数器
 *
 * @author <a href="kangjinghang@gmail.com">kangjinghang</a>
 * @date 2021-05-22
 * @since 1.0.0
 */
public class CountersExample {

    private static final MetricRegistry METRIC_REGISTRY = new MetricRegistry();

    private static final ConsoleReporter REPORTER = ConsoleReporter.forRegistry(METRIC_REGISTRY)
            .convertRatesTo(TimeUnit.SECONDS)
            .convertDurationsTo(TimeUnit.SECONDS)
            .build();

    private static final Queue<Long> QUEUE = new LinkedBlockingDeque<>(1000);


    public static void main(String[] args) {
        Counter counter = METRIC_REGISTRY.counter("queue-count", Counter::new);

        REPORTER.start(10, TimeUnit.SECONDS);

        new Thread(() -> {
            for (; ; ) {
                randomSleep();
                QUEUE.add(System.nanoTime());
                counter.inc();
            }
        }).start();
        new Thread(() -> {
            for (; ; ) {
                randomSleep();
                QUEUE.poll();
                counter.dec();
            }
        }).start();
    }

    private static void randomSleep() {
        try {
            TimeUnit.MILLISECONDS.sleep(ThreadLocalRandom.current().nextInt(500));
        } catch (InterruptedException e) {
            System.err.println("sleeping error");
            Thread.currentThread().interrupt();
        }
    }

}
