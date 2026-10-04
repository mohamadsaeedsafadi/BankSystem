package test.recommendations;

import recommendations.HighSpendingRecommendation;
import recommendations.LowBalanceRecommendation;
import recommendations.RecommendationStrategy;
import accounts.Account;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class RecommendationStrategyTest {

    @Test
    void testHighSpendingRecommendation() {
        Account account = mock(Account.class);

        RecommendationStrategy strategy = new HighSpendingRecommendation();

        // حالة رصيد سلبي
        when(account.getBalance()).thenReturn(-100.0);
        assertEquals("You are in negative balance. Avoid overdraft.", strategy.generate(account));

        // حالة رصيد إيجابي
        when(account.getBalance()).thenReturn(1000.0);
        assertEquals("Spending normal.", strategy.generate(account));
    }

    @Test
    void testLowBalanceRecommendation() {
        Account account = mock(Account.class);

        RecommendationStrategy strategy = new LowBalanceRecommendation();

        // رصيد أقل من 500
        when(account.getBalance()).thenReturn(200.0);
        assertEquals("Your balance is low. Consider reducing expenses.", strategy.generate(account));

        // رصيد أعلى من 500
        when(account.getBalance()).thenReturn(1000.0);
        assertEquals("Balance OK.", strategy.generate(account));
    }
}
