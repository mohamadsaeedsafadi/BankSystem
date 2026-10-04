package banking_system;

import Service.AccountService;
import Service.TransactionService;
import notifications.NotificationCenter;
import accounts.Account;
import interest.InterestStrategy;
import lombok.Getter;
import lombok.Setter;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor(force = true) // يسمح بإنشاء object فارغ حتى لو fields final
public class BankFacade {

    private AccountService accountService;
    private TransactionService transactionService;
    private NotificationCenter notificationCenter;

    // constructor مخصص للاستخدام التقليدي
    public BankFacade(AccountService accountService,
                      TransactionService transactionService,
                      NotificationCenter notificationCenter) {
        this.accountService = accountService;
        this.transactionService = transactionService;
        this.notificationCenter = notificationCenter;
    }

    // ----------------- التوابع -----------------
    public void createAccount(Account account) {
        accountService.createAccount(account);
        notificationCenter.notifyAllObservers("Account created: " + account.getAccountId());
    }

    public void closeAccount(String accountId) {
        accountService.closeAccount(accountId);
        notificationCenter.notifyAllObservers("Account closed: " + accountId);
    }

    public void deposit(String accountId, double amount) {
        transactionService.execute(transactions.TransactionType.DEPOSIT, amount, null, accountId);
        notificationCenter.notifyAllObservers("Deposit of " + amount + " to account " + accountId);
    }

    public void withdraw(String accountId, double amount) {
        transactionService.execute(transactions.TransactionType.WITHDRAW, amount, accountId, null);
        notificationCenter.notifyAllObservers("Withdrawal of " + amount + " from account " + accountId);
    }

    public void transfer(String fromId, String toId, double amount) {
        transactionService.execute(transactions.TransactionType.TRANSFER, amount, fromId, toId);
        notificationCenter.notifyAllObservers("Transfer of " + amount + " from " + fromId + " to " + toId);
    }

    public void setAccountInterestStrategy(String accountId, InterestStrategy strategy) {
        Account account = accountService.getAccount(accountId);
        if (account != null) {
            account.setInterestStrategy(strategy);
            accountService.updateAccount(account);
            notificationCenter.notifyAllObservers(
                    "Interest strategy set for account " + accountId + ": " + strategy.getStrategyName()
            );
        } else {
            System.out.println("❌ Account not found: " + accountId);
        }
    }

    public void calculateAndShowInterest(String accountId, int days) {
        Account account = accountService.getAccount(accountId);
        if (account != null) {
            double interest = account.calculateInterest(days);
            InterestStrategy strategy = account.getInterestStrategy();

            System.out.println("\n💰 === Interest Calculation ===");
            System.out.println("Account: " + accountId);
            System.out.println("Balance: " + account.getBalance());
            System.out.println("Days: " + days);

            if (strategy != null) {
                System.out.println("Strategy: " + strategy.getStrategyName());
                System.out.println("Description: " + strategy.getDescription());
            }

            System.out.println("Interest Amount: " + interest);
            System.out.println("Total after interest: " + (account.getBalance() + interest));

            notificationCenter.notifyAllObservers(
                    "Interest calculated for account " + accountId + ": " + interest
            );
        } else {
            System.out.println("❌ Account not found: " + accountId);
        }
    }

    public void scheduleTransaction(String fromId,
                                    String toId,
                                    double amount,
                                    transactions.TransactionType type,
                                    LocalDateTime time,
                                    long repeatDays) {
        transactionService.schedule(type, amount, fromId, toId, time, repeatDays);
        notificationCenter.notifyAllObservers(
                "Scheduled transaction: " + type + " | amount=" + amount +
                        " | at " + time + " | repeat every " + repeatDays + " days"
        );
    }

    public void runScheduledTasks() {
        transactionService.tickScheduler();
    }

    public Account getAccount(String id) {
        return accountService.findAccountById(id);
    }

    public void displayAccount(String accountId) {
        Account account = accountService.getAccount(accountId);
        if (account != null) {
            account.displayAccountInfo();
        } else {
            System.out.println("❌ Account not found: " + accountId);
        }
    }
}
