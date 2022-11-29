package michael.linker.rewater.ui.advanced.devices.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import michael.linker.rewater.config.RepositoryConfiguration;
import michael.linker.rewater.data.model.IdNameModel;
import michael.linker.rewater.data.model.status.DetailedStatusModel;
import michael.linker.rewater.data.repository.devices.DevicesRepositoryNotFoundException;
import michael.linker.rewater.data.repository.devices.IDevicesRepository;
import michael.linker.rewater.data.repository.devices.model.CreateDeviceRepositoryModel;
import michael.linker.rewater.data.repository.devices.model.DeviceRepositoryModel;
import michael.linker.rewater.data.repository.devices.model.UpdateDeviceRepositoryModel;
import michael.linker.rewater.data.repository.networks.INetworksRepository;
import michael.linker.rewater.data.repository.schedules.ISchedulesRepository;
import michael.linker.rewater.data.repository.schedules.SchedulesRepositoryNotFoundException;
import michael.linker.rewater.data.repository.schedules.model.ScheduleWithNetworkIdNameRepositoryModel;
import michael.linker.rewater.ui.advanced.devices.model.DeviceAfterPairingUiModel;
import michael.linker.rewater.ui.advanced.devices.model.DeviceCardUiModel;
import michael.linker.rewater.ui.advanced.networks.model.NetworkUiModel;
import michael.linker.rewater.ui.advanced.schedules.model.ScheduleUiModel;

public class DevicesViewModel extends ViewModel {
    private final IDevicesRepository mDevicesRepository;
    private final ISchedulesRepository mSchedulesRepository;
    private final INetworksRepository mNetworksRepository;

    private final MutableLiveData<List<DeviceCardUiModel>> mDeviceCardModels;
    private final MutableLiveData<List<ScheduleWithNetworkIdNameRepositoryModel>>
            mScheduleWithNetworkModels;
    private final MutableLiveData<List<String>> mAlreadyTakenDeviceNames;

    private String mDeviceHardwareId;
    private final MutableLiveData<String> mDeviceId;
    private final MutableLiveData<String> mDeviceName;
    private final MutableLiveData<DetailedStatusModel> mDeviceStatus;
    private final MutableLiveData<ScheduleUiModel> mParentScheduleModel;
    private final MutableLiveData<NetworkUiModel> mParentNetworkModel;

    public DevicesViewModel() {
        mDevicesRepository = RepositoryConfiguration.getDevicesRepository();
        mNetworksRepository = RepositoryConfiguration.getNetworksRepository();
        mSchedulesRepository = RepositoryConfiguration.getSchedulesRepository();

        mDeviceCardModels = new MutableLiveData<>();
        mScheduleWithNetworkModels = new MutableLiveData<>();
        mAlreadyTakenDeviceNames = new MutableLiveData<>();

        mDeviceId = new MutableLiveData<>();
        mDeviceName = new MutableLiveData<>();
        mDeviceStatus = new MutableLiveData<>();

        mParentNetworkModel = new MutableLiveData<>();
        mParentScheduleModel = new MutableLiveData<>();
        this.updateListsFromRepositories();
    }

    public LiveData<List<DeviceCardUiModel>> getDeviceCardModels() {
        return mDeviceCardModels;
    }

    public LiveData<List<ScheduleWithNetworkIdNameRepositoryModel>> getScheduleWithNetworkModels() {
        return mScheduleWithNetworkModels;
    }

    public MutableLiveData<List<String>> getAlreadyTakenDeviceNames() {
        return mAlreadyTakenDeviceNames;
    }

    public LiveData<String> getDeviceId() {
        return mDeviceId;
    }

    public LiveData<String> getDeviceName() {
        return mDeviceName;
    }

    public LiveData<DetailedStatusModel> getDeviceStatus() {
        return mDeviceStatus;
    }

    public void setDeviceName(final String name) {
        mDeviceName.setValue(name);
    }

    public LiveData<ScheduleUiModel> getParentScheduleModel() {
        return mParentScheduleModel;
    }

    public LiveData<NetworkUiModel> getParentNetworkModel() {
        return mParentNetworkModel;
    }

    public void setDeviceId(final String deviceId)
            throws DevicesRepositoryNotFoundException {
        try {
            final DeviceRepositoryModel model = mDevicesRepository.getDeviceById(deviceId);
            mDeviceId.setValue(deviceId);
            mDeviceName.setValue(model.getName());
            mDeviceStatus.setValue(model.getStatus());

            if (model.getParentSchedule() != null && model.getParentSchedule().getId() != null) {
                try {
                    mParentScheduleModel.setValue(new ScheduleUiModel(
                            mSchedulesRepository.getScheduleById(
                                    model.getParentSchedule().getId())));
                    mParentNetworkModel.setValue(new NetworkUiModel(
                            mNetworksRepository.getNetworkById(model.getParentNetwork().getId())));

                } catch (SchedulesRepositoryNotFoundException ignored) {
                    mParentScheduleModel.setValue(null);
                    mParentNetworkModel.setValue(null);
                }
            }
        } catch (DevicesRepositoryNotFoundException | SchedulesRepositoryNotFoundException e) {
            throw new DevicesViewModelFailedException(e.getMessage());
        }
    }

    public void setDeviceDuringPairing(final DeviceAfterPairingUiModel model) {
        mDeviceHardwareId = model.getHardwareId();
        mDeviceId.setValue(model.getDeviceId());
        mDeviceName.setValue(model.getDeviceName());

        // Always null
        mParentScheduleModel.setValue(null);
        mParentNetworkModel.setValue(null);
    }

    public void attachParentsByScheduleId(final String scheduleId, final String parentNetworkId)
            throws DevicesViewModelFailedException {
        try {
            mParentScheduleModel.setValue(new ScheduleUiModel(
                    mSchedulesRepository.getScheduleById(scheduleId)));
            mParentNetworkModel.setValue(new NetworkUiModel(
                    mNetworksRepository.getNetworkById(parentNetworkId)));
        } catch (DevicesRepositoryNotFoundException | SchedulesRepositoryNotFoundException e) {
            throw new DevicesViewModelFailedException(e.getMessage());
        }
    }

    public void commitAndUpdateDevice()
            throws DevicesViewModelFailedException {
        try {
            mDevicesRepository.updateDevice(mDeviceId.getValue(),
                    new UpdateDeviceRepositoryModel(
                            mDeviceName.getValue(),
                            mParentScheduleModel.getValue() != null ?
                                    mParentScheduleModel.getValue().getId() : null
                    )
            );
            this.updateListsFromRepositories();
        } catch (DevicesRepositoryNotFoundException e) {
            throw new DevicesViewModelFailedException(e.getMessage());
        }
    }

    public void commitAndCreateNewDevice()
            throws DevicesViewModelFailedException {
        try {
            mDevicesRepository.createDevice(
                    new CreateDeviceRepositoryModel(
                            mDeviceHardwareId,
                            mDeviceName.getValue(),
                            mParentScheduleModel.getValue() != null ?
                                    mParentScheduleModel.getValue().getId() : null
                    )
            );
            this.updateListsFromRepositories();
        } catch (DevicesRepositoryNotFoundException e) {
            throw new DevicesViewModelFailedException(e.getMessage());
        }
    }

    public void detachParents() {
        final String deviceName = mDeviceName.getValue();
        if (deviceName != null) {
            mDeviceName.setValue(deviceName);
            mParentScheduleModel.setValue(null);
            mParentNetworkModel.setValue(null);
        }
    }

    public void removeDevice() {
        mDevicesRepository.removeDevice(mDeviceId.getValue());
        this.updateListsFromRepositories();
    }

    public void updateListsFromRepositories() {
        mNetworksRepository.updateNetworkList();
        final List<DeviceRepositoryModel> deviceList = mDevicesRepository.getDeviceList();
        final List<DeviceCardUiModel> cardModelList = this.buildDeviceCardModelList(deviceList);

        mDeviceCardModels.setValue(cardModelList);
        mAlreadyTakenDeviceNames.setValue(deviceList.stream()
                .map(DeviceRepositoryModel::getName)
                .collect(Collectors.toList()));
        mScheduleWithNetworkModels.setValue(mSchedulesRepository.getScheduleWithNetworkList());
    }

    private List<DeviceCardUiModel> buildDeviceCardModelList(
            final List<DeviceRepositoryModel> deviceList) {
        final List<DeviceCardUiModel> cardModelList = new ArrayList<>();

        for (DeviceRepositoryModel device : deviceList) {
            IdNameModel parentScheduleIdNameModel = null;
            IdNameModel parentNetworkIdNameModel = null;

            if (device.getParentSchedule() != null
                    && device.getParentSchedule().getId() != null) {
                parentScheduleIdNameModel = device.getParentSchedule();
                parentNetworkIdNameModel = device.getParentNetwork();
            }

            cardModelList.add(new DeviceCardUiModel(
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