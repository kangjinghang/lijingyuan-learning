package top.lijingyuan.metrics.gauges;

import com.codahale.metrics.ConsoleReporter;
import com.codahale.metrics.Gauge;
import com.codahale.metrics.MetricRegistry;

import java.util.Queue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

import static com.codahale.metrics.MetricRegistry.name;

/**
 * example of {@link Gauge}
 * <p>
 * 就是仪表盘，准备的反映一个数字，队列的个数，在线人数等等。
 *
 * @author <a href="kangjinghang@gmail.com">kangjinghang</a>
 * @date 2021-05-22
 * @since 1.0.0
 */
public class SimpleGaugeExample {

    private static final MetricRegistry METRIC_REGISTRY = new MetricRegistry();

    private static final ConsoleReporter REPORTER = ConsoleReporter.forRegistry(METRIC_REGISTRY)
            .convertRatesTo(TimeUnit.SECONDS)
            .convertDurationsTo(TimeUnit.SECONDS)
            .build();

    private static final Queue<Long> QUEUE = new LinkedBlockingDeque<>(1000);


    public static void main(String[] args) {
        METRIC_REGISTRY.register(name(SimpleGaugeExample.class, "queue-size"), (Gauge<Integer>) QUEUE::size);

        REPORTER.start(3, TimeUnit.SECONDS);
        new Thread(() -> {
            for (; ; ) {
                randomSleep();
                QUEUE.add(System.nanoTime());
            }
        }).start();
        new Thread(() -> {
            for (; ; ) {
                randomSleep();
                QUEUE.poll();
            }
        }).start();
    }

    private static void randomSleep() {
        try {
            TimeUnit.SECONDS.sleep(ThreadLocalRandom.current().nextInt(10));
        } catch (InterruptedException e) {
            System.err.println("sleeping error");
            Thread.currentThread().interrupt();
        }
    }

}
