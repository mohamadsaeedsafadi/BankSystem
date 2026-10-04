package test.accounts;
import accounts.*;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AccountGroupTest {

    @Test
    void accountGroupShouldSumBalancesCorrectly() {
        Account a1 = new CheckingAccount("C1", "A", 1000);
        Account a2 = new SavingsAccount("S1", "B", 2000);

        AccountGroup group = new AccountGroup("G1", "Family");
        group.addAccount(a1);
        group.addAccount(a2);

        assertEquals(3000, group.getBalance());
    }

    @Test
    void settingStateOnGroupShouldApplyToAllAccounts() {
        Account a1 = new CheckingAccount("C2", "A", 500);
        Account a2 = new SavingsAccount("S2", "B", 1500);

        AccountGroup group = new AccountGroup("G2", "Team");
        group.addAccount(a1);
        group.addAccount(a2);

        group.setState(new FrozenState());

        assertTrue(a1.getState() instanceof FrozenState);
        assertTrue(a2.getState() instanceof FrozenState);
    }
}
