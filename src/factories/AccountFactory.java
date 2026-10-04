package factories;

import accounts.*;

public class AccountFactory {

    // دالة ثابتة لإنشاء حساب بناءً على النوع
    public static Account createAccount(String accountType, String accountId, String accountHolder, double initialBalance) {
        System.out.println(" AccountFactory: Creating account of type: " + accountType);

        switch (accountType.toLowerCase()) {
            case "savings":
                return new SavingsAccount(accountId, accountHolder, initialBalance);
            case "checking":
                return new CheckingAccount(accountId, accountHolder, initialBalance);
            case "loan":
                return new LoanAccount(accountId, accountHolder, initialBalance);
            case "investment":
                return new InvestmentAccount(accountId, accountHolder, initialBalance);
            case "group":
                return new AccountGroup(accountId, accountHolder);
            default:
                throw new IllegalArgumentException("Unknown account type: " + accountType);
        }
    }

    // يمكن إضافة دوال أخرى لإنشاء حسابات بإعدادات خاصة
    public static Account createSavingsAccount(String accountId, String accountHolder, double initialBalance) {
        return new SavingsAccount(accountId, accountHolder, initialBalance);
    }

    public static Account createCheckingAccount(String accountId, String accountHolder, double initialBalance) {
        return new CheckingAccount(accountId, accountHolder, initialBalance);
    }

    public static Account createLoanAccount(String accountId, String accountHolder, double loanAmount) {
        return new LoanAccount(accountId, accountHolder, loanAmount);
    }

    public static Account createInvestmentAccount(String accountId, String accountHolder, double initialInvestment) {
        return new InvestmentAccount(accountId, accountHolder, initialInvestment);
    }

    public static Account createAccountGroup(String groupId, String groupName) {
        return new AccountGroup(groupId, groupName);
    }
}