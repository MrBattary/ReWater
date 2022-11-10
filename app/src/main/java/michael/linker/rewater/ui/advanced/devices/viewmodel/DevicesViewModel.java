package michael.linker.rewater.ui.advanced.devices.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import michael.linker.rewater.config.RepositoryConfiguration;
import michael.linker.rewater.data.repository.devices.IDevicesRepository;
import michael.linker.rewater.data.repository.devices.model.CompactDeviceModel;

public class DevicesViewModel extends ViewModel {
    private final IDevicesRepository mDevicesRepository;
    private final MutableLiveData<List<CompactDeviceModel>> mCompactDeviceModels;

    public DevicesViewModel() {
        mDevicesRepository = RepositoryConfiguration.getDevicesRepository();
        mCompactDeviceModels = new MutableLiveData<>();
        mCompactDeviceModels.setValue(mDevicesRepository.getCompactDeviceList());
    }

    public LiveData<List<CompactDeviceModel>> getCompactDeviceModels() {
        return mCompactDeviceModels;
    }
}