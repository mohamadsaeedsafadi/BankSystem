package accounts;

import interest.InterestStrategy;
import interest.NoInterestStrategy;
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
public class LoanAccount implements Account {
    private String accountId;
    private String accountHolderName;
    private double balance;
    private AccountState state;
    private InterestStrategy interestStrategy;

    public LoanAccount(String accountId, String accountHolderName, double loanAmount) {
        this.accountId = accountId;
        this.accountHolderName = accountHolderName;
        this.balance = loanAmount;
        this.state = new ActiveState();
        this.interestStrategy = new NoInterestStrategy();
    }

    @Override
    public void deposit(double amount) { state.deposit(this, amount); }

    @Override
    public void withdraw(double amount) {
        System.out.println("Loan accounts cannot withdraw money.");
    }

    @Override
    public double getBalance() { return balance; }

    @Override
    public void displayAccountInfo() {
        System.out.println("Loan Account [" + accountId + "] - Holder: " + accountHolderName +
                " - Outstanding Loan: " + balance + " - State: " +
                state.getClass().getSimpleName() + " - Interest: " +
                (interestStrategy != null ? interestStrategy.getStrategyName() : "None"));
    }

    @Override
    public String getAccountId() { return accountId; }

    @Override
    public String getAccountHolderName() { return accountHolderName; }

    @Override
    public void increaseBalance(double amount) { balance -= amount; }

    @Override
    public void decreaseBalance(double amount) { balance += amount; }

    @Override
    public void setInterestStrategy(InterestStrategy strategy) { this.interestStrategy = strategy; }

    @Override
    public InterestStrategy getInterestStrategy() { return interestStrategy; }

    @Override
    public double calculateInterest(int days) {
        return interestStrategy != null ? interestStrategy.calculateInterest(balance, days) : 0.0;
    }
}
