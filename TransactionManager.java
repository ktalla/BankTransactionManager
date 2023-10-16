package inheritanceprojectfiles;

import java.util.Scanner;

/** Represents the User Interface
 * @author Keerthana Talla
 * @author Ishani Mahtre
 */

public class TransactionManager {
    private AccountDatabase accountDatabase;

    /**
     * Default constructor
     */
    public TransactionManager() {
        accountDatabase = new AccountDatabase();
    }

    /**
     * User Interface method -- handles user's input
     */
    public void run() {
        System.out.println("Transaction Manager is running.");
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        String[] inputData = input.split("\\s+");
//        for(int i=0; i<inputData.length; i++){
//            System.out.println(inputData[i]);
//        }
        while (!inputData[0].equals("Q")) {
            switch (inputData[0]) {
                case "O":
                    System.out.println(handleCommandO(inputData));
                    break;
                case "C":
                    System.out.println(handleCommandC(inputData));
                    break;
                case "UB":
                    accountDatabase.printUpdatedBalances();
                    break;
                case "D":
                    System.out.println(handleCommandD(inputData));
                    break;
                case "W":
                    System.out.println(handleCommandW(inputData));
                    break;
                case "P":
                    accountDatabase.printSorted();
                    break;
                case "PI":
                    accountDatabase.printFeesAndInterests();
                    break;
                default:
                    System.out.println("Invalid command!");
                    break;
            }
            input = scanner.nextLine();
            while(input.isEmpty())
                input = scanner.nextLine();
            inputData = input.split("\\s+");
        }
        System.out.println("Transaction Manager terminated.");
        scanner.close();
    }

    /**
     *
     * @param inputData String array with each cell having an input value
     * @return String with error message if input is incorrect, otherwise returns empty string
     */
    public String inputCheckForO(String[] inputData) {
        try {
            if ((inputData[1].equals("CC") || inputData[1].equals("S")) && inputData.length < 7) { //CollegeChecking and Savings requires 7 pieces of information
                return "Missing Data for opening an account.";
            } else if (inputData.length < 6) {
                return "Missing Data for opening an account."; //other accounts require 6 pieces of information
            } else {
                String accountType = inputData[1];
                String firstName = inputData[2];
                String lastName = inputData[3];
                Date date = Date.fromString(inputData[4]);
                double balance = Double.parseDouble(inputData[5]); //check if 0 or negative
                if (balance <= 0) {
                    return "Initial deposit cannot be 0 or negative.";
                }
                if (!date.isValid()) {
                    return "DOB invalid: " + date + " not a valid calendar date!";
                }
                if (!date.isPresentorFutureDate()) {
                    return "DOB invalid: " + date + " cannot be today or a future day.";
                }
                if (date.getAge() < 16) {
                    return "DOB invalid: " + date + " under 16.";
                }
                if (accountType.equals("CC") && (Integer.parseInt(inputData[6])<0 || Integer.parseInt(inputData[6])>2)) {
                   return "Invalid campus code.";
                }
                if (accountType.equals("S") && (Integer.parseInt(inputData[6]) < 0 || Integer.parseInt(inputData[6]) > 1)) {
                    return "Invalid loyalty code.";
                }
                return "";
            }
        }
        catch(NumberFormatException e){
            return "Not a valid amount.";
        }
    }



    public String openCheckingAccount(Profile profile, double balance){
        Checking checkingAcc = new Checking(profile, balance);
        Account account = createDummyAccount("CC", profile, 0);
        if(!(accountDatabase.contains(account)) && accountDatabase.open(checkingAcc)){
            return profile.toString() + "(C) opened.";
        }
        else{
            return profile.toString() + "(C) is already in the database.";
        }
    }

    public String openCCAccount(Profile profile, double balance, int campusCode, Date dob){
        if(dob.getAge()<24) {
            Campus campus = Campus.fromCode(campusCode);
            CollegeChecking ccAccount = new CollegeChecking(profile, balance, campus);
            Account account = createDummyAccount("C", profile, 0);
            if (!(accountDatabase.contains(account)) && accountDatabase.open(ccAccount)) {
                return profile.toString() + "(CC) opened.";
            } else {
                return profile.toString() + "(CC) is already in the database."; //already is a c acc and ur trynna make a cc
            }
        }
        else{
            return "DOB invalid: " + dob + " over 24.";
        }
    }
    public String openSavingsAccount(Profile profile, double balance, int loyaltyCode){
        boolean loyalty; //try catch here
        if(loyaltyCode == 1){
            loyalty = true;
        }
        else {
            loyalty = false;
        }
        Savings savingsAcc = new Savings(profile, balance, loyalty);
        if(accountDatabase.open(savingsAcc)){
            return profile.toString() + "(S) opened.";
        }
        else{
            return profile.toString() + "(S) is already in the database.";
        }
    }
    public String openMMAccount(Profile profile, double balance){
        if(balance>=2000) {
            MoneyMarket moneyMarket = new MoneyMarket(profile, balance, 0);
            if(accountDatabase.open(moneyMarket)){
                return profile.toString() + "(MM) opened.";
            }
            else{
                return profile.toString() + "(MM) is already in the database.";
            }
        }
        else{
            return "Minimum of $2000 to open a Money Market account.";
        }
    }
    public String handleCommandO(String[] inputData) {
        String error = inputCheckForO(inputData);
        if (error.isEmpty()) {
            String accountType = inputData[1];
            Date dob = Date.fromString(inputData[4]);
            double balance = Double.parseDouble(inputData[5]);
            Profile profile = new Profile(inputData[2], inputData[3], dob);
            switch(accountType){
                case "C":
                    return openCheckingAccount(profile, balance);
                case "CC":
                    int campusCode = Integer.parseInt(inputData[6]);
                    return openCCAccount(profile, balance, campusCode, dob);
                case "S":
                    int loyaltyCode = Integer.parseInt(inputData[6]);
                    return openSavingsAccount(profile, balance, loyaltyCode);
                case "MM":
                    return openMMAccount(profile, balance);
            }
        }
        return error;
    }

    private String handleCommandW(String[] inputData) {
        String accountType = inputData[1];
        Date dob = Date.fromString(inputData[4]);
        Profile profile = new Profile(inputData[2], inputData[3], dob);
        try {
            double withdrawalAmount = Double.parseDouble(inputData[5]);
            if (withdrawalAmount <= 0) {
                return "Withdraw - amount cannot be 0 or negative.";
            } else {
                Account account = createDummyAccount(accountType, profile, withdrawalAmount);
                if (accountDatabase.contains(account)) {
                    double currentBalance = accountDatabase.getBalance(account); // Assuming you have a method to get the account balance
                    if (withdrawalAmount > currentBalance) {
                        return profile.toString() + "(" + accountType + ") Withdraw - insufficient funds.";
                    } else {
                        accountDatabase.withdraw(account);
                        return profile.toString() + "(" + accountType + ") Withdraw - balance updated.";
                    }
                } else {
                    return profile.toString() + "(" + accountType + ") is not in the database.";
                }
            }
        } catch (NumberFormatException e) {
            return "Not a valid amount.";
        }
    }

//   private String handleCommandUB(){
//        System.out.println("*list of accounts with fees and interests applied.");
//
//   }

   public Account createDummyAccount(String accountType, Profile profile, double amount){
           switch(accountType){
               case "C":
                   return new Checking(profile, amount);
               case "CC":
                   return new CollegeChecking(profile, amount, null);
               case "S":
                   return new Savings(profile, amount, true);
               case "MM":
                   return new MoneyMarket(profile, amount, 0);
               default:
                   return null;
           }
   }
    private String handleCommandD(String[] inputData) {
        if (inputData.length < 5) {
            return "Missing date for depositing into account.";
        }
        try {
            String accountType = inputData[1];
            String firstName = inputData[2];
            String lastName = inputData[3];
            Date dob = Date.fromString(inputData[4]);
            Profile profile = new Profile(inputData[2], inputData[3], dob);
            double amount = Double.parseDouble(inputData[5]);

            if (amount <= 0) {
                return "Deposit - amount cannot be 0 or negative.";
            }

            Account account = createDummyAccount(accountType, profile, amount);

            if (accountDatabase.contains(account)) {
                accountDatabase.deposit(account);
                return profile.toString() + "(" + accountType + ") Deposit - balance updated.";
            } else {
                return profile.toString() + "(" + accountType + ") is not in the database.";
            }
        } catch (Exception e) {
            return "Not a valid amount.";
        }
    }


    private String handleCommandC(String[] inputData) {
        try {
            if (inputData.length < 5) {
                return("Missing data for closing an account.");
            }
            String accountType = inputData[1];
            String firstName = inputData[2];
            String lastName = inputData[3];
            Date dob = Date.fromString(inputData[4]);

            if (!dob.isValid()) {return "DOB invalid: " + dob + " not a valid calendar date!";}
            if (!dob.isPresentorFutureDate()) { return "DOB invalid: " + dob + " cannot be today or a future day.";}
            if (dob.getAge() < 16) {return "DOB invalid: " + dob + " under 16.";}
            Profile profile = new Profile(inputData[2], inputData[3], dob);
            // Find the account in the database
            Account account = createDummyAccount(accountType, profile, 0);

            if (accountDatabase.close(account)) {
                return profile.toString() + " (" + accountType + ") has been closed.";
            } else
                return profile.toString() + " (" + accountType + ") not in the database.";
        } catch (Exception e) {
            return (e.toString());
        }
    }


}
