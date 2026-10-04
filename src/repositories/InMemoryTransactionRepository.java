package repositories;

import transactions.Transaction;
import transactions.BasicTransaction;
import java.util.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@Getter
@Setter
@NoArgsConstructor
public class InMemoryTransactionRepository implements TransactionRepository {

    private Map<String, Transaction> transactions = new HashMap<>();

    @Override
    public void addTransaction(Transaction transaction) {
        transactions.put(transaction.getTransactionId(), transaction);
    }

    @Override
    public Transaction getTransactionById(String transactionId) {
        return transactions.get(transactionId);
    }

    @Override
    public List<Transaction> getAllTransactions() {
        return new ArrayList<>(transactions.values());
    }

    @Override
    public List<Transaction> getTransactionsForAccount(String accountId) {
        List<Transaction> list = new ArrayList<>();

        for (Transaction t : transactions.values()) {
            if (t instanceof BasicTransaction basicTx) {
                String src = basicTx.getSourceAccountId();
                String tgt = basicTx.getTargetAccountId();

                if ((src != null && src.equals(accountId)) ||
                        (tgt != null && tgt.equals(accountId))) {
                    list.add(t);
                }
            }
        }

        return list;
    }
}
