package top.lijingyuan.guava.learning.cache;


import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;

import java.util.HashMap;
import java.util.LinkedList;

/**
 * LinkedListLRUCache
 *
 * @author <a href="kangjinghang@xueqiu.com">kangjinghang</a>
 * @date 2021-05-27
 * @since 1.0.0
 */
public class LinkedListLRUCache<K, V> implements LRUCache<K, V> {

    private final int limit;

    private final LinkedList<K> keys = new LinkedList<>();

    private final HashMap<K, V> cache = new HashMap<>();

    public LinkedListLRUCache(int limit) {
        Preconditions.checkArgument(limit > 0, "The limit big than zero.");
        this.limit = limit;
    }

    @Override
    public void put(K key, V value) {
        Preconditions.checkNotNull(key);
        Preconditions.checkNotNull(value);
        if (this.keys.size() >= limit) {
            K oldestKey = keys.removeFirst();
            cache.remove(oldestKey);
        }
        keys.addLast(key);
        cache.put(key, value);
    }

    @Override
    public V get(K key) {
        boolean exist = keys.remove(key);
        if (!exist) {
            return null;
        }
        keys.addLast(key);
        return cache.get(key);
    }

    @Override
    public void remove(K key) {
        boolean exist = keys.remove(key);
        if (exist) {
            cache.remove(key);
        }
    }

    @Override
    public int size() {
        return this.keys.size();
    }

    @Override
    public void clear() {
        this.keys.clear();
        this.cache.clear();
    }

    @Override
    public int limit() {
        return this.limit;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("keys", keys)
                .add("cache", cache)
                .toString();
    }
}
