package michael.linker.rewater.data.model.unit;

import michael.linker.rewater.R;
import michael.linker.rewater.data.res.StringsProvider;

public class WateringPeriodModel implements IUnit {
    private static final int MIN_WATERING_PERIOD_IN_MIN = 1;
    private static final String SPACE = " ";
    private final Integer mDays, mHours, mMinutes;

    public WateringPeriodModel(
            final int days,
            final int hours,
            final int minutes) {
        mDays = days;
        mHours = hours;
        mMinutes = minutes;
    }

    public Integer getDays() {
        return mDays;
    }

    public Integer getHours() {
        return mHours;
    }

    public Integer getMinutes() {
        return mMinutes;
    }

    @Override
    public String formatToCompact() {
        return mDays + StringsProvider.getString(R.string.unit_days_short) + SPACE +
                mHours + StringsProvider.getString(R.string.unit_hours_short) + SPACE +
                mMinutes + StringsProvider.getString(R.string.unit_minutes_short);
    }

    @Override
    public boolean isDataCorrect() {
        final int wateringPeriodMin = (mDays * 24 * 60) + (mHours * 60) + mMinutes;
        return wateringPeriodMin < MIN_WATERING_PERIOD_IN_MIN;
    }
}
