package michael.linker.rewater.data.web.api.model;

public class PeriodPart {
    private final Integer days;
    private final Integer hours;
    private final Integer minutes;

    public PeriodPart(Integer days, Integer hours, Integer minutes) {
        this.days = days;
        this.hours = hours;
        this.minutes = minutes;
    }

    public Integer getDays() {
        return days;
    }

    public Integer getHours() {
        return hours;
    }

    public Integer getMinutes() {
        return minutes;
    }
}
