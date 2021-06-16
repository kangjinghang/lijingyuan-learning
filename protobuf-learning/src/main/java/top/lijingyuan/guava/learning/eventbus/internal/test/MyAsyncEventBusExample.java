package top.lijingyuan.guava.learning.eventbus.internal.test;

import top.lijingyuan.guava.learning.eventbus.internal.Bus;
import top.lijingyuan.guava.learning.eventbus.internal.MyAsyncEventBus;

/**
 * MyAsyncEventBusExample
 *
 * @author <a href="kangjinghang@xueqiu.com">kangjinghang</a>
 * @date 2021-05-24
 * @since 1.0.0
 */
public class MyAsyncEventBusExample {

    public static void main(String[] args) {
        final Bus eventBus = new MyAsyncEventBus();
        eventBus.register(new MySimpleListener());
        eventBus.register(new MySimpleListener2());
        System.out.println("post the simple event.");
        eventBus.post("Simple Event");
        System.out.println("----------");
        eventBus.post(12, "alex");
    }

}
