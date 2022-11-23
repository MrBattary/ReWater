package michael.linker.rewater.data.repository.devices.model;

public class UpdateDeviceRepositoryModel {
    private final String mName;
    private final String mScheduleId;

    public UpdateDeviceRepositoryModel(
            final String newName,
            final String newScheduleId) {
        mName = newName;
        mScheduleId = newScheduleId;
    }

    public String getName() {
        return mName;
    }

    public String getScheduleId() {
        return mScheduleId;
    }
}
