package top.lijingyuan.disruptor.learning;

import com.lmax.disruptor.*;
import com.lmax.disruptor.dsl.ProducerType;

import java.util.UUID;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * MultiDisruptorDemo
 *
 * @author <a href="kangjinghang@gmail.com">kangjinghang</a>
 * @date 2021-07-19
 * @since 1.0.0
 */
public class MultiDisruptorDemo {

    public static void main(String[] args) {
        // 1.创建ringBuffer
        RingBuffer<Order> ringBuffer = RingBuffer.create(ProducerType.MULTI, Order::new, 1024 * 1024, new YieldingWaitStrategy());

        // 2.通过ringBuffer创建一个屏障
        SequenceBarrier barrier = ringBuffer.newBarrier();

        // 3.创建多个消费者组
        Consumer[] consumers = new Consumer[16];
        for (int i = 0; i < consumers.length; i++) {
            consumers[i] = new Consumer("C" + i);
        }

        // 4.构造多消费者工作池
        WorkerPool<Order> workerPool = new WorkerPool<>(ringBuffer, barrier,
                new EventExceptionHandler(), consumers);

        // 5.设置多个消费者的sequence序号，用于单独统计消费速度，并设置到ringBuffer中
        ringBuffer.addGatingSequences(workerPool.getWorkerSequences());

        // 6.启动workerPool（消费者）
        ExecutorService pool = Executors.newFixedThreadPool(8);
        workerPool.start(pool);

        final CountDownLatch countDownLatch = new CountDownLatch(1);
        for (int i = 0; i < 10; i++) {
            final Producer producer = new Producer(ringBuffer);
            new Thread(() -> {
                try {
                    countDownLatch.countDown();
                } catch (Exception e) {

                }
                for (int j = 0; j < 10; j++) {
                    producer.setData(UUID.randomUUID().toString());
                }
            }).start();
        }

        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("线程创建完毕，开始生产数据......");
        countDownLatch.countDown();

        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("任务总数：" + Consumer.getCount());
        pool.shutdown();
    }

    public static class EventExceptionHandler implements ExceptionHandler<Order> {

        @Override
        public void handleEventException(Throwable ex, long sequence, Order event) {

        }

        @Override
        public void handleOnStartException(Throwable ex) {

        }

        @Override
        public void handleOnShutdownException(Throwable ex) {

        }
    }

}
