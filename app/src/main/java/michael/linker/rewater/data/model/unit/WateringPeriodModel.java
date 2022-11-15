package michael.linker.rewater.data.model.unit;

import michael.linker.rewater.R;
import michael.linker.rewater.data.res.StringsProvider;

public class WateringPeriodModel implements IUnit {
    private static final String SPACE = " ";
    private final int mDays, mHours, mMinutes;

    public WateringPeriodModel(
            final int days,
            final int hours,
            final int minutes) {
        mDays = days;
        mHours = hours;
        mMinutes = minutes;
    }

    @Override
    public String formatToCompact() {
        return mDays + StringsProvider.getString(R.string.unit_days_short) + SPACE +
                mHours + StringsProvider.getString(R.string.unit_hours_short) + SPACE +
                mMinutes + StringsProvider.getString(R.string.unit_minutes_short);
    }
}
