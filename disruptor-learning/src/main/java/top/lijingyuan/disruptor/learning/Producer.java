package top.lijingyuan.disruptor.learning;

import com.lmax.disruptor.RingBuffer;

/**
 * Producer
 *
 * @author <a href="kangjinghang@xueqiu.com">kangjinghang</a>
 * @date 2021-07-19
 * @since 1.0.0
 */
public class Producer {

    private RingBuffer<Order> ringBuffer;

    public Producer(RingBuffer<Order> ringBuffer) {
        this.ringBuffer = ringBuffer;
    }

    public void setData(String id) {
        long sequence = ringBuffer.next();
        try {
            Order order = ringBuffer.get(sequence);
            order.setId(id);
        } finally {
            ringBuffer.publish(sequence);
        }
    }

}
