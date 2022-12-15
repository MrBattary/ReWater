package michael.linker.rewater.ui.advanced.devices.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;
import michael.linker.rewater.config.RepositoryConfiguration;
import michael.linker.rewater.data.model.IdNameModel;
import michael.linker.rewater.data.model.status.DetailedStatusModel;
import michael.linker.rewater.data.repository.devices.IDevicesRepository;
import michael.linker.rewater.data.repository.devices.model.CreateDeviceRepositoryModel;
import michael.linker.rewater.data.repository.devices.model.DeviceRepositoryModel;
import michael.linker.rewater.data.repository.devices.model.UpdateDeviceRepositoryModel;
import michael.linker.rewater.data.repository.networks.INetworksRepository;
import michael.linker.rewater.data.repository.schedules.ISchedulesRepository;
import michael.linker.rewater.ui.advanced.devices.model.DeviceAfterPairingUiModel;
import michael.linker.rewater.ui.advanced.devices.model.DeviceCardUiModel;
import michael.linker.rewater.ui.advanced.schedules.model.ScheduleUiModel;

public class DevicesViewModel extends ViewModel {
    private final IDevicesRepository mDevicesRepository;
    private final ISchedulesRepository mSchedulesRepository;
    private final INetworksRepository mNetworksRepository;

    private final MutableLiveData<List<DeviceCardUiModel>> mDeviceCardModels;
    private final MutableLiveData<List<ScheduleUiModel>> mSchedulesModels;
    private final MutableLiveData<List<String>> mAlreadyTakenDeviceNames;

    private String mDeviceHardwareId;
    private final MutableLiveData<String> mDeviceId;
    private final MutableLiveData<String> mDeviceName;
    private final MutableLiveData<DetailedStatusModel> mDeviceStatus;
    private final MutableLiveData<ScheduleUiModel> mParentScheduleModel;
    private final MutableLiveData<IdNameModel> mParentNetworkModel;

    public DevicesViewModel() {
        mDevicesRepository = RepositoryConfiguration.getDevicesRepository();
        mNetworksRepository = RepositoryConfiguration.getNetworksRepository();
        mSchedulesRepository = RepositoryConfiguration.getSchedulesRepository();

        mDeviceCardModels = new MutableLiveData<>();
        mSchedulesModels = new MutableLiveData<>();
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

    public LiveData<List<ScheduleUiModel>> getSchedulesModels() {
        return mSchedulesModels;
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

    public LiveData<IdNameModel> getParentNetworkIdNameModel() {
        return mParentNetworkModel;
    }

    public void setDeviceId(final String deviceId)
            throws DevicesViewModelFailedException {
        Single.fromCallable(() -> mDevicesRepository.getDeviceById(deviceId))
                .doOnSuccess(device ->
                        {
                            mDeviceId.postValue(deviceId);
                            mDeviceName.postValue(device.getName());
                            mDeviceStatus.postValue(device.getStatus());

                            if (device.getParentSchedule() != null &&
                                    device.getParentSchedule().getId() != null) {

                                this.requestParentScheduleFromRepositoryById(
                                        device.getParentSchedule().getId());
                                mParentNetworkModel.postValue(device.getParentNetwork());
                            } else {
                                mParentScheduleModel.postValue(null);
                                mParentNetworkModel.postValue(null);
                            }
                        }
                )
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(m -> {
                }, e -> {
                    throw new DevicesViewModelFailedException(e.getMessage());
                });
    }

    public void setDeviceDuringPairing(final DeviceAfterPairingUiModel model) {
        mDeviceHardwareId = model.getHardwareId();
        mDeviceId.setValue(model.getDeviceId());
        mDeviceName.setValue(model.getDeviceName());

        // Always null
        mParentScheduleModel.setValue(null);
        mParentNetworkModel.setValue(null);
    }

    public void attachParentsByScheduleId(
            final String parentScheduleId, final String parentNetworkId)
            throws DevicesViewModelFailedException {
        this.requestParentScheduleFromRepositoryById(parentScheduleId);
        Single.fromCallable(() -> mNetworksRepository.getNetworkById(parentNetworkId))
                .doOnSuccess(parentNetwork ->
                        mParentNetworkModel.postValue(
                                new IdNameModel(
                                        parentNetwork.getId(),
                                        parentNetwork.getName()
                                )
                        )
                )
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe();
    }

    public void commitAndUpdateDevice() throws DevicesViewModelFailedException {
        Single.fromCallable(() -> {
                    mDevicesRepository.updateDevice(mDeviceId.getValue(),
                            new UpdateDeviceRepositoryModel(
                                    mDeviceName.getValue(),
                                    mParentScheduleModel.getValue() != null ?
                                            mParentScheduleModel.getValue().getId() : null
                            )
                    );
                    return true;
                })
                .doOnSuccess(b -> this.updateListsFromRepositories())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(m -> {
                }, e -> {
                    throw new DevicesViewModelFailedException(e.getMessage());
                });
    }

    public void commitAndCreateNewDevice()
            throws DevicesViewModelFailedException {
        Single.fromCallable(() -> {
                    mDevicesRepository.createDevice(
                            new CreateDeviceRepositoryModel(
                                    mDeviceHardwareId,
                                    mDeviceName.getValue(),
                                    mParentScheduleModel.getValue() != null ?
                                            mParentScheduleModel.getValue().getId() : null
                            )
                    );
                    return true;
                })
                .doOnSuccess(b -> this.updateListsFromRepositories())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(m -> {
                }, e -> {
                    throw new DevicesViewModelFailedException(e.getMessage());
                });
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
        Single.fromCallable(() -> {
                    mDevicesRepository.removeDevice(mDeviceId.getValue());
                    return true;
                })
                .doOnSuccess(b -> this.updateListsFromRepositories())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe();
    }

    public void updateListsFromRepositories() {
        Single.fromCallable(() -> {
                    mNetworksRepository.updateNetworkList();
                    return true;
                }).doOnSuccess(b ->
                        Single.fromCallable(mDevicesRepository::getDeviceList)
                                .doOnSuccess(deviceList -> {
                                    final List<DeviceCardUiModel> cardModelList =
                                            this.buildDeviceCardModelList(deviceList);
                                    mDeviceCardModels.postValue(cardModelList);
                                    mAlreadyTakenDeviceNames.postValue(deviceList.stream()
                                            .map(DeviceRepositoryModel::getName)
                                            .collect(Collectors.toList()));
                                    mSchedulesModels.postValue(
                                            mSchedulesRepository.getAllSchedules().stream()
                                                    .map(ScheduleUiModel::new)
                                                    .collect(Collectors.toList()));
                                })
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe();
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

    private void requestParentScheduleFromRepositoryById(final String scheduleId) {
        Single.fromCallable(() -> mSchedulesRepository.getScheduleById(scheduleId))
                .doOnSuccess(parentSchedule ->
                        mParentScheduleModel.postValue(
                                new ScheduleUiModel(parentSchedule))
                )
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe();
    }
}