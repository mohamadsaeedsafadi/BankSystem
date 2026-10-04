package Service;

import accounts.Account;
import accounts.AccountGroup;

import java.util.HashMap;
import java.util.Map;

public class AccountGroupService {

    private final Map<String, AccountGroup> groups = new HashMap<>();

    public void createGroup(String id, String name) {
        if (groups.containsKey(id)) {
            System.out.println("❌ Group already exists.");
            return;
        }
        groups.put(id, new AccountGroup(id, name));
        System.out.println("✅ Group created.");
    }

    public AccountGroup getGroup(String id) {
        return groups.get(id);
    }

    public void addAccountToGroup(String groupId, Account account) {
        AccountGroup g = groups.get(groupId);
        if (g == null) {
            System.out.println("❌ Group not found.");
            return;
        }
        g.addAccount(account);
        System.out.println("✅ Account added to group.");
    }

    public void showGroup(String groupId) {
        AccountGroup g = groups.get(groupId);
        if (g == null) {
            System.out.println("❌ Group not found.");
            return;
        }
        g.displayAccountInfo();
    }
}
