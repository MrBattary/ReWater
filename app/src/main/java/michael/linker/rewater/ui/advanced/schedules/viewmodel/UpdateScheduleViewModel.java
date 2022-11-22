package michael.linker.rewater.ui.advanced.schedules.viewmodel;

import android.util.Pair;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import michael.linker.rewater.config.RepositoryConfiguration;
import michael.linker.rewater.data.model.unit.WaterVolumeMetricModel;
import michael.linker.rewater.data.model.unit.WateringPeriodModel;
import michael.linker.rewater.data.repository.devices.IDevicesRepository;
import michael.linker.rewater.data.repository.schedules.ISchedulesRepository;
import michael.linker.rewater.data.repository.schedules.SchedulesRepositoryAlreadyExistsException;
import michael.linker.rewater.data.repository.schedules.SchedulesRepositoryNotFoundException;
import michael.linker.rewater.data.repository.schedules.model.CreateOrUpdateScheduleRepositoryModel;
import michael.linker.rewater.data.repository.schedules.model.ScheduleModel;
import michael.linker.rewater.data.repository.schedules.model.ScheduleRepositoryModel;
import michael.linker.rewater.ui.advanced.devices.model.DeviceIdNameUiModel;

public class UpdateScheduleViewModel extends ViewModel {
    private final ISchedulesRepository mSchedulesRepository;
    private final IDevicesRepository mDevicesRepository;

    private String mScheduleId, mParentNetworkId;
    private final MutableLiveData<String> mScheduleName;
    private final MutableLiveData<List<String>> mAlreadyTakenSchedulesNames;
    private final MutableLiveData<Integer> mDays, mHours, mMinutes, mLitres, mMillilitres;

    private final MutableLiveData<List<DeviceIdNameUiModel>> mUnattachedDeviceList;
    private final MutableLiveData<List<DeviceIdNameUiModel>> mAttachedDeviceList;

    public UpdateScheduleViewModel() {
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

    public void setParentNetworkId(final String parentNetworkId) {
        mParentNetworkId = parentNetworkId;
        mScheduleId = null;
        mScheduleName.setValue(null);
        mDays.setValue(null);
        mHours.setValue(null);
        mMinutes.setValue(null);
        mLitres.setValue(null);
        mMillilitres.setValue(null);
        mAttachedDeviceList.setValue(new ArrayList<>());
        this.updateLists();
    }

    public void setScheduleIdAndLoadIt(final String scheduleId)
            throws SchedulesViewModelFailedException {
        try {
            final ScheduleRepositoryModel scheduleRepositoryModel =
                    mSchedulesRepository.getScheduleById(scheduleId);

            mScheduleId = scheduleRepositoryModel.getId();
            mScheduleName.setValue(scheduleRepositoryModel.getName());
            mDays.setValue(scheduleRepositoryModel.getPeriod().getDays());
            mHours.setValue(scheduleRepositoryModel.getPeriod().getHours());
            mMinutes.setValue(scheduleRepositoryModel.getPeriod().getMinutes());
            mLitres.setValue(scheduleRepositoryModel.getVolume().getLitres());
            mMillilitres.setValue(scheduleRepositoryModel.getVolume().getMillilitres());
            mAttachedDeviceList.setValue(
                    scheduleRepositoryModel.getDeviceModels().stream()
                            .map(deviceModel ->
                                    new DeviceIdNameUiModel(
                                            deviceModel.getId(),
                                            deviceModel.getName()))
                            .collect(Collectors.toList())
            );
            this.updateLists();
        } catch (SchedulesRepositoryNotFoundException e) {
            throw new SchedulesViewModelFailedException(e);
        }
    }

    public void attachDeviceToSchedule(final String deviceId) {
        final List<DeviceIdNameUiModel> unattachedDevices = mUnattachedDeviceList.getValue();
        final List<DeviceIdNameUiModel> attachedDevices = mAttachedDeviceList.getValue();

        Pair<List<DeviceIdNameUiModel>, List<DeviceIdNameUiModel>> listPair =
                this.moveDeviceIdNameUiModelFromFirstListToSecondListByDeviceId(
                        unattachedDevices, attachedDevices, deviceId);

        mUnattachedDeviceList.setValue(listPair.first);
        mAttachedDeviceList.setValue(listPair.second);
    }

    public void detachDeviceFromSchedule(final String deviceId) {
        final List<DeviceIdNameUiModel> attachedDevices = mAttachedDeviceList.getValue();
        final List<DeviceIdNameUiModel> unattachedDevices = mUnattachedDeviceList.getValue();

        Pair<List<DeviceIdNameUiModel>, List<DeviceIdNameUiModel>> listPair =
                this.moveDeviceIdNameUiModelFromFirstListToSecondListByDeviceId(
                        attachedDevices, unattachedDevices, deviceId);

        mAttachedDeviceList.setValue(listPair.first);
        mUnattachedDeviceList.setValue(listPair.second);
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
        try {
            mSchedulesRepository.createSchedule(
                    mParentNetworkId,
                    this.buildCreateOrUpdateScheduleRepositoryModel());
            this.updateLists();
        } catch (SchedulesRepositoryAlreadyExistsException e) {
            throw new SchedulesViewModelFailedException(e);
        }
    }

    public void commitAndUpdateSchedule() throws SchedulesViewModelFailedException {
        try {
            mSchedulesRepository.updateSchedule(
                    mScheduleId,
                    this.buildCreateOrUpdateScheduleRepositoryModel());
            this.updateLists();
        } catch (SchedulesRepositoryAlreadyExistsException e) {
            throw new SchedulesViewModelFailedException(e);
        }
    }

    private CreateOrUpdateScheduleRepositoryModel buildCreateOrUpdateScheduleRepositoryModel() {
        return new CreateOrUpdateScheduleRepositoryModel(
                mScheduleName.getValue(),
                new WateringPeriodModel(mDays.getValue(), mHours.getValue(),
                        mMinutes.getValue()),
                new WaterVolumeMetricModel(mLitres.getValue(), mMillilitres.getValue()),
                mAttachedDeviceList.getValue().stream()
                        .map(DeviceIdNameUiModel::getId)
                        .collect(Collectors.toList())
        );
    }


    private void updateLists() {
        mUnattachedDeviceList.setValue(mDevicesRepository.getDeviceAttachList().stream()
                .map(deviceModel ->
                        new DeviceIdNameUiModel(
                                deviceModel.getId(),
                                deviceModel.getName()))
                .collect(Collectors.toList()));
        mAlreadyTakenSchedulesNames.setValue(mSchedulesRepository.getCompactScheduleList().stream()
                .map(ScheduleModel::getName)
                .collect(Collectors.toList()));
    }
}