package top.lijingyuan.guava.learning.eventbus.listeners;

import com.google.common.eventbus.Subscribe;
import lombok.extern.slf4j.Slf4j;

/**
 * ConcreteListener
 *
 * @author <a href="kangjinghang@gmail.com">kangjinghang</a>
 * @date 2021-05-24
 * @since 1.0.0
 */
@Slf4j
public class ConcreteListener extends BaseListener {

    @Subscribe
    public void conTask(final String event) {
        log.info("Received event [{}] will handle by {}.{}", event, this.getClass().getSimpleName(), "conTask");
    }

}
