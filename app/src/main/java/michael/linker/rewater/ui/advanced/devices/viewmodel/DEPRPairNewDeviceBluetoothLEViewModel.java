package michael.linker.rewater.ui.advanced.devices.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.polidea.rxandroidble3.RxBleClient;
import com.polidea.rxandroidble3.exceptions.BleScanException;
import com.polidea.rxandroidble3.scan.ScanFilter;
import com.polidea.rxandroidble3.scan.ScanResult;
import com.polidea.rxandroidble3.scan.ScanSettings;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.Disposable;
import michael.linker.rewater.R;
import michael.linker.rewater.config.ConnectionConfiguration;
import michael.linker.rewater.data.res.StringsProvider;
import michael.linker.rewater.ui.advanced.devices.enums.UiRequestStatus;
import michael.linker.rewater.ui.advanced.devices.model.DeviceUiRequest;

public class DEPRPairNewDeviceBluetoothLEViewModel extends ViewModel {
    private final RxBleClient mRxBleClient;
    private Disposable mScanDisposable;

    private final MutableLiveData<ScanResult> mScanResult;
    private final MutableLiveData<DeviceUiRequest> mScanStatus;

    public DEPRPairNewDeviceBluetoothLEViewModel() {
        mRxBleClient = ConnectionConfiguration.getRxBleClient();

        mScanResult = new MutableLiveData<>();
        mScanStatus = new MutableLiveData<>();
        mScanStatus.setValue(new DeviceUiRequest(UiRequestStatus.UNDEFINED));
    }

    public MutableLiveData<ScanResult> getScanResult() {
        return mScanResult;
    }

    public MutableLiveData<DeviceUiRequest> getScanStatus() {
        return mScanStatus;
    }

    public void startDeviceScan() {
        mScanDisposable = mRxBleClient.scanBleDevices(
                        new ScanSettings.Builder()
                                .setScanMode(ScanSettings.SCAN_MODE_BALANCED)
                                .setCallbackType(ScanSettings.CALLBACK_TYPE_ALL_MATCHES)
                                .build(),
                        new ScanFilter.Builder().build())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(this::stopDeviceScan)
                .subscribe(scanResult -> {
                            mScanResult.postValue(scanResult);
                            mScanStatus.postValue(new DeviceUiRequest(UiRequestStatus.OK));
                        },
                        throwable -> {
                            if (throwable instanceof BleScanException) {
                                switch (((BleScanException) throwable).getReason()) {
                                    case BleScanException.BLUETOOTH_NOT_AVAILABLE:
                                        mScanStatus.postValue(
                                                new DeviceUiRequest(UiRequestStatus.ERROR,
                                                        StringsProvider.getString(
                                                                R.string.bluetooth_failure_device_not_support)));
                                        break;
                                    case BleScanException.BLUETOOTH_DISABLED:
                                        mScanStatus.postValue(
                                                new DeviceUiRequest(UiRequestStatus.ERROR,
                                                        StringsProvider.getString(
                                                                R.string.bluetooth_failure_turned_off)));
                                        break;
                                    case BleScanException.LOCATION_PERMISSION_MISSING:
                                        mScanStatus.postValue(
                                                new DeviceUiRequest(UiRequestStatus.ERROR,
                                                        StringsProvider.getString(
                                                                R.string.bluetooth_failure_permission_location_not_found)));
                                        break;
                                    case BleScanException.LOCATION_SERVICES_DISABLED:
                                        mScanStatus.postValue(
                                                new DeviceUiRequest(UiRequestStatus.ERROR,
                                                        StringsProvider.getString(
                                                                R.string.bluetooth_failure_permission_location_services_not_found)));
                                        break;
                                    default:
                                        mScanStatus.postValue(
                                                new DeviceUiRequest(UiRequestStatus.ERROR,
                                                        StringsProvider.getString(
                                                                R.string.bluetooth_failure_scan)));
                                        break;
                                }
                            } else {
                                mScanStatus.postValue(
                                        new DeviceUiRequest(UiRequestStatus.ERROR,
                                                StringsProvider.getString(
                                                        R.string.bluetooth_failure_scan)));
                            }
                        });
    }

    public boolean isScanning() {
        return mScanDisposable != null;
    }

    public void stopDeviceScan() {
        mScanDisposable.dispose();
        mScanDisposable = null;
    }
}
