package Service;

import notifications.NotificationCenter;
import repositories.TicketRepository;
import tickets.SupportTicket;
import tickets.TicketStatus;

import java.util.List;
import java.util.UUID;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TicketService {

    private final TicketRepository repo;
    private final NotificationCenter notifier;

    public TicketService(TicketRepository repo, NotificationCenter notifier) {
        this.repo = repo;
        this.notifier = notifier;
    }

    public SupportTicket createTicket(String customerId, String message) { /* ... */ return null; }
    public List<SupportTicket> getAllTickets() { return repo.getAll(); }
    public List<SupportTicket> getTicketsForCustomer(String customerId) { return repo.getTicketsByCustomer(customerId); }
    public SupportTicket getById(String id) { return repo.getById(id); }
    public void changeStatus(String id, TicketStatus status) { /* ... */ }
    public void closeTicket(String id) { changeStatus(id, TicketStatus.CLOSED); }
}
