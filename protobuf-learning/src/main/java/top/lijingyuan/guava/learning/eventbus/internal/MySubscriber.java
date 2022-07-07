package top.lijingyuan.guava.learning.eventbus.internal;

import lombok.Data;

import java.lang.reflect.Method;

/**
 * Subscriber
 *
 * @author <a href="kangjinghang@gmail.com">kangjinghang</a>
 * @date 2021-05-25
 * @since 1.0.0
 */
@Data
public class MySubscriber {

    private Object subscribeObject;

    private Method subscribeMethod;

    private boolean disable;

    public MySubscriber(Object subscribeObject, Method subscribeMethod) {
        this.subscribeObject = subscribeObject;
        this.subscribeMethod = subscribeMethod;
    }
}
