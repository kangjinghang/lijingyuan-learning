package top.lijingyuan.disruptor.learning;

import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.YieldingWaitStrategy;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

/**
 * DisruptorDemo
 *
 * @author <a href="kangjinghang@gmail.com">kangjinghang</a>
 * @date 2021-07-19
 * @since 1.0.0
 */
public class DisruptorDemo {

    public static void main(String[] args) {
        // 1.创建一个线程工厂提供线程来触发Consumer的事件处理
        ThreadFactory threadFactory = Executors.defaultThreadFactory();

        // 2.创建工厂
        StringEventFactory eventFactory = new StringEventFactory();

        // 3.创建ringBuffer，大小一定是2的N次方
        int ringBufferSize = 16;
        // 4.创建Disruptor
        Disruptor<StringEvent> disruptor = new Disruptor<>(
                // 事件工厂
                eventFactory,
                // 环形数组大小
                ringBufferSize,
                // 线程工厂
                threadFactory,
                // 单个生产者
                ProducerType.SINGLE,
                // 生产者放满后的生产者等待策略
                new YieldingWaitStrategy());
        // 5.连接消费者方法
        disruptor.handleEventsWith(new StringHandler());

        // 6.启动
        disruptor.start();

        // 以上：消费者启动了

        // 下面开始生产
        // 7.创建ringBuffer容器
        RingBuffer<StringEvent> ringBuffer = disruptor.getRingBuffer();
        // 8.创建生产者
        StringEventProducer producer = new StringEventProducer(ringBuffer);

        // 生产事件（数据）
        for (int i = 0; i < 100; i++) {
            producer.onData(String.valueOf(i));
        }

        // 9.关闭disruptor和Executor
        disruptor.shutdown();

    }

}
