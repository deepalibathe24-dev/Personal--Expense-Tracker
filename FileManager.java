import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/** Handles saving and loading transactions from a local text file (no database). */
public class FileManager {
    private static final String FILE_NAME = "transactions.txt";

    public static void save(List<Transaction> list) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(FILE_NAME))) {
            for (Transaction t : list) {
                pw.println(t.toFileString());
            }
        } catch (IOException e) {
            System.out.println("Error saving data: " + e.getMessage());
        }
    }

    public static List<Transaction> load() {
        List<Transaction> list = new ArrayList<>();
        File file = new File(FILE_NAME);
        if (!file.exists()) {
            return list;
        }
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                try {
                    String[] p = line.split("\\|");
                    int id = Integer.parseInt(p[1]);
                    String desc = p[2];
                    double amount = Double.parseDouble(p[3]);
                    String category = p[4];
                    LocalDate date = LocalDate.parse(p[5]);

                    if (p[0].equals("INCOME")) {
                        list.add(new Income(id, desc, amount, category, date));
                    } else {
                        list.add(new Expense(id, desc, amount, category, date));
                    }
                } catch (Exception e) {
                    System.out.println("Skipped a damaged line in file.");
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading data: " + e.getMessage());
        }
        return list;
    }
}
