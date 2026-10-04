package Service;

import accounts.Account;
import recommendations.LowBalanceRecommendation;
import recommendations.HighSpendingRecommendation;
import recommendations.RecommendationStrategy;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RecommendationService {

    private final List<RecommendationStrategy> strategies = new ArrayList<>();

    public RecommendationService() {
        strategies.add(new LowBalanceRecommendation());
        strategies.add(new HighSpendingRecommendation());
    }

    public void printRecommendations(Account account) {
        System.out.println("\nRecommendations for " + account.getAccountId() + ":");
        for (RecommendationStrategy s : strategies) {
            System.out.println("- " + s.generate(account));
        }
    }
}
