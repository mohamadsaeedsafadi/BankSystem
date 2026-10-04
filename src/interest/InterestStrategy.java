package interest;

public interface InterestStrategy {
    double calculateInterest(double balance, int days);
    String getStrategyName();
    String getDescription();
}
