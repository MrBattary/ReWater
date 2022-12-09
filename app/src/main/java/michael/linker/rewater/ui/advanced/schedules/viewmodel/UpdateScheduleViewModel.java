package michael.linker.rewater.ui.advanced.schedules.viewmodel;

import android.util.Pair;

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
import michael.linker.rewater.data.model.unit.WaterVolumeMetricModel;
import michael.linker.rewater.data.model.unit.WateringPeriodModel;
import michael.linker.rewater.data.repository.devices.IDevicesRepository;
import michael.linker.rewater.data.repository.networks.INetworksRepository;
import michael.linker.rewater.data.repository.schedules.ISchedulesRepository;
import michael.linker.rewater.data.repository.schedules.model.CreateOrUpdateScheduleRepositoryModel;
import michael.linker.rewater.data.repository.schedules.model.ScheduleRepositoryModel;
import michael.linker.rewater.ui.advanced.devices.model.DeviceIdNameUiModel;

public class UpdateScheduleViewModel extends ViewModel {
    private final INetworksRepository mNetworksRepository;
    private final ISchedulesRepository mSchedulesRepository;
    private final IDevicesRepository mDevicesRepository;

    private String mScheduleId, mParentNetworkId;
    private final MutableLiveData<String> mScheduleName;
    private final MutableLiveData<List<String>> mAlreadyTakenSchedulesNames;
    private final MutableLiveData<Integer> mDays, mHours, mMinutes, mLitres, mMillilitres;

    private final MutableLiveData<List<DeviceIdNameUiModel>> mUnattachedDeviceList;
    private final MutableLiveData<List<DeviceIdNameUiModel>> mAttachedDeviceList;

    public UpdateScheduleViewModel() {
        mNetworksRepository = RepositoryConfiguration.getNetworksRepository();
        mSchedulesRepository = RepositoryConfiguration.getSchedulesRepository();
        mDevicesRepository = RepositoryConfiguration.getDevicesRepository();

        mScheduleName = new MutableLiveData<>();
        mAlreadyTakenSchedulesNames = new MutableLiveData<>();
        mDays = new MutableLiveData<>();
        mHours = new MutableLiveData<>();
        mMinutes = new MutableLiveData<>();
        mLitres = new MutableLiveData<>();
        mMillilitres = new MutableLiveData<>();

        mUnattachedDeviceList = new MutableLiveData<>();
        mAttachedDeviceList = new MutableLiveData<>();
    }

    public MutableLiveData<String> getScheduleName() {
        return mScheduleName;
    }

    public MutableLiveData<Integer> getDays() {
        return mDays;
    }

    public MutableLiveData<Integer> getMinutes() {
        return mMinutes;
    }

    public MutableLiveData<Integer> getHours() {
        return mHours;
    }

    public MutableLiveData<Integer> getLitres() {
        return mLitres;
    }

    public MutableLiveData<Integer> getMillilitres() {
        return mMillilitres;
    }

    public void setScheduleName(final String scheduleName) {
        mScheduleName.setValue(scheduleName);
    }

    public void setDays(final Integer days) {
        mDays.setValue(days);
    }

    public void setHours(final Integer hours) {
        mHours.setValue(hours);
    }

    public void setMinutes(final Integer minutes) {
        mMinutes.setValue(minutes);
    }

    public void setLitres(final Integer litres) {
        mLitres.setValue(litres);
    }

    public void setMillilitres(final Integer millilitres) {
        mMillilitres.setValue(millilitres);
    }

    public LiveData<List<String>> getAlreadyTakenSchedulesNames() {
        return mAlreadyTakenSchedulesNames;
    }

    public LiveData<List<DeviceIdNameUiModel>> getUnattachedDeviceList() {
        return mUnattachedDeviceList;
    }

    public LiveData<List<DeviceIdNameUiModel>> getAttachedDeviceList() {
        return mAttachedDeviceList;
    }

    public void setParentNetworkIdAndRefreshViewModel(final String parentNetworkId) {
        mParentNetworkId = parentNetworkId;
        mScheduleId = null;
        mScheduleName.setValue(null);
        mDays.setValue(null);
        mHours.setValue(null);
        mMinutes.setValue(null);
        mLitres.setValue(null);
        mMillilitres.setValue(null);
        mAttachedDeviceList.setValue(new ArrayList<>());
        this.updateListsFromRepositories();
    }

    public void setScheduleIdAndRefreshViewModel(final String scheduleId)
            throws SchedulesViewModelFailedException {
        Single.fromCallable(() -> mSchedulesRepository.getScheduleById(scheduleId))
                .doOnSuccess(schedule -> {
                    mParentNetworkId = null;
                    mScheduleId = schedule.getId();
                    mScheduleName.postValue(schedule.getName());
                    mDays.postValue(schedule.getPeriod().getDays());
                    mHours.postValue(schedule.getPeriod().getHours());
                    mMinutes.postValue(schedule.getPeriod().getMinutes());
                    mLitres.postValue(schedule.getVolume().getLitres());
                    mMillilitres.postValue(schedule.getVolume().getMillilitres());
                    mAttachedDeviceList.postValue(
                            schedule.getDeviceModels().stream()
                                    .map(deviceModel ->
                                            new DeviceIdNameUiModel(
                                                    deviceModel.getId(),
                                                    deviceModel.getName()))
                                    .collect(Collectors.toList())
                    );
                    this.updateListsFromRepositories();
                }).doOnError(e -> {
                    throw new SchedulesViewModelFailedException(e);
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe();
    }

    public void attachMultipleDevicesToSchedule(final List<String> deviceIdList) {
        List<DeviceIdNameUiModel> unattachedDevices = mUnattachedDeviceList.getValue();
        List<DeviceIdNameUiModel> attachedDevices = mAttachedDeviceList.getValue();

        for (String deviceId : deviceIdList) {
            Pair<List<DeviceIdNameUiModel>, List<DeviceIdNameUiModel>> listPair =
                    this.moveDeviceIdNameUiModelFromFirstListToSecondListByDeviceId(
                            unattachedDevices, attachedDevices, deviceId);

            unattachedDevices = listPair.first;
            attachedDevices = listPair.second;
        }

        mUnattachedDeviceList.postValue(unattachedDevices);
        mAttachedDeviceList.postValue(attachedDevices);
    }

    public void detachDeviceFromSchedule(final String deviceId) {
        final List<DeviceIdNameUiModel> attachedDevices = mAttachedDeviceList.getValue();
        final List<DeviceIdNameUiModel> unattachedDevices = mUnattachedDeviceList.getValue();

        Pair<List<DeviceIdNameUiModel>, List<DeviceIdNameUiModel>> listPair =
                this.moveDeviceIdNameUiModelFromFirstListToSecondListByDeviceId(
                        attachedDevices, unattachedDevices, deviceId);

        mAttachedDeviceList.postValue(listPair.first);
        mUnattachedDeviceList.postValue(listPair.second);
    }

    private Pair<List<DeviceIdNameUiModel>, List<DeviceIdNameUiModel>>
    moveDeviceIdNameUiModelFromFirstListToSecondListByDeviceId(
            final List<DeviceIdNameUiModel> firstList,
            final List<DeviceIdNameUiModel> secondList,
            final String deviceId) {
        if (firstList != null && secondList != null) {
            DeviceIdNameUiModel movingDeviceModel = null;
            for (int i = 0; i < firstList.size(); i++) {
                if (firstList.get(i).getId().equals(deviceId)) {
                    movingDeviceModel = firstList.remove(i);
                }
            }
            secondList.add(movingDeviceModel);

            return new Pair<>(firstList, secondList);
        }
        return new Pair<>(new ArrayList<>(), new ArrayList<>());
    }

    public boolean isProvidedDataCorrect() {
        return this.isProvidedPeriodDataCorrect() && this.isProvidedVolumeDataCorrect();
    }

    private boolean isProvidedPeriodDataCorrect() {
        final Integer days = mDays.getValue();
        final Integer hours = mHours.getValue();
        final Integer minutes = mMinutes.getValue();
        if (days == null || hours == null || minutes == null) {
            return false;
        }

        return new WateringPeriodModel(days, hours, minutes).isDataCorrect();
    }

    private boolean isProvidedVolumeDataCorrect() {
        final Integer litres = mLitres.getValue();
        final Integer millilitres = mMillilitres.getValue();
        if (litres == null || millilitres == null) {
            return false;
        }

        return new WaterVolumeMetricModel(litres, millilitres).isDataCorrect();
    }

    public void commitAndCreateSchedule() throws SchedulesViewModelFailedException {
        Single.fromCallable(() -> {
                    mSchedulesRepository.createSchedule(
                            mParentNetworkId,
                            this.buildCreateOrUpdateScheduleRepositoryModel());
                    return true;
                })
                .doOnSuccess(b -> this.updateListsFromRepositories())
                .doOnError(e -> {
                    throw new SchedulesViewModelFailedException(e);
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe();
    }

    public void commitAndUpdateSchedule() throws SchedulesViewModelFailedException {
        Single.fromCallable(() -> {
                    mSchedulesRepository.updateSchedule(
                            mScheduleId,
                            this.buildCreateOrUpdateScheduleRepositoryModel());
                    return true;
                })
                .doOnSuccess(b -> this.updateListsFromRepositories())
                .doOnError(e -> {
                    throw new SchedulesViewModelFailedException(e);
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe();
    }

    public void commitAndDeleteSchedule() {
        Single.fromCallable(() -> {
                    mSchedulesRepository.removeSchedule(mScheduleId);
                    return true;
                })
                .doOnSuccess(b -> this.updateListsFromRepositories())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe();
    }

    private CreateOrUpdateScheduleRepositoryModel buildCreateOrUpdateScheduleRepositoryModel() {
        return new CreateOrUpdateScheduleRepositoryModel(
                mScheduleName.getValue(),
                new WateringPeriodModel(mDays.getValue(), mHours.getValue(), mMinutes.getValue()),
                new WaterVolumeMetricModel(mLitres.getValue(), mMillilitres.getValue()),
                mAttachedDeviceList.getValue().stream()
                        .map(DeviceIdNameUiModel::getId)
                        .collect(Collectors.toList())
        );
    }


    public void updateListsFromRepositories() {
        Single.fromCallable(() -> {
                    mNetworksRepository.updateNetworkList();
                    return true;
                })
                .doOnSuccess(
                        b -> {
                            Single.fromCallable(mDevicesRepository::getDeviceAttachList)
                                    .doOnSuccess(deviceRepositoryModels ->
                                            mUnattachedDeviceList.postValue(
                                                    deviceRepositoryModels.stream()
                                                            .map(DeviceIdNameUiModel::new)
                                                            .collect(Collectors.toList())))
                                    .subscribeOn(Schedulers.io())
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribe();

                            Single.fromCallable(mSchedulesRepository::getAllSchedules)
                                    .doOnSuccess(scheduleRepositoryModels ->
                                            mAlreadyTakenSchedulesNames.postValue(
                                                    scheduleRepositoryModels.stream()
                                                            .map(ScheduleRepositoryModel::getName)
                                                            .collect(Collectors.toList())
                                            )
                                    )
                                    .subscribeOn(Schedulers.io())
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribe();
                        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe();
    }
}