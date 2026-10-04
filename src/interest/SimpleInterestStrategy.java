package interest;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.Builder;

@Getter
@Setter
@NoArgsConstructor
@Builder
public class SimpleInterestStrategy implements InterestStrategy {

    private double annualRate; // المعدل السنوي (مثال: 0.05 = 5%)

    // نحتفظ بالـ constructor المخصص القديم
    public SimpleInterestStrategy(double annualRate) {
        this.annualRate = annualRate;
    }

    @Override
    public double calculateInterest(double balance, int days) {
        if (balance <= 0) return 0.0;

        double years = days / 365.0;
        double interest = balance * annualRate * years;

        return Math.round(interest * 100.0) / 100.0;
    }

    @Override
    public String getStrategyName() { return "Simple Interest"; }

    @Override
    public String getDescription() {
        return "فائدة بسيطة: المبلغ × " + (annualRate * 100) + "% × (الأيام/365)";
    }

    @Override
    public String toString() {
        return "SimpleInterestStrategy[rate=" + (annualRate * 100) + "%]";
    }
}
