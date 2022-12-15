package michael.linker.rewater.data.repository.schedules.model;

import java.util.List;
import java.util.stream.Collectors;

import michael.linker.rewater.data.model.unit.WaterVolumeMetricModel;
import michael.linker.rewater.data.model.unit.WateringPeriodModel;
import michael.linker.rewater.data.repository.devices.model.DeviceWithoutParentsRepositoryModel;
import michael.linker.rewater.data.web.api.schedules.response.GetScheduleResponse;

public class ScheduleRepositoryModel {
    private final String mId;
    private final String mName;
    private final String mParentNetworkId;
    private final WateringPeriodModel mPeriod;
    private final WaterVolumeMetricModel mVolume;
    private final List<DeviceWithoutParentsRepositoryModel> mDeviceWithoutParentsRepositoryModels;

    public ScheduleRepositoryModel(final GetScheduleResponse response) {
        mId = response.getId();
        mName = response.getName();
        mParentNetworkId = response.getNetworkId();
        mPeriod = new WateringPeriodModel(response.getPeriod());
        mVolume = new WaterVolumeMetricModel(response.getVolume());
        mDeviceWithoutParentsRepositoryModels = response.getDevices().stream()
                .map(DeviceWithoutParentsRepositoryModel::new)
                .collect(Collectors.toList());
    }

    public ScheduleRepositoryModel(
            final String id,
            final String name,
            final String parentNetworkId,
            final WateringPeriodModel period,
            final WaterVolumeMetricModel volume,
            final List<DeviceWithoutParentsRepositoryModel> deviceWithoutParentsRepositoryModels) {
        mId = id;
        mName = name;
        mParentNetworkId = parentNetworkId;
        mPeriod = period;
        mVolume = volume;
        mDeviceWithoutParentsRepositoryModels = deviceWithoutParentsRepositoryModels;
    }

    public String getId() {
        return mId;
    }

    public String getName() {
        return mName;
    }

    public String getParentNetworkId() {
        return mParentNetworkId;
    }

    public WateringPeriodModel getPeriod() {
        return mPeriod;
    }

    public WaterVolumeMetricModel getVolume() {
        return mVolume;
    }

    public List<DeviceWithoutParentsRepositoryModel> getDeviceModels() {
        return mDeviceWithoutParentsRepositoryModels;
    }
}
