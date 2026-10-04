package accounts;

import interest.InterestStrategy;

public interface Account {
    void deposit(double amount);
    void withdraw(double amount);
    double getBalance();
    void displayAccountInfo();
    AccountState getState();
    void setState(AccountState state);
    String getAccountId();
    String getAccountHolderName();
    void increaseBalance(double amount);
    void decreaseBalance(double amount);

    // ⬇️ NEW: Strategy Pattern methods
    void setInterestStrategy(InterestStrategy strategy);
    InterestStrategy getInterestStrategy();
    double calculateInterest(int days);
}