package michael.linker.rewater.ui.advanced.devices.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import michael.linker.rewater.config.RepositoryConfiguration;
import michael.linker.rewater.data.model.unit.WaterVolumeMetricModel;
import michael.linker.rewater.data.repository.devices.DevicesRepositoryFailedException;
import michael.linker.rewater.data.repository.devices.IDevicesRepository;
import michael.linker.rewater.data.repository.devices.model.ManualWateringDeviceRepositoryModel;

public class DeviceManualWateringViewModel extends ViewModel {
    private final IDevicesRepository mDevicesRepository;

    private final MutableLiveData<String> mDeviceId;
    private final MutableLiveData<Integer> mLitresValue;
    private final MutableLiveData<Integer> mMillilitresValue;

    public DeviceManualWateringViewModel() {
        mDevicesRepository = RepositoryConfiguration.getDevicesRepository();

        mDeviceId = new MutableLiveData<>();
        mLitresValue = new MutableLiveData<>();
        mMillilitresValue = new MutableLiveData<>();

        mLitresValue.setValue(0);
        mMillilitresValue.setValue(0);
    }

    public LiveData<Integer> getLitresValue() {
        return mLitresValue;
    }

    public LiveData<Integer> getMillilitresValue() {
        return mMillilitresValue;
    }

    public void setLitresValue(final Integer litresValue) {
        mLitresValue.setValue(litresValue);
    }

    public void setMillilitresValue(final Integer millilitresValue) {
        mMillilitresValue.setValue(millilitresValue);
    }

    public void setWaterVolumeMetricData(final WaterVolumeMetricModel model) {
        mLitresValue.setValue(model.getLitres());
        mMillilitresValue.setValue(model.getMillilitres());
    }

    public void setDeviceId(final String deviceId) {
        mDeviceId.setValue(deviceId);
    }

    public boolean isProvidedDataCorrect() {
        final Integer litres = mLitresValue.getValue();
        final Integer millilitres = mMillilitresValue.getValue();
        if (litres == null || millilitres == null) {
            return false;
        }

        return new WaterVolumeMetricModel(litres, millilitres).isDataCorrect();
    }

    public void wateringWithProvidedModel()
            throws DevicesViewModelFailedException {
        try {
            this.water(false);
        } catch (DevicesRepositoryFailedException e) {
            throw new DevicesViewModelFailedException(e.getMessage());
        }
    }

    public void forceWateringWithProvidedModel() {
        this.water(true);
    }

    private void water(boolean isForceWatering) throws DevicesRepositoryFailedException {
        mDevicesRepository.manualWatering(mDeviceId.getValue(),
                new ManualWateringDeviceRepositoryModel(
                        new WaterVolumeMetricModel(
                                mLitresValue.getValue(),
                                mMillilitresValue.getValue()
                        ),
                        isForceWatering));
    }
}
