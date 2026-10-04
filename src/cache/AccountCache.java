package cache;

import accounts.Account;
import java.util.concurrent.TimeUnit;

public class AccountCache {
    // ⭐️ 50 حساب مع صلاحية 5 دقائق
    private static final LRUCache<String, Account> CACHE =
            new LRUCache<>(50, 5, TimeUnit.MINUTES);

    public static void cacheAccount(Account account) {
        CACHE.put(account.getAccountId(), account);
    }

    public static boolean exists(String id) {
        return CACHE.contains(id);
    }public static Account get(String id) {
        return CACHE.get(id);
    }

    // ⭐️ جديد: إزالة حساب من الكاش
    public static void remove(String id) {
        // نحتاج إضافة دالة remove في LRUCache
    }

    // ⭐️ جديد: تنظيف الكاش كاملاً
    public static void clear() {
        // نحتاج إضافة دالة clear في LRUCache
    }
}