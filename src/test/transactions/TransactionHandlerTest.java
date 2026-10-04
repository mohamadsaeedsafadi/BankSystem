package test.transactions;

import transactions.*;
import Service.AccountService;
import accounts.Account;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

class TransactionHandlerTest {

    private AccountService accountService;
    private Account account;

    @BeforeEach
    void setup() {
        accountService = mock(AccountService.class);
        account = mock(Account.class);
        when(accountService.getAccount("acc")).thenReturn(account);
    }

    @Test
    void testAutoApprovalHandler() {
        AutoApprovalHandler handler = new AutoApprovalHandler();
        BasicTransaction tx = new BasicTransaction("txAuto", TransactionType.DEPOSIT, 400.0, null, "acc", accountService);
        handler.handle(tx);
        verify(account, times(1)).deposit(400.0);
    }

    @Test
    void testTellerApprovalHandler() {
        TellerApprovalHandler handler = new TellerApprovalHandler();
        BasicTransaction tx = new BasicTransaction("txTeller", TransactionType.DEPOSIT, 3000.0, null, "acc", accountService);
        handler.handle(tx);
        verify(account, times(1)).deposit(3000.0);
    }

    @Test
    void testManagerApprovalHandler() {
        ManagerApprovalHandler handler = new ManagerApprovalHandler();
        BasicTransaction tx = new BasicTransaction("txManager", TransactionType.DEPOSIT, 10000.0, null, "acc", accountService);
        handler.handle(tx);
        verify(account, times(1)).deposit(10000.0);
    }

    @Test
    void testChainOfResponsibility() {
        AutoApprovalHandler auto = new AutoApprovalHandler();
        TellerApprovalHandler teller = new TellerApprovalHandler();
        ManagerApprovalHandler manager = new ManagerApprovalHandler();

        auto.setNext(teller);
        teller.setNext(manager);

        BasicTransaction tx = new BasicTransaction("txChain", TransactionType.DEPOSIT, 700.0, null, "acc", accountService);
        auto.handle(tx);

        verify(account, times(1)).deposit(700.0); // يجب أن يمر عبر Teller
    }
}
