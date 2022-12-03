package michael.linker.rewater.ui.advanced.devices.model.bluetooth;

//TODO (ML): Finish this when data will be provided
public class BluetoothDeviceNetworkResponseApiModel {
    private final boolean success;

    public BluetoothDeviceNetworkResponseApiModel(boolean success) {
        this.success = success;
    }

    public boolean isSuccess() {
        return success;
    }
}
