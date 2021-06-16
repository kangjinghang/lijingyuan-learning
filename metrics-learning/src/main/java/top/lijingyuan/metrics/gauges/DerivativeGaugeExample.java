package top.lijingyuan.metrics.gauges;

import com.codahale.metrics.*;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.CacheStats;
import com.google.common.cache.LoadingCache;

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
public class DerivativeGaugeExample {

    private static final LoadingCache<String, String> CACHE = CacheBuilder.newBuilder()
            .maximumSize(10).expireAfterAccess(5, TimeUnit.SECONDS).recordStats()
            .build(new CacheLoader<String, String>() {
                @Override
                public String load(String key) throws Exception {
//                    System.out.println(123);
                    return key.toUpperCase();
                }
            });

    private static final MetricRegistry METRIC_REGISTRY = new MetricRegistry();

    private static final ConsoleReporter REPORTER = ConsoleReporter.forRegistry(METRIC_REGISTRY)
            .convertRatesTo(TimeUnit.SECONDS)
            .convertDurationsTo(TimeUnit.SECONDS)
            .build();

    public static void main(String[] args) throws InterruptedException {

        REPORTER.start(10, TimeUnit.SECONDS);

        Gauge cachedGauge = METRIC_REGISTRY.gauge("cache-status", () -> CACHE::stats);
        METRIC_REGISTRY.register("missCount", new DerivativeGauge<CacheStats, Long>(cachedGauge) {
            @Override
            protected Long transform(CacheStats value) {
                return value.missCount();
            }
        });
        METRIC_REGISTRY.register("exceptionCount", new DerivativeGauge<CacheStats, Long>(cachedGauge) {
            @Override
            protected Long transform(CacheStats value) {
                return value.loadExceptionCount();
            }
        });

        while (true) {
            business();
            TimeUnit.SECONDS.sleep(1);
        }
    }

    private static void business() {
//        System.out.println(CACHE.getUnchecked("alex"));
        CACHE.getUnchecked("alex");
    }

}
