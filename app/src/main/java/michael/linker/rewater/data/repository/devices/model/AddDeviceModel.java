package michael.linker.rewater.data.repository.devices.model;

public class AddDeviceModel extends ShortDeviceModel {
    private final String mId;
    private final String mDeviceHardwareId;

    public AddDeviceModel(
            final String id,
            final String deviceHardwareId,
            final String deviceName,
            final String parentScheduleId) {
        super(deviceName, parentScheduleId);
        mId = id;
        mDeviceHardwareId = deviceHardwareId;
    }

    public String getDeviceHardwareId() {
        return mDeviceHardwareId;
    }

    public String getId() {
        return mId;
    }
}
