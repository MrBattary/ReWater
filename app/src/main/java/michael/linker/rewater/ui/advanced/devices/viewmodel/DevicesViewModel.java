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
import michael.linker.rewater.data.repository.devices.model.AddDeviceModel;
import michael.linker.rewater.data.repository.devices.model.DeviceModel;
import michael.linker.rewater.data.repository.devices.model.UpdateDeviceModel;
import michael.linker.rewater.data.repository.networks.INetworksRepository;
import michael.linker.rewater.data.repository.networks.model.NetworkModel;
import michael.linker.rewater.data.repository.schedules.ISchedulesRepository;
import michael.linker.rewater.data.repository.schedules.SchedulesRepositoryNotFoundException;
import michael.linker.rewater.data.repository.schedules.model.ScheduleModel;
import michael.linker.rewater.ui.advanced.devices.model.DeviceCardModel;
import michael.linker.rewater.ui.advanced.devices.model.DeviceInfoModel;

public class DevicesViewModel extends ViewModel {
    private final IDevicesRepository mDevicesRepository;
    private final ISchedulesRepository mSchedulesRepository;
    private final INetworksRepository mNetworksRepository;

    private final MutableLiveData<List<DeviceCardModel>> mDeviceCardModels;
    private final MutableLiveData<List<ScheduleModel>> mCompactScheduleModels;
    private final MutableLiveData<List<String>> mAlreadyTakenDeviceNames;

    private final MutableLiveData<String> mDeviceId;
    private String mDeviceHardwareId;
    private final MutableLiveData<String> mDeviceName;
    private final MutableLiveData<ScheduleModel> mParentScheduleModel;
    private final MutableLiveData<NetworkModel> mParentNetworkModel;

    public DevicesViewModel() {
        mDevicesRepository = RepositoryConfiguration.getDevicesRepository();
        mNetworksRepository = RepositoryConfiguration.getNetworksRepository();
        mSchedulesRepository = RepositoryConfiguration.getSchedulesRepository();

        mDeviceCardModels = new MutableLiveData<>();
        mCompactScheduleModels = new MutableLiveData<>();
        mAlreadyTakenDeviceNames = new MutableLiveData<>();

        mDeviceId = new MutableLiveData<>();
        mDeviceName = new MutableLiveData<>();

        mParentNetworkModel = new MutableLiveData<>();
        mParentScheduleModel = new MutableLiveData<>();
        this.updateLists();
    }

    public LiveData<List<DeviceCardModel>> getDeviceCardModels() {
        return mDeviceCardModels;
    }

    public LiveData<List<ScheduleModel>> getCompactScheduleModels() {
        return mCompactScheduleModels;
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

    public void setDeviceName(final String name) {
        mDeviceName.setValue(name);
    }

    public LiveData<ScheduleModel> getParentScheduleModel() {
        return mParentScheduleModel;
    }

    public LiveData<NetworkModel> getParentNetworkModel() {
        return mParentNetworkModel;
    }

    public void setDeviceId(final String deviceId)
            throws DevicesRepositoryNotFoundException {
        try {
            final DeviceModel model = mDevicesRepository.getDeviceById(deviceId);
            mDeviceId.setValue(deviceId);
            mDeviceName.setValue(model.getName());

            try {
                final String parentScheduleId =
                        mSchedulesRepository.getScheduleIdByIdOfAttachedDevice(
                                deviceId);
                final String parentNetworkId =
                        mNetworksRepository.getNetworkIdByIdOfAttachedSchedule(
                                parentScheduleId);

                mParentScheduleModel.setValue(
                        mSchedulesRepository.getCompactScheduleById(parentScheduleId));
                mParentNetworkModel.setValue(
                        mNetworksRepository.getNetworkById(parentNetworkId));

            } catch (SchedulesRepositoryNotFoundException ignored) {
                mParentScheduleModel.setValue(null);
                mParentNetworkModel.setValue(null);
            }
        } catch (DevicesRepositoryNotFoundException | SchedulesRepositoryNotFoundException e) {
            throw new DevicesViewModelFailedException(e.getMessage());
        }
    }

    public void setDeviceDuringPairing(final AddDeviceModel model) {
        mDeviceId.setValue(model.getId());
        mDeviceHardwareId = model.getDeviceHardwareId();
        mDeviceName.setValue(model.getName());

        // Always null
        mParentScheduleModel.setValue(null);
        mParentNetworkModel.setValue(null);
    }

    public void attachParentsByScheduleId(final String scheduleId)
            throws DevicesViewModelFailedException {
        try {
            final String parentNetworkId =
                    mNetworksRepository.getNetworkIdByIdOfAttachedSchedule(
                            scheduleId);

            mParentScheduleModel.setValue(
                    mSchedulesRepository.getCompactScheduleById(scheduleId));
            mParentNetworkModel.setValue(
                    mNetworksRepository.getNetworkById(parentNetworkId));
        } catch (DevicesRepositoryNotFoundException | SchedulesRepositoryNotFoundException e) {
            throw new DevicesViewModelFailedException(e.getMessage());
        }
    }

    public void commitAndUpdateDevice(final DeviceInfoModel infoModel)
            throws DevicesViewModelFailedException {
        try {
            mDevicesRepository.updateDevice(mDeviceId.getValue(),
                    new UpdateDeviceModel(
                            infoModel.getName(),
                            mParentScheduleModel.getValue() != null ?
                                    mParentScheduleModel.getValue().getId() : null
                    )
            );
            this.updateLists();
        } catch (DevicesRepositoryNotFoundException e) {
            throw new DevicesViewModelFailedException(e.getMessage());
        }
    }

    public void commitAndAddNewDevice(final DeviceInfoModel infoModel)
            throws DevicesViewModelFailedException {
        try {
            mDevicesRepository.addNewDevice(
                    new AddDeviceModel(
                            mDeviceId.getValue(),
                            mDeviceHardwareId,
                            infoModel.getName(),
                            mParentScheduleModel.getValue() != null ?
                                    mParentScheduleModel.getValue().getId() : null
                    )
            );
            this.updateLists();
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
        this.updateLists();
    }

    private void updateLists() {
        final List<DeviceModel> deviceList = mDevicesRepository.getDeviceList();
        final List<DeviceCardModel> cardModelList = this.buildDeviceCardModelList(deviceList);

        mDeviceCardModels.setValue(cardModelList);
        mAlreadyTakenDeviceNames.setValue(deviceList.stream()
                .map(DeviceModel::getName)
                .collect(Collectors.toList()));
        mCompactScheduleModels.setValue(mSchedulesRepository.getCompactScheduleList());
    }

    private List<DeviceCardModel> buildDeviceCardModelList(
            final List<DeviceModel> deviceList) {
        final List<DeviceCardModel> cardModelList = new ArrayList<>();
        for (DeviceModel device : deviceList) {
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
                            mNetworksRepository.getNetworkById(parentNetworkId).getHeading()
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