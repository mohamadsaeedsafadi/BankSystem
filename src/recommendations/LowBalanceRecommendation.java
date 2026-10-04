package recommendations;

import accounts.Account;

public class LowBalanceRecommendation implements RecommendationStrategy {
    @Override
    public String generate(Account account) {
        return account.getBalance() < 500 ?
                "Your balance is low. Consider reducing expenses." :
                "Balance OK.";
    }
}
