# Personal Expense Tracker

**Student:** <Your Name Here>

A Java console application to track income and expenses. It stores all data in a
local text file (`transactions.txt`) and **does not use a database**.

## Features
- Add income and expense entries (with category and date)
- View all transactions, or only income / only expenses
- Category-wise expense summary with percentages
- Monthly report with net savings
- Delete a transaction by ID
- Balance summary with overspending warning
- Data saved automatically in `transactions.txt`

## OOP Concepts Used
| Concept | Where |
|---|---|
| Abstraction | `Transaction` (abstract class) |
| Inheritance | `Income` and `Expense` extend `Transaction` |
| Polymorphism | `getSignedAmount()` / `getType()` behave differently per class |
| Encapsulation | Private fields with getters in `Transaction` |
| Collections | `ArrayList`, `TreeMap`, `Iterator` |
| File Handling | `FileManager` (BufferedReader / PrintWriter) |
| Exception Handling | Input validation, date parsing, file errors |

## Project Structure
```
ExpenseTracker/
  Transaction.java      (abstract base class)
  Income.java
  Expense.java
  FileManager.java      (save / load file)
  ExpenseTracker.java   (main class + menu)
  README.md
```

## Run in VS Code
Open the project folder in VS Code. In the terminal, run:

```
javac Transaction.java Income.java Expense.java FileManager.java ExpenseTracker.java
java ExpenseTracker
```
