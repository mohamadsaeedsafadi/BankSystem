package test.transactions;

import transactions.*;
import Service.AccountService;
import accounts.Account;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.mockito.Mockito.*;

class TransactionSchedulerTest {

    private AccountService accountService;
    private Account account;
    private TransactionScheduler scheduler;

    @BeforeEach
    void setup() {
        accountService = mock(AccountService.class);
        account = mock(Account.class);
        when(accountService.getAccount("acc")).thenReturn(account);
        scheduler = new TransactionScheduler();
    }

    @Test
    void testRunSchedulerTick() {
        BasicTransaction tx = new BasicTransaction("tx1", TransactionType.DEPOSIT, 100.0, null, "acc", accountService);
        ScheduledTransaction st = new ScheduledTransaction(tx, LocalDateTime.now().minusSeconds(1), 0);
        scheduler.addScheduledTransaction(st);

        scheduler.runSchedulerTick();
        verify(account, times(1)).deposit(100.0);
        assert(scheduler.getScheduledList().isEmpty()); // تمت إزالة المعاملة لمرة واحدة
    }
}
