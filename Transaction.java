import java.time.LocalDate;

/**
 * Abstract base class for every money entry (Income / Expense).
 * Demonstrates: Abstraction, Encapsulation.
 */
public abstract class Transaction {
    private int id;
    private String description;
    private double amount;
    private String category;
    private LocalDate date;

    public Transaction(int id, String description, double amount, String category, LocalDate date) {
        this.id = id;
        this.description = description;
        this.amount = amount;
        this.category = category;
        this.date = date;
    }

    public int getId()              { return id; }
    public String getDescription()  { return description; }
    public double getAmount()       { return amount; }
    public String getCategory()     { return category; }
    public LocalDate getDate()      { return date; }

    // Each child class decides its own type and its effect on the balance
    public abstract String getType();
    public abstract double getSignedAmount();

    // Line stored in transactions.txt
    public String toFileString() {
        return getType() + "|" + id + "|" + description + "|" + amount + "|" + category + "|" + date;
    }

    @Override
    public String toString() {
        String desc = description.length() > 20 ? description.substring(0, 17) + "..." : description;
        return String.format("| %-4d | %-10s | %-8s | %-14s | %-20s | %10.2f |",
                id, date, getType(), category, desc, amount);
    }
}
