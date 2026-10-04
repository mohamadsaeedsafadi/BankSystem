package transactions;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ScheduledTransaction {

    private BasicTransaction transaction;
    private LocalDateTime nextExecutionTime;
    private long repeatIntervalDays; // 0 = execute once only

    // باقي التوابع كما هي
    public boolean isDue(LocalDateTime now) {
        return !now.isBefore(nextExecutionTime);
    }

    public void execute() {
        transaction.process();
    }

    public void scheduleNext() {
        if (repeatIntervalDays > 0) {
            nextExecutionTime = nextExecutionTime.plusDays(repeatIntervalDays);
        }
    }

    public boolean isRecurring() {
        return repeatIntervalDays > 0;
    }
}
