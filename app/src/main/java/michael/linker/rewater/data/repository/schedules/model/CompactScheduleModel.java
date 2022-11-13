package michael.linker.rewater.data.repository.schedules.model;

import michael.linker.rewater.data.model.unit.WaterVolumeModel;
import michael.linker.rewater.data.model.unit.WateringPeriodModel;

public class CompactScheduleModel {
    private final String mId, mName;
    private final WateringPeriodModel mPeriod;
    private final WaterVolumeModel mVolume;

    public CompactScheduleModel(
            final String id,
            final String name,
            final WateringPeriodModel period,
            final WaterVolumeModel volume) {
        mId = id;
        mName = name;
        mPeriod = period;
        mVolume = volume;
    }

    public String getId() {
        return mId;
    }

    public String getName() {
        return mName;
    }

    public WateringPeriodModel getPeriod() {
        return mPeriod;
    }

    public WaterVolumeModel getVolume() {
        return mVolume;
    }
}
