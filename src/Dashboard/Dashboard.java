package Dashboard;

import banking_system.BankFacade;
import Service.*;
import security.*;
import accounts.Account;
import factories.AccountFactory;

import java.util.Scanner;

import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;
import lombok.Builder;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class Dashboard {

    private final BankFacade bank;
    private final Scanner scanner = new Scanner(System.in);

    private TicketService ticketService;
    private RecommendationService recService;
    private ReportService reportService;
    private AuthService auth;
    private AccountGroupService groupService;

    private User loggedUser = null;

    // constructor التقليدي
    public Dashboard(BankFacade bank,
                     TicketService ticketService,
                     RecommendationService recService,
                     ReportService reportService,
                     AuthService auth,
                     AccountGroupService groupService) {
        this.bank = bank;
        this.ticketService = ticketService;
        this.recService = recService;
        this.reportService = reportService;
        this.auth = auth;
        this.groupService = groupService;
    }

    // ---------------- LOGIN ----------------
    private boolean loginPrompt() {
        System.out.println("\n--- Login ---");
        System.out.print("Username: ");
        String u = scanner.nextLine();
        System.out.print("Password: ");
        String p = scanner.nextLine();

        User user = auth.login(u, p);
        if (user == null) {
            System.out.println("❌ Login failed.");
            return false;
        }

        loggedUser = user;
        System.out.println("Welcome " + user.getUsername() + " Roles: " + user.getRoles());
        return true;
    }

    // ---------------- MAIN LOOP ----------------
    public void start() {
        // فرض تسجيل الدخول دائمًا
        while (true) {
            loggedUser = null; // إعادة التهيئة
            while (!loginPrompt()) {
                System.out.println("❌ Please try again.");
            }

            // الآن تم تسجيل الدخول بنجاح
            while (loggedUser != null) {
                System.out.println("\n===== DASHBOARD (" + loggedUser.getUsername() + ") =====");
                System.out.println("1. Create Account");
                System.out.println("2. Close Account");
                System.out.println("3. Display Account");
                System.out.println("4. Deposit");
                System.out.println("5. Withdraw");
                System.out.println("6. Transfer");
                System.out.println("7. Account Groups");
                System.out.println("8. Recommendations");
                System.out.println("9. Reports (Admin/Manager)");
                System.out.println("0. Logout");
                System.out.print("Choose: ");

                int choice = safeInt();

                switch (choice) {
                    case 1 -> createAccountUI();
                    case 2 -> closeAccountUI();
                    case 3 -> displayAccountUI();
                    case 4 -> depositUI();
                    case 5 -> withdrawUI();
                    case 6 -> transferUI();
                    case 7 -> groupMenu();
                    case 8 -> recommendationsMenu();
                    case 9 -> reportsMenu();
                    case 0 -> { loggedUser = null; System.out.println("Logged out."); }
                    default -> System.out.println("Invalid.");
                }
            }
        }
    }

    // ---------------- ACCOUNT OPS ----------------
    public void createAccountUI() {
        if (!loggedUser.getRoles().contains(Role.ADMIN) &&
                !loggedUser.getRoles().contains(Role.MANAGER)) {
            System.out.println("❌ Only ADMIN/MANAGER can create accounts.");
            return;
        }

        System.out.print("Account type (savings/checking): ");
        String type = scanner.nextLine();
        System.out.print("Account ID: ");
        String id = scanner.nextLine();
        System.out.print("Owner name: ");
        String owner = scanner.nextLine();
        System.out.print("Initial balance: ");
        double balance = Double.parseDouble(scanner.nextLine());

        Account acc = AccountFactory.createAccount(type, id, owner, balance);
        bank.createAccount(acc);
    }

    private void closeAccountUI() {
        if (!loggedUser.getRoles().contains(Role.ADMIN)) {
            System.out.println("❌ Only ADMIN can close accounts.");
            return;
        }

        System.out.print("Account ID: ");
        bank.closeAccount(scanner.nextLine());
    }

    private void displayAccountUI() {
        System.out.print("Account ID: ");
        bank.displayAccount(scanner.nextLine());
    }

    public void depositUI() {
        System.out.print("Account ID: ");
        String id = scanner.nextLine();
        System.out.print("Amount: ");
        double amt = Double.parseDouble(scanner.nextLine());
        bank.deposit(id, amt);
    }

    public void withdrawUI() {
        if (!loggedUser.getRoles().contains(Role.TELLER) &&
                !loggedUser.getRoles().contains(Role.MANAGER) &&
                !loggedUser.getRoles().contains(Role.ADMIN)) {
            System.out.println("❌ Unauthorized.");
            return;
        }

        System.out.print("Account ID: ");
        String id = scanner.nextLine();
        System.out.print("Amount: ");
        double amt = Double.parseDouble(scanner.nextLine());
        bank.withdraw(id, amt);
    }

    private void transferUI() {
        if (!loggedUser.getRoles().contains(Role.MANAGER) &&
                !loggedUser.getRoles().contains(Role.ADMIN)) {
            System.out.println("❌ Unauthorized.");
            return;
        }

        System.out.print("From Account: ");
        String from = scanner.nextLine();
        System.out.print("To Account: ");
        String to = scanner.nextLine();
        System.out.print("Amount: ");
        double amt = Double.parseDouble(scanner.nextLine());

        bank.transfer(from, to, amt);
    }

    // ---------------- ACCOUNT GROUPS ----------------
    private void groupMenu() {
        while (true) {
            System.out.println("\n===== ACCOUNT GROUPS =====");
            System.out.println("1. Create Group");
            System.out.println("2. Add Account to Group");
            System.out.println("3. View Group");
            System.out.println("0. Back");
            System.out.print("Choose: ");

            int choice = safeInt();

            switch (choice) {
                case 1 -> createGroupUI();
                case 2 -> addAccountToGroupUI();
                case 3 -> viewGroupUI();
                case 0 -> { return; }
                default -> System.out.println("Invalid.");
            }
        }
    }

    public void createGroupUI() {
        if (!loggedUser.getRoles().contains(Role.ADMIN) &&
                !loggedUser.getRoles().contains(Role.MANAGER)) {
            System.out.println("❌ Only ADMIN/MANAGER can create groups.");
            return;
        }

        System.out.print("Group ID: ");
        String id = scanner.nextLine();
        System.out.print("Group Name: ");
        String name = scanner.nextLine();

        groupService.createGroup(id, name);
    }

    public void addAccountToGroupUI() {
        System.out.print("Group ID: ");
        String gid = scanner.nextLine();
        System.out.print("Account ID: ");
        String aid = scanner.nextLine();

        Account acc = bank.getAccount(aid);
        if (acc == null) {
            System.out.println("❌ Account not found.");
            return;
        }

        groupService.addAccountToGroup(gid, acc);
    }

    public void viewGroupUI() {
        System.out.print("Group ID: ");
        groupService.showGroup(scanner.nextLine());
    }

    // ---------------- OTHER ----------------
    public void recommendationsMenu() {
        System.out.print("Account ID: ");
        String id = scanner.nextLine();
        Account acc = bank.getAccount(id);

        if (acc == null) {
            System.out.println("❌ Account not found.");
            return;
        }

        recService.printRecommendations(acc);
    }

    public void reportsMenu() {
        if (!loggedUser.getRoles().contains(Role.ADMIN) &&
                !loggedUser.getRoles().contains(Role.MANAGER)) {
            System.out.println("❌ Unauthorized.");
            return;
        }

        reportService.runDailyTransactionsReport();
    }

    // ---------------- UTIL ----------------
    private int safeInt() {
        while (true) {
            try { return Integer.parseInt(scanner.nextLine()); }
            catch (Exception e) { System.out.print("Enter number: "); }
        }
    }
}
