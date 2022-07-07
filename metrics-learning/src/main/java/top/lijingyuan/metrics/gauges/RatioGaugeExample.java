package top.lijingyuan.metrics.gauges;

import com.codahale.metrics.*;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

/**
 * example of {@link RatioGauge}
 * <p>
 * 反映比例，如成功率。
 *
 * @author <a href="kangjinghang@gmail.com">kangjinghang</a>
 * @date 2021-05-22
 * @since 1.0.0
 */
public class RatioGaugeExample {

    private static final MetricRegistry METRIC_REGISTRY = new MetricRegistry();

    private static final ConsoleReporter REPORTER = ConsoleReporter.forRegistry(METRIC_REGISTRY)
            .convertRatesTo(TimeUnit.SECONDS)
            .convertDurationsTo(TimeUnit.SECONDS)
            .build();

    private static final Meter TOTAL_METER = new Meter();

    private static final Meter SUCCESS_METER = new Meter();

    public static void main(String[] args) throws Exception {
        METRIC_REGISTRY.gauge("success-size", () -> new RatioGauge() {
            @Override
            protected Ratio getRatio() {
                return Ratio.of(SUCCESS_METER.getCount(), TOTAL_METER.getCount());
            }
        });

        REPORTER.start(10, TimeUnit.SECONDS);
        for (; ; ) {
            business();
            randomSleep();
        }
    }

    private static void business() {
        TOTAL_METER.mark();
        try {
            int x = 10 / ThreadLocalRandom.current().nextInt(5);
            SUCCESS_METER.mark();
        } catch (Exception e) {
            System.err.println("error");
        }
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
