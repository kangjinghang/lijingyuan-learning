package top.lijingyuan.metrics.timer;

import com.codahale.metrics.*;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

/**
 * example of {@link Timer}
 * <p>
 * 与{@link Meter} 正好相反，{@link Timer} 代表一个事件消耗多少时间， {@link Meter}是单位时间内有多少事件发生。
 *
 * @author <a href="kangjinghang@gmail.com">kangjinghang</a>
 * @date 2021-05-22
 * @since 1.0.0
 */
public class TimerExample {

    private static final MetricRegistry METRIC_REGISTRY = new MetricRegistry();

    private static final ConsoleReporter REPORTER = ConsoleReporter.forRegistry(METRIC_REGISTRY)
//            .convertRatesTo(TimeUnit.SECONDS)
//            .convertDurationsTo(TimeUnit.SECONDS)
            .build();

    private static final Timer TIMER = METRIC_REGISTRY.timer("request", Timer::new);

    public static void main(String[] args) {
        REPORTER.start(10, TimeUnit.SECONDS);
        while (true) {
            business();
        }
    }

    private static void business() {
        Timer.Context context = TIMER.time();
        try {
            TimeUnit.SECONDS.sleep(ThreadLocalRandom.current().nextInt(10));
        } catch (InterruptedException e) {
            System.err.println("sleeping error");
            Thread.currentThread().interrupt();
        } finally {
            context.stop();
        }
    }

}
