package top.lijingyuan.guava.learning.eventbus.listeners;

import com.google.common.eventbus.Subscribe;
import com.google.common.eventbus.SubscriberExceptionContext;
import com.google.common.eventbus.SubscriberExceptionHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * ExceptionListener
 *
 * @author <a href="kangjinghang@xueqiu.com">kangjinghang</a>
 * @date 2021-05-24
 * @since 1.0.0
 */
@Slf4j
public class ExceptionListener {

    @Subscribe
    public void m1(String event) {
        log.info("===m1 : {}===", event);
    }

    @Subscribe
    public void m2(String event) {
        log.info("===m2 : {}===", event);
    }

    @Subscribe
    public void m3(String event) {
        log.info("===m3 : {}===", event);
        throw new RuntimeException();
    }

    public static final class MyExceptionHandler implements SubscriberExceptionHandler {

        @Override
        public void handleException(Throwable exception, SubscriberExceptionContext context) {
            System.out.println(context.getEvent());
            System.out.println(context.getEventBus());
            System.out.println(context.getSubscriber());
            System.out.println(context.getSubscriberMethod());
        }

    }

}
