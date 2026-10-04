package test.tickets;

import tickets.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class SupportTicketTest {

    private SupportTicket ticket;
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @BeforeEach
    void setup() {
        // إعادة توجيه System.out لالتقاط الطباعة
        System.setOut(new PrintStream(outContent));

        ticket = new SupportTicket("T1", "C123", "Issue with account");
    }

    @Test
    void testTicketCreation() {
        // تحقق من الحقول الأساسية
        assertEquals("T1", ticket.getId());
        assertEquals("C123", ticket.getCustomerId());
        assertEquals("Issue with account", ticket.getMessage());
        assertEquals(TicketStatus.OPEN, ticket.getStatus());
        assertNotNull(ticket.getCreatedAt());
        assertTrue(ticket.getCreatedAt().isBefore(LocalDateTime.now().plusSeconds(1)));
    }

    @Test
    void testDisplayOutput() {
        ticket.display();

        String output = outContent.toString();

        // تحقق أن كل المعلومات موجودة في الطباعة
        assertTrue(output.contains("T1"));
        assertTrue(output.contains("C123"));
        assertTrue(output.contains("OPEN"));
        assertTrue(output.contains("Issue with account"));
        assertTrue(output.contains("Created:"));

        // نظف System.out بعد الاختبار
        System.setOut(originalOut);
    }

    @Test
    void testTicketStatusChange() {
        // تغيير الحالة يدويًا
        ticket.setStatus(TicketStatus.RESOLVED);
        assertEquals(TicketStatus.RESOLVED, ticket.getStatus());
    }

    @org.junit.jupiter.api.AfterEach
    void tearDown() {
        // إعادة System.out للوضع الطبيعي بعد كل اختبار
        System.setOut(originalOut);
    }
}
