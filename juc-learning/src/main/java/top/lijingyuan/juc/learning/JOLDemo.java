package top.lijingyuan.juc.learning;

import org.openjdk.jol.info.ClassLayout;

public class JOLDemo {

    public static void main(String[] args) throws InterruptedException {
        Object o = new Object();
        System.out.println("未进入同步块，MarkWord 为：");
        System.out.println(ClassLayout.parseInstance(o).toPrintable());
        synchronized (o) {
            // 睡眠 5s
            Thread.sleep(5000);
            System.out.println(("进入同步块，MarkWord 为："));
            System.out.println(ClassLayout.parseInstance(o).toPrintable());
        }
    }

}