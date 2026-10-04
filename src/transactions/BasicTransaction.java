package transactions;

import Service.AccountService;
import accounts.Account;

public class BasicTransaction extends Transaction {

    private String sourceAccountId;
    private String targetAccountId;
    private AccountService accountService;

    public BasicTransaction(String transactionId,
                            TransactionType type,
                            double amount,
                            String sourceAccountId,
                            String targetAccountId,
                            AccountService accountService) {

        super(transactionId, type, amount);
        this.sourceAccountId = sourceAccountId;
        this.targetAccountId = targetAccountId;
        this.accountService = accountService;
    }

    public String getSourceAccountId() { return sourceAccountId; }
    public String getTargetAccountId() { return targetAccountId; }

    @Override
    public void process() {
        Account source = (sourceAccountId != null) ? accountService.getAccount(sourceAccountId) : null;
        Account target = (targetAccountId != null) ? accountService.getAccount(targetAccountId) : null;

        // تحقق أساسي من وجود الحسابات
        if (type == TransactionType.WITHDRAW || type == TransactionType.TRANSFER) {
            if (source == null) {
                System.out.println("Source account not found: " + sourceAccountId);
                return;
            }
        }
        if (type == TransactionType.DEPOSIT || type == TransactionType.TRANSFER) {
            if (target == null) {
                System.out.println("Target account not found: " + targetAccountId);
                return;
            }
        }

        // ملاحظة: عمليات السحب/إيداع تمر عبر account.withdraw/deposit
        // الذي يعتمد على الـ State لفرض القواعد (Closed/Suspended/Frozen)
        switch (type) {
            case DEPOSIT:
                target.deposit(amount);
                accountService.updateAccount(target);
                break;

            case WITHDRAW:
                source.withdraw(amount);
                accountService.updateAccount(source);
                break;

            case TRANSFER:
                source.withdraw(amount);
                target.deposit(amount);
                accountService.updateAccount(source);
                accountService.updateAccount(target);
                break;
        }
        AuditLogger.log("Transaction executed: "
                + transactionId
                + " | type=" + type
                + " | amount=" + amount
                + " | from=" + sourceAccountId
                + " | to=" + targetAccountId);

        System.out.println("Transaction processed: " + transactionId);
    }
}
