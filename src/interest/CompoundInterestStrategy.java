package interest;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.Builder;

@Getter
@Setter
@NoArgsConstructor
@Builder
public class CompoundInterestStrategy implements InterestStrategy {

    private double annualRate;
    private int compoundingFrequency;

    // نحتفظ بالـ constructor المخصص القديم
    public CompoundInterestStrategy(double annualRate, int compoundingFrequency) {
        this.annualRate = annualRate;
        this.compoundingFrequency = compoundingFrequency;
    }

    @Override
    public double calculateInterest(double balance, int days) {
        if (balance <= 0) return 0.0;

        double years = days / 365.0;
        double ratePerPeriod = annualRate / compoundingFrequency;
        int totalPeriods = (int) (compoundingFrequency * years);

        double futureValue = balance * Math.pow(1 + ratePerPeriod, totalPeriods);
        double interest = futureValue - balance;

        return Math.round(interest * 100.0) / 100.0;
    }

    @Override
    public String getStrategyName() { return "Compound Interest"; }

    @Override
    public String getDescription() {
        String frequency = "";
        switch (compoundingFrequency) {
            case 1: frequency = "سنوياً"; break;
            case 2: frequency = "نصف سنوي"; break;
            case 4: frequency = "ربع سنوي"; break;
            case 12: frequency = "شهري"; break;
            default: frequency = compoundingFrequency + " مرة في السنة";
        }
        return "فائدة مركبة: " + (annualRate * 100) + "% " + frequency;
    }

    @Override
    public String toString() {
        return "CompoundInterestStrategy[rate=" + (annualRate * 100) + "%, frequency=" + compoundingFrequency + "]";
    }
}
