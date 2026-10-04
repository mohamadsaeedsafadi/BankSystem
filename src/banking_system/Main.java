package banking_system;

import Service.*;
import accounts.*;
import notifications.*;
import repositories.*;
import transactions.TransactionType;
import factories.*;
import Dashboard.*;
import security.*;

import java.time.LocalDateTime;

public class

Main {
    public static void main(String[] args) {

        // notifications
        NotificationCenter nc = new NotificationCenter();
        nc.addObserver(new EmailNotifier("user@mail.com"));
        nc.addObserver(new SMSNotifier("+1111111"));
        nc.addObserver(new AppNotifier("MobileUser"));

        // repositories
        AccountRepository ar = new InMemoryAccountRepository();
        TransactionRepository tr = new InMemoryTransactionRepository();
        repositories.TicketRepository ticketRepo = new InMemoryTicketRepository();

        // services
        AccountService as = new AccountService(ar);
        AccountGroupService groupService = new AccountGroupService();

        TransactionService ts = new TransactionService(as, tr);

        TicketService ticketService = new TicketService(ticketRepo, nc);
        RecommendationService recService = new RecommendationService();
        ReportService reportService = new ReportService(tr);

        // facade
        BankFacade bank = new BankFacade(as, ts, nc);

        // seed some accounts
        Account a1 = AccountFactory.createAccount("savings","A100","Ahmed",5000);
        Account a2 = AccountFactory.createAccount("checking","B200","Fatima",3000);
        Account a3 = AccountFactory.createAccount("checking","1","salam",3000);

        bank.createAccount(a1);
        bank.createAccount(a2);
        bank.createAccount(a3);


        // ⭐⭐⭐ اختبار الكاش — المكان الصحيح ⭐⭐⭐
        System.out.println("=== TEST ACCOUNT CACHE ===");
        as.getAccount("A100");   // أول مرة → من REPOSITORY
        as.getAccount("A100");   // ثاني مرة → من CACHE
        System.out.println("=================================");
        // ⭐⭐⭐ انتهى الاختبار ⭐⭐⭐


        // schedule example
        LocalDateTime runAt = LocalDateTime.now().plusSeconds(3);
        bank.scheduleTransaction("A100","B200",100, TransactionType.TRANSFER, runAt, 0);
        try { Thread.sleep(3500); } catch (Exception ignored) {}
        bank.runScheduledTasks();

        // auth users
        AuthService auth = AuthService.getInstance();
        auth.registerUser("admin","admin123", Role.ADMIN);
        auth.registerUser("manager","mgr123", Role.MANAGER);
        auth.registerUser("teller","tell123", Role.TELLER);
        auth.registerUser("cust","cust123", Role.CUSTOMER);

        // dashboard
        Dashboard dash = new Dashboard(bank, ticketService, recService, reportService, auth, groupService);
        dash.start();
    }
}
