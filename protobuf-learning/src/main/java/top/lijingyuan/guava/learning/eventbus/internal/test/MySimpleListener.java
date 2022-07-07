package top.lijingyuan.guava.learning.eventbus.internal.test;

import lombok.extern.slf4j.Slf4j;
import top.lijingyuan.guava.learning.eventbus.internal.MySubscribe;

/**
 * MySimpleListener
 *
 * @author <a href="kangjinghang@gmail.com">kangjinghang</a>
 * @date 2021-05-24
 * @since 1.0.0
 */
@Slf4j
public class MySimpleListener {

    @MySubscribe
    public void doAction(final String event) {
        log.info("MySimpleListener received event [{}] and will take a action.", event);
    }

}
