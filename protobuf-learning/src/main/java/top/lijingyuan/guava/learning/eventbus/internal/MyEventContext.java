package top.lijingyuan.guava.learning.eventbus.internal;

import java.lang.reflect.Method;

/**
 * MyEventContext
 *
 * @author <a href="kangjinghang@xueqiu.com">kangjinghang</a>
 * @date 2021-05-25
 * @since 1.0.0
 */
public interface MyEventContext {

    String getSource();

    Object getSubscriber();

    Method getSubscribe();

    Object getEvent();

}
