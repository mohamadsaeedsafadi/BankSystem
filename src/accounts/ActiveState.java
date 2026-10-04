package accounts;

public class ActiveState implements AccountState {

    @Override
    public void deposit(Account account, double amount) {
        if (amount <= 0) {
            System.out.println("Amount must be > 0.");
            return;
        }
        account.increaseBalance(amount);
        System.out.println("Deposit of " + amount + " successful.");
    }

    @Override
    public void withdraw(Account account, double amount) {
        if (amount <= 0) {
            System.out.println("Amount must be > 0.");
            return;
        }

        if (account.getBalance() < amount) {
            System.out.println("Insufficient balance.");
            return;
        }

        account.decreaseBalance(amount);
        System.out.println("Withdrawal of " + amount + " successful.");
    }

    @Override
    public void close(Account account) {
        account.setState(new ClosedState());
        System.out.println("Account is now closed.");
    }
}
