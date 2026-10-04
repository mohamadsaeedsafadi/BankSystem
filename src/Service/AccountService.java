package Service;

import accounts.*;
import repositories.AccountRepository;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AccountService {

    private final AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    // إنشاء حساب مع الكاش
    public void createAccount(Account account) {
        accountRepository.addAccount(account);
        // ⭐️ تخزين في الكاش بعد الإنشاء
    }

    // تحديث حساب مع تحديث الكاش
    public void updateAccount(Account account) {
        accountRepository.updateAccount(account);
        // ⭐️ تحديث الكاش
        cache.AccountCache.cacheAccount(account);
    }

    // جلب حساب مع الكاش
    public Account getAccount(String accountId) {
        // 1) تحقق من الكاش
        if (cache.AccountCache.exists(accountId)) {
            System.out.println("[CACHE] Account " + accountId + " fetched from CACHE");

            return cache.AccountCache.get(accountId);
        }

        // 2) جلب من قاعدة البيانات
        Account acc = accountRepository.getAccountById(accountId);

        // 3) تخزين في الكاش
        if (acc != null) {
            cache.AccountCache.cacheAccount(acc);
        }
        System.out.println("[REPO] Account " + accountId + " fetched from REPOSITORY");

        return acc;
    }
    public Account findAccountById(String id) {
        return getAccount(id);  // نفس الشي، يمر عبر الكاش
    }
    // إغلاق حساب مع إزالة من الكاش
    public void closeAccount(String accountId) {
        Account acc = accountRepository.getAccountById(accountId); // ⭐️ مباشرة من DB

        if (acc != null) {
            acc.getState().close(acc);
            accountRepository.updateAccount(acc);

            // ⭐️ إزالة من الكاش بعد الإغلاق
            // (سنحتاج لإضافة دالة remove في AccountCache)
        }
    }

    // ⭐️ دالة واحدة فقط (لا داعي للتكرار)
    // public Account findAccountById(String id) تم حذفها

    // ⭐️ جديد: حذف حساب كلياً
    public void deleteAccount(String accountId) {
        accountRepository.deleteAccount(accountId);
        // ⭐️ إزالة من الكاش
        // AccountCache.remove(accountId);
    }
}