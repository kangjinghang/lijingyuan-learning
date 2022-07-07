package top.lijingyuan.netty.chapter4.server.handler;

import com.codahale.metrics.ConsoleReporter;
import com.codahale.metrics.Gauge;
import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.jmx.JmxReporter;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;

import java.util.concurrent.atomic.AtomicLong;

/**
 * MetricHandler
 *
 * @author <a href="kangjinghang@gmail.com">kangjinghang</a>
 * @date 2021-09-07
 * @since 1.0.0
 */
@ChannelHandler.Sharable
public class MetricHandler extends ChannelDuplexHandler {

    private AtomicLong totalConnectionCounter = new AtomicLong();

    {
        MetricRegistry metricRegistry = new MetricRegistry();
        metricRegistry.register("totalConnectionCounter", (Gauge<Long>) () -> totalConnectionCounter.longValue());

        ConsoleReporter consoleReporter = ConsoleReporter.forRegistry(metricRegistry).build();
//        consoleReporter.start(5, TimeUnit.SECONDS);

        JmxReporter jmxReporter = JmxReporter.forRegistry(metricRegistry).build();
        jmxReporter.start();
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        totalConnectionCounter.incrementAndGet();
        super.channelActive(ctx);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        totalConnectionCounter.decrementAndGet();
        super.channelInactive(ctx);
    }

}
