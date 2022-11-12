package michael.linker.rewater.data.repository.schedules.model;

import java.util.List;

import michael.linker.rewater.data.model.unit.WaterVolumeModel;
import michael.linker.rewater.data.model.unit.WateringPeriodModel;

public class CompactScheduleModel {
    private final String mId, mName;
    private final WateringPeriodModel mPeriod;
    private final WaterVolumeModel mVolume;
    private final List<String> mAttachedDevicesIds;

    public CompactScheduleModel(
            final String id,
            final String name,
            final WateringPeriodModel period,
            final WaterVolumeModel volume,
            final List<String> attachedDevicesIds) {
        mId = id;
        mName = name;
        mPeriod = period;
        mVolume = volume;
        mAttachedDevicesIds = attachedDevicesIds;
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

    public List<String> getAttachedDevicesIds() {
        return mAttachedDevicesIds;
    }
}
