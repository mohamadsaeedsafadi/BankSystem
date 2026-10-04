/*package Service;

import transactions.*;
import java.time.LocalDateTime;
import java.util.UUID;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TransactionService {

    private AccountService accountService;
    private TransactionHandler handlerChain;
    private TransactionScheduler scheduler;

    public TransactionService(AccountService accountService) {
        this.accountService = accountService;

        TransactionHandler auto = new AutoApprovalHandler();
        TransactionHandler teller = new TellerApprovalHandler();
        TransactionHandler manager = new ManagerApprovalHandler();

        auto.setNext(teller);
        teller.setNext(manager);

        this.handlerChain = auto;
        this.scheduler = new TransactionScheduler();
    }

    // تنفيذ فوري
    public void execute(TransactionType type, double amount, String sourceId, String targetId) {
        String txId = UUID.randomUUID().toString();
        BasicTransaction tx = new BasicTransaction(txId, type, amount, sourceId, targetId, accountService);
        handlerChain.handle(tx);
    }

    // إنشاء Scheduled Transaction
    public void schedule(TransactionType type,
                         double amount,
                         String sourceId,
                         String targetId,
                         LocalDateTime when,
                         long repeatDays) {

        String txId = UUID.randomUUID().toString();
        BasicTransaction tx = new BasicTransaction(txId, type, amount, sourceId, targetId, accountService);

        ScheduledTransaction st = new ScheduledTransaction(tx, when, repeatDays);
        scheduler.addScheduledTransaction(st);
    }

    public void tickScheduler() {
        scheduler.runSchedulerTick();
    }
}
*/
package Service;

import transactions.*;
import repositories.TransactionRepository;
import java.time.LocalDateTime;
import java.util.UUID;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TransactionService {

    private final AccountService accountService;
    private final TransactionRepository transactionRepository;
    private TransactionHandler handlerChain;
    private TransactionScheduler scheduler;

    public TransactionService(AccountService accountService,
                              TransactionRepository transactionRepository) {

        this.accountService = accountService;
        this.transactionRepository = transactionRepository;

        // 🔗 بناء سلسلة الموافقة
        TransactionHandler auto = new AutoApprovalHandler();
        TransactionHandler teller = new TellerApprovalHandler();
        TransactionHandler manager = new ManagerApprovalHandler();

        auto.setNext(teller);
        teller.setNext(manager);

        this.handlerChain = auto;
        this.scheduler = new TransactionScheduler();
    }

    // ⭐ تنفيذ فوري + تخزين في repo + caching
    public void execute(TransactionType type, double amount, String sourceId, String targetId) {

        String txId = UUID.randomUUID().toString();
        BasicTransaction tx = new BasicTransaction(txId, type, amount, sourceId, targetId, accountService);

        handlerChain.handle(tx);   // تنفيذ العملية بالـ chain

        // تخزين في الريبو
        transactionRepository.addTransaction(tx);

        // تخزين في الكاش
        cache.TransactionCache.cacheTransaction(tx);
    }

    // ⭐ جلب عملية باستخدام cache → repo fallback
    public Transaction getTransaction(String txId) {

        // 1) موجودة في الكاش؟
        if (cache.TransactionCache.exists(txId)) {
            return cache.TransactionCache.get(txId);
        }

        // 2) غير موجودة؟ جيبها من الريبو
        Transaction tx = transactionRepository.getTransactionById(txId);

        // 3) خزّنها بالكاش
        if (tx != null) {
            cache.TransactionCache.cacheTransaction(tx);
        }

        return tx;
    }

    // ⭐ إنشاء Scheduled Transaction مع التخزين
    public void schedule(TransactionType type,
                         double amount,
                         String sourceId,
                         String targetId,
                         LocalDateTime when,
                         long repeatDays) {

        String txId = UUID.randomUUID().toString();
        BasicTransaction tx = new BasicTransaction(txId, type, amount, sourceId, targetId, accountService);

        // إنشاء scheduled object
        ScheduledTransaction st = new ScheduledTransaction(tx, when, repeatDays);

        scheduler.addScheduledTransaction(st);

        // تخزين في الريبو
        transactionRepository.addTransaction(tx);

        // تخزين في الكاش
        cache.TransactionCache.cacheTransaction(tx);
    }

    public void tickScheduler() {
        scheduler.runSchedulerTick();
    }
}
