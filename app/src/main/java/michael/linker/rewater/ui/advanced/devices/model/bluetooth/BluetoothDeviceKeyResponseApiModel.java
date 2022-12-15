package michael.linker.rewater.ui.advanced.devices.model.bluetooth;

// TODO (ML): Finish this when data will be provided
public class BluetoothDeviceKeyResponseApiModel {
    private final String hardwareId;
    private final boolean success;

    public BluetoothDeviceKeyResponseApiModel(String hardwareId, boolean success) {
        this.hardwareId = hardwareId;
        this.success = success;
    }

    public String getHardwareId() {
        return hardwareId;
    }

    public boolean isSuccess() {
        return success;
    }
}
