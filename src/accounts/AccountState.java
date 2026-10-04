package accounts;

public interface AccountState {

    void deposit(Account account, double amount);

    void withdraw(Account account, double amount);

    void close(Account account);
}
