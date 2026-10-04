package accounts;

import interest.InterestStrategy;
import interest.SimpleInterestStrategy;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CheckingAccount implements Account {
    private String accountId;
    private String accountHolderName;
    private double balance;
    private AccountState state;
    private InterestStrategy interestStrategy; // ⬅️ NEW

    // constructor مخصص لتوافق Factory أو إنشاء مباشر
    public CheckingAccount(String accountId, String accountHolderName, double initialBalance) {
        this.accountId = accountId;
        this.accountHolderName = accountHolderName;
        this.balance = initialBalance;
        this.state = new ActiveState();
        this.interestStrategy = new SimpleInterestStrategy(0.01); // 1% فائدة افتراضية
    }

    // باقي التوابع كما هي
    @Override
    public void deposit(double amount) {
        state.deposit(this, amount);
    }

    @Override
    public void withdraw(double amount) {
        state.withdraw(this, amount);
    }

    @Override
    public double getBalance() { return balance; }

    @Override
    public void displayAccountInfo() {
        System.out.println("Checking Account [" + accountId + "] - Holder: " + accountHolderName +
                " - Balance: " + balance + " - State: " + state.getClass().getSimpleName() +
                " - Interest: " + (interestStrategy != null ? interestStrategy.getStrategyName() : "None"));
    }

    @Override
    public AccountState getState() { return state; }

    @Override
    public void setState(AccountState state) { this.state = state; }

    @Override
    public String getAccountId() { return accountId; }

    @Override
    public String getAccountHolderName() { return accountHolderName; }

    @Override
    public void increaseBalance(double amount) { balance += amount; }

    @Override
    public void decreaseBalance(double amount) { balance -= amount; }

    @Override
    public void setInterestStrategy(InterestStrategy strategy) {
        this.interestStrategy = strategy;
    }

    @Override
    public InterestStrategy getInterestStrategy() {
        return interestStrategy;
    }

    @Override
    public double calculateInterest(int days) {
        if (interestStrategy != null) {
            return interestStrategy.calculateInterest(balance, days);
        }
        return 0.0;
    }
}
