package accounts;

public class OverdraftAccount extends AccountDecorator {

    private final double overdraftLimit;

    public OverdraftAccount(Account account, double overdraftLimit) {
        super(account);
        this.overdraftLimit = overdraftLimit;
    }

    @Override
    public void withdraw(double amount) {
        double available = decoratedAccount.getBalance() + overdraftLimit;

        if (amount > available) {
            System.out.println("Overdraft limit exceeded.");
            return;
        }

        decoratedAccount.decreaseBalance(amount);
        System.out.println("Withdrawal with overdraft successful.");
    }

    @Override
    public void displayAccountInfo() {
        super.displayAccountInfo();
        System.out.println("Overdraft limit: " + overdraftLimit);
    }
}
