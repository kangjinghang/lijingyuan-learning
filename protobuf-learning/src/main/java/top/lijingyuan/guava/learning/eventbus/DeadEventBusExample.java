package top.lijingyuan.guava.learning.eventbus;

import com.google.common.eventbus.EventBus;
import top.lijingyuan.guava.learning.eventbus.listeners.DeadEventListener;

/**
 * DeadEventBusExample
 *
 * @author <a href="kangjinghang@gmail.com">kangjinghang</a>
 * @date 2021-05-24
 * @since 1.0.0
 */
public class DeadEventBusExample {

    public static void main(String[] args) {
        final EventBus eventBus = new EventBus("DeadEventBus") {
            @Override
            public String toString() {
                return "DEAD-EVENT-BUS";
            }
        };
        DeadEventListener deadEventListener = new DeadEventListener();
        eventBus.register(deadEventListener);
        eventBus.post("Hello");
        eventBus.post("Hello");
        eventBus.unregister(deadEventListener);
        eventBus.post("Hello");
        eventBus.post("Hello");
    }

}
