package michael.linker.rewater.data.web.model;

import java.util.List;

public class FullScheduleModel {
    private final String mId, mName;
    private final Integer mMouthsPeriod, mDaysPeriod, mHoursPeriod, mMinutesPeriod;
    private final Integer mLiters, mMilliliters;
    private final List<String> mAttachedDevicesIds;

    public FullScheduleModel(
            final String id,
            final String name,
            final Integer mouthsPeriod,
            final Integer daysPeriod,
            final Integer hoursPeriod,
            final Integer minutesPeriod,
            final Integer liters,
            final Integer milliliters,
            final List<String> attachedDevicesIds) {
        mId = id;
        mName = name;
        mMouthsPeriod = mouthsPeriod;
        mDaysPeriod = daysPeriod;
        mHoursPeriod = hoursPeriod;
        mMinutesPeriod = minutesPeriod;
        mLiters = liters;
        mMilliliters = milliliters;
        mAttachedDevicesIds = attachedDevicesIds;
    }

    public String getId() {
        return mId;
    }

    public String getName() {
        return mName;
    }
}
