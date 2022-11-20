package michael.linker.rewater.ui.advanced.schedules.model;

import java.util.List;

import michael.linker.rewater.data.model.unit.WaterVolumeMetricModel;
import michael.linker.rewater.data.model.unit.WateringPeriodModel;
import michael.linker.rewater.data.repository.devices.model.DeviceModel;
import michael.linker.rewater.data.repository.schedules.model.ScheduleRepositoryModel;

public class ScheduleUiModel {
    private final String mId, mName;
    private final WateringPeriodModel mPeriod;
    private final WaterVolumeMetricModel mVolume;
    private final List<DeviceModel> mDeviceModels;

    public ScheduleUiModel(
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

    public ScheduleUiModel(final ScheduleRepositoryModel repositoryModel) {
        mId = repositoryModel.getId();
        mName = repositoryModel.getName();
        mPeriod = repositoryModel.getPeriod();
        mVolume = repositoryModel.getVolume();
        mDeviceModels = repositoryModel.getDeviceModels();
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
