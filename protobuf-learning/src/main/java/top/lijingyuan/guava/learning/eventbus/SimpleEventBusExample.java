package top.lijingyuan.guava.learning.eventbus;

import com.google.common.eventbus.EventBus;
import top.lijingyuan.guava.learning.eventbus.listeners.SimpleListener;

/**
 * SimpleEventBusExample
 *
 * @author <a href="kangjinghang@xueqiu.com">kangjinghang</a>
 * @date 2021-05-24
 * @since 1.0.0
 */
public class SimpleEventBusExample {

    public static void main(String[] args) {
        final EventBus eventBus = new EventBus();
        eventBus.register(new SimpleListener());
        System.out.println("post the simple event.");
        eventBus.post("Simple Event");
    }

}
