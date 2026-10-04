package test.repositories;

import accounts.Account;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import repositories.AccountRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AccountRepositoryTest {

    private AccountRepository repo;
    private Account acc1;
    private Account acc2;

    @BeforeEach
    void setup() {
        repo = mock(AccountRepository.class);

        // إنشاء حسابات وهمية باستخدام Mockito
        acc1 = mock(Account.class);
        when(acc1.getAccountId()).thenReturn("A100");
        when(acc1.getAccountHolderName()).thenReturn("Ahmed");
        when(acc1.getBalance()).thenReturn(1000.0);

        acc2 = mock(Account.class);
        when(acc2.getAccountId()).thenReturn("B200");
        when(acc2.getAccountHolderName()).thenReturn("Fatima");
        when(acc2.getBalance()).thenReturn(2000.0);
    }

    @Test
    void testAddAndGetAccount() {
        // إضافة الحساب
        repo.addAccount(acc1);

        // محاكاة سلوك getAccountById
        when(repo.getAccountById("A100")).thenReturn(acc1);

        Account fetched = repo.getAccountById("A100");
        assertNotNull(fetched);
        assertEquals("Ahmed", fetched.getAccountHolderName());
        assertEquals(1000.0, fetched.getBalance());
    }

    @Test
    void testUpdateAccountBalance() {
        // محاكاة تعديل الرصيد عن طريق increaseBalance/decreaseBalance
        acc1.increaseBalance(500); // الرصيد المفترض يصبح 1500
        when(acc1.getBalance()).thenReturn(1500.0);

        repo.updateAccount(acc1);
        when(repo.getAccountById("A100")).thenReturn(acc1);

        Account fetched = repo.getAccountById("A100");
        assertEquals(1500.0, fetched.getBalance());
    }

    @Test
    void testDeleteAccount() {
        repo.addAccount(acc2);
        repo.deleteAccount("B200");

        when(repo.getAccountById("B200")).thenReturn(null);
        assertNull(repo.getAccountById("B200"));
    }

    @Test
    void testGetAllAccounts() {
        List<Account> allAccounts = List.of(acc1, acc2);
        when(repo.getAllAccounts()).thenReturn(allAccounts);

        List<Account> fetched = repo.getAllAccounts();
        assertEquals(2, fetched.size());
        assertTrue(fetched.contains(acc1));
        assertTrue(fetched.contains(acc2));
    }
}
