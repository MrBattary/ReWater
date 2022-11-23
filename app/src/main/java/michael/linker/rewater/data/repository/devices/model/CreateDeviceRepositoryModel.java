package michael.linker.rewater.data.repository.devices.model;

public class CreateDeviceRepositoryModel extends UpdateDeviceRepositoryModel {
    private final String mDeviceHardwareId;

    public CreateDeviceRepositoryModel(
            final String deviceHardwareId,
            final String deviceName,
            final String parentScheduleId) {
        super(deviceName, parentScheduleId);
        mDeviceHardwareId = deviceHardwareId;
    }

    public String getDeviceHardwareId() {
        return mDeviceHardwareId;
    }
}
