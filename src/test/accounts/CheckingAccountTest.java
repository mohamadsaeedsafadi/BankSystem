package test.accounts;
import accounts.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CheckingAccountTest {

    private CheckingAccount account;

    @BeforeEach
    void setUp() {
        account = new CheckingAccount("C1", "Ahmad", 1000);
    }

    @Test
    void depositShouldIncreaseBalance() {
        account.deposit(500);
        assertEquals(1500, account.getBalance());
    }

    @Test
    void withdrawShouldDecreaseBalance() {
        account.withdraw(300);
        assertEquals(700, account.getBalance());
    }

    @Test
    void withdrawMoreThanBalanceShouldFail() {
        account.withdraw(2000);
        assertEquals(1000, account.getBalance());
    }

    @Test
    void negativeDepositShouldNotChangeBalance() {
        account.deposit(-100);
        assertEquals(1000, account.getBalance());
    }
}
