package michael.linker.rewater.ui.advanced.devices.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;
import java.util.Objects;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;
import michael.linker.rewater.config.RepositoryConfiguration;
import michael.linker.rewater.data.repository.devices.IDevicesRepository;
import michael.linker.rewater.ui.advanced.devices.enums.AddPairNewDeviceLook;
import michael.linker.rewater.ui.advanced.devices.enums.UiRequestStatus;
import michael.linker.rewater.ui.advanced.devices.model.DeviceAfterPairingUiModel;
import michael.linker.rewater.ui.advanced.devices.model.DeviceUiRequest;

public class PairNewDeviceViewModel extends ViewModel {
    private final List<AddPairNewDeviceLook> mLookOrder;
    private final IDevicesRepository mDevicesRepository;
    private final MutableLiveData<DeviceAfterPairingUiModel> mDeviceAfterPairingUiModel;

    private final MutableLiveData<AddPairNewDeviceLook> mCurrentLook;
    private final MutableLiveData<DeviceUiRequest> mBluetoothConnected;
    private final MutableLiveData<DeviceUiRequest> mAccessKeyAccepted;
    private final MutableLiveData<DeviceUiRequest> mNetworkUpdated;

    public PairNewDeviceViewModel() {
        mLookOrder = List.of(
                AddPairNewDeviceLook.BLUETOOTH,
                AddPairNewDeviceLook.ACCESS,
                AddPairNewDeviceLook.NETWORK,
                AddPairNewDeviceLook.FINISH);
        mDevicesRepository = RepositoryConfiguration.getDevicesRepository();
        mDeviceAfterPairingUiModel = new MutableLiveData<>();

        mCurrentLook = new MutableLiveData<>();
        mBluetoothConnected = new MutableLiveData<>();
        mAccessKeyAccepted = new MutableLiveData<>();
        mNetworkUpdated = new MutableLiveData<>();

        mCurrentLook.setValue(AddPairNewDeviceLook.BLUETOOTH);
        mBluetoothConnected.setValue(new DeviceUiRequest(UiRequestStatus.UNDEFINED));
        mAccessKeyAccepted.setValue(new DeviceUiRequest(UiRequestStatus.UNDEFINED));
        mNetworkUpdated.setValue(new DeviceUiRequest(UiRequestStatus.UNDEFINED));
    }

    public LiveData<DeviceAfterPairingUiModel> getDeviceAfterPairingUiModel() {
        return mDeviceAfterPairingUiModel;
    }

    public LiveData<AddPairNewDeviceLook> getCurrentLook() {
        return mCurrentLook;
    }

    public LiveData<DeviceUiRequest> getBluetoothConnected() {
        return mBluetoothConnected;
    }

    public LiveData<DeviceUiRequest> getAccessKeyAccepted() {
        return mAccessKeyAccepted;
    }

    public LiveData<DeviceUiRequest> getNetworkUpdated() {
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
            mBluetoothConnected.setValue(new DeviceUiRequest(UiRequestStatus.UNDEFINED));
            mCurrentLook.setValue(AddPairNewDeviceLook.BLUETOOTH);
        }
        if (Objects.equals(mCurrentLook.getValue(), AddPairNewDeviceLook.NETWORK)) {
            mBluetoothConnected.setValue(new DeviceUiRequest(UiRequestStatus.UNDEFINED));
            mAccessKeyAccepted.setValue(new DeviceUiRequest(UiRequestStatus.UNDEFINED));
            mCurrentLook.setValue(AddPairNewDeviceLook.BLUETOOTH);
        }
        if (Objects.equals(mCurrentLook.getValue(), AddPairNewDeviceLook.FINISH)) {
            mNetworkUpdated.setValue(new DeviceUiRequest(UiRequestStatus.UNDEFINED));
            mCurrentLook.setValue(AddPairNewDeviceLook.NETWORK);
        }
    }

    public void setConnectedToDevice() {
        mBluetoothConnected.setValue(new DeviceUiRequest(UiRequestStatus.OK));
    }

    public void setNotConnectedToDevice() {
        mBluetoothConnected.setValue(new DeviceUiRequest(UiRequestStatus.ERROR));
    }

    public void sendProvidedDeviceHardwareId(final String hardwareId) {
        Single.fromCallable(() -> mDevicesRepository.getDeviceByHardware(hardwareId))
                .doOnSuccess(device -> {
                    // Parent schedule always null
                    mDeviceAfterPairingUiModel.postValue(
                            new DeviceAfterPairingUiModel(
                                    hardwareId,
                                    device.getId(),
                                    device.getName(),
                                    null));

                    mAccessKeyAccepted.postValue(new DeviceUiRequest(UiRequestStatus.OK));
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(repositoryModel -> {
                }, e -> mAccessKeyAccepted.postValue(new DeviceUiRequest(UiRequestStatus.ERROR)));
    }

    public void setNetworkDataUpdated() {
        mNetworkUpdated.setValue(new DeviceUiRequest(UiRequestStatus.OK));
    }

    public void setNetworkDataNotUpdated() {
        mNetworkUpdated.setValue(new DeviceUiRequest(UiRequestStatus.ERROR));
    }
}
