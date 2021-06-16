package top.lijingyuan.guava.learning.eventbus.service;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import lombok.extern.slf4j.Slf4j;
import top.lijingyuan.guava.learning.eventbus.event.Request;
import top.lijingyuan.guava.learning.eventbus.event.Response;

/**
 * RequestQueryHandler
 *
 * @author <a href="kangjinghang@xueqiu.com">kangjinghang</a>
 * @date 2021-05-24
 * @since 1.0.0
 */
@Slf4j
public class RequestQueryHandler {

    private final EventBus eventBus;

    public RequestQueryHandler(EventBus eventBus) {
        this.eventBus = eventBus;
    }

    @Subscribe
    public void doQuery(Request request) {
        log.info("Start query the orderNo [{}]", request);
        this.eventBus.post(new Response());
    }

}
