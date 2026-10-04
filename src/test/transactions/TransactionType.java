package test.transactions;

import transactions.TransactionType;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class TransactionTypeTest {

    @Test
    void testTransactionTypeValues() {
        assertEquals("DEPOSIT", TransactionType.DEPOSIT.name());
        assertEquals("WITHDRAW", TransactionType.WITHDRAW.name());
        assertEquals("TRANSFER", TransactionType.TRANSFER.name());
    }
}

