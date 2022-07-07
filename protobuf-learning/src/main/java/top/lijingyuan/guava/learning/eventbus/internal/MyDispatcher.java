package top.lijingyuan.guava.learning.eventbus.internal;

import java.lang.reflect.Method;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;

/**
 * MyDispatcher
 *
 * @author <a href="kangjinghang@gmail.com">kangjinghang</a>
 * @date 2021-05-25
 * @since 1.0.0
 */
public class MyDispatcher {

    private final Executor executor;

    private final MyEventExceptionHandler exceptionHandler;

    public static final Executor SEQ_EXECUTOR_SERVICE = SeqExecutorService.INSTANCE;

    public static final Executor PER_THREAD_EXECUTOR_SERVICE = PerThreadExecutorService.INSTANCE;

    public MyDispatcher(MyEventExceptionHandler exceptionHandler, Executor executor) {
        this.executor = executor;
        this.exceptionHandler = exceptionHandler;
    }

    public void dispatch(Bus bus, MyRegistry registry, Object event, String topic) {
        ConcurrentLinkedQueue<MySubscriber> subscribers = registry.scanSubscribers(topic);
        if (subscribers == null) {
            if (exceptionHandler != null) {
                // TODO hello
                exceptionHandler.handle(new IllegalArgumentException("The topic " + topic + " not bind yet"),
                        new DefaultEventContext(bus.getName(), null, event));
            }
            return;
        }
        subscribers.stream().filter(s -> !s.isDisable()).filter(s -> {
            Method subscribeMethod = s.getSubscribeMethod();
            Class<?> parameterType = subscribeMethod.getParameterTypes()[0];
            return parameterType.isAssignableFrom(event.getClass());
        }).forEach(s -> realInvokeSubscribe(s, event, bus));
    }

    private void realInvokeSubscribe(MySubscriber subscriber, Object event, Bus bus) {
        Method subscribeMethod = subscriber.getSubscribeMethod();
        Object subscribeObject = subscriber.getSubscribeObject();
        executor.execute(() -> {
            try {
                subscribeMethod.invoke(subscribeObject, event);
            } catch (Exception e) {
                if (exceptionHandler != null) {
                    exceptionHandler.handle(e, new DefaultEventContext(bus.getName(), subscriber, event));
                }
            }
        });
    }

    public void close() {
        if (executor instanceof SeqExecutorService) {
            ((ExecutorService) executor).shutdown();
        }
    }

    static MyDispatcher newDispatcher(MyEventExceptionHandler exceptionHandler, Executor executor) {
        return new MyDispatcher(exceptionHandler, executor);
    }

    static MyDispatcher seqDispatcher(MyEventExceptionHandler exceptionHandler) {
        return new MyDispatcher(exceptionHandler, SEQ_EXECUTOR_SERVICE);
    }

    static MyDispatcher perThreadDispatcher(MyEventExceptionHandler exceptionHandler) {
        return new MyDispatcher(exceptionHandler, PER_THREAD_EXECUTOR_SERVICE);
    }

    private static class SeqExecutorService implements Executor {

        private static final SeqExecutorService INSTANCE = new SeqExecutorService();

        @Override
        public void execute(Runnable command) {
            command.run();
        }
    }

    private static class PerThreadExecutorService implements Executor {

        private static final PerThreadExecutorService INSTANCE = new PerThreadExecutorService();

        @Override
        public void execute(Runnable command) {
            new Thread(command).start();
        }
    }

    private static class DefaultEventContext implements MyEventContext {

        private final String eventBusName;

        private final MySubscriber subscriber;

        private final Object event;

        public DefaultEventContext(String eventBusName, MySubscriber subscriber, Object event) {
            this.eventBusName = eventBusName;
            this.subscriber = subscriber;
            this.event = event;
        }

        @Override
        public String getSource() {
            return this.eventBusName;
        }

        @Override
        public Object getSubscriber() {
            return subscriber != null ? subscriber.getSubscribeObject() : null;
        }

        @Override
        public Method getSubscribe() {
            return subscriber != null ? subscriber.getSubscribeMethod() : null;
        }

        @Override
        public Object getEvent() {
            return this.event;
        }
    }

}
