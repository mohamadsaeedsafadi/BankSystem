package test.accounts;
import accounts.*;


import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OverdraftAccountTest {

    @Test
    void overdraftShouldAllowExtraWithdrawal() {

        Account baseAccount = new CheckingAccount("OD1", "Ali", 500);
        Account overdraftAccount = new OverdraftAccount(baseAccount, 300);

        overdraftAccount.withdraw(700);

        assertEquals(-200, overdraftAccount.getBalance());
    }

    @Test
    void overdraftShouldRejectExcessAmount() {

        Account baseAccount = new CheckingAccount("OD2", "Sara", 500);
        Account overdraftAccount = new OverdraftAccount(baseAccount, 200);

        overdraftAccount.withdraw(800);

        assertEquals(500, overdraftAccount.getBalance());
    }
}
