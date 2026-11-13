import java.time.LocalDate;

/** Money going out (food, travel, books...). Demonstrates: Inheritance + Polymorphism. */
public class Expense extends Transaction {

    public Expense(int id, String description, double amount, String category, LocalDate date) {
        super(id, description, amount, category, date);
    }

    @Override
    public String getType() {
        return "EXPENSE";
    }

    @Override
    public double getSignedAmount() {
        return -getAmount();     // reduces balance
    }
}
