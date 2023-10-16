package inheritanceprojectfiles;

/**
 * Subclass Savings account of Account
 * @author Keerthana Talla
 * @author Ishani Mhatre
 */
public class Savings extends Account{
    protected boolean isLoyal; //loyal customer status
    private static final double INTEREST_RATE = 4.0;
    protected static final int MONTHLY_FEE = 25;

    /**
     * Default constructor
     */
    public Savings(){

    }

    /**
     * Parameterized constructor
     * @param holder Profile object that contains account holder's information
     * @param balance double that contains Savings account balance
     * @param isLoyal loyalty status of account holder
     */
    public Savings(Profile holder, double balance, boolean isLoyal){
        super(holder, balance);
        this.isLoyal = isLoyal;
    }

    /**
     * Getter method for isLoyal
     * @return boolean that determines holder's loyalty
     */
    public boolean isLoyal() {
        return isLoyal;
    }

    /**
     * Setter method for isLoyal
     * @param loyal status isLoyal should be set to
     */
    public void setLoyal(boolean loyal) {
        isLoyal = loyal;
    }

    /**
     * Calculates and prints monthly interest rate
     * @return monthly interest rate
     */
    @Override
    public double monthlyInterest() {
        if(isLoyal()){
            double interestRate = INTEREST_RATE+0.25;
            return (balance*(interestRate/100))/12;
        }
        else
            return (balance*(INTEREST_RATE/100))/12;
    }

    /**
     *
     * @return
     */
    @Override
    public double monthlyFee() {
        if(balance>=500){
            return 0;
        }
        else{
            return MONTHLY_FEE;
        }
    }

    @Override
    public int compareTo(Account account) {
        if(account instanceof Savings && !(account instanceof MoneyMarket)){
            return super.compareTo(account);
        }
        else if(account instanceof MoneyMarket && this instanceof MoneyMarket){
            return super.compareTo(account);
        }
        else {
            //System.out.println("Comparing " + account.getClass().getName() + " and " + this.getClass().getName());
            return this.getClass().getName().compareTo(account.getClass().getName()); //this should be called
        }
    }

    @Override
    public String toString(){
        if(isLoyal)
            return "Savings:: " +holder.toString() + ":: Balance " + String.format("$%,.2f", balance) + ":: is loyal";
        else
            return "Savings:: " +holder.toString() + ":: Balance " + String.format("$%,.2f", balance);
    }

    @Override
    public boolean equals(Account account) {
        if (account instanceof Savings) {
            return super.equals(account);
        } else return false;
    }
}
