package transactions;

public class ManagerApprovalHandler extends TransactionHandler {

    @Override
    public void handle(BasicTransaction transaction) {
        System.out.println("ManagerApprovalHandler: Manager approved the transaction.");
        transaction.process();
    }
}
