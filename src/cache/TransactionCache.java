package cache;

import transactions.Transaction;
import java.util.concurrent.TimeUnit;

public class TransactionCache {

    private static final LRUCache<String, Transaction> CACHE =
            new LRUCache<>(100, 10, TimeUnit.MINUTES); // 10 دقائق صلاحية

    public static void cacheTransaction(Transaction t) {
        CACHE.put(t.getTransactionId(), t);
    }

    public static Transaction get(String id) {
        return CACHE.get(id);
    }

    public static boolean exists(String id) {
        return CACHE.contains(id);
    }
}
