package tickets;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@Getter
@Setter
@NoArgsConstructor
public class SupportTicket {
    private String id;
    private String customerId;
    private String message;
    private TicketStatus status;
    private LocalDateTime createdAt;

    public SupportTicket(String id, String customerId, String message) {
        this.id = id;
        this.customerId = customerId;
        this.message = message;
        this.status = TicketStatus.OPEN;
        this.createdAt = LocalDateTime.now();
    }

    public void display() {
        System.out.println(
                "Ticket [" + id + "] | Customer: " + customerId +
                        " | Status: " + status +
                        " | Message: " + message +
                        " | Created: " + createdAt
        );
    }
}
