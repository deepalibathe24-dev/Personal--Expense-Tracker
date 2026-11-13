# Personal--Expense-Tracker
Java console app to track income and expenses using file handling (no database)
Student: Deepali Bathe

A Java console application for tracking income and expenses. It saves all data in a local text file (transactions.txt) and does not use a database.

Features
Add income and expense entries with category and date
View all transactions, or only income / only expenses
Category-wise expense summary with percentages
Monthly report showing net savings
Delete a transaction by ID
Balance summary with overspending warning
Save data locally in transactions.txt

Project Structure
ExpenseTracker/
├── Transaction.java      # Abstract base class
├── Income.java           # Extends Transaction
├── Expense.java          # Extends Transaction
├── FileManager.java      # Saves and loads transactions.txt
├── ExpenseTracker.java   # Main class with the menu
└── README.md

OOP Concepts Used
Concept           	Where it is used
Abstraction	        Transaction is an abstract class
Inheritance	        Income and Expense extend Transaction
Polymorphism	      getSignedAmount() behaves differently in each child class
Encapsulation	      Private fields accessed through getters
Collections	        ArrayList, TreeMap, Iterator
File Handling	      FileManager using BufferedReader and PrintWriter
Exception Handling	Input validation, date parsing, file errors

Run in VS Code

Open the project folder in VS Code. In the terminal, run:

javac Transaction.java Income.java Expense.java FileManager.java ExpenseTracker.java
java ExpenseTracker


Sample Output
--------------- MENU ---------------
1. Add Income
2. Add Expense
3. View All Transactions
4. View Income only / Expense only
5. Category-wise Expense Summary
6. Monthly Report
7. Delete a Transaction
8. Show Balance
0. Exit
------------------------------------

----- CATEGORY-WISE EXPENSES -----
Food            Rs.    150.00  (14.3%)
Study           Rs.    600.00  (57.1%)
Travel          Rs.    300.00  (28.6%)
----------------------------------
TOTAL           Rs.   1050.00

Total Income  : Rs. 5000.00
Total Expense : Rs. 1050.00
Net Savings   : Rs. 3950.00
