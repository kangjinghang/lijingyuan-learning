package top.lijingyuan.guava.learning.concurrent;

import com.google.common.util.concurrent.Monitor;

import java.util.LinkedList;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * MonitorExample
 *
 * @author <a href="kangjinghang@xueqiu.com">kangjinghang</a>
 * @date 2021-05-26
 * @since 1.0.0
 */
public class MonitorExample {

    public static void main(String[] args) {
        final Synchronized sync = new Synchronized();

        final MonitorGuard monitorGuard = new MonitorGuard();

        final AtomicInteger counter = new AtomicInteger(0);

        for (int i = 0; i <= 3; i++) {
            new Thread(() -> {
                for (; ; ) {
                    int data = counter.getAndIncrement();
                    System.out.println(Thread.currentThread() + "offer" + data);
                    monitorGuard.offer(data);
                    try {
                        TimeUnit.SECONDS.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            }).start();
        }
        for (int i = 0; i <= 2; i++) {
            new Thread(() -> {
                for (; ; ) {
                    int data = monitorGuard.take();
                    System.out.println(Thread.currentThread() + "take" + data);
                    try {
                        TimeUnit.SECONDS.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            }).start();
        }
//
//        for (int i = 0; i <= 3; i++) {
//            new Thread(() -> {
//                for (; ; ) {
//                    int data = counter.getAndIncrement();
//                    System.out.println(Thread.currentThread() + "offer" + data);
//                    sync.offer(data);
//                    try {
//                        TimeUnit.SECONDS.sleep(1);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                }
//
//            }).start();
//        }
//        for (int i = 0; i <= 2; i++) {
//            new Thread(() -> {
//                for (; ; ) {
//                    int data = sync.take();
//                    System.out.println(Thread.currentThread() + "take" + data);
//                    try {
//                        TimeUnit.SECONDS.sleep(1);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                }
//
//            }).start();
//        }
    }

    static class MonitorGuard {
        private final LinkedList<Integer> queue = new LinkedList<>();
        private final int max = 10;
        private final Monitor monitor = new Monitor();
        private final Monitor.Guard CAN_OFFER = monitor.newGuard(() -> queue.size() < max);
        private final Monitor.Guard CAN_TAKE = monitor.newGuard(() -> !queue.isEmpty());

        public void offer(int value) {
            try {
                monitor.enterWhen(CAN_OFFER);
                queue.addLast(value);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                monitor.leave();
            }
        }

        public int take() {
            try {
                monitor.enterWhen(CAN_TAKE);
                return queue.removeFirst();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                monitor.leave();
            }
            return -1;
        }


    }

    static class Synchronized {
        private final LinkedList<Integer> queue = new LinkedList<>();
        private final int max = 10;

        public void offer(int value) {
            synchronized (queue) {
                while (queue.size() >= max) {
                    try {
                        queue.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                queue.add(value);
                queue.notifyAll();
            }
        }

        public int take() {
            synchronized (queue) {
                while (queue.isEmpty()) {
                    try {
                        queue.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                Integer result = queue.removeFirst();
                queue.notifyAll();
                return result;
            }
        }

    }

}
