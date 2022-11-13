package michael.linker.rewater.ui.advanced.devices.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import michael.linker.rewater.config.RepositoryConfiguration;
import michael.linker.rewater.data.model.IdNameModel;
import michael.linker.rewater.data.repository.devices.DevicesRepositoryNotFoundException;
import michael.linker.rewater.data.repository.devices.IDevicesRepository;
import michael.linker.rewater.data.repository.devices.model.CompactDeviceModel;
import michael.linker.rewater.data.repository.devices.model.EditableDeviceModel;
import michael.linker.rewater.data.repository.networks.INetworksRepository;
import michael.linker.rewater.data.repository.networks.model.CompactNetworkModel;
import michael.linker.rewater.data.repository.schedules.ISchedulesRepository;
import michael.linker.rewater.data.repository.schedules.SchedulesRepositoryNotFoundException;
import michael.linker.rewater.data.repository.schedules.model.CompactScheduleModel;
import michael.linker.rewater.ui.advanced.devices.model.DeviceCardModel;

public class DevicesViewModel extends ViewModel {
    private final IDevicesRepository mDevicesRepository;
    private final ISchedulesRepository mSchedulesRepository;
    private final INetworksRepository mNetworksRepository;

    private final MutableLiveData<List<DeviceCardModel>> mDeviceCardModels;
    private final MutableLiveData<List<CompactScheduleModel>> mCompactScheduleModels;
    private final MutableLiveData<List<String>> mAlreadyTakenDeviceNames;

    private final MutableLiveData<String> mEditableDeviceId;
    private final MutableLiveData<EditableDeviceModel> mEditableDeviceModel;
    private final MutableLiveData<CompactScheduleModel> mParentScheduleModel;
    private final MutableLiveData<CompactNetworkModel> mParentNetworkModel;

    public DevicesViewModel() {
        mDevicesRepository = RepositoryConfiguration.getDevicesRepository();
        mNetworksRepository = RepositoryConfiguration.getNetworksRepository();
        mSchedulesRepository = RepositoryConfiguration.getSchedulesRepository();

        mDeviceCardModels = new MutableLiveData<>();
        mCompactScheduleModels = new MutableLiveData<>();
        mAlreadyTakenDeviceNames = new MutableLiveData<>();

        mEditableDeviceId = new MutableLiveData<>();
        mEditableDeviceModel = new MutableLiveData<>();

        mParentNetworkModel = new MutableLiveData<>();
        mParentScheduleModel = new MutableLiveData<>();
        this.updateLists();
    }

    public LiveData<List<DeviceCardModel>> getDeviceCardModels() {
        return mDeviceCardModels;
    }

    public LiveData<List<CompactScheduleModel>> getCompactScheduleModels() {
        return mCompactScheduleModels;
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

    public void setEditableDeviceId(final String deviceId)
            throws DevicesRepositoryNotFoundException {
        try {
            final CompactDeviceModel model = mDevicesRepository.getCompactNetworkById(deviceId);
            mEditableDeviceId.setValue(deviceId);
            mEditableDeviceModel.setValue(new EditableDeviceModel(model.getName()));

            try {
                final String parentScheduleId =
                        mSchedulesRepository.getScheduleIdByIdOfAttachedDevice(
                                deviceId);
                if (parentScheduleId != null) {
                    final String parentNetworkId =
                            mNetworksRepository.getNetworkIdByIdOfAttachedSchedule(
                                    parentScheduleId);

                    mParentScheduleModel.setValue(
                            mSchedulesRepository.getCompactScheduleById(parentScheduleId));
                    mParentNetworkModel.setValue(
                            mNetworksRepository.getCompactNetworkById(parentNetworkId));
                }
            } catch (SchedulesRepositoryNotFoundException ignored) {
                mParentScheduleModel.setValue(null);
                mParentNetworkModel.setValue(null);
            }
        } catch (DevicesRepositoryNotFoundException | SchedulesRepositoryNotFoundException e) {
            throw new DevicesViewModelFailedException(e.getMessage());
        }
    }

    public void detachParents() {
        final EditableDeviceModel model = mEditableDeviceModel.getValue();
        if (model != null) {
            mEditableDeviceModel.setValue(new EditableDeviceModel(model.getName()));
            mParentScheduleModel.setValue(null);
            mParentNetworkModel.setValue(null);
        }
    }

    public void removeDevice(final String id) {
        mDevicesRepository.removeDevice(id);
        this.updateLists();
    }

    private void updateLists() {
        final List<CompactDeviceModel> deviceList = mDevicesRepository.getCompactDeviceList();
        final List<DeviceCardModel> cardModelList = this.buildDeviceCardModelList(deviceList);

        mDeviceCardModels.setValue(cardModelList);
        mAlreadyTakenDeviceNames.setValue(deviceList.stream()
                .map(CompactDeviceModel::getName)
                .collect(Collectors.toList()));
        mCompactScheduleModels.setValue(mSchedulesRepository.getCompactScheduleList());
    }

    private List<DeviceCardModel> buildDeviceCardModelList(
            final List<CompactDeviceModel> deviceList) {
        final List<DeviceCardModel> cardModelList = new ArrayList<>();
        for (CompactDeviceModel device : deviceList) {
            IdNameModel parentScheduleIdNameModel = null;
            IdNameModel parentNetworkIdNameModel = null;
            try {
                final String parentScheduleId =
                        mSchedulesRepository.getScheduleIdByIdOfAttachedDevice(
                                device.getId());
                if (parentScheduleId != null) {
                    final String parentNetworkId =
                            mNetworksRepository.getNetworkIdByIdOfAttachedSchedule(
                                    parentScheduleId);

                    parentScheduleIdNameModel = new IdNameModel(
                            parentScheduleId,
                            mSchedulesRepository.getCompactScheduleById(parentScheduleId).getName()
                    );
                    parentNetworkIdNameModel = new IdNameModel(
                            parentNetworkId,
                            mNetworksRepository.getCompactNetworkById(parentNetworkId).getHeading()
                    );
                }
            } catch (SchedulesRepositoryNotFoundException ignored) {
            }
            cardModelList.add(new DeviceCardModel(
                    device.getId(),
                    device.getName(),
                    parentScheduleIdNameModel,
                    parentNetworkIdNameModel,
                    device.getStatus())
            );
        }
        return cardModelList;
    }
}