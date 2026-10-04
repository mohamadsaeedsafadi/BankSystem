package transactions;

public abstract class TransactionHandler {

    protected TransactionHandler next;

    public void setNext(TransactionHandler next) {
        this.next = next;
    }

    public abstract void handle(BasicTransaction transaction);
}
