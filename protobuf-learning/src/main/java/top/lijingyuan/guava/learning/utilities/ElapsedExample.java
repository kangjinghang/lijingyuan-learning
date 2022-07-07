package top.lijingyuan.guava.learning.utilities;

import java.util.concurrent.TimeUnit;

/**
 * ElapsedExample
 *
 * @author <a href="kangjinghang@gmail.com">kangjinghang</a>
 * @date 2021-05-23
 * @since 1.0.0
 */
public class ElapsedExample {

    public static void main(String[] args) throws InterruptedException {
        process("4567722");
    }

    private static void process(String orderNo) throws InterruptedException {
        System.out.printf("start process the order %s\n", orderNo);
        long startTime = System.nanoTime();
        TimeUnit.SECONDS.sleep(1);
        System.out.printf("The orderNo %s process successful and elapsed %s\n", orderNo, (System.nanoTime() - startTime));
    }

}
