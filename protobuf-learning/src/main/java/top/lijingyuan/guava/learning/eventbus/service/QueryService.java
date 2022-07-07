package top.lijingyuan.guava.learning.eventbus.service;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import lombok.extern.slf4j.Slf4j;
import top.lijingyuan.guava.learning.eventbus.event.Request;
import top.lijingyuan.guava.learning.eventbus.event.Response;

/**
 * QueryService
 *
 * @author <a href="kangjinghang@gmail.com">kangjinghang</a>
 * @date 2021-05-24
 * @since 1.0.0
 */
@Slf4j
public class QueryService {

    private final EventBus eventBus;

    public QueryService(EventBus eventBus) {
        this.eventBus = eventBus;
        this.eventBus.register(this);
    }

    public void query(String orderNo) {
        log.info("Received the orderNo [{}]", orderNo);
        this.eventBus.post(new Request(orderNo));
    }

    @Subscribe
    public void handleResponse(Response response) {
        log.info("response [{}]", response);
    }

}
