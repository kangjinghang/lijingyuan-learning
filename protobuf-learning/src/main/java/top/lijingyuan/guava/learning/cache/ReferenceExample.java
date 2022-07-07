package top.lijingyuan.guava.learning.cache;

import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * ReferenceExample
 *
 * @author <a href="kangjinghang@gmail.com">kangjinghang</a>
 * @date 2021-05-27
 * @since 1.0.0
 */
public class ReferenceExample {

    public static void main(String[] args) {
//        strongReference();
//        softReference();
        weakReference();
    }

    private static void weakReference() {
        int counter = 1;
        List<WeakReference<Ref>> container = new ArrayList<>();
        for (; ; ) {
            int current = counter++;
            container.add(new WeakReference<>(new Ref(current)));
            System.out.println("The " + current + " Ref will be insert into container");
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private static void softReference() {
        int counter = 1;
        List<SoftReference<Ref>> container = new ArrayList<>();
        for (; ; ) {
            int current = counter++;
            container.add(new SoftReference<>(new Ref(current)));
            System.out.println("The " + current + " Ref will be insert into container");
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private static void strongReference() {
        int counter = 1;
        List<Ref> container = new ArrayList<>();
        for (; ; ) {
            int current = counter++;
            container.add(new Ref(current));
            System.out.println("The " + current + " Ref will be insert into container");
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private static class Ref {
        private byte[] data = new byte[1024 * 1024];

        private final int index;

        public Ref(int index) {
            this.index = index;
        }

        @Override
        protected void finalize() throws Throwable {
            System.out.println("The index [" + index + "] will be gc.");
        }
    }

}
