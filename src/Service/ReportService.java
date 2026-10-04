package Service;

import reports.DailyTransactionsReport;
import repositories.TransactionRepository;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReportService {

    private final TransactionRepository txRepo;

    public ReportService(TransactionRepository txRepo) {
        this.txRepo = txRepo;
    }

    public void runDailyTransactionsReport() {
        var r = new DailyTransactionsReport(txRepo);
        r.generate();
    }
}
