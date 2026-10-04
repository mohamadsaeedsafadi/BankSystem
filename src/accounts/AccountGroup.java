package accounts;

import interest.InterestStrategy;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AccountGroup implements Account {

    // لا نستخدم Lombok على هذه الحقول لتجنب التعارض مع واجهة Account
    private String groupId;
    private String groupName;

    // استخدام Lombok على الحقول الداخلية
    @Getter @Setter
    private List<Account> accounts = new ArrayList<>();

    @Getter @Setter
    private AccountState state = new ActiveState();

    // constructor مخصص لتوافق Factory أو إنشاء مباشر
    public AccountGroup(String groupId, String groupName) {
        this.groupId = groupId;
        this.groupName = groupName;
        this.accounts = new ArrayList<>();
        this.state = new ActiveState();
    }

    // تنفيذ واجهة Account
    @Override
    public String getAccountId() { return groupId; }

    @Override
    public String getAccountHolderName() { return groupName; }

    // باقي التوابع كما هي بدون أي تغيير
    public void addAccount(Account account) { accounts.add(account); }

    public void removeAccount(Account account) { accounts.remove(account); }

    @Override
    public void deposit(double amount) {
        System.out.println("Cannot deposit directly into an Account Group.");
    }

    @Override
    public void withdraw(double amount) {
        System.out.println("Cannot withdraw directly from an Account Group.");
    }

    @Override
    public double getBalance() {
        double total = 0;
        for (Account a : accounts) total += a.getBalance();
        return total;
    }

    @Override
    public void displayAccountInfo() {
        System.out.println("Account Group [" + groupId + "] - Name: " + groupName +
                " - Total Balance: " + getBalance() + " - State: " +
                state.getClass().getSimpleName());
        for (Account acc : accounts) acc.displayAccountInfo();
    }

    @Override
    public void setState(AccountState state) {
        this.state = state;
        for (Account a : accounts) a.setState(state);
    }

    @Override
    public void increaseBalance(double amount) {
        System.out.println("Groups do not manipulate balance directly.");
    }

    @Override
    public void decreaseBalance(double amount) {
        System.out.println("Groups do not manipulate balance directly.");
    }

    @Override
    public void setInterestStrategy(InterestStrategy strategy) {
        System.out.println("Account groups use individual account interest strategies.");
    }

    @Override
    public InterestStrategy getInterestStrategy() { return null; }

    @Override
    public double calculateInterest(int days) {
        double totalInterest = 0.0;
        for (Account account : accounts) {
            totalInterest += account.calculateInterest(days);
        }
        return totalInterest;
    }
}
