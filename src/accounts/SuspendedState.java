package accounts;

public class SuspendedState implements AccountState {

    @Override
    public void deposit(Account acc, double amount) {
        System.out.println("Cannot deposit: Account is suspended.");
    }

    @Override
    public void withdraw(Account acc, double amount) {
        System.out.println("Cannot withdraw: Account is suspended.");
    }

    @Override
    public void close(Account acc) {
        acc.setState(new ClosedState());
        System.out.println("Account closed from suspended state.");
    }
}
