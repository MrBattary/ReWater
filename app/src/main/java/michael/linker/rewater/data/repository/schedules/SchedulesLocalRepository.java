package michael.linker.rewater.data.repository.schedules;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import michael.linker.rewater.config.StubDataConfiguration;
import michael.linker.rewater.data.local.stub.IDevicesData;
import michael.linker.rewater.data.local.stub.ISchedulesData;
import michael.linker.rewater.data.local.stub.links.IOneToManyDataLink;
import michael.linker.rewater.data.local.stub.model.FullDeviceModel;
import michael.linker.rewater.data.local.stub.model.FullScheduleModel;
import michael.linker.rewater.data.repository.devices.model.DeviceWithoutParentsRepositoryModel;
import michael.linker.rewater.data.repository.schedules.model.CreateOrUpdateScheduleRepositoryModel;
import michael.linker.rewater.data.repository.schedules.model.ScheduleRepositoryModel;

public class SchedulesLocalRepository implements ISchedulesRepository {
    private final ISchedulesData mSchedulesData;
    private final IDevicesData mDevicesData;
    private final IOneToManyDataLink mScheduleToDevicesDataLink;
    private final IOneToManyDataLink mNetworkToSchedulesDataLink;

    public SchedulesLocalRepository() {
        mSchedulesData = StubDataConfiguration.getSchedulesData();
        mDevicesData = StubDataConfiguration.getDevicesData();
        mScheduleToDevicesDataLink = StubDataConfiguration.getScheduleToDevicesDataLink();
        mNetworkToSchedulesDataLink = StubDataConfiguration.getNetworkToSchedulesDataLink();
    }

    @Override
    public List<ScheduleRepositoryModel> getAllSchedules() {
        final List<ScheduleRepositoryModel> modelList = new ArrayList<>();
        final List<FullScheduleModel> dataModelList = mSchedulesData.getScheduleList();
        for (FullScheduleModel scheduleModel : dataModelList) {
            final String parentNetworkId =
                    mNetworkToSchedulesDataLink.getLeftEntityIdByRightEntityId(
                            scheduleModel.getId());

            final List<String> scheduleDeviceIdList =
                    mScheduleToDevicesDataLink.getRightEntityIdListByLeftEntityId(
                            scheduleModel.getId());
            final List<DeviceWithoutParentsRepositoryModel> scheduleDeviceList = new ArrayList<>();
            for (String deviceId : scheduleDeviceIdList) {
                final FullDeviceModel deviceModel = mDevicesData.getDeviceById(deviceId);
                scheduleDeviceList.add(
                        new DeviceWithoutParentsRepositoryModel(
                                deviceId,
                                deviceModel.getName(),
                                deviceModel.getStatus()
                        )
                );
            }

            modelList.add(new ScheduleRepositoryModel(
                    scheduleModel.getId(),
                    scheduleModel.getName(),
                    parentNetworkId,
                    scheduleModel.getPeriod(),
                    scheduleModel.getVolume(),
                    scheduleDeviceList));
        }
        return modelList;
    }

    @Override
    public List<ScheduleRepositoryModel> getScheduleListByNetworkId(final String networkId) {
        final List<ScheduleRepositoryModel> modelList = new ArrayList<>();
        final List<FullScheduleModel> dataModelList =
                mSchedulesData.getScheduleList();
        final List<String> parentNetworkScheduleList =
                mNetworkToSchedulesDataLink.getRightEntityIdListByLeftEntityId(networkId);
        for (FullScheduleModel scheduleModel : dataModelList) {
            if (parentNetworkScheduleList.contains(scheduleModel.getId())) {
                final List<String> scheduleDeviceIdList =
                        mScheduleToDevicesDataLink.getRightEntityIdListByLeftEntityId(
                                scheduleModel.getId());

                final List<DeviceWithoutParentsRepositoryModel> scheduleDeviceList =
                        new ArrayList<>();
                for (String deviceId : scheduleDeviceIdList) {
                    final FullDeviceModel deviceModel = mDevicesData.getDeviceById(deviceId);
                    scheduleDeviceList.add(
                            new DeviceWithoutParentsRepositoryModel(
                                    deviceId,
                                    deviceModel.getName(),
                                    deviceModel.getStatus()
                            )
                    );
                }

                modelList.add(new ScheduleRepositoryModel(
                        scheduleModel.getId(),
                        scheduleModel.getName(),
                        networkId,
                        scheduleModel.getPeriod(),
                        scheduleModel.getVolume(),
                        scheduleDeviceList
                ));
            }
        }
        return modelList;
    }

    @Override
    public ScheduleRepositoryModel getScheduleById(final String scheduleId)
            throws SchedulesRepositoryNotFoundException {
        final FullScheduleModel scheduleModel = mSchedulesData.getScheduleById(scheduleId);
        if (scheduleModel == null) {
            throw new SchedulesRepositoryNotFoundException(
                    "Requested schedule with id: " + scheduleId + " was not found!");
        }
        final List<String> scheduleDeviceIdList =
                mScheduleToDevicesDataLink.getRightEntityIdListByLeftEntityId(
                        scheduleModel.getId());
        final String parentNetworkId =
                mNetworkToSchedulesDataLink.getLeftEntityIdByRightEntityId(scheduleId);

        final List<DeviceWithoutParentsRepositoryModel> scheduleDeviceList = new ArrayList<>();
        for (String deviceId : scheduleDeviceIdList) {
            final FullDeviceModel deviceModel = mDevicesData.getDeviceById(deviceId);
            scheduleDeviceList.add(
                    new DeviceWithoutParentsRepositoryModel(
                            deviceId,
                            deviceModel.getName(),
                            deviceModel.getStatus()
                    )
            );
        }

        return new ScheduleRepositoryModel(
                scheduleModel.getId(),
                scheduleModel.getName(),
                parentNetworkId, scheduleModel.getPeriod(),
                scheduleModel.getVolume(),
                scheduleDeviceList
        );
    }

    @Override
    public void createSchedule(
            final String networkId,
            final CreateOrUpdateScheduleRepositoryModel model
    )
            throws SchedulesRepositoryAlreadyExistsException {
        final List<String> existScheduleIdList =
                mNetworkToSchedulesDataLink.getRightEntityIdListByLeftEntityId(networkId);
        for (String alreadyExistScheduleId : existScheduleIdList) {
            if (mSchedulesData.getScheduleById(alreadyExistScheduleId)
                    .getName().equals(model.getName())) {
                throw new SchedulesRepositoryAlreadyExistsException(
                        "Schedule with name: " + model.getName() + " already exists!");
            }
        }

        final String newScheduleId = UUID.randomUUID().toString();
        mNetworkToSchedulesDataLink.addDataLink(networkId, newScheduleId);
        mSchedulesData.createSchedule(new FullScheduleModel(
                newScheduleId,
                model.getName(),
                model.getPeriod(),
                model.getVolume()
        ));
        for (String attachedDeviceId : model.getDeviceModelIds()) {
            mScheduleToDevicesDataLink.addDataLink(newScheduleId, attachedDeviceId);
        }
    }

    @Override
    public void updateSchedule(String scheduleId, CreateOrUpdateScheduleRepositoryModel model)
            throws SchedulesRepositoryNotFoundException {
        if (mSchedulesData.getScheduleById(scheduleId) == null) {
            throw new SchedulesRepositoryNotFoundException(
                    "Requested schedule with id: " + scheduleId + " was not found!");
        }

        mSchedulesData.updateSchedule(scheduleId, new FullScheduleModel(
                scheduleId,
                model.getName(),
                model.getPeriod(),
                model.getVolume()
        ));
        mScheduleToDevicesDataLink.removeAllRightEntityIdsByLeftEntityId(scheduleId);
        for (String attachedDeviceId : model.getDeviceModelIds()) {
            mScheduleToDevicesDataLink.addDataLink(scheduleId, attachedDeviceId);
        }
    }

    @Override
    public void removeSchedule(final String scheduleId) {
        mSchedulesData.removeSchedule(scheduleId);
        mNetworkToSchedulesDataLink.removeRightEntityId(scheduleId);
        mScheduleToDevicesDataLink.removeAllRightEntityIdsByLeftEntityId(scheduleId);
    }
}
