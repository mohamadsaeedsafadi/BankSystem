package test.Dashboard;

import Dashboard.Dashboard;
import Service.*;
import accounts.Account;
import banking_system.BankFacade;
import org.junit.jupiter.api.*;
import org.mockito.*;

import java.io.ByteArrayInputStream;

import static org.mockito.Mockito.*;

class DashboardFastTest {

    @Mock private BankFacade bank;
    @Mock private TicketService ticketService;
    @Mock private RecommendationService recService;
    @Mock private ReportService reportService;
    @Mock private AccountGroupService groupService;

    private AuthService auth;
    private Dashboard dashboard;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        auth = AuthService.getInstance();
        auth.registerUser("adminTest", "123", security.Role.ADMIN);
        auth.registerUser("custTest", "111", security.Role.CUSTOMER);

        dashboard = new Dashboard(bank, ticketService, recService, reportService, auth, groupService);
    }

    @Test
    void testDepositUI() {
        // إعداد الإدخال للدالة depositUI
        String input = String.join("\n",
                "A100",  // Account ID
                "500"    // Amount
        );
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        dashboard.depositUI();
        verify(bank).deposit("A100", 500);
    }

    @Test
    void testWithdrawUI() {
        String input = String.join("\n",
                "A100",  // Account ID
                "200"    // Amount
        );
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        // يجب تسجيل الدخول كمستخدم لديه صلاحية TELLER/MANAGER/ADMIN
        var user = auth.login("adminTest", "123");
        dashboard.setLoggedUser(user);

        dashboard.withdrawUI();
        verify(bank).withdraw("A100", 200);
    }

    @Test
    void testCreateAccountUI() {
        String input = String.join("\n",
                "savings",  // Type
                "A999",     // ID
                "TestUser", // Owner
                "1000"      // Initial balance
        );
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        var user = auth.login("adminTest", "123");
        dashboard.setLoggedUser(user);

        dashboard.createAccountUI();
        verify(bank).createAccount(any(Account.class));
    }

    @Test
    void testRecommendationsUI() {
        String input = "A100\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        var user = auth.login("adminTest", "123");
        dashboard.setLoggedUser(user);

        dashboard.recommendationsMenu();
        verify(recService).printRecommendations(any(Account.class));
    }

    @Test
    void testReportsUI() {
        var user = auth.login("adminTest", "123");
        dashboard.setLoggedUser(user);

        dashboard.reportsMenu();
        verify(reportService).runDailyTransactionsReport();
    }

    @Test
    void testCreateGroupUI() {
        String input = String.join("\n",
                "G1",       // Group ID
                "MyGroup"   // Group Name
        );
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        var user = auth.login("adminTest", "123");
        dashboard.setLoggedUser(user);

        dashboard.createGroupUI();
        verify(groupService).createGroup("G1", "MyGroup");
    }

    @Test
    void testAddAccountToGroupUI() {
        String input = String.join("\n",
                "G1",   // Group ID
                "A100"  // Account ID
        );
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        var user = auth.login("adminTest", "123");
        dashboard.setLoggedUser(user);

        // محاكاة جلب الحساب من البنك
        when(bank.getAccount("A100")).thenReturn(mock(Account.class));

        dashboard.addAccountToGroupUI();
        verify(groupService).addAccountToGroup(eq("G1"), any(Account.class));
    }

    @Test
    void testViewGroupUI() {
        String input = "G1\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        var user = auth.login("adminTest", "123");
        dashboard.setLoggedUser(user);

        dashboard.viewGroupUI();
        verify(groupService).showGroup("G1");
    }
}
