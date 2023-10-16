package inheritanceprojectfiles;

import java.util.Calendar;

public class Date implements Comparable<Date> {

    /**
     * default constructor
     */
    public Date () {
    }

    // days in a month for the whole year
    public static final int daysinMonth_1 = 30; //max days first value
    public static final int daysinMonth_2 = 31; //max days second value
    public static final int febRegular = 28; //max for non leap year
    public static final int febLeapYear = 29; //max for leap year
    // 12 cases for each month
    public static final int JAN = 1;
    public static final int FEB = 2;
    public static final int MARCH = 3;
    public static final int APRIL = 4;
    public static final int MAY = 5;
    public static final int JUNE = 6;
    public static final int JULY = 7;
    public static final int AUGUST = 8;
    public static final int SEPT = 9;
    public static final int OCT = 10;
    public static final int NOV = 11;
    public static final int DEC = 12;

    // integer values for checking Leap Year
    public static final int QUADRENNIAL = 4;
    public static final int CENTENNIAL = 100;
    public static final int QUATERCENTENNIAL = 400;

    // variables for upper and lower bounds of valid Dates
    public static final int LOWERBOUND = 1; //cannot be negative
    public static final int COUNTOFMONTHSINYEAR = 12; //total months

    private int year;
    private int month;
    private int day;

    public Date(int year, int month, int day) {
        this.year = year;
        this.month = month;
        this.day = day;
    }

    /**
     * Calls upon three methods including withinBounds, isFutureDate, and notMoreThanSixMonths to test if Date input is valid
     * @return true if valid, false is not
     */
    public boolean isValid() {

        if (!withinBounds()) {
            return false;
        }

        return true;
    }


    /**
     * Finding the upper bound for max days in a month
     * @return int for Max days in a month
     */
    private int calculateMaxDaysInMonth() {
        switch (month) {
            case JAN: return daysinMonth_2;
            case FEB: return isLeapYear() ? febLeapYear : febRegular;
            case MARCH: return daysinMonth_2;
            case APRIL: return daysinMonth_1;
            case MAY: return daysinMonth_2;
            case JUNE: return daysinMonth_1;
            case JULY: return daysinMonth_2;
            case AUGUST: return daysinMonth_2;
            case SEPT: return daysinMonth_1;
            case OCT: return daysinMonth_2;
            case NOV: return daysinMonth_1;
            case DEC: return daysinMonth_2;
            default: return 0;
        }
    }

    /**
     * Method for checking date if lear year
     * @return true if Leap Year and false is not
     */
    private boolean isLeapYear() {
        return (year % QUADRENNIAL == 0 && year % CENTENNIAL != 0) || (year % QUATERCENTENNIAL == 0);
    }

    /**
     * Method to convert from string to date object
     * @param str input string for Date
     * @return Date object with year, month, and day
     */
    public static Date fromString(String str) {
        String[] parts = str.split("/");
        int year = Integer.parseInt(parts[2]);
        int month = Integer.parseInt(parts[0]);
        int day = Integer.parseInt(parts[1]);
        return new Date(year, month, day);
    }

    /**
     * Method to check if Date input is not negative and month is not more than 12
     * @return true if valid and false if not
     */
    public boolean withinBounds() {
        if (year < LOWERBOUND || month < LOWERBOUND || month > COUNTOFMONTHSINYEAR || day < LOWERBOUND) {
            return false;
        }
        int maxDaysInMonth = calculateMaxDaysInMonth();
        if (day > maxDaysInMonth) {
            return false;
        }
        return true;
    }

   public int getAge(){
       Calendar birthDateCalendar = Calendar.getInstance();
       birthDateCalendar.set(this.getYear(), this.getMonth() - 1, this.getDay());
       Calendar today = Calendar.getInstance();
       int age = today.get(Calendar.YEAR) - birthDateCalendar.get(Calendar.YEAR);

       if (today.get(Calendar.MONTH) < birthDateCalendar.get(Calendar.MONTH) ||
               (today.get(Calendar.MONTH) == birthDateCalendar.get(Calendar.MONTH) &&
                       today.get(Calendar.DAY_OF_MONTH) < birthDateCalendar.get(Calendar.DAY_OF_MONTH))) {
           age--;
       }
       return age;
   }

        /**
         * Check that Date is in the future, not past
         * @return true if Date set in the future, false if not
         */
        boolean isPresentorFutureDate() {
            Calendar today = Calendar.getInstance();
            Calendar eventDate = Calendar.getInstance();
            eventDate.set(this.getYear(), this.getMonth(), this.getDay());

            if(eventDate.after(today) || (eventDate.equals(today))) {
                return false;
            }

            return true;

        }


    /**
     * checking  date object with current year, month, day
     * @param date for date object
     * @return less than 0, equal to, or greater than 0 based on compareTo
     */
    @Override
    public int compareTo(Date date) {
        if (this.year != date.year) {
            return Integer.compare(this.year, date.year);
        }
        if (this.month != date.month) {
            return Integer.compare(this.month, date.month);
        }
        return Integer.compare(this.day, date.day);
    }

    /**
     * Check if object equal to date instance
     * @param obj created  object to compare instance of date object
     * @return true if Leap Year and false is not
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Date) {
            Date date = (Date) obj;
            return this.year == date.year && this.month == date.month && this.day == date.day;
        }
        return false;
    }
    @Override
    public String toString() {
        return String.format("%-1d/%-1d/%-1d", month, day, year);
    }

    /**
     * Testbed to exercise the isValid method
     * @param args command line arguments
     */
    public static void main(String[] args) {
        // Implement test cases to thoroughly test the isValid() method.
        testMonthOutOfRange();
        testYearOutOfRange();
        testDayOutOfRange();
        futureDateOutOfRange();
        testDaysInFeb_NonLeap();
        negativeMonthVal();
        negativeDayVal();
        testNegativeYear();
        zeroVal();

        testDaysInFeb_Leap();
        correct_testDaysInFeb_nonLeap();
        correctInput();
    }

    /**
     * Test case #1
     * Implement test cases for months out of range
     */
    private static void testMonthOutOfRange() {
        Date date = new Date(2023, 13, 15);
        boolean expectedOutput = false;
        boolean actualOutput = date.isValid();
        testResult(date, expectedOutput, actualOutput);
    }

    /**
     * Test case #2
     * Implement test cases for years out of range and from the past
     */
    private static void testYearOutOfRange() {
        Date date = new Date(2000, 11, 15);
        boolean expectedOutput = false;
        boolean actualOutput = date.isValid();
        testResult(date, expectedOutput, actualOutput);
    }

    /**
     * Test case #3
     * Implement test cases for days out of range.
     */
    private static void testDayOutOfRange() {
        Date date = new Date(2023, 11, 35);
        boolean expectedOutput = false;
        boolean actualOutput = date.isValid();
        testResult(date, expectedOutput, actualOutput);
    }

    /**
     * Test case #4
     * Implement test cases for years out of range of six months into the future.
     */
    private static void futureDateOutOfRange() {
        Date date = new Date(2027, 10, 21);
        boolean expectedOutput = false;
        boolean actualOutput = date.isValid();
        testResult(date, expectedOutput, actualOutput);
    }

    /**
     * Test case #5
     * Implement test cases for February in non-leap years.
     */
    private static void testDaysInFeb_NonLeap() {
        Date date = new Date(2023, 02, 29);
        boolean expectedOutput = false;
        boolean actualOutput = date.isValid();
        testResult(date, expectedOutput, actualOutput);
    }

    /**
     * Test case #6
     * Implement test cases for negative value for Month
     */
    private static void negativeMonthVal() {
        Date date = new Date(2024, -1, 25);
        boolean expectedOutput = false;
        boolean actualOutput = date.isValid();
        testResult(date, expectedOutput, actualOutput);
    }

    /**
     * Test case #7
     * Implement test cases for negative value for Day
     */
    private static void negativeDayVal() {
        Date date = new Date(2023, 12, -2);
        boolean expectedOutput = false;
        boolean actualOutput = date.isValid();
        testResult(date, expectedOutput, actualOutput);
    }


    /**
     * test case #8
     * Implement test cases for negative year
     */
    private static void testNegativeYear() {
        Date date = new Date(-1, 12, 31);
        boolean expectedOutput = false;
        boolean actualOutput = date.isValid();
        testResult(date, expectedOutput, actualOutput);
    }

    /**
     * Test case #9
     * Implement test cases for values of 0
     */
    private static void zeroVal() {
        Date date = new Date(2023, 11, 0);
        boolean expectedOutput = false;
        boolean actualOutput = date.isValid();
        testResult(date, expectedOutput, actualOutput);
    }

    /**
     * Test case #10
     * Implement test to check maximum value for February in leap year.
     */
    private static void testDaysInFeb_Leap() {
        Date date = new Date(02,29,2024);
        boolean expectedOutput = true;
        boolean actualOutput = date.isValid();
        testResult(date, expectedOutput, actualOutput);
    }

    /**
     * Test case #11
     * Implement test to check maximum value for February in non leap year.
     */
    private static void correct_testDaysInFeb_nonLeap() {
        Date date = new Date(02,28,2023);
        boolean expectedOutput = true;
        boolean actualOutput = date.isValid();
        testResult(date, expectedOutput, actualOutput);
    }


    /**
     * Test case #12
     * Implement test for valid date input
     */
    private static void correctInput() { //valid test case 2
        Date date = new Date(10, 20, 2023);
        boolean expectedOutput = false;
        boolean actualOutput = date.isValid();
        testResult(date, expectedOutput, actualOutput);
    }

    /**
     * Method that converts the date to a string for valid or not valid and prints result. Assert to check if expected is the same as actual
     * @param date valid date input
     * @param expectedOutput expected date output
     * @param actualOutput actual output
     */
    private static void testResult(Date date, boolean expectedOutput, boolean actualOutput) {
        String result = date.toString() + " is " + (actualOutput ? "valid" : "invalid");
        System.out.println(result);
        assert expectedOutput == actualOutput : "Test failed for " + date.toString();
    }

    /**
     * Getter method for year
     * @return year
     */
    public int getYear() {
        return year;
    }

    /**
     * Getter method for month
     * @return month
     */
    public int getMonth() {
        return month;
    }

    /**
     * Getter method for Date
     * @return day
     */
    public int getDay() {
        return day;
    }
}
