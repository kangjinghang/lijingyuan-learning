package top.lijingyuan.guava.learning.eventbus.listeners;

import com.google.common.eventbus.Subscribe;
import lombok.extern.slf4j.Slf4j;

/**
 * SimpleListener
 *
 * @author <a href="kangjinghang@gmail.com">kangjinghang</a>
 * @date 2021-05-24
 * @since 1.0.0
 */
@Slf4j
public class SimpleListener {

    @Subscribe
    public void doAction(final String event) {
        log.info("Received event [{}] and will take a action.", event);
    }

}
