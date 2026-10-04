package interest;

public class NoInterestStrategy implements InterestStrategy {

    public NoInterestStrategy() {
        // constructor بسيط
    }

    @Override
    public double calculateInterest(double balance, int days) {
        return 0.0; // بدون فائدة
    }

    @Override
    public String getStrategyName() {
        return "No Interest";
    }

    @Override
    public String getDescription() {
        return "بدون فائدة - للحسابات الجارية أو الخاصة";
    }

    @Override
    public String toString() {
        return "NoInterestStrategy[]";
    }
}