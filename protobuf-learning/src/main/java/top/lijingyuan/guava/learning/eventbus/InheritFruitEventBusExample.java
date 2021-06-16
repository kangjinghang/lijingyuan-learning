package top.lijingyuan.guava.learning.eventbus;

import com.google.common.eventbus.EventBus;
import top.lijingyuan.guava.learning.eventbus.event.Apple;
import top.lijingyuan.guava.learning.eventbus.event.Fruit;
import top.lijingyuan.guava.learning.eventbus.listeners.FruitListener;

/**
 * InheritFruitEventBusExample
 *
 * @author <a href="kangjinghang@xueqiu.com">kangjinghang</a>
 * @date 2021-05-24
 * @since 1.0.0
 */
public class InheritFruitEventBusExample {

    public static void main(String[] args) {
        final EventBus eventBus = new EventBus();
        eventBus.register(new FruitListener());
        System.out.println("post the string event.");
        eventBus.post(new Apple("a apple"));
        System.out.println("==========");
        eventBus.post(new Fruit("a fruit"));
    }

}
