package test.reports;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import reports.DailyTransactionsReport;
import repositories.TransactionRepository;
import transactions.Transaction;
import transactions.TransactionType;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class DailyTransactionsReportTest {

    private TransactionRepository txRepo;
    private DailyTransactionsReport report;

    @BeforeEach
    void setup() {
        txRepo = mock(TransactionRepository.class);
        report = new DailyTransactionsReport(txRepo);
    }

    @Test
    void testGenerateReport() {
        // إنشاء بيانات اختبارية
        Transaction tx1 = new Transaction("tx1", TransactionType.DEPOSIT, 100) {
            @Override public void process() {}
        };
        Transaction tx2 = new Transaction("tx2", TransactionType.WITHDRAW, 50) {
            @Override public void process() {}
        };

        when(txRepo.getAllTransactions()).thenReturn(List.of(tx1, tx2));

        // إعادة توجيه System.out للـ ByteArrayOutputStream للتحقق من الطباعة
        var outContent = new java.io.ByteArrayOutputStream();
        var originalOut = System.out;
        System.setOut(new java.io.PrintStream(outContent));

        // تنفيذ التقرير
        report.generate();

        String output = outContent.toString();

        // تحقق من أن البيانات ظهرت في الطباعة
        assertTrue(output.contains("tx1 | DEPOSIT | 100"));
        assertTrue(output.contains("tx2 | WITHDRAW | 50"));
        assertTrue(output.contains("=== Daily Transactions Report ==="));
        assertTrue(output.contains("=== End Report ==="));

        // إعادة System.out للوضع الطبيعي
        System.setOut(originalOut);

        // تحقق من أن loadData تم استدعاؤه من repo
        verify(txRepo, times(1)).getAllTransactions();
    }
}
