package top.lijingyuan.guava.learning.eventbus.internal.test;

import lombok.extern.slf4j.Slf4j;
import top.lijingyuan.guava.learning.eventbus.internal.MySubscribe;

import java.util.concurrent.TimeUnit;

/**
 * MySimpleListener2
 *
 * @author <a href="kangjinghang@gmail.com">kangjinghang</a>
 * @date 2021-05-24
 * @since 1.0.0
 */
@Slf4j
public class MySimpleListener2 {

    @MySubscribe
    public void doAction(final String event) {
        try {
            TimeUnit.SECONDS.sleep(15);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.info("MySimpleListener2 received event [{}] and will take a action.", event);
    }

    @MySubscribe(topic = "alex")
    public void doAlexAction(final Integer event) {
        log.info("MySimpleListener2 alex received event [{}] and will take a action.", event);
    }

    @MySubscribe(topic = "test")
    public void doTestAction(final Integer event) {
        throw new RuntimeException();
    }

}
