package test.accounts;

import accounts.*;



import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AccountManagerTest {

    @Test
    void shouldFindAccountFromList() {
        Account account = new CheckingAccount("A1", "Ahmad", 1000);

        AccountManager manager = new AccountManager(List.of(account));

        Account result = manager.findAccountById("A1");

        assertNotNull(result);
        assertEquals("A1", result.getAccountId());
    }

    @Test
    void shouldReturnNullIfAccountNotFound() {
        AccountManager manager = new AccountManager(List.of());

        Account result = manager.findAccountById("X");

        assertNull(result);
    }
}
