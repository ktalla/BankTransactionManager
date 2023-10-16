package inheritanceprojectfiles;

public class MoneyMarket extends Savings{

    private int withdrawal; //number of withdrawls
    private static final double INTEREST_RATE = 4.5;

    public MoneyMarket(){

    }

    public MoneyMarket(Profile holder, double balance, int withdrawal) {
        super(holder, balance, true);
        this.withdrawal = withdrawal;
    }

    public int getWithdrawal() {
        return withdrawal;
    }

    public void setWithdrawal(int withdrawal) {
        this.withdrawal = withdrawal;
    }

    @Override
    public double monthlyInterest() {
        if(isLoyal){
            double interestRate = INTEREST_RATE+0.25;
            return (balance*(interestRate/100))/12;
        }
        else{
            return (balance*(INTEREST_RATE/100))/12;
        }
    }


    @Override
    public double monthlyFee() {
        double currentFee;
        if(balance>=2000){
            currentFee=0;
        }
        else{
            currentFee = Savings.MONTHLY_FEE;
        }
        if(withdrawal>3){
           return currentFee+10;
        }
        else return currentFee;
    }
    @Override
    public int compareTo(Account account) {
        if(account instanceof MoneyMarket){
            return super.compareTo(account);
        }
        else {
            return this.getClass().getName().compareTo(account.getClass().getName());
        }
    }

    @Override
    public String toString() {
        if (isLoyal)
            return "Money Market:: Savings:: " + holder.toString() + ":: Balance " + String.format("$%,.2f", balance) + ":: is loyal:: withdrawal:" + withdrawal;
        else
            return "Money Market:: Savings:: " + holder.toString() + ":: Balance " + String.format("$%,.2f", balance) + ":: withdrawal:" + withdrawal;
    }

    @Override
    public boolean equals(Account account) {
        if (account instanceof MoneyMarket) {
            return super.equals(account);
        } else return false;
    }
}

