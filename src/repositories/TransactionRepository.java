package repositories;

import transactions.Transaction;
import java.util.List;

public interface TransactionRepository {

    // إضافة عملية جديدة
    void addTransaction(Transaction transaction);

    // جلب عملية حسب ID
    Transaction getTransactionById(String transactionId);

    // جلب كل العمليات
    List<Transaction> getAllTransactions();

    // جلب العمليات المتعلقة بحساب معين
    List<Transaction> getTransactionsForAccount(String accountId);
}
