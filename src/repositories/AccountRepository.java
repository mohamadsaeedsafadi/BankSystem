package repositories;

import accounts.Account;
import java.util.List;

public interface AccountRepository {

    // إضافة حساب جديد
    void addAccount(Account account);

    // جلب حساب حسب ID
    Account getAccountById(String accountId);

    // تحديث حساب موجود
    void updateAccount(Account account);

    // حذف حساب
    void deleteAccount(String accountId);

    // جلب كل الحسابات
    List<Account> getAllAccounts();
}
