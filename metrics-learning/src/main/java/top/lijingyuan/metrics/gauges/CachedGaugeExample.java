package top.lijingyuan.metrics.gauges;

import com.codahale.metrics.CachedGauge;
import com.codahale.metrics.ConsoleReporter;
import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.RatioGauge;

import java.util.concurrent.TimeUnit;

/**
 * example of {@link RatioGauge}
 * <p>
 * gauge做一个缓存，不会每次都运行，避免metrics本身的性能影响到正常业务程序的性能，一段时间内拿的是内存中的gauge值。
 * 比如评论数，5分钟内都没啥影响，性能无侵入的效果。
 * <p>
 * 也可以用Guava的cacheGauge，反正都能达到cache的效果。
 *
 * @author <a href="kangjinghang@xueqiu.com">kangjinghang</a>
 * @date 2021-05-22
 * @since 1.0.0
 */
public class CachedGaugeExample {

    private static final MetricRegistry METRIC_REGISTRY = new MetricRegistry();

    private static final ConsoleReporter REPORTER = ConsoleReporter.forRegistry(METRIC_REGISTRY)
            .convertRatesTo(TimeUnit.SECONDS)
            .convertDurationsTo(TimeUnit.SECONDS)
            .build();

    public static void main(String[] args) throws InterruptedException {
        METRIC_REGISTRY.gauge("success-size", () -> new CachedGauge<Long>(30, TimeUnit.SECONDS) {
            @Override
            protected Long loadValue() {
                return queryFromDb();
            }
        });

        REPORTER.start(10, TimeUnit.SECONDS);

        System.out.println("current: " + System.currentTimeMillis());
        Thread.currentThread().join();
    }

    private static long queryFromDb() {
        System.out.println("====queryFromDb====");
        return System.currentTimeMillis();
    }

}
