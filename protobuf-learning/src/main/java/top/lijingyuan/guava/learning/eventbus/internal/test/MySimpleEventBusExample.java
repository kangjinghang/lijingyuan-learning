package top.lijingyuan.guava.learning.eventbus.internal.test;

import top.lijingyuan.guava.learning.eventbus.internal.Bus;
import top.lijingyuan.guava.learning.eventbus.internal.MyEventBus;

/**
 * MySimpleEventBusExample
 *
 * @author <a href="kangjinghang@xueqiu.com">kangjinghang</a>
 * @date 2021-05-24
 * @since 1.0.0
 */
public class MySimpleEventBusExample {

    public static void main(String[] args) {
        final Bus eventBus = new MyEventBus(((cause, context) -> {
            cause.printStackTrace();
            System.out.println("==============");
            System.out.println(context.getSource());
            System.out.println(context.getEvent());
            System.out.println(context.getSubscribe());
            System.out.println(context.getSubscriber());
        }));
        eventBus.register(new MySimpleListener());
        eventBus.register(new MySimpleListener2());
        System.out.println("post the simple event.");
        eventBus.post("Simple Event");
        System.out.println("----------");
        eventBus.post(12, "alex");
        System.out.println("----------");

//        eventBus.post(12, "test");

    }

}
