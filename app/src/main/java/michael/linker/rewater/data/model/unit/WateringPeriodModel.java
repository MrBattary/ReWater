package michael.linker.rewater.data.model.unit;

import michael.linker.rewater.R;
import michael.linker.rewater.data.res.StringsProvider;

public class WateringPeriodModel implements IUnit {
    private static final String SPACE = " ";
    private final int mMouths, mDays, mHours, mMinutes;

    public WateringPeriodModel(
            final int mouths,
            final int days,
            final int hours,
            final int minutes) {
        mMouths = mouths;
        mDays = days;
        mHours = hours;
        mMinutes = minutes;
    }

    @Override
    public String formatToCompact() {
        return mMouths + SPACE + StringsProvider.getString(R.string.unit_mouths_short) + SPACE +
                mDays + SPACE + StringsProvider.getString(R.string.unit_days_short) + SPACE +
                mHours + SPACE + StringsProvider.getString(R.string.unit_hours_short) + SPACE +
                mMinutes + SPACE + StringsProvider.getString(R.string.unit_minutes_short);
    }
}
