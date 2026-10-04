package test.accounts;
import accounts.*;

import interest.SimpleInterestStrategy;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class InterestStrategyTest {

    @Test
    void checkingAccountShouldCalculateSimpleInterest() {
        CheckingAccount account = new CheckingAccount("C5", "Ali", 1000);
        account.setInterestStrategy(new SimpleInterestStrategy(0.01));

        double interest = account.calculateInterest(30);

        assertTrue(interest > 0);
    }

    @Test
    void loanAccountShouldHaveNoInterest() {
        LoanAccount loan = new LoanAccount("L1", "Yazan", 5000);

        double interest = loan.calculateInterest(30);

        assertEquals(0.0, interest);
    }
}
