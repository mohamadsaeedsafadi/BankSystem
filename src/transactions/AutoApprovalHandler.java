package transactions;

public class AutoApprovalHandler extends TransactionHandler {

    private static final double LIMIT = 500.0;

    @Override
    public void handle(BasicTransaction transaction) {
        if (transaction.getAmount() <= LIMIT) {
            System.out.println("AutoApprovalHandler: Transaction auto-approved.");
            transaction.process();
        } else if (next != null) {
            next.handle(transaction);
        }
    }
}
