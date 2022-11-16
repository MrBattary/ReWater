package michael.linker.rewater.ui.advanced.devices.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import michael.linker.rewater.config.RepositoryConfiguration;
import michael.linker.rewater.data.model.unit.WaterVolumeMetricModel;
import michael.linker.rewater.data.repository.devices.IDevicesRepository;

public class DeviceManualWateringViewModel extends ViewModel {
    private static final int ZERO_WATERING_VOLUME = 0;
    private final IDevicesRepository mDevicesRepository;

    private final MutableLiveData<String> mDeviceId;
    private final MutableLiveData<WaterVolumeMetricModel> mWaterVolumeMetricData;

    public DeviceManualWateringViewModel() {
        mDevicesRepository = RepositoryConfiguration.getDevicesRepository();

        mDeviceId = new MutableLiveData<>();
        mWaterVolumeMetricData = new MutableLiveData<>();
    }

    public LiveData<WaterVolumeMetricModel> getWaterVolumeMetricData() {
        return mWaterVolumeMetricData;
    }

    public void setWaterVolumeMetricData(final WaterVolumeMetricModel model) {
        mWaterVolumeMetricData.setValue(model);
    }

    public void setDeviceId(final String deviceId) {
        mDeviceId.setValue(deviceId);
    }

    public boolean isProvidedModelNotZeroVolume() {
        final WaterVolumeMetricModel waterModel = mWaterVolumeMetricData.getValue();
        if (waterModel == null) {
            return true;
        }

        final int wateringVolumeMl = waterModel.getLitres() * 1000
                + mWaterVolumeMetricData.getValue().getMillilitres();
        return wateringVolumeMl != ZERO_WATERING_VOLUME;
    }

    public void waterWithProvidedModel()
            throws DevicesViewModelFailedException {
        // TODO: Send request for the watering with the provided id
    }

    public void forceWaterWithProvidedModel() {
        // TODO: Send request for the force watering
    }
}
