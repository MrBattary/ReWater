package michael.linker.rewater.data.repository.devices.model;

public class DeviceHardwareModel {
    private final String mHardwareId;

    public DeviceHardwareModel(final String hardwareId) {
        mHardwareId = hardwareId;
    }

    public String getHardwareId() {
        return mHardwareId;
    }
}
