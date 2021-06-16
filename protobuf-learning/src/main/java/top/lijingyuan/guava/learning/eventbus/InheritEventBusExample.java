package top.lijingyuan.guava.learning.eventbus;

import com.google.common.eventbus.EventBus;
import top.lijingyuan.guava.learning.eventbus.listeners.ConcreteListener;

/**
 * InheritEventBusExample
 *
 * @author <a href="kangjinghang@xueqiu.com">kangjinghang</a>
 * @date 2021-05-24
 * @since 1.0.0
 */
public class InheritEventBusExample {

    public static void main(String[] args) {
        final EventBus eventBus = new EventBus();
        eventBus.register(new ConcreteListener());
        System.out.println("post the string event.");
        eventBus.post("I am string event");
        System.out.println("post the integer event.");
        eventBus.post(100);
    }

}
