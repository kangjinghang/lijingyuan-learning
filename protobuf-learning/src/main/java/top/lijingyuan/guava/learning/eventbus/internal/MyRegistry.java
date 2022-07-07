package top.lijingyuan.guava.learning.eventbus.internal;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Registry
 *
 * <pre>
 * topic1->subscriber->subscribe
 *       ->subscriber->subscribe
 *       ->subscriber->subscribe
 * topic2->subscriber->subscribe
 *       ->subscriber->subscribe
 *       ->subscriber->subscribe
 * </pre>
 *
 * @author <a href="kangjinghang@gmail.com">kangjinghang</a>
 * @date 2021-05-25
 * @since 1.0.0
 */
class MyRegistry {

    private final ConcurrentHashMap<String, ConcurrentLinkedQueue<MySubscriber>> subscriberContainer = new ConcurrentHashMap<>();

    public void bind(Object subscriber) {
        List<Method> subscribeMethods = getSubscribeMethods(subscriber);
        subscribeMethods.forEach(method -> tireSubscriber(subscriber, method));
    }

    public void unBind(Object subscriber) {
        for (Map.Entry<String, ConcurrentLinkedQueue<MySubscriber>> entry : subscriberContainer.entrySet()) {
            ConcurrentLinkedQueue<MySubscriber> queue = entry.getValue();
            queue.forEach(s -> {
                if (s.getSubscribeObject() == subscriber) {
                    // 用打删除标记的方式，避免从队列里移除元素容易出现的问题
                    s.setDisable(true);
                }
            });
        }
    }

    public ConcurrentLinkedQueue<MySubscriber> scanSubscribers(final String topic) {
        return subscriberContainer.get(topic);
    }

    private void tireSubscriber(Object subscriber, Method method) {
        final MySubscribe annotation = method.getAnnotation(MySubscribe.class);
        String topic = annotation.topic();
        subscriberContainer.computeIfAbsent(topic, key -> new ConcurrentLinkedQueue<>());
        subscriberContainer.get(topic).add(new MySubscriber(subscriber, method));
    }

    private List<Method> getSubscribeMethods(Object subscriber) {
        final List<Method> methods = new ArrayList<>();
        Class<?> temp = subscriber.getClass();
        while (temp != null) {
            Method[] declaredMethods = temp.getDeclaredMethods();
            Arrays.stream(declaredMethods).filter(m -> m.isAnnotationPresent(MySubscribe.class)
                    && m.getParameterCount() == 1 && m.getModifiers() == Modifier.PUBLIC).forEach(methods::add);
            temp = temp.getSuperclass();
        }
        return methods;
    }


}
