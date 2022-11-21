package michael.linker.rewater.data.repository.schedules.model;

import java.util.List;

import michael.linker.rewater.data.model.unit.WaterVolumeMetricModel;
import michael.linker.rewater.data.model.unit.WateringPeriodModel;

public class CreateUpdateScheduleRepositoryModel {
    private final String mNetworkId, mName;
    private final WateringPeriodModel mPeriod;
    private final WaterVolumeMetricModel mVolume;
    private final List<String> mDeviceModelIds;

    public CreateUpdateScheduleRepositoryModel(
            final String networkId,
            final String name,
            final WateringPeriodModel period,
            final WaterVolumeMetricModel volume,
            final List<String> deviceModelIds) {
        mNetworkId = networkId;
        mName = name;
        mPeriod = period;
        mVolume = volume;
        mDeviceModelIds = deviceModelIds;
    }

    public String getNetworkId() {
        return mNetworkId;
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
