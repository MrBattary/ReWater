package michael.linker.rewater.ui.advanced.devices.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import michael.linker.rewater.config.RepositoryConfiguration;
import michael.linker.rewater.data.repository.devices.DevicesRepositoryNotFoundException;
import michael.linker.rewater.data.repository.devices.IDevicesRepository;
import michael.linker.rewater.data.repository.devices.model.CompactDeviceModel;

public class DevicesViewModel extends ViewModel {
    private final IDevicesRepository mDevicesRepository;
    private final MutableLiveData<List<CompactDeviceModel>> mCompactDeviceModels;
    private final MutableLiveData<String> mEditableDeviceId;
    private final MutableLiveData<CompactDeviceModel> mEditableDeviceModel;

    public DevicesViewModel() {
        mDevicesRepository = RepositoryConfiguration.getDevicesRepository();
        mCompactDeviceModels = new MutableLiveData<>();
        mEditableDeviceId = new MutableLiveData<>();
        mEditableDeviceModel = new MutableLiveData<>();
        mCompactDeviceModels.setValue(mDevicesRepository.getCompactDeviceList());
    }

    public LiveData<List<CompactDeviceModel>> getCompactDeviceModels() {
        return mCompactDeviceModels;
    }

    public void setEditableDeviceId(final String id) throws DevicesRepositoryNotFoundException {
        try {
            final CompactDeviceModel model = mDevicesRepository.getCompactNetworkById(id);
            mEditableDeviceModel.setValue(model);
            mEditableDeviceId.setValue(id);
        } catch (DevicesRepositoryNotFoundException e) {
            throw new DevicesViewModelFailedException(e.getMessage());
        }
    }
}