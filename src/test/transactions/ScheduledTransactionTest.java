package test.transactions;

import transactions.*;
import Service.AccountService;
import accounts.Account;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.mockito.Mockito.*;

class ScheduledTransactionTest {

    private AccountService accountService;
    private Account account;

    @BeforeEach
    void setup() {
        accountService = mock(AccountService.class);
        account = mock(Account.class);
        when(accountService.getAccount("acc")).thenReturn(account);
    }

    @Test
    void testSingleExecution() {
        BasicTransaction tx = new BasicTransaction("txSched", TransactionType.DEPOSIT, 100.0, null, "acc", accountService);
        ScheduledTransaction st = new ScheduledTransaction(tx, LocalDateTime.now().minusMinutes(1), 0);
        st.execute();
        verify(account, times(1)).deposit(100.0);
    }

    @Test
    void testRecurringExecution() {
        BasicTransaction tx = new BasicTransaction("txRecurring", TransactionType.DEPOSIT, 50.0, null, "acc", accountService);
        ScheduledTransaction st = new ScheduledTransaction(tx, LocalDateTime.now().minusMinutes(1), 1);
        st.execute();
        st.scheduleNext();
        assert(st.getNextExecutionTime().isAfter(LocalDateTime.now()));
    }
}
