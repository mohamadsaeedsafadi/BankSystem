package transactions;

public class TellerApprovalHandler extends TransactionHandler {

    private static final double LIMIT = 5000.0;

    @Override
    public void handle(BasicTransaction transaction) {
        if (transaction.getAmount() <= LIMIT) {
            System.out.println("TellerApprovalHandler: Teller approved the transaction.");
            transaction.process();
        } else if (next != null) {
            next.handle(transaction);
        }
    }
}
