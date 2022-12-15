package michael.linker.rewater.ui.advanced.devices.model.bluetooth;

//TODO (ML): Finish this when data will be provided
public class BluetoothDeviceNetworkApiModel {
    private final String mWifiSsid, mWifiPassword;

    public BluetoothDeviceNetworkApiModel(String wifiSsid, String wifiPassword) {
        mWifiSsid = wifiSsid;
        mWifiPassword = wifiPassword;
    }

    public String getWifiSsid() {
        return mWifiSsid;
    }

    public String getWifiPassword() {
        return mWifiPassword;
    }
}
