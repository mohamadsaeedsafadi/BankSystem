package accounts;

import cache.AccountCache;

import java.util.List;

public class AccountManager {

    private List<Account> accountList;

    public AccountManager(List<Account> accountList) {
        this.accountList = accountList;
    }

    public Account findAccountById(String id) {

        // 1) إن وجد في الكاش — رجّعه
        if (AccountCache.exists(id)) {
            return AccountCache.get(id);
        }

        // 2) بحث طبيعي من الذاكرة
        for (Account acc : accountList) {
            if (acc.getAccountId().equals(id)) {

                // خزّنه بالكاش
                AccountCache.cacheAccount(acc);

                return acc;
            }
        }

        return null;
    }
}
