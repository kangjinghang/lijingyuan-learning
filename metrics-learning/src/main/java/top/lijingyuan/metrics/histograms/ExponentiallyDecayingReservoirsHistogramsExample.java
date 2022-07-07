package top.lijingyuan.metrics.histograms;

import com.codahale.metrics.ConsoleReporter;
import com.codahale.metrics.ExponentiallyDecayingReservoir;
import com.codahale.metrics.Histogram;
import com.codahale.metrics.MetricRegistry;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

/**
 * example of {@link Histogram}
 * 指数衰减方式
 *
 * <p>
 * 直方图，展示平均数、中位数、标注误差率、75线、95线、99线
 *
 * @author <a href="kangjinghang@gmail.com">kangjinghang</a>
 * @date 2021-05-22
 * @since 1.0.0
 */
public class ExponentiallyDecayingReservoirsHistogramsExample {

    private static final MetricRegistry METRIC_REGISTRY = new MetricRegistry();

    private static final ConsoleReporter REPORTER = ConsoleReporter.forRegistry(METRIC_REGISTRY)
            .convertRatesTo(TimeUnit.SECONDS)
            .convertDurationsTo(TimeUnit.SECONDS)
            .build();

    private static final Histogram HISTOGRAM = new Histogram(new ExponentiallyDecayingReservoir());

    public static void main(String[] args) {
        REPORTER.start(10, TimeUnit.SECONDS);
        METRIC_REGISTRY.register("histogram", HISTOGRAM);
        while (true) {
            doSearch();
            randomSleep();
        }
    }

    private static void doSearch() {
        HISTOGRAM.update(ThreadLocalRandom.current().nextInt(10));
    }

    private static void randomSleep() {
        try {
            TimeUnit.SECONDS.sleep(ThreadLocalRandom.current().nextInt(5));
        } catch (InterruptedException e) {
            System.err.println("sleeping error");
            Thread.currentThread().interrupt();
        }
    }

}
