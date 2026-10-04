/*package test.transactions;
import transactions.*;

import Service.AccountService;
import accounts.Account;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.mockito.Mockito.*;

class BasicTransactionTest {

    private AccountService accountService;
    private Account sourceAccount;
    private Account targetAccount;

    @BeforeEach
    void setup() {
        accountService = mock(AccountService.class);
        sourceAccount = mock(Account.class);
        targetAccount = mock(Account.class);

        when(accountService.getAccount("source")).thenReturn(sourceAccount);
        when(accountService.getAccount("target")).thenReturn(targetAccount);
    }

    @Test
    void testDeposit() {
        BasicTransaction depositTx = new BasicTransaction(
                "tx1",
                TransactionType.DEPOSIT,
                100.0,
                null,
                "target",
                accountService
        );

        depositTx.process();

        verify(targetAccount, times(1)).deposit(100.0);
        verify(accountService, times(1)).updateAccount(targetAccount);
    }

    @Test
    void testWithdraw() {
        BasicTransaction withdrawTx = new BasicTransaction(
                "tx2",
                TransactionType.WITHDRAW,
                50.0,
                "source",
                null,
                accountService
        );

        withdrawTx.process();

        verify(sourceAccount, times(1)).withdraw(50.0);
        verify(accountService, times(1)).updateAccount(sourceAccount);
    }

    @Test
    void testTransfer() {
        BasicTransaction transferTx = new BasicTransaction(
                "tx3",
                TransactionType.TRANSFER,
                200.0,
                "source",
                "target",
                accountService
        );

        transferTx.process();

        verify(sourceAccount, times(1)).withdraw(200.0);
        verify(targetAccount, times(1)).deposit(200.0);
        verify(accountService, times(1)).updateAccount(sourceAccount);
        verify(accountService, times(1)).updateAccount(targetAccount);
    }

    @Test
    void testSourceAccountNotFound() {
        when(accountService.getAccount("missing")).thenReturn(null);

        BasicTransaction tx = new BasicTransaction(
                "tx4",
                TransactionType.WITHDRAW,
                100.0,
                "missing",
                null,
                accountService
        );

        tx.process();

        verify(accountService, never()).updateAccount(any());
    }

    @Test
    void testTargetAccountNotFound() {
        when(accountService.getAccount("missing")).thenReturn(null);

        BasicTransaction tx = new BasicTransaction(
                "tx5",
                TransactionType.DEPOSIT,
                100.0,
                null,
                "missing",
                accountService
        );

        tx.process();

        verify(accountService, never()).updateAccount(any());
    }
}
*/
package test.transactions;

import transactions.*;
import Service.AccountService;
import accounts.Account;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

class BasicTransactionTest {

    private AccountService accountService;
    private Account sourceAccount;
    private Account targetAccount;

    @BeforeEach
    void setup() {
        accountService = mock(AccountService.class);
        sourceAccount = mock(Account.class);
        targetAccount = mock(Account.class);

        when(accountService.getAccount("source")).thenReturn(sourceAccount);
        when(accountService.getAccount("target")).thenReturn(targetAccount);
    }

    @Test
    void testDeposit() {
        BasicTransaction depositTx = new BasicTransaction(
                "tx1", TransactionType.DEPOSIT, 100.0, null, "target", accountService
        );
        depositTx.process();
        verify(targetAccount, times(1)).deposit(100.0);
        verify(accountService, times(1)).updateAccount(targetAccount);
    }

    @Test
    void testWithdraw() {
        BasicTransaction withdrawTx = new BasicTransaction(
                "tx2", TransactionType.WITHDRAW, 50.0, "source", null, accountService
        );
        withdrawTx.process();
        verify(sourceAccount, times(1)).withdraw(50.0);
        verify(accountService, times(1)).updateAccount(sourceAccount);
    }

    @Test
    void testTransfer() {
        BasicTransaction transferTx = new BasicTransaction(
                "tx3", TransactionType.TRANSFER, 200.0, "source", "target", accountService
        );
        transferTx.process();
        verify(sourceAccount, times(1)).withdraw(200.0);
        verify(targetAccount, times(1)).deposit(200.0);
        verify(accountService, times(1)).updateAccount(sourceAccount);
        verify(accountService, times(1)).updateAccount(targetAccount);
    }

    @Test
    void testSourceAccountNotFound() {
        when(accountService.getAccount("missing")).thenReturn(null);
        BasicTransaction tx = new BasicTransaction(
                "tx4", TransactionType.WITHDRAW, 100.0, "missing", null, accountService
        );
        tx.process();
        verify(accountService, never()).updateAccount(any());
    }

    @Test
    void testTargetAccountNotFound() {
        when(accountService.getAccount("missing")).thenReturn(null);
        BasicTransaction tx = new BasicTransaction(
                "tx5", TransactionType.DEPOSIT, 100.0, null, "missing", accountService
        );
        tx.process();
        verify(accountService, never()).updateAccount(any());
    }
}
