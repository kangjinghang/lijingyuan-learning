package top.lijingyuan.disruptor.learning;

import com.lmax.disruptor.RingBuffer;

/**
 * 生产者发送事件（数据）
 *
 * @author <a href="kangjinghang@gmail.com">kangjinghang</a>
 * @date 2021-07-19
 * @since 1.0.0
 */
public class StringEventProducer {

    /**
     * 核心类：环形数组这个类
     */
    public final RingBuffer<StringEvent> ringBuffer;

    public StringEventProducer(RingBuffer<StringEvent> ringBuffer) {
        this.ringBuffer = ringBuffer;
    }

    /**
     * 生产事件（数据）的方法
     * 其实就是往环形数据里放数据
     *
     * @param data
     */
    public void onData(String data) {
        // 1.获取ringBuffer的下一个序号，也就是我们放置数据的位置
        long sequence = ringBuffer.next();
        try {
            // 2.取出空的事件队列（初始化）
            StringEvent stringEvent = ringBuffer.get(sequence);
            // 3.获取事件队列传递的数据
            stringEvent.setValue(data);
        } finally {
            // 4.发布事件（数据）
            ringBuffer.publish(sequence);
        }
    }

}
