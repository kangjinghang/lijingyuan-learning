package top.lijingyuan.guava.learning.cache;

/**
 * LRUCache
 *
 * @author <a href="kangjinghang@gmail.com">kangjinghang</a>
 * @date 2021-05-27
 * @since 1.0.0
 */
public interface LRUCache<K, V> {

    void put(K key, V value);

    V get(K key);

    void remove(K key);

    int size();

    void clear();

    int limit();

}
