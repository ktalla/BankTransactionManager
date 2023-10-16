package inheritanceprojectfiles;
import java.text.DecimalFormat;
/**
 * Class to hold accounts of different types
 * @author Ishani Mhatre
 */
public class AccountDatabase {
    private Account[] accounts; // List of various types of accounts
    private int numAcct; // Number of accounts in the array

    private final int NOT_FOUND = -1;

    public AccountDatabase() {
        accounts = new Account[4]; // Initial capacity is 4
        numAcct = 0;
    }

    // Increase the capacity by 4
    private void grow() {
        Account[] newAccounts = new Account[accounts.length + 4];
        for(int i =0; i<numAcct; i++){
            newAccounts[i] = accounts[i];
        }
        accounts = newAccounts;
    }

    // Find an account in the array
    private int find(Account account) {
        for (int i = 0; i < numAcct; i++) {
            if (account.equals(accounts[i]) && account.getClass().getName().equals(accounts[i].getClass().getName())) {
                return i;
            }
        }
        return NOT_FOUND; // Account not found
    }

    // Check if the account is already in the database
    public boolean contains(Account account) {
        return find(account) != NOT_FOUND;
    }

    // Add a new account to the database
    public boolean open(Account account) {
        if (contains(account)) {
//            System.out.println(account.holder.toString());
//            System.out.println(accounts[find(account)].holder.toString());
//            System.out.println(account.holder.compareTo(accounts[find(account)].holder));

            return false; // Account already exists
        }
        accounts[numAcct] = account;
        numAcct++;
        if (numAcct == accounts.length) {
            grow();
        }
        return true;
    }

    // Remove the given account from the database
    public boolean close(Account account) {
        int index = find(account);
        if (index == NOT_FOUND) {
            return false; // Account not found
        }
        // Move the last account to the removed account's position
        accounts[index] = accounts[numAcct - 1];
        accounts[numAcct - 1] = null;
        numAcct--;
        return true;
    }

    // Withdraw an amount from the account (false if insufficient funds)
    public boolean withdraw(Account account) { //check if moneymarket withdrawls are less than three
        int index = find(account);
        if (index == NOT_FOUND) {
            return false; // Account not found
        }
        if (accounts[index].balance < account.balance) {
            return false; // Insufficient funds
        }
        double amountToWithdraw = account.balance;
        if(account instanceof MoneyMarket){
            MoneyMarket mm = ((MoneyMarket) accounts[index]);
            mm.setWithdrawal(mm.getWithdrawal()+1);
            if((mm.balance - amountToWithdraw) <2000){
                mm.setLoyal(false);
            }
        }
        double currentBalance = accounts[index].getBalance();
        accounts[index].setBalance(currentBalance-amountToWithdraw);
        return true;
    }

    // Deposit an amount into the account
    public void deposit(Account account) {
        int index = find(account);
        accounts[index].balance += account.balance;
        if(account instanceof MoneyMarket) {
            MoneyMarket mm = ((MoneyMarket) accounts[index]);
            if(mm.balance>=2000){
                mm.setLoyal(true);
            }
        }
    }

    // Sort accounts by account type and profile
    public void printSorted() {
        if(numAcct==0){
            System.out.println("Account Database is empty!");
        }
        else {
            // Sort the accounts using bubble sort
            System.out.println("\n*Accounts sorted by account type and profile.");
            bubbleSort();
            for (int i = 0; i < numAcct; i++) {
                System.out.println(accounts[i].toString());
            }
            System.out.println("*end of list.\n");
        }
    }
    private void bubbleSort(){
        for (int i = 0; i < numAcct - 1; i++) {
            for (int j = 0; j < numAcct - i - 1; j++) {
                if (accounts[j].compareTo(accounts[j + 1]) > 0) { //element on left is greater than element on the right
                    Account temp = accounts[j];
                    accounts[j] = accounts[j + 1];
                    accounts[j + 1] = temp;
                }
            }
        }
    }




    // Calculate interests and fees
    public void printFeesAndInterests() {
        if(numAcct==0){
            System.out.println("Account Database is empty!");
        }
        else {
            bubbleSort();
            System.out.println("\n*list of accounts with fee and monthly interest");
            for (int i = 0; i < numAcct; i++) {
                Account account = accounts[i];
                double monthlyInterest = account.monthlyInterest();
                DecimalFormat decimalFormat = new DecimalFormat("$#,##0.00");
                String formattedInterest = decimalFormat.format(monthlyInterest);
                double monthlyFee = account.monthlyFee();

                // Print the interest and fee for each account
                System.out.println(accounts[i].toString() +
                        "::fee " + String.format("$%,.2f", monthlyFee) + "::monthly interest " + formattedInterest);

            }
            System.out.println("*end of list\n");
        }
    }


    // Apply interests and fees to update balances
    public void printUpdatedBalances() {
        if(numAcct==0){
            System.out.println("Account database is empty!");
        }
        else {
            bubbleSort();
            System.out.println("*list of accounts with fees and interests applied.\n");
            for (int i = 0; i < numAcct; i++) {
                Account account = accounts[i];
                double monthlyInterest = account.monthlyInterest();
                double monthlyFee = account.monthlyFee();

                // Update the account balance based on the interest and fees
                double updatedBalance = account.getBalance() + monthlyInterest - monthlyFee;
                account.setBalance(updatedBalance);
                if(account instanceof MoneyMarket){
                    ((MoneyMarket) account).setWithdrawal(0);
                }

                // Print the updated balance for each account
                System.out.println(accounts[i].toString());
            }
            System.out.println("*end of list\n");
        }
    }

    public double getBalance(Account account) {
        int index = find(account); // Find the index of the specified account
        if (index != NOT_FOUND) {
            return accounts[index].balance; // Return the balance of the specified account
        }
        return NOT_FOUND; // Return a negative value (or another suitable indicator) to indicate that the account was not found.
    }


}