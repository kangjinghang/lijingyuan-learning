package top.lijingyuan.guava.learning.eventbus.internal;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * MyAsyncEventBus
 *
 * @author <a href="kangjinghang@xueqiu.com">kangjinghang</a>
 * @date 2021-05-26
 * @since 1.0.0
 */
public class MyAsyncEventBus extends MyEventBus {

    public MyAsyncEventBus(String busName, MyEventExceptionHandler eventExceptionHandler, ThreadPoolExecutor executor) {
        super(busName, eventExceptionHandler, executor);
    }

    public MyAsyncEventBus(String busName, ThreadPoolExecutor executor) {
        this(busName, null, executor);
    }

    public MyAsyncEventBus(String busName) {
        super(busName, null, Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() * 2));
    }

    public MyAsyncEventBus() {
        this("sync-default");
    }

    public MyAsyncEventBus(MyEventExceptionHandler eventExceptionHandler, ThreadPoolExecutor executor) {
        this("sync-default", eventExceptionHandler, executor);
    }
}
