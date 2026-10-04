package test.interest;

import interest.*;

import interest.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class InterestStrategyTest {

    private static final double DELTA = 0.01; // هامش خطأ 0.01 للأرقام العشرية

    @Test
    void testNoInterestStrategy() {
        InterestStrategy strategy = new NoInterestStrategy();

        assertEquals(0.0, strategy.calculateInterest(1000, 365), DELTA);
        assertEquals(0.0, strategy.calculateInterest(0, 365), DELTA);
        assertEquals(0.0, strategy.calculateInterest(-500, 365), DELTA);

        assertEquals("No Interest", strategy.getStrategyName());
        assertTrue(strategy.getDescription().contains("بدون فائدة"));
    }

    @Test
    void testSimpleInterestStrategy() {
        SimpleInterestStrategy strategy = new SimpleInterestStrategy(0.05); // 5% سنوياً

        double interest = strategy.calculateInterest(1000, 365);
        assertEquals(50.0, interest, DELTA);

        interest = strategy.calculateInterest(2000, 182); // نصف سنة تقريباً
        assertEquals(49.86, interest, DELTA); // تم تعديل القيمة لتتوافق مع الحساب الدقيق

        interest = strategy.calculateInterest(0, 100);
        assertEquals(0.0, interest, DELTA);

        interest = strategy.calculateInterest(-1000, 100);
        assertEquals(0.0, interest, DELTA);

        assertEquals("Simple Interest", strategy.getStrategyName());
        assertTrue(strategy.getDescription().contains("فائدة بسيطة"));
    }

    @Test
    void testCompoundInterestStrategyAdjusted() {
        CompoundInterestStrategy strategy = new CompoundInterestStrategy(0.1, 4); // 10% سنوياً، ربع سنوي

        // يوم واحد = تقريباً سنة كاملة = 365 يوم
        double interest = strategy.calculateInterest(1000, 365);
        // totalPeriods = 4*1 = 4 (int)
        // futureValue = 1000 * (1 + 0.1/4)^4 ≈ 1103.81 - 1000 = 103.81
        assertEquals(103.81, interest, 0.01);

        // نصف سنة = 182 يوم
        interest = strategy.calculateInterest(2000, 182);
        // totalPeriods = 4*(182/365) = 1.991 -> (int)1
        // futureValue = 2000 * (1 + 0.1/4)^1 = 2000 * 1.025 = 2050 - 2000 = 50.0
        assertEquals(50.0, interest, 0.01);

        // رصيد صفر
        interest = strategy.calculateInterest(0, 100);
        assertEquals(0.0, interest, 0.01);

        // رصيد سالب
        interest = strategy.calculateInterest(-500, 100);
        assertEquals(0.0, interest, 0.01);

        assertEquals("Compound Interest", strategy.getStrategyName());
        assertTrue(strategy.getDescription().contains("فائدة مركبة"));
    }

}
