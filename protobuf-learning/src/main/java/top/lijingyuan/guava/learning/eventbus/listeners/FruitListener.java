package top.lijingyuan.guava.learning.eventbus.listeners;

import com.google.common.eventbus.Subscribe;
import lombok.extern.slf4j.Slf4j;
import top.lijingyuan.guava.learning.eventbus.event.Apple;
import top.lijingyuan.guava.learning.eventbus.event.Fruit;

/**
 * FruitListener
 *
 * @author <a href="kangjinghang@gmail.com">kangjinghang</a>
 * @date 2021-05-24
 * @since 1.0.0
 */
@Slf4j
public class FruitListener {

    @Subscribe
    public void eat(final Fruit event) {
        log.info("Fruit eat [{}].", event);
    }

    @Subscribe
    public void eat(final Apple event) {
        log.info("Apple eat [{}].", event);
    }

}
