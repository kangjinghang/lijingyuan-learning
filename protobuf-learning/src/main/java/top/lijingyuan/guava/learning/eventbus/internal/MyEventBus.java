package top.lijingyuan.guava.learning.eventbus.internal;

import java.util.concurrent.Executor;

import static top.lijingyuan.guava.learning.eventbus.internal.MyDispatcher.SEQ_EXECUTOR_SERVICE;

/**
 * EventBus
 *
 * @author <a href="kangjinghang@gmail.com">kangjinghang</a>
 * @date 2021-05-25
 * @since 1.0.0
 */
public class MyEventBus implements Bus {

    private final MyRegistry registry = new MyRegistry();

    private String busName;

    private static final String DEFAULT_BUS_NAME = "default";

    private static final String DEFAULT_TOPIC = "default-topic";

//    private final MyEventExceptionHandler eventExceptionHandler;

    private final MyDispatcher dispatcher;

    public MyEventBus() {
        this(DEFAULT_BUS_NAME, null, SEQ_EXECUTOR_SERVICE);
    }

    public MyEventBus(MyEventExceptionHandler eventExceptionHandler) {
        this(DEFAULT_BUS_NAME, eventExceptionHandler, SEQ_EXECUTOR_SERVICE);
    }

    public MyEventBus(String busName) {
        this(busName, null, SEQ_EXECUTOR_SERVICE);
    }

    MyEventBus(String busName, MyEventExceptionHandler eventExceptionHandler, Executor executor) {
        this.busName = busName;
//        this.eventExceptionHandler = eventExceptionHandler;
        this.dispatcher = MyDispatcher.newDispatcher(eventExceptionHandler, executor);
    }

    @Override
    public void register(Object subscriber) {
        registry.bind(subscriber);
    }

    @Override
    public void unregister(Object subscriber) {
        registry.unBind(subscriber);
    }

    @Override
    public void post(Object event) {
        this.post(event, DEFAULT_TOPIC);
    }

    @Override
    public void post(Object event, String topic) {
        this.dispatcher.dispatch(this, registry, event, topic);
    }

    @Override
    public void close() {
        this.dispatcher.close();
    }

    @Override
    public String getName() {
        return this.busName;
    }

}
