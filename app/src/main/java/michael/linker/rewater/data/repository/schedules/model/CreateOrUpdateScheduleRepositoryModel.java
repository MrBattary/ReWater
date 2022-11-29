package michael.linker.rewater.data.repository.schedules.model;

import java.util.List;

import michael.linker.rewater.data.model.unit.WaterVolumeMetricModel;
import michael.linker.rewater.data.model.unit.WateringPeriodModel;

public class CreateOrUpdateScheduleRepositoryModel {
    private final String mName;
    private final WateringPeriodModel mPeriod;
    private final WaterVolumeMetricModel mVolume;
    private final List<String> mDeviceModelIds;

    public CreateOrUpdateScheduleRepositoryModel(
            final String name,
            final WateringPeriodModel period,
            final WaterVolumeMetricModel volume,
            final List<String> deviceModelIds) {
        mName = name;
        mPeriod = period;
        mVolume = volume;
        mDeviceModelIds = deviceModelIds;
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

    public List<String> getDeviceModelIds() {
        return mDeviceModelIds;
    }
}
