package top.lijingyuan.metrics.gauges;

import com.codahale.metrics.ConsoleReporter;
import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.jvm.JmxAttributeGauge;

import javax.management.ObjectName;
import java.util.concurrent.TimeUnit;

import static com.codahale.metrics.MetricRegistry.name;

/**
 * example of {@link JmxAttributeGauge}
 * <p>
 * 暴露原来只能通过jconsole才能看到的jmx bean 的信息
 *
 * @author <a href="kangjinghang@gmail.com">kangjinghang</a>
 * @date 2021-05-22
 * @since 1.0.0
 */
public class JmxAttributeGaugeExample {

    private static final MetricRegistry METRIC_REGISTRY = new MetricRegistry();

    private static final ConsoleReporter REPORTER = ConsoleReporter.forRegistry(METRIC_REGISTRY)
            .convertRatesTo(TimeUnit.SECONDS)
            .convertDurationsTo(TimeUnit.SECONDS)
            .build();


    public static void main(String[] args) throws Exception {
        METRIC_REGISTRY.register(name(JmxAttributeGaugeExample.class, "HeapMemory"), new JmxAttributeGauge(
                new ObjectName("java.lang:type=Memory"), "HeapMemoryUsage"
        ));
        METRIC_REGISTRY.register(name(JmxAttributeGaugeExample.class, "NonHeapMemory"), new JmxAttributeGauge(
                new ObjectName("java.lang:type=Memory"), "NonHeapMemoryUsage"
        ));

        REPORTER.start(10, TimeUnit.SECONDS);
        Thread.currentThread().join();
    }

}
