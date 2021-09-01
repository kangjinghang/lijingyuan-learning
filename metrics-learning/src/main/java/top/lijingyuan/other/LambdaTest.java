package top.lijingyuan.other;

import java.util.concurrent.TimeUnit;

/**
 * LambdaTest
 *
 * @author <a href="kangjinghang@xueqiu.com">kangjinghang</a>
 * @date 2021-07-14
 * @since 1.0.0
 */
public class LambdaTest {

    // top.lijingyuan.other.LambdaTest@5641b917
    // 作用域不同，就是当前类
    Runnable r1 = () -> System.out.println(this);

    Runnable r2 = new Runnable() {
        @Override
        public void run() {
            // top.lijingyuan.other.LambdaTest$1@e498d38
            // 作用域是一个新建的内部类
            System.out.println(this);
        }
    };

    public static void main(String[] args) {
        LambdaTest lambdaTest = new LambdaTest();

        Thread t1 = new Thread(lambdaTest.r1);
        t1.start();
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("-------");
        Thread t2 = new Thread(lambdaTest.r2);
        t2.start();
    }

}
