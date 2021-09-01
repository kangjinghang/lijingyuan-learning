package top.lijingyuan.guava.learning.cache;

import com.google.common.base.Optional;
import com.google.common.cache.*;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * CacheLorderTest
 *
 * @author <a href="kangjinghang@xueqiu.com">kangjinghang</a>
 * @date 2021-05-27
 * @since 1.0.0
 */
public class CacheLorderTest {

    public static void main(String[] args) throws ExecutionException {
        testBasic();
//        testEvictionBySize();
//        testEvictionByWeight();
//        testEvictionByAccessTime();
//        testCacheRemoveNotification();
//        testCacheStat();
//        testCacheSpec();
//        testEvictionByWriteTime();
    }

    private static void testLoadNullValue() {
        LoadingCache<String, Employee> cache = CacheBuilder.newBuilder()
                .build(createCacheLoader());
        Employee alex = cache.getUnchecked("alex");
    }

    private static void testCacheSpec() {
        String spec = "maximumSize=3,recordStats";
        CacheBuilderSpec cacheBuilderSpec = CacheBuilderSpec.parse(spec);
        LoadingCache<String, Employee> cache = CacheBuilder.from(cacheBuilderSpec)
                .build(createCacheLoader());
        Employee alex = cache.getUnchecked("alex");
    }

    private static void testCacheStat() throws ExecutionException {
        CacheLoader<String, String> cacheLoader = CacheLoader.from(String::toUpperCase);
        LoadingCache<String, String> cache = CacheBuilder.newBuilder()
                .recordStats()
                .build(cacheLoader);
        System.out.println(cache.getUnchecked("alex"));
        CacheStats stats = cache.stats();
        System.out.println(stats.hitCount());
        System.out.println(stats.missCount());
        System.out.println("-------");

        stats = cache.stats();

        System.out.println(cache.getUnchecked("alex"));
        System.out.println(stats.hitCount());
        System.out.println(stats.missCount());

    }

    private static void testCacheRemoveNotification() throws ExecutionException {
        CacheLoader<String, String> cacheLoader = CacheLoader.from(String::toUpperCase);
        RemovalListener<String, String> listener = notification -> {
            if (notification.wasEvicted()) {
                RemovalCause removalCause = notification.getCause();
                System.out.println("Removal listener invoked" + notification.getKey() + "  " + removalCause);
            }
        };
        LoadingCache<String, String> cache = CacheBuilder.newBuilder()
                .maximumSize(3)
                .removalListener(listener)
                .build(cacheLoader);
        System.out.println(cache.get("alex"));
        System.out.println(cache.get("a"));
        System.out.println(cache.get("b"));
        System.out.println(cache.get("c"));
    }

    private static void testLoadNullValueUseOptional() {
        CacheLoader<String, Optional<Employee>> cacheLoader = new CacheLoader<String, Optional<Employee>>() {

            @Override
            public Optional<Employee> load(String key) throws Exception {
                if (key.equals("null")) {
                    return Optional.fromNullable(null);
                } else {
                    return Optional.fromNullable(new Employee(key, key, key));
                }
            }
        };
        LoadingCache<String, Optional<Employee>> cache = CacheBuilder.newBuilder()
                .build(cacheLoader);
        Optional<Employee> alex = cache.getUnchecked("alex");
        Optional<Employee> optional = cache.getUnchecked("null");
    }

    private static void testBasic() throws ExecutionException {
        LoadingCache<String, Employee> cache = CacheBuilder.newBuilder()
                .maximumSize(10)
                .expireAfterWrite(10, TimeUnit.SECONDS)
                .refreshAfterWrite(2,TimeUnit.SECONDS)
                .build(new CacheLoader<String, Employee>() {
                    @Override
                    public Employee load(String key) throws Exception {
                        System.out.println("load...");
                        return new Employee(key, key, key);
                    }

                    @Override
                    public ListenableFuture<Employee> reload(String key, Employee oldValue) throws Exception {
                        System.out.println("reload...");
                        return Futures.immediateFuture(new Employee("jj","Kk","ll"));
                    }
                });
        Employee employee = cache.get("kang");
        System.out.println(employee);
        try {
            TimeUnit.SECONDS.sleep(13);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(cache.get("kang"));
    }

    private static CacheLoader<String, Employee> createCacheLoader() {
//        return new CacheLoader<String, Employee>() {
//            @Override
//            public Employee load(String key) throws Exception {
//                return findEmployeeByName(key);
//            }
//        };
//        return CacheLoader.from(key -> new Employee(key, key, key));
        System.out.println("createCacheLoader");
        return CacheLoader.from(key -> key.equals("null") ? null : new Employee(key, key, key));
    }

    private static void testEvictionBySize() throws ExecutionException {
        LoadingCache<String, Employee> cache = CacheBuilder.newBuilder()
                .maximumSize(3)
                .build(createCacheLoader());
        cache.getUnchecked("a");
        cache.getUnchecked("b");
        cache.getUnchecked("c");
        System.out.println(cache.size());
        cache.getUnchecked("d");
        System.out.println(cache.size());
        System.out.println(cache.get("b"));
        System.out.println(cache.get("a"));
    }

    // write / read / update
    private static void testEvictionByAccessTime() throws ExecutionException {
        LoadingCache<String, Employee> cache = CacheBuilder.newBuilder()
                .expireAfterAccess(2, TimeUnit.SECONDS)
                .build(createCacheLoader());
        cache.getUnchecked("a");
        System.out.println(cache.size() == 1);
        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(cache.getIfPresent("a"));
    }

    // write/update
    private static void testEvictionByWriteTime() throws ExecutionException {
        LoadingCache<String, Employee> cache = CacheBuilder.newBuilder()
                .expireAfterWrite(2, TimeUnit.SECONDS)
                .build(createCacheLoader());
        cache.getUnchecked("a");
        System.out.println(cache.size() == 1);
        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(cache.getIfPresent("a"));
    }

    private static void testWeightKey() throws ExecutionException {
        LoadingCache<String, Employee> cache = CacheBuilder.newBuilder()
                .expireAfterAccess(2, TimeUnit.SECONDS)
                .weakKeys() // 转为WeakReference
                .weakValues()
                .build(createCacheLoader());
        cache.getUnchecked("a");
        System.out.println(cache.size() == 1);
        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(cache.getIfPresent("a"));
    }

    private static void testSoftKey() throws ExecutionException {
        LoadingCache<String, Employee> cache = CacheBuilder.newBuilder()
                .expireAfterAccess(2, TimeUnit.SECONDS)
                .softValues() // 转为SoftReference
                .build(createCacheLoader());
        cache.getUnchecked("a");
        System.out.println(cache.size() == 1);
        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(cache.getIfPresent("a"));
    }


    private static void testEvictionByWeight() throws ExecutionException {
        Weigher<String, Employee> weigher = (key, employee) -> employee.getName().length() + employee.getEmpId().length() + employee.getEmpId().length();
        LoadingCache<String, Employee> cache = CacheBuilder.newBuilder()
                .maximumWeight(45)
                .concurrencyLevel(1)
                .weigher(weigher)
                .build(createCacheLoader());
        cache.getUnchecked("Gavin");
        cache.getUnchecked("Kevin");
        cache.getUnchecked("Allen");
        System.out.println(cache.size());
        cache.getUnchecked("Jason");
        System.out.println(cache.size());
        System.out.println(cache.get("Kevin"));
        System.out.println(cache.get("Gavin"));
    }

    private static Employee findEmployeeByName(String name) {
        System.out.println("The employee " + name + " is loading from db.");
        return new Employee(name, name, name);
    }

}
