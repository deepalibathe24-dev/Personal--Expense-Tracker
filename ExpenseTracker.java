import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeParseException;
import java.util.*;

/**
 * Personal Expense Tracker - main class (menu-driven console app).
 * Data is stored in transactions.txt (no database).
 */
public class ExpenseTracker {
    private static final Scanner sc = new Scanner(System.in);
    private static final List<Transaction> transactions = FileManager.load();

    public static void main(String[] args) {
        System.out.println("=========================================");
        System.out.println("        PERSONAL EXPENSE TRACKER");
        System.out.println("=========================================");

        int choice;
        do {
            showMenu();
            choice = readInt("Enter your choice: ");
            switch (choice) {
                case 1: addTransaction(true);  break;
                case 2: addTransaction(false); break;
                case 3: printTable(transactions, "ALL TRANSACTIONS"); break;
                case 4: viewByType();          break;
                case 5: categorySummary();     break;
                case 6: monthlyReport();       break;
                case 7: deleteTransaction();   break;
                case 8: showBalance();         break;
                case 0: System.out.println("\nData saved in transactions.txt. Goodbye!"); break;
                default: System.out.println("Invalid choice! Try again.");
            }
        } while (choice != 0);
    }

    private static void showMenu() {
        System.out.println("\n--------------- MENU ---------------");
        System.out.println("1. Add Income");
        System.out.println("2. Add Expense");
        System.out.println("3. View All Transactions");
        System.out.println("4. View Income only / Expense only");
        System.out.println("5. Category-wise Expense Summary");
        System.out.println("6. Monthly Report");
        System.out.println("7. Delete a Transaction");
        System.out.println("8. Show Balance");
        System.out.println("0. Exit");
        System.out.println("------------------------------------");
    }

    // ---------------- Operations ----------------

    private static void addTransaction(boolean isIncome) {
        String description = readText("Description: ");
        double amount = readAmount();
        String category = readText("Category (e.g. Food, Travel, Books, Salary): ");
        LocalDate date = readDate();

        int id = nextId();
        Transaction t = isIncome
                ? new Income(id, description, amount, category, date)
                : new Expense(id, description, amount, category, date);

        transactions.add(t);
        FileManager.save(transactions);
        System.out.println((isIncome ? "Income" : "Expense") + " added with ID " + id);
    }

    private static void viewByType() {
        System.out.println("1. Income only");
        System.out.println("2. Expense only");
        int c = readInt("Choose: ");
        String type = (c == 1) ? "INCOME" : (c == 2) ? "EXPENSE" : null;
        if (type == null) {
            System.out.println("Invalid choice.");
            return;
        }
        List<Transaction> filtered = new ArrayList<>();
        for (Transaction t : transactions) {
            if (t.getType().equals(type)) filtered.add(t);
        }
        printTable(filtered, type + " TRANSACTIONS");
    }

    private static void categorySummary() {
        Map<String, Double> totals = new TreeMap<>();
        double grandTotal = 0;
        for (Transaction t : transactions) {
            if (t instanceof Expense) {
                totals.put(t.getCategory(), totals.getOrDefault(t.getCategory(), 0.0) + t.getAmount());
                grandTotal += t.getAmount();
            }
        }
        if (totals.isEmpty()) {
            System.out.println("\nNo expenses recorded yet.");
            return;
        }
        System.out.println("\n----- CATEGORY-WISE EXPENSES -----");
        for (Map.Entry<String, Double> e : totals.entrySet()) {
            double percent = (e.getValue() / grandTotal) * 100;
            System.out.printf("%-15s Rs. %9.2f  (%.1f%%)%n", e.getKey(), e.getValue(), percent);
        }
        System.out.println("----------------------------------");
        System.out.printf("%-15s Rs. %9.2f%n", "TOTAL", grandTotal);
    }

    private static void monthlyReport() {
        System.out.print("Enter month (yyyy-MM, e.g. 2026-11): ");
        String input = sc.nextLine().trim();
        YearMonth ym;
        try {
            ym = YearMonth.parse(input);
        } catch (DateTimeParseException e) {
            System.out.println("Invalid month format.");
            return;
        }

        double income = 0, expense = 0;
        List<Transaction> monthList = new ArrayList<>();
        for (Transaction t : transactions) {
            if (YearMonth.from(t.getDate()).equals(ym)) {
                monthList.add(t);
                if (t instanceof Income) income += t.getAmount();
                else expense += t.getAmount();
            }
        }
        printTable(monthList, "REPORT FOR " + ym);
        if (!monthList.isEmpty()) {
            System.out.printf("Total Income  : Rs. %.2f%n", income);
            System.out.printf("Total Expense : Rs. %.2f%n", expense);
            System.out.printf("Net Savings   : Rs. %.2f%n", income - expense);
        }
    }

    private static void deleteTransaction() {
        int id = readInt("Enter transaction ID to delete: ");
        Iterator<Transaction> it = transactions.iterator();
        while (it.hasNext()) {
            if (it.next().getId() == id) {
                it.remove();
                FileManager.save(transactions);
                System.out.println("Transaction deleted.");
                return;
            }
        }
        System.out.println("No transaction found with that ID.");
    }

    private static void showBalance() {
        double income = 0, expense = 0, balance = 0;
        for (Transaction t : transactions) {
            balance += t.getSignedAmount();          // polymorphism
            if (t instanceof Income) income += t.getAmount();
            else expense += t.getAmount();
        }
        System.out.println("\n--------- BALANCE SUMMARY ---------");
        System.out.printf("Total Income  : Rs. %.2f%n", income);
        System.out.printf("Total Expense : Rs. %.2f%n", expense);
        System.out.printf("Balance       : Rs. %.2f%n", balance);
        if (balance < 0) {
            System.out.println("Warning: You are spending more than you earn!");
        }
    }

    // ---------------- Helpers ----------------

    private static void printTable(List<Transaction> list, String title) {
        if (list.isEmpty()) {
            System.out.println("\nNo records found.");
            return;
        }
        String line = "+------+------------+----------+----------------+----------------------+------------+";
        System.out.println("\n" + title);
        System.out.println(line);
        System.out.println("| ID   | Date       | Type     | Category       | Description          | Amount     |");
        System.out.println(line);
        for (Transaction t : list) System.out.println(t);
        System.out.println(line);
    }

    private static int nextId() {
        int max = 0;
        for (Transaction t : transactions) {
            if (t.getId() > max) max = t.getId();
        }
        return max + 1;
    }

    private static int readInt(String msg) {
        while (true) {
            System.out.print(msg);
            try {
                return Integer.parseInt(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
    }

    private static double readAmount() {
        while (true) {
            System.out.print("Amount (Rs.): ");
            try {
                double a = Double.parseDouble(sc.nextLine().trim());
                if (a > 0) return a;
                System.out.println("Amount must be greater than 0.");
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid amount.");
            }
        }
    }

    private static String readText(String msg) {
        while (true) {
            System.out.print(msg);
            String s = sc.nextLine().trim();
            if (!s.isEmpty() && !s.contains("|")) return s;
            System.out.println("Input cannot be empty or contain the | symbol.");
        }
    }

    private static LocalDate readDate() {
        while (true) {
            System.out.print("Date (yyyy-MM-dd) or press Enter for today: ");
            String s = sc.nextLine().trim();
            if (s.isEmpty()) return LocalDate.now();
            try {
                return LocalDate.parse(s);
            } catch (DateTimeParseException e) {
                System.out.println("Invalid date. Example: 2026-11-13");
            }
        }
    }
}
