package michael.linker.rewater.ui.advanced.devices.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;
import java.util.Objects;

import michael.linker.rewater.config.RepositoryConfiguration;
import michael.linker.rewater.data.repository.devices.IDevicesRepository;
import michael.linker.rewater.ui.advanced.devices.model.AddPairNewDeviceLook;
import michael.linker.rewater.ui.advanced.devices.model.AddPairNewDeviceRequest;
import michael.linker.rewater.ui.advanced.devices.model.RequestStatus;

public class AddPairNewDeviceViewModel extends ViewModel {
    private final List<AddPairNewDeviceLook> mLookOrder;
    private final IDevicesRepository mDevicesRepository;
    private final MutableLiveData<AddPairNewDeviceLook> mCurrentLook;
    private final MutableLiveData<AddPairNewDeviceRequest> mBluetoothConnected;
    private final MutableLiveData<AddPairNewDeviceRequest> mAccessKeyAccepted;
    private final MutableLiveData<AddPairNewDeviceRequest> mNetworkUpdated;


    public AddPairNewDeviceViewModel() {
        mLookOrder = List.of(
                AddPairNewDeviceLook.BLUETOOTH,
                AddPairNewDeviceLook.ACCESS,
                AddPairNewDeviceLook.NETWORK,
                AddPairNewDeviceLook.FINISH);
        mDevicesRepository = RepositoryConfiguration.getDevicesRepository();

        mCurrentLook = new MutableLiveData<>();
        mBluetoothConnected = new MutableLiveData<>();
        mAccessKeyAccepted = new MutableLiveData<>();
        mNetworkUpdated = new MutableLiveData<>();

        mBluetoothConnected.setValue(new AddPairNewDeviceRequest(RequestStatus.UNDEFINED));
        mAccessKeyAccepted.setValue(new AddPairNewDeviceRequest(RequestStatus.UNDEFINED));
        mNetworkUpdated.setValue(new AddPairNewDeviceRequest(RequestStatus.UNDEFINED));
        mCurrentLook.setValue(AddPairNewDeviceLook.BLUETOOTH);
    }

    public LiveData<AddPairNewDeviceLook> getCurrentLook() {
        return mCurrentLook;
    }

    public LiveData<AddPairNewDeviceRequest> getBluetoothConnected() {
        return mBluetoothConnected;
    }

    public LiveData<AddPairNewDeviceRequest> getAccessKeyAccepted() {
        return mAccessKeyAccepted;
    }

    public LiveData<AddPairNewDeviceRequest> getNetworkUpdated() {
        return mNetworkUpdated;
    }

    public void nextLook() {
        if (Objects.equals(mCurrentLook.getValue(), AddPairNewDeviceLook.FINISH)) {
            return;
        }
        int currentLookNumber = mLookOrder.indexOf(mCurrentLook.getValue());
        mCurrentLook.setValue(mLookOrder.get(currentLookNumber + 1));
    }

    public void previousLook() {
        if (Objects.equals(mCurrentLook.getValue(), AddPairNewDeviceLook.BLUETOOTH)) {
            return;
        }
        if (Objects.equals(mCurrentLook.getValue(), AddPairNewDeviceLook.ACCESS)) {
            mBluetoothConnected.setValue(new AddPairNewDeviceRequest(RequestStatus.UNDEFINED));
            mCurrentLook.setValue(AddPairNewDeviceLook.BLUETOOTH);
        }
        if (Objects.equals(mCurrentLook.getValue(), AddPairNewDeviceLook.NETWORK)) {
            mBluetoothConnected.setValue(new AddPairNewDeviceRequest(RequestStatus.UNDEFINED));
            mAccessKeyAccepted.setValue(new AddPairNewDeviceRequest(RequestStatus.UNDEFINED));
            mCurrentLook.setValue(AddPairNewDeviceLook.BLUETOOTH);
        }
        if (Objects.equals(mCurrentLook.getValue(), AddPairNewDeviceLook.FINISH)) {
            mNetworkUpdated.setValue(new AddPairNewDeviceRequest(RequestStatus.UNDEFINED));
            mCurrentLook.setValue(AddPairNewDeviceLook.NETWORK);
        }
    }

    public void connectToDevice() {
        // TODO: STUB
        mBluetoothConnected.setValue(new AddPairNewDeviceRequest(RequestStatus.OK));
    }

    public void sendKey(final String key) {
        // TODO: STUB
        if(mDevicesRepository.isDeviceCanBePaired(null)) {
            // TODO: Handle hash
            mAccessKeyAccepted.setValue(new AddPairNewDeviceRequest(RequestStatus.OK));
        }
        mAccessKeyAccepted.setValue(new AddPairNewDeviceRequest(RequestStatus.ERROR));
    }

    // TODO: Provide model
    public void updateNetworkData() {
        // TODO: STUB
        mNetworkUpdated.setValue(new AddPairNewDeviceRequest(RequestStatus.OK));
    }
}
