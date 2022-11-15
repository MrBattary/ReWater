package michael.linker.rewater.data.repository.devices.model;

public class ShortDeviceModel {
    private final String mName;
    private final String mNewParentScheduleId;

    public ShortDeviceModel(
            final String name,
            final String newParentScheduleId) {
        mName = name;
        mNewParentScheduleId = newParentScheduleId;
    }

    public String getName() {
        return mName;
    }

    public String getNewParentScheduleId() {
        return mNewParentScheduleId;
    }
}
