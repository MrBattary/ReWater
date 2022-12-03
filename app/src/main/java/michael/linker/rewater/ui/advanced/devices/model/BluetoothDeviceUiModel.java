package michael.linker.rewater.ui.advanced.devices.model;

import androidx.annotation.NonNull;

public class BluetoothDeviceUiModel {
    private final String mName, mMac;

    public BluetoothDeviceUiModel(final String name, @NonNull final String mac) {
        mName = name;
        mMac = mac;
    }

    public String getName() {
        return mName;
    }

    public String getMac() {
        return mMac;
    }

    @NonNull
    @Override
    public String toString() {
        return mName == null ? mMac : mName;
    }
}
