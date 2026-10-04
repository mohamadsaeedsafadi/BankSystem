package accounts;

public class FrozenState implements AccountState {

    @Override
    public void deposit(Account acc, double amount) {
        System.out.println("Cannot deposit: Account is frozen.");
    }

    @Override
    public void withdraw(Account acc, double amount) {
        System.out.println("Cannot withdraw: Account is frozen.");
    }

    @Override
    public void close(Account acc) {
        acc.setState(new ClosedState());
        System.out.println("Account closed from frozen state.");
    }
}
