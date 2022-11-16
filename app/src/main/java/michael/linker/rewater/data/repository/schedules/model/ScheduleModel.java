package michael.linker.rewater.data.repository.schedules.model;

import michael.linker.rewater.data.model.unit.WaterVolumeMetricModel;
import michael.linker.rewater.data.model.unit.WateringPeriodModel;

public class ScheduleModel {
    private final String mId, mName;
    private final WateringPeriodModel mPeriod;
    private final WaterVolumeMetricModel mVolume;

    public ScheduleModel(
            final String id,
            final String name,
            final WateringPeriodModel period,
            final WaterVolumeMetricModel volume) {
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

    public WaterVolumeMetricModel getVolume() {
        return mVolume;
    }
}
