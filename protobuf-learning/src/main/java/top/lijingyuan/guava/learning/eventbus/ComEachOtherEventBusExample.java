package top.lijingyuan.guava.learning.eventbus;

import com.google.common.eventbus.EventBus;
import top.lijingyuan.guava.learning.eventbus.listeners.MultipleListener;
import top.lijingyuan.guava.learning.eventbus.service.QueryService;
import top.lijingyuan.guava.learning.eventbus.service.RequestQueryHandler;

/**
 * ComEachOtherEventBusExample
 *
 * @author <a href="kangjinghang@gmail.com">kangjinghang</a>
 * @date 2021-05-24
 * @since 1.0.0
 */
public class ComEachOtherEventBusExample {

    public static void main(String[] args) {
        final EventBus eventBus = new EventBus();
        QueryService queryService = new QueryService(eventBus);
        eventBus.register(new RequestQueryHandler(eventBus));
        eventBus.register(new MultipleListener());
        queryService.query("8899");
    }

}
