package accounts;

public class ClosedState implements AccountState {

    @Override
    public void deposit(Account acc, double amount) {
        System.out.println("Cannot deposit: Account is closed.");
    }

    @Override
    public void withdraw(Account acc, double amount) {
        System.out.println("Cannot withdraw: Account is closed.");
    }

    @Override
    public void close(Account acc) {
        System.out.println("Account already closed.");
    }
}
