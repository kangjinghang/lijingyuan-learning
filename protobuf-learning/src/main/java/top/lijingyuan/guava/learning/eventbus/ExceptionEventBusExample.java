package top.lijingyuan.guava.learning.eventbus;

import com.google.common.eventbus.EventBus;
import top.lijingyuan.guava.learning.eventbus.listeners.ExceptionListener;

/**
 * ExceptionEventBusExample
 *
 * @author <a href="kangjinghang@xueqiu.com">kangjinghang</a>
 * @date 2021-05-24
 * @since 1.0.0
 */
public class ExceptionEventBusExample {

    public static void main(String[] args) {
        final EventBus eventBus = new EventBus(new ExceptionListener.MyExceptionHandler());
        eventBus.register(new ExceptionListener());
        eventBus.post("exception post");
    }

}
