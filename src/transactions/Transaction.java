package transactions;

import java.time.LocalDateTime;

public abstract class Transaction {

    protected String transactionId;
    protected TransactionType type;
    protected double amount;
    protected LocalDateTime timestamp;

    public Transaction(String transactionId, TransactionType type, double amount) {
        this.transactionId = transactionId;
        this.type = type;
        this.amount = amount;
        this.timestamp = LocalDateTime.now();
    }

    public String getTransactionId() { return transactionId; }
    public TransactionType getType() { return type; }
    public double getAmount() { return amount; }
    public LocalDateTime getTimestamp() { return timestamp; }

    public abstract void process();
}
