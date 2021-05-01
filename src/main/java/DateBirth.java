import java.time.LocalDate;
import java.time.format.DateTimeFormatterBuilder;

/**
 * Date of Birth Class with Comparator Method
 */
public class DateBirth implements Comparable<DateBirth>{
    private String id;
    private String day;
    private String month;
    private String year;

    public DateBirth (String id, String year, String month, String day) {
        this.id = id;
        this.year = year;
        this.month = month;
        this.day = day;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    /**
     * Retrieve Date of Birth in LocalDate format
     * @return
     */
    public LocalDate getDateOfBirth() {
        String dateString  = getYear()+"-"+getMonth()+"-"+getDay();
        DateTimeFormatterBuilder builder = new DateTimeFormatterBuilder()
                .appendPattern("[yyyy-MM-dd]")
                .appendPattern("[yyyy-M-dd]")
                .appendPattern("[yyyy-M-d]");
        LocalDate date = LocalDate.parse(dateString, builder.toFormatter());
        return date;
    }

    /**
     * Retrieve Date of Birth in String to be used by ThreadPrint class
     * @return
     */
    public String getDateOfBirthString() {
        return getYear() + "  " + getMonth() + "  " + getDay();
    }

    /**
     * Comparator created to compare Date of Births
     * @param dateBirth
     * @return
     */
    @Override
    public int compareTo(DateBirth dateBirth) {
        if (dateBirth == null) {
            return 0;
        }
        return getDateOfBirth().compareTo(dateBirth.getDateOfBirth());
    }
}
