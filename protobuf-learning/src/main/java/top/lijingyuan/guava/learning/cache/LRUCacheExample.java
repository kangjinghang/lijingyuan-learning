package top.lijingyuan.guava.learning.cache;


import java.util.concurrent.TimeUnit;

/**
 * LRUCacheExample
 *
 * @author <a href="kangjinghang@xueqiu.com">kangjinghang</a>
 * @date 2021-05-27
 * @since 1.0.0
 */
public class LRUCacheExample {

    public static void main(String[] args) {
        final SoftLRUCache<String, byte[]> cache = new SoftLRUCache<>(100);
        for (int i = 0; i < 100; i++) {
            cache.put(String.valueOf(i), new byte[1024 * 1024]);
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("The " + i + " entry is cached.");
        }
    }

}