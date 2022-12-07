package michael.linker.rewater.data.web.api.part;

import michael.linker.rewater.data.model.unit.WateringPeriodModel;

public class PeriodPart {
    private final Integer days;
    private final Integer hours;
    private final Integer minutes;

    public PeriodPart(Integer days, Integer hours, Integer minutes) {
        this.days = days;
        this.hours = hours;
        this.minutes = minutes;
    }

    public PeriodPart(WateringPeriodModel periodModel) {
        days = periodModel.getDays();
        hours = periodModel.getHours();
        minutes = periodModel.getMinutes();
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
