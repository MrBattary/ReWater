package michael.linker.rewater.data.repository.devices.model;

public class AddDeviceModel extends ShortDeviceModel {
    private final String mDeviceHardwareId;

    public AddDeviceModel(
            final String deviceId,
            final String parentScheduleId,
            final String deviceHardwareId) {
        super(deviceId, parentScheduleId);
        mDeviceHardwareId = deviceHardwareId;
    }

    public String getDeviceHardwareId() {
        return mDeviceHardwareId;
    }
}
