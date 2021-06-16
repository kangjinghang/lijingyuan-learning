package top.lijingyuan.guava.learning.eventbus.listeners;

import com.google.common.eventbus.Subscribe;
import lombok.extern.slf4j.Slf4j;

/**
 * MultipleListener
 *
 * @author <a href="kangjinghang@xueqiu.com">kangjinghang</a>
 * @date 2021-05-24
 * @since 1.0.0
 */
@Slf4j
public class MultipleListener {

    @Subscribe
    public void task1(final String event) {
        log.info("The event [{}] received and will take a action by [{}].", event, "task1");
    }

    @Subscribe
    public void task2(final String event) {
        log.info("The event [{}] received and will take a action by [{}].", event, "task2");
    }

    @Subscribe
    public void integerTask(final Integer event) {
        log.info("The event [{}] received and will take a action by [{}].", event, "integerTask");
    }

}
