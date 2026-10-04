package recommendations;

import accounts.Account;

public class HighSpendingRecommendation implements RecommendationStrategy {

    @Override
    public String generate(Account account) {
        return account.getBalance() < 0 ?
                "You are in negative balance. Avoid overdraft." :
                "Spending normal.";
    }
}
