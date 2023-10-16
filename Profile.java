package inheritanceprojectfiles;

/**
 * Represents account user's information
 * @author Keerthana Talla
 * @author Ishani Mhatre
 */
public class Profile implements Comparable<Profile>{
    private String fname; //first name
    private String lname; //last name
    private Date dob; //date of birth

    public static final int SAME_PROFILE = 0;

    /**
     * Default constructor
     */
    public Profile() {
        // Leave values as null
    }

    /**
     * Constructor to access properties of Profile
     * @param fname String first name
     * @param lname String last name
     * @param dob Date object of date of birth
     */
    public Profile(String fname, String lname, Date dob) {
        this.fname = fname;
        this.lname = lname;
        this.dob = dob;
    }

    /**
     * Getter method for first name
     * @return account holder first name
     */
    public String getFirstName() {
        return fname;
    }

    /**
     * Getter method for last name
     * @return account holder last name
     */
    public String getLastName() {
        return lname;
    }

    /**
     * Getter method for date of birth
     * @return account holder date of birth
     */
    public Date getDateOfBirth() {
        return dob;
    }

    /**
     * Implement CompareTo method for two profiles ranked by last name, first name, then date of birth.
     * @return result of comparison of two Profiles
     */
    @Override
    public int compareTo(Profile profile) {
        if(profile.lname.equalsIgnoreCase(this.lname)){
            if(profile.fname.equalsIgnoreCase(this.fname)){
                if(profile.dob.equals(this.dob)){
                    return SAME_PROFILE;
                }
                else{
                    return profile.dob.compareTo(this.dob);
                }
            }
            else{
                return profile.fname.compareTo(this.fname);
            }
        }
        else{
            return profile.lname.compareTo(this.lname);
        }
    }

    /**
     * Overrides method in Object class and outputs the description of an account holder.
     * @return String
     */
    @Override
        public String toString(){
        return this.fname + " " + this.lname + " " + this.dob;
    }
}
