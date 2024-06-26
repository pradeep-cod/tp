package activeedge.userdetails;

public class LogBMI extends UserDetails {

    private String date;
    private String time;

    public LogBMI(Integer value, String date, String time) {
        super(value);
        this.date = date;
        this.time = time;
    }

    @Override
    public String toString() {
        return "BMI " + getValue() + " (Recorded on: " + date + " " + time + ")";
    }
}

