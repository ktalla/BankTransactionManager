package inheritanceprojectfiles;

public class CollegeChecking extends Checking{

    private Campus campus; //campus code
    public CollegeChecking(Profile holder, double balance, Campus campus){
        super(holder, balance);
        this.campus = campus;
    }

    @Override
    public double monthlyFee() {
        return 0.0;
    }

    @Override
    public int compareTo(Account account) {
        if(account instanceof CollegeChecking){
            //System.out.println(account.getClass().getName());
            return super.compareTo(account);
        }
        else {
            return this.getClass().getName().compareTo(account.getClass().getName());
        }
    }

    @Override
    public String toString(){
        return "College Checking:: " +holder.toString() + ":: Balance " + String.format("$%,.2f", balance) + "::" + campus.name();
    }
    @Override
    public boolean equals(Account account) {
        if (account instanceof CollegeChecking) {
            return super.equals(account);
        } else return false;
    }
}
