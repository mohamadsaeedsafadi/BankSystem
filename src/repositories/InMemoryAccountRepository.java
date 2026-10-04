package repositories;

import accounts.Account;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@Getter
@Setter
@NoArgsConstructor
public class InMemoryAccountRepository implements AccountRepository {

    private Map<String, Account> accounts = new HashMap<>();

    @Override
    public void addAccount(Account account) {
        accounts.put(account.getAccountId(), account);
    }

    @Override
    public Account getAccountById(String accountId) {
        return accounts.get(accountId);
    }

    @Override
    public void updateAccount(Account account) {
        accounts.put(account.getAccountId(), account); // overwrite
    }

    @Override
    public void deleteAccount(String accountId) {
        accounts.remove(accountId);
    }

    @Override
    public List<Account> getAllAccounts() {
        return new ArrayList<>(accounts.values());
    }
}
