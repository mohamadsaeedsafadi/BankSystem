package reports;

import repositories.TransactionRepository;
import transactions.Transaction;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DailyTransactionsReport extends ReportTemplate {

    private final TransactionRepository txRepo;
    private List<Transaction> data;

    // نحتفظ بالـ constructor المخصص
    public DailyTransactionsReport(TransactionRepository txRepo) {
        this.txRepo = txRepo;
    }

    @Override
    protected void loadData() {
        data = txRepo.getAllTransactions(); // simple: all transactions
    }

    @Override
    protected void formatData() {
        System.out.println("Formatting daily transactions (" + data.size() + " records).");
    }

    @Override
    protected void export() {
        System.out.println("=== Daily Transactions Report ===");
        for (var t : data) System.out.println(t.getTransactionId() + " | " + t.getType() + " | " + t.getAmount());
        System.out.println("=== End Report ===");
    }
}
