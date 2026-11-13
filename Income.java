import java.time.LocalDate;

/** Money coming in (salary, pocket money, scholarship...). Demonstrates: Inheritance. */
public class Income extends Transaction {

    public Income(int id, String description, double amount, String category, LocalDate date) {
        super(id, description, amount, category, date);
    }

    @Override
    public String getType() {
        return "INCOME";
    }

    @Override
    public double getSignedAmount() {
        return getAmount();      // adds to balance
    }
}
