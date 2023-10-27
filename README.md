# TransactionManager

# RU Bank Transaction Manager

RU Bank Transaction Manager is a simple software developed by my partner and me to handle banking transactions for RU Bank. The software allows users to input transactions via command lines on the terminal. It is an interactive system that produces output instantly upon entering a transaction. The software supports various types of transactions, including account opening, closing, depositing, withdrawing, and displaying account details. 

## Concepts Reinforced
- Polymorphism
- Inheritance
- JUnit
- UML Class Diagram

## Account Types and Rules
- **General Rules**
  -  age of 16 or older to open, for College Checking, must be under the age of 24 to open.
  -  can hold different types of accounts, however, cannot hold a College Checking and Checking at the same time
    
- **Checking Account**
  - Monthly Fee: $12
  - Annual Interest Rate: 1.0%
  - Waived Monthly Fee if Account Balance >= $1000

- **College Checking Account**
  - No Monthly Fee
  - Must be under 24 to open

- **Savings Account**
  - Monthly Fee: $25 (Waived if Balance >= $500)
  - Annual Interest Rate: 4% (4.25% for Loyal Customers)

- **Money Market Savings Account**
  - Monthly Fee: $10 (Applies if Withdrawals > 3 times)
  - Annual Interest Rate: 4.5% (4.75% for Loyal Customers)
  - Minimum Deposit to Open: $2000

## Commands

- **O command**: Open an account with the desired account type.
  - Format: O \<AccountType\> \<FirstName\> \<LastName\> \<DOB\> \<InitialDeposit\>
  - Example: O C John Doe 2/19/2000 599.99

- **C command**: Close an existing account.
  - Format: C \<AccountType\> \<FirstName\> \<LastName\> \<DOB\>
  - Example: C MM Jane Doe 10/1/1995

- **D command**: Deposit money to an existing account.
  - Format: D \<AccountType\> \<FirstName\> \<LastName\> \<DOB\> \<Amount\>
  - Example: D C John Doe 2/19/1990 100

- **W command**: Withdraw money from an existing account.
  - Format: W \<AccountType\> \<FirstName\> \<LastName\> \<DOB\> \<Amount\>
  - Example: W C John Doe 2/19/1990 50

- **P command**: Display all accounts in the database, sorted by account types and account holders' profiles.

- **PI command**: Display all accounts along with calculated fees and monthly interests based on current account balances.

- **UB command**: Update account balances by applying fees and earned interests. Resets the number of withdrawals for Money Market accounts to 0.

- **Q command**: Stop the program execution and display "Transaction Manager is terminated."

## Usage
To use RU Bank Transaction Manager, simply input the commands as described above through the terminal.

## TestCases
- Refer to Project2TestCases.txt for testcases
- Expected output in Project2ExpectedOutput.txt

## Contributing


## License



