package top.lijingyuan.guava.learning.cache;

/**
 * LinkedHashLRUCacheExample
 *
 * @author <a href="kangjinghang@xueqiu.com">kangjinghang</a>
 * @date 2021-05-27
 * @since 1.0.0
 */
public class LinkedHashLRUCacheExample {

    public static void main(String[] args) {
        LRUCache<String, String> cache = new LinkedListLRUCache<>(3);
        cache.put("1", "1");
        cache.put("2", "2");
        cache.put("3", "3");
        System.out.println(cache);

        cache.put("4", "4");
        System.out.println(cache);

        cache.get("2");
        System.out.println(cache);

//        LRUCache<String, String> cache = new LinkedHashLRUCache<>(3);
//        cache.put("1", "1");
//        cache.put("2", "2");
//        cache.put("3", "3");
//        System.out.println(cache);
//
//        cache.put("4", "4");
//        System.out.println(cache);
//
//        cache.get("2");
//        System.out.println(cache);
    }

}
