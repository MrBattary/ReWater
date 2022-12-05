package michael.linker.rewater.ui.advanced.devices.viewmodel;

import android.bluetooth.BluetoothDevice;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.nio.charset.StandardCharsets;

import me.aflak.bluetooth.Bluetooth;
import me.aflak.bluetooth.interfaces.BluetoothCallback;
import me.aflak.bluetooth.interfaces.DeviceCallback;
import me.aflak.bluetooth.interfaces.DiscoveryCallback;
import michael.linker.rewater.config.ConnectionConfiguration;
import michael.linker.rewater.ui.advanced.devices.model.bluetooth.BluetoothDeviceKeyApiModel;
import michael.linker.rewater.ui.advanced.devices.model.BluetoothDeviceUiModel;
import michael.linker.rewater.ui.advanced.devices.model.bluetooth.BluetoothDeviceKeyResponseApiModel;
import michael.linker.rewater.ui.advanced.devices.model.bluetooth.BluetoothDeviceNetworkApiModel;
import michael.linker.rewater.ui.advanced.devices.model.bluetooth.BluetoothDeviceNetworkResponseApiModel;

public class PairNewDeviceBluetoothViewModel extends ViewModel {
    private final Gson mGson;
    private final Bluetooth mBluetooth;
    private boolean mIsScanning;

    private final MutableLiveData<BluetoothDeviceUiModel> mBluetoothDeviceData;
    private BluetoothDevice mConnectableBluetoothDevice;

    public PairNewDeviceBluetoothViewModel() {
        mGson = new Gson();
        mBluetooth = ConnectionConfiguration.getBluetooth();
        mIsScanning = false;

        mBluetoothDeviceData = new MutableLiveData<>();
        mConnectableBluetoothDevice = null;
    }

    public BluetoothDevice getConnectableBluetoothDevice() {
        return mConnectableBluetoothDevice;
    }

    public LiveData<BluetoothDeviceUiModel> getBluetoothDeviceData() {
        return mBluetoothDeviceData;
    }

    public void setConnectableBluetoothDevice(BluetoothDevice connectableBluetoothDevice) {
        mConnectableBluetoothDevice = connectableBluetoothDevice;
    }

    public void setBluetoothDeviceData(final BluetoothDeviceUiModel model) {
        mBluetoothDeviceData.postValue(model);
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
        if (!isScanning()) {
            mBluetooth.startScanning();
            mIsScanning = true;
        }
    }

    public void stopScanning() {
        if (isScanning()) {
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

    public void sendKey(final BluetoothDeviceKeyApiModel keyModel)
            throws PairNewDeviceViewModelNotFoundException {
        this.sendGsonData(mGson.toJson(keyModel));
    }

    public BluetoothDeviceKeyResponseApiModel getKeyResponse(final byte[] message)
            throws PairNewDeviceViewModelNotFoundException {
        return getResponseFromGson(message, BluetoothDeviceKeyResponseApiModel.class);
    }

    public void sendNetworkSettings(BluetoothDeviceNetworkApiModel networkModel)
            throws PairNewDeviceViewModelNotFoundException {
        this.sendGsonData(mGson.toJson(networkModel));
    }

    public BluetoothDeviceNetworkResponseApiModel getNetworkSettingsResponse(final byte[] message)
            throws PairNewDeviceViewModelNotFoundException {
        return getResponseFromGson(message, BluetoothDeviceNetworkResponseApiModel.class);
    }

    private void sendGsonData(final String objectAsGsonString)
            throws PairNewDeviceViewModelNotFoundException {
        if (isConnectedToDevice()) {
            mBluetooth.send(objectAsGsonString, StandardCharsets.UTF_8);
        } else {
            throw new PairNewDeviceViewModelNotFoundException("No bluetooth connection found!");
        }
    }

    private <T> T getResponseFromGson(final byte[] response, Class<T> classOfT)
            throws PairNewDeviceViewModelNotFoundException {
        try {
            return mGson.fromJson(new String(response), classOfT);
        } catch (JsonSyntaxException e) {
            throw new PairNewDeviceViewModelNotFoundException("The object was not recognized!");
        }
    }
}
