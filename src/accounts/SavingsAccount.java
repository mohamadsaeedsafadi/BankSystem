package accounts;

import interest.InterestStrategy;
import interest.CompoundInterestStrategy;
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
public class SavingsAccount implements Account {
    private String id;
    private String holder;
    private double balance;
    private AccountState state;
    private InterestStrategy interestStrategy;

    // constructor مخصص لتوافق Factory أو إنشاء مباشر
    public SavingsAccount(String id, String holder, double initial) {
        this.id = id;
        this.holder = holder;
        this.balance = initial;
        this.state = new ActiveState();
        this.interestStrategy = new CompoundInterestStrategy(0.03, 4);
    }

    @Override
    public void deposit(double amount) { state.deposit(this, amount); }

    @Override
    public void withdraw(double amount) { state.withdraw(this, amount); }

    @Override
    public double getBalance() { return balance; }

    @Override
    public void displayAccountInfo() {
        System.out.println("Savings [" + id + "] - Holder: " + holder +
                " - Balance: " + balance + " - State: " +
                state.getClass().getSimpleName() + " - Interest: " +
                (interestStrategy != null ? interestStrategy.getStrategyName() : "None"));
    }

    @Override
    public String getAccountId() { return id; }

    @Override
    public String getAccountHolderName() { return holder; }

    @Override
    public void increaseBalance(double amount) { balance += amount; }

    @Override
    public void decreaseBalance(double amount) { balance -= amount; }

    @Override
    public void setInterestStrategy(InterestStrategy strategy) { this.interestStrategy = strategy; }

    @Override
    public InterestStrategy getInterestStrategy() { return interestStrategy; }

    @Override
    public double calculateInterest(int days) {
        return interestStrategy != null ? interestStrategy.calculateInterest(balance, days) : 0.0;
    }
}
