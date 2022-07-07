package top.lijingyuan.guava.learning.eventbus.listeners;

import com.google.common.eventbus.DeadEvent;
import com.google.common.eventbus.Subscribe;
import lombok.extern.slf4j.Slf4j;

/**
 * DeadEventListener
 *
 * @author <a href="kangjinghang@gmail.com">kangjinghang</a>
 * @date 2021-05-24
 * @since 1.0.0
 */
@Slf4j
public class DeadEventListener {

    @Subscribe
    public void handle(DeadEvent event){
        System.out.println(event.getSource());
        System.out.println(event.getEvent());
    }

}
