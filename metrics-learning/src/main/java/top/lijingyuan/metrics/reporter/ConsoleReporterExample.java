package top.lijingyuan.metrics.reporter;

import com.codahale.metrics.*;
import com.codahale.metrics.jmx.JmxReporter;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

/**
 * ConsoleReporter
 *
 * @author <a href="kangjinghang@xueqiu.com">kangjinghang</a>
 * @date 2021-05-22
 * @since 1.0.0
 */
public class ConsoleReporterExample {

    private static final MetricRegistry REGISTRY = new MetricRegistry();

    private static final Counter TOTAL_BUSINESS = new Counter();

    private static final Counter SUCCESS_BUSINESS = new Counter();

    private static final Counter FAIL_BUSINESS = new Counter();

    private static final Timer TIMER = new Timer();

    private static final Histogram VOLUME_HISTOGRAM = new Histogram(new ExponentiallyDecayingReservoir());

    private static final RatioGauge SUCCESS_RATIO = new RatioGauge() {
        @Override
        protected Ratio getRatio() {
            return Ratio.of(SUCCESS_BUSINESS.getCount(), TOTAL_BUSINESS.getCount());
        }
    };

    private static final JmxReporter REPORTER = JmxReporter.forRegistry(REGISTRY)
            .convertRatesTo(TimeUnit.SECONDS)
            .convertDurationsTo(TimeUnit.SECONDS)
            .build();

    private static final ConsoleReporter CONSOLE_REPORTER = ConsoleReporter.forRegistry(REGISTRY)
            .convertRatesTo(TimeUnit.SECONDS)
            .convertDurationsTo(TimeUnit.SECONDS)
            .build();

    static {
        REGISTRY.register("cloud-disk-upload-total", TOTAL_BUSINESS);
        REGISTRY.register("cloud-disk-upload-success", SUCCESS_BUSINESS);
        REGISTRY.register("cloud-disk-upload-failure", FAIL_BUSINESS);
        REGISTRY.register("cloud-disk-upload-frequency", TIMER);
        REGISTRY.register("cloud-disk-upload-volume", VOLUME_HISTOGRAM);
        REGISTRY.register("cloud-disk-upload-suc-rate", SUCCESS_RATIO);
    }

    public static void main(String[] args) {
        REPORTER.start();
        CONSOLE_REPORTER.start(10, TimeUnit.SECONDS);
        while (true) {
            upload(new byte[ThreadLocalRandom.current().nextInt(10000)]);
        }
    }

    private static void upload(byte[] buffer) {
        TOTAL_BUSINESS.inc();
        Timer.Context context = TIMER.time();
        try {
            int x = 1 / ThreadLocalRandom.current().nextInt(10);
            TimeUnit.MILLISECONDS.sleep(1000);
            VOLUME_HISTOGRAM.update(buffer.length);
            SUCCESS_BUSINESS.inc();
        } catch (Exception e) {
//            System.err.println(e.getMessage());
            FAIL_BUSINESS.inc();
        } finally {
            context.close();
        }
    }

}
