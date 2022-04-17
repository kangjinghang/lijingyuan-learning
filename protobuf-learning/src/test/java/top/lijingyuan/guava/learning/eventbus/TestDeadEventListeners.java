package top.lijingyuan.guava.learning.eventbus;

import com.google.common.eventbus.EventBus;
import org.junit.Test;

public class TestDeadEventListeners {

    @Test
    public void testDeadEventListeners() throws Exception {

        EventBus eventBus = new EventBus("test");
        DeadEventListener deadEventListener = new DeadEventListener();
        eventBus.register(deadEventListener);

        eventBus.post(new TestEvent(200));
        eventBus.post(new TestEvent(300));

        System.out.println("deadEvent:" + deadEventListener.isNotDelivered());

    }

}