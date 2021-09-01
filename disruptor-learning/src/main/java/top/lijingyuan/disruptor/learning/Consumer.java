package top.lijingyuan.disruptor.learning;

import com.lmax.disruptor.WorkHandler;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Consumer
 *
 * @author <a href="kangjinghang@xueqiu.com">kangjinghang</a>
 * @date 2021-07-19
 * @since 1.0.0
 */
public class Consumer implements WorkHandler<Order> {

    private String consumerId;

    private static AtomicInteger count = new AtomicInteger(0);

    private Random random = new Random();

    public Consumer(String consumerId) {
        this.consumerId = consumerId;
    }

    @Override
    public void onEvent(Order order) throws Exception {
        count.incrementAndGet();
        System.out.println("消费者-->" + this.consumerId + "，订单ID：" + order.getId());
    }

    public static int getCount() {
        return count.get();
    }

}
