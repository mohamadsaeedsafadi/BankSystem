package transactions;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TransactionScheduler {

    private List<ScheduledTransaction> scheduledList = new ArrayList<>();

    public void addScheduledTransaction(ScheduledTransaction st) {
        scheduledList.add(st);
        System.out.println("Scheduled transaction added: "
                + st.getTransaction().getTransactionId()
                + " at " + st.getNextExecutionTime());
    }

    public void runSchedulerTick() {
        LocalDateTime now = LocalDateTime.now();
        List<ScheduledTransaction> executed = new ArrayList<>();

        for (ScheduledTransaction st : scheduledList) {
            if (st.isDue(now)) {
                st.execute();
                if (!st.isRecurring()) {
                    executed.add(st); // remove one-time transactions
                } else {
                    st.scheduleNext(); // update future execution time
                }
            }
        }

        scheduledList.removeAll(executed);
    }
}
