package top.lijingyuan.metrics.meters;

import com.codahale.metrics.ConsoleReporter;
import com.codahale.metrics.Meter;
import com.codahale.metrics.MetricRegistry;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

/**
 * example of {@link Meter}
 *
 * <p>
 * <p>
 * 反映的是一系列事件的处理速率（rate），每个单位时间内能处理多少事件，就像qps/tps，
 * 就像是top或者uptime命令的load averages 负载均衡。
 * 这个例子就是每分钟能处理多少个请求，多少容量的请求
 *
 * @author <a href="kangjinghang@gmail.com">kangjinghang</a>
 * @date 2021-05-22
 * @since 1.0.0
 */
public class MeterExample {

    private static final MetricRegistry METRIC_REGISTRY = new MetricRegistry();

    private static final Meter REQUEST_METER = METRIC_REGISTRY.meter("tps");

    private static final Meter VOLUME_METER = METRIC_REGISTRY.meter("volume");

    public static void main(String[] args) {
        ConsoleReporter reporter = ConsoleReporter.forRegistry(METRIC_REGISTRY)
                .convertRatesTo(TimeUnit.MINUTES)
                .convertDurationsTo(TimeUnit.SECONDS)
                .build();
        reporter.start(10, TimeUnit.SECONDS);
        for (; ; ) {
            handleRequest(new byte[ThreadLocalRandom.current().nextInt(1000)]);
            randomSleep();
        }
    }

    private static void handleRequest(byte[] request) {
        REQUEST_METER.mark();
        VOLUME_METER.mark(request.length);
        randomSleep();
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
