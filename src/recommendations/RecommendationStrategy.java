package recommendations;

import accounts.Account;

public interface RecommendationStrategy {
    String generate(Account account);
}
