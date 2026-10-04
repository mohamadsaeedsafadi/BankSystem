package accounts;

import interest.InterestStrategy;

public abstract class AccountDecorator implements Account {

    protected Account decoratedAccount;

    protected AccountDecorator(Account account) {
        this.decoratedAccount = account;
    }

    @Override
    public void deposit(double amount) {
        decoratedAccount.deposit(amount);
    }

    @Override
    public void withdraw(double amount) {
        decoratedAccount.withdraw(amount);
    }

    @Override
    public double getBalance() {
        return decoratedAccount.getBalance();
    }

    @Override
    public void displayAccountInfo() {
        decoratedAccount.displayAccountInfo();
    }

    @Override
    public AccountState getState() {
        return decoratedAccount.getState();
    }

    @Override
    public void setState(AccountState state) {
        decoratedAccount.setState(state);
    }

    @Override
    public String getAccountId() {
        return decoratedAccount.getAccountId();
    }

    @Override
    public String getAccountHolderName() {
        return decoratedAccount.getAccountHolderName();
    }

    @Override
    public void increaseBalance(double amount) {
        decoratedAccount.increaseBalance(amount);
    }

    @Override
    public void decreaseBalance(double amount) {
        decoratedAccount.decreaseBalance(amount);
    }

    @Override
    public void setInterestStrategy(InterestStrategy strategy) {
        decoratedAccount.setInterestStrategy(strategy);
    }

    @Override
    public InterestStrategy getInterestStrategy() {
        return decoratedAccount.getInterestStrategy();
    }

    @Override
    public double calculateInterest(int days) {
        return decoratedAccount.calculateInterest(days);
    }
}
