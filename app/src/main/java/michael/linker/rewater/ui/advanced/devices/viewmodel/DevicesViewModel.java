package michael.linker.rewater.ui.advanced.devices.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;
import java.util.stream.Collectors;

import michael.linker.rewater.config.RepositoryConfiguration;
import michael.linker.rewater.data.repository.devices.DevicesRepositoryNotFoundException;
import michael.linker.rewater.data.repository.devices.IDevicesRepository;
import michael.linker.rewater.data.repository.devices.model.CompactDeviceModel;
import michael.linker.rewater.data.repository.devices.model.EditableDeviceModel;
import michael.linker.rewater.data.repository.networks.INetworksRepository;
import michael.linker.rewater.data.repository.networks.model.CompactNetworkModel;
import michael.linker.rewater.data.repository.schedules.ISchedulesRepository;
import michael.linker.rewater.data.repository.schedules.model.CompactScheduleModel;

public class DevicesViewModel extends ViewModel {
    private final IDevicesRepository mDevicesRepository;
    private final ISchedulesRepository mSchedulesRepository;
    private final INetworksRepository mNetworksRepository;

    private final MutableLiveData<List<CompactDeviceModel>> mCompactDeviceModels;
    private final MutableLiveData<List<String>> mAlreadyTakenDeviceNames;

    private final MutableLiveData<String> mEditableDeviceId;
    private final MutableLiveData<EditableDeviceModel> mEditableDeviceModel;
    private final MutableLiveData<CompactScheduleModel> mParentScheduleModel;
    private final MutableLiveData<CompactNetworkModel> mParentNetworkModel;

    public DevicesViewModel() {
        mDevicesRepository = RepositoryConfiguration.getDevicesRepository();
        mNetworksRepository = RepositoryConfiguration.getNetworksRepository();
        mSchedulesRepository = RepositoryConfiguration.getSchedulesRepository();

        mCompactDeviceModels = new MutableLiveData<>();
        mAlreadyTakenDeviceNames = new MutableLiveData<>();

        mEditableDeviceId = new MutableLiveData<>();
        mEditableDeviceModel = new MutableLiveData<>();

        mParentNetworkModel = new MutableLiveData<>();
        mParentScheduleModel = new MutableLiveData<>();
        this.updateLists();
    }

    public LiveData<List<CompactDeviceModel>> getCompactDeviceModels() {
        return mCompactDeviceModels;
    }

    public MutableLiveData<List<String>> getAlreadyTakenDeviceNames() {
        return mAlreadyTakenDeviceNames;
    }

    public LiveData<String> getEditableDeviceId() {
        return mEditableDeviceId;
    }

    public LiveData<EditableDeviceModel> getEditableDeviceModel() {
        return mEditableDeviceModel;
    }

    public LiveData<CompactScheduleModel> getParentScheduleModel() {
        return mParentScheduleModel;
    }

    public LiveData<CompactNetworkModel> getParentNetworkModel() {
        return mParentNetworkModel;
    }

    public void setEditableDeviceId(final String id) throws DevicesRepositoryNotFoundException {
        try {
            final CompactDeviceModel model = mDevicesRepository.getCompactNetworkById(id);
            final String parentScheduleId =
                    model.getParentSchedule() != null ? model.getParentSchedule().getId() : null;
            final String parentNetworkId =
                    model.getParentNetwork() != null ? model.getParentNetwork().getId() : null;

            mEditableDeviceId.setValue(id);
            mEditableDeviceModel.setValue(new EditableDeviceModel(
                    model.getName(),
                    parentScheduleId,
                    parentNetworkId
            ));
            if (parentNetworkId != null && parentScheduleId != null) {
                mParentScheduleModel.setValue(
                        mSchedulesRepository.getCompactScheduleById(parentScheduleId));
                mParentNetworkModel.setValue(
                        mNetworksRepository.getCompactNetworkById(parentNetworkId));
            } else {
                mParentScheduleModel.setValue(null);
                mParentNetworkModel.setValue(null);
            }
        } catch (DevicesRepositoryNotFoundException e) {
            throw new DevicesViewModelFailedException(e.getMessage());
        }
    }

    public void detachParents() {
        final EditableDeviceModel model = mEditableDeviceModel.getValue();
        if (model != null) {
            mEditableDeviceModel.setValue(new EditableDeviceModel(
                    model.getName(),
                    null,
                    null
            ));
            mParentScheduleModel.setValue(null);
            mParentNetworkModel.setValue(null);
        }
    }

    public void removeDevice(final String id) {
        mDevicesRepository.removeDevice(id);
        this.updateLists();
    }

    private void updateLists() {
        final List<CompactDeviceModel> newList = mDevicesRepository.getCompactDeviceList();
        mCompactDeviceModels.setValue(newList);
        mAlreadyTakenDeviceNames.setValue(newList.stream()
                .map(CompactDeviceModel::getName)
                .collect(Collectors.toList()));
    }
}