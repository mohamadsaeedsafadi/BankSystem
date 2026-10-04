package repositories;

import tickets.SupportTicket;
import java.util.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@Getter
@Setter
@NoArgsConstructor
public class InMemoryTicketRepository implements TicketRepository {

    private Map<String, SupportTicket> tickets = new HashMap<>();

    @Override
    public void addTicket(SupportTicket ticket) {
        tickets.put(ticket.getId(), ticket);
    }

    @Override
    public SupportTicket getById(String id) {
        return tickets.get(id);
    }

    @Override
    public List<SupportTicket> getAll() {
        return new ArrayList<>(tickets.values());
    }

    @Override
    public List<SupportTicket> getTicketsByCustomer(String customerId) {
        List<SupportTicket> list = new ArrayList<>();
        for (SupportTicket t : tickets.values()) {
            if (t.getCustomerId().equals(customerId)) list.add(t);
        }
        return list;
    }

    @Override
    public void updateTicket(SupportTicket ticket) {
        tickets.put(ticket.getId(), ticket);
    }
}
