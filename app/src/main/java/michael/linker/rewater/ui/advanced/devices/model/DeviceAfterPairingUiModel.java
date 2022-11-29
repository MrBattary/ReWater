package michael.linker.rewater.ui.advanced.devices.model;

public class DeviceAfterPairingUiModel {
    private final String mHardwareId, mDeviceId, mDeviceName, mParentScheduleId;

    public DeviceAfterPairingUiModel(
            final String hardwareId,
            final String deviceId,
            final String deviceName,
            final String parentScheduleId) {
        mHardwareId = hardwareId;
        mDeviceId = deviceId;
        mDeviceName = deviceName;
        mParentScheduleId = parentScheduleId;
    }

    public String getHardwareId() {
        return mHardwareId;
    }

    public String getDeviceId() {
        return mDeviceId;
    }

    public String getDeviceName() {
        return mDeviceName;
    }

    public String getParentScheduleId() {
        return mParentScheduleId;
    }
}
