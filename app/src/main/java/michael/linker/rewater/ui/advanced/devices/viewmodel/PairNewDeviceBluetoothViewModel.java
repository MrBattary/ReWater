package michael.linker.rewater.ui.advanced.devices.viewmodel;

import android.bluetooth.BluetoothDevice;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import me.aflak.bluetooth.Bluetooth;
import me.aflak.bluetooth.interfaces.BluetoothCallback;
import me.aflak.bluetooth.interfaces.DeviceCallback;
import me.aflak.bluetooth.interfaces.DiscoveryCallback;
import michael.linker.rewater.config.ConnectionConfiguration;
import michael.linker.rewater.ui.advanced.devices.model.BluetoothDeviceUiModel;

public class PairNewDeviceBluetoothViewModel extends ViewModel {
    private final Bluetooth mBluetooth;
    private boolean mIsScanning;

    private final MutableLiveData<BluetoothDeviceUiModel> mConnectedDeviceData;

    public PairNewDeviceBluetoothViewModel() {
        mBluetooth = ConnectionConfiguration.getBluetooth();
        mIsScanning = false;

        mConnectedDeviceData = new MutableLiveData<>();
    }

    public LiveData<BluetoothDeviceUiModel> getConnectedDeviceData() {
        return mConnectedDeviceData;
    }

    public void setConnectedDeviceData(final BluetoothDeviceUiModel model) {
        mConnectedDeviceData.postValue(model);
    }

    public void setBluetoothCallback(final BluetoothCallback callback) {
        mBluetooth.setBluetoothCallback(callback);
    }

    public void setDiscoveryCallback(final DiscoveryCallback callback) {
        mBluetooth.setDiscoveryCallback(callback);
    }

    public void setDeviceCallback(final DeviceCallback callback) {
        mBluetooth.setDeviceCallback(callback);
    }

    public void removeCallbacks() {
        mBluetooth.removeBluetoothCallback();
        mBluetooth.removeDiscoveryCallback();
        mBluetooth.removeDeviceCallback();
    }

    public boolean isEnabled() {
        return mBluetooth.isEnabled();
    }

    public boolean isScanning() {
        return mIsScanning;
    }

    public void startScanning() {
        if (!mIsScanning) {
            mBluetooth.startScanning();
            mIsScanning = true;
        }
    }

    public void stopScanning() {
        if (mIsScanning) {
            mBluetooth.stopScanning();
            mIsScanning = false;
        }
    }

    public void onStart() {
        mBluetooth.onStart();
    }

    public void onStop() {
        mBluetooth.onStop();
    }

    public void connectToDevice(final BluetoothDevice device) {
        if (!isConnectedToDevice()) {
            mBluetooth.connectToDevice(device);
        }
    }

    public boolean isConnectedToDevice() {
        return mBluetooth.isConnected();
    }

    public void disconnectFromDevice() {
        if (isConnectedToDevice()) {
            mBluetooth.disconnect();
        }
    }
}
