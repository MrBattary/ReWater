package michael.linker.rewater.data.repository.schedules.model;

import java.util.List;

import michael.linker.rewater.data.model.unit.WaterVolumeMetricModel;
import michael.linker.rewater.data.model.unit.WateringPeriodModel;
import michael.linker.rewater.data.repository.devices.model.DeviceModel;

public class ScheduleRepositoryModel {
    private final String mId, mName;
    private final WateringPeriodModel mPeriod;
    private final WaterVolumeMetricModel mVolume;
    private final List<DeviceModel> mDeviceModels;

    public ScheduleRepositoryModel(
            final String id,
            final String name,
            final WateringPeriodModel period,
            final WaterVolumeMetricModel volume,
            final List<DeviceModel> deviceModels) {
        mId = id;
        mName = name;
        mPeriod = period;
        mVolume = volume;
        mDeviceModels = deviceModels;
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

    public List<DeviceModel> getDeviceModels() {
        return mDeviceModels;
    }
}
