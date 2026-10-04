package test.accounts;
import accounts.*;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AccountStateTest {

    @Test
    void frozenAccountShouldNotAllowDepositOrWithdraw() {
        CheckingAccount account = new CheckingAccount("C2", "Omar", 500);
        account.setState(new FrozenState());

        account.deposit(200);
        account.withdraw(100);

        assertEquals(500, account.getBalance());
    }

    @Test
    void closedAccountShouldRemainUnchanged() {
        CheckingAccount account = new CheckingAccount("C3", "Sara", 800);
        account.setState(new ClosedState());

        account.deposit(300);
        account.withdraw(100);

        assertEquals(800, account.getBalance());
    }

    @Test
    void activeToClosedTransitionShouldWork() {
        CheckingAccount account = new CheckingAccount("C4", "Lina", 1000);
        account.getState().close(account);

        assertTrue(account.getState() instanceof ClosedState);
    }
}
