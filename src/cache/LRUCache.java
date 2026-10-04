package cache;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class LRUCache<K, V> {

    private final int capacity;
    private final long ttlMillis; // ⭐️ جديد
    private final Map<K, CacheEntry<V>> map;

    private static class CacheEntry<V> {
        private final V value;
        private final long timestamp;

        CacheEntry(V value) {
            this.value = value;
            this.timestamp = System.currentTimeMillis();
        }

        boolean isExpired(long ttlMillis) {
            return (System.currentTimeMillis() - timestamp) > ttlMillis;
        }
    }

    public LRUCache(int capacity, long ttl, TimeUnit unit) {
        this.capacity = capacity;
        this.ttlMillis = unit.toMillis(ttl);
        this.map = new LinkedHashMap<>(capacity, 0.75f, true) {
            @Override
            protected boolean removeEldestEntry(Map.Entry<K, CacheEntry<V>> eldest) {
                // ⭐️ تنظيف السجلات المنتهية أولاً
                cleanupExpiredEntries();
                return size() > LRUCache.this.capacity;
            }
        };
    }

    public synchronized void put(K key, V value) {
        map.put(key, new CacheEntry<>(value));
    }

    public synchronized V get(K key) {
        CacheEntry<V> entry = map.get(key);
        if (entry == null) {
            return null;
        }

        // ⭐️ التحقق من انتهاء الصلاحية
        if (entry.isExpired(ttlMillis)) {
            map.remove(key);
            return null;
        }

        return entry.value;
    }

    public synchronized boolean contains(K key) {
        return get(key) != null; // ⭐️ تستدعي get التي تتحقق من الصلاحية
    }

    private synchronized void cleanupExpiredEntries() {
        map.entrySet().removeIf(entry -> entry.getValue().isExpired(ttlMillis));
    }
    public synchronized void remove(K key) {
        map.remove(key);
    }

    public synchronized void clear() {
        map.clear();
    }


}

