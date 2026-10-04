package repositories;

import tickets.SupportTicket;
import java.util.List;

public interface TicketRepository {
    void addTicket(SupportTicket ticket);
    SupportTicket getById(String id);
    List<SupportTicket> getAll();
    List<SupportTicket> getTicketsByCustomer(String customerId);
    void updateTicket(SupportTicket ticket);
}
