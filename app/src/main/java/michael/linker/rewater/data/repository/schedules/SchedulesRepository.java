package michael.linker.rewater.data.repository.schedules;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import michael.linker.rewater.config.DataConfiguration;
import michael.linker.rewater.data.repository.devices.model.DeviceModel;
import michael.linker.rewater.data.repository.schedules.model.CreateOrUpdateScheduleRepositoryModel;
import michael.linker.rewater.data.repository.schedules.model.ScheduleModel;
import michael.linker.rewater.data.repository.schedules.model.ScheduleRepositoryModel;
import michael.linker.rewater.data.local.stub.IDevicesData;
import michael.linker.rewater.data.local.stub.ISchedulesData;
import michael.linker.rewater.data.local.stub.links.IOneToManyDataLink;
import michael.linker.rewater.data.local.stub.model.FullDeviceModel;
import michael.linker.rewater.data.local.stub.model.FullScheduleModel;

public class SchedulesRepository implements ISchedulesRepository {
    private final ISchedulesData mSchedulesData;
    private final IDevicesData mDevicesData;
    private final IOneToManyDataLink mScheduleToDevicesDataLink;
    private final IOneToManyDataLink mNetworkToSchedulesDataLink;

    public SchedulesRepository() {
        mSchedulesData = DataConfiguration.getSchedulesData();
        mDevicesData = DataConfiguration.getDevicesData();
        mScheduleToDevicesDataLink = DataConfiguration.getScheduleToDevicesDataLink();
        mNetworkToSchedulesDataLink = DataConfiguration.getNetworkToSchedulesDataLink();
    }

    @Override
    public List<ScheduleModel> getCompactScheduleList() {
        final List<FullScheduleModel> dataModelList =
                mSchedulesData.getScheduleList().getScheduleModelList();
        final List<ScheduleModel> modelList = new ArrayList<>();
        for (FullScheduleModel dataFullModel : dataModelList) {
            modelList.add(new ScheduleModel(
                    dataFullModel.getId(),
                    dataFullModel.getName(),
                    dataFullModel.getPeriod(),
                    dataFullModel.getVolume()
            ));
        }
        return modelList;
    }

    @Override
    public List<ScheduleRepositoryModel> getScheduleListByNetworkId(final String networkId) {
        final List<ScheduleRepositoryModel> modelList = new ArrayList<>();
        final List<FullScheduleModel> dataModelList =
                mSchedulesData.getScheduleList().getScheduleModelList();
        final List<String> parentNetworkScheduleList =
                mNetworkToSchedulesDataLink.getRightEntityIdListByLeftEntityId(networkId);
        for (FullScheduleModel scheduleModel : dataModelList) {
            if (parentNetworkScheduleList.contains(scheduleModel.getId())) {
                final List<String> scheduleDeviceIdList =
                        mScheduleToDevicesDataLink.getRightEntityIdListByLeftEntityId(
                                scheduleModel.getId());

                final List<DeviceModel> scheduleDeviceList = new ArrayList<>();
                for (String deviceId : scheduleDeviceIdList) {
                    final FullDeviceModel deviceModel = mDevicesData.getDeviceById(deviceId);
                    scheduleDeviceList.add(
                            new DeviceModel(
                                    deviceId,
                                    deviceModel.getName(),
                                    deviceModel.getStatus()
                            )
                    );
                }

                modelList.add(new ScheduleRepositoryModel(
                        scheduleModel.getId(),
                        scheduleModel.getName(),
                        scheduleModel.getPeriod(),
                        scheduleModel.getVolume(),
                        scheduleDeviceList
                ));
            }
        }
        return modelList;
    }

    @Override
    public ScheduleRepositoryModel getScheduleById(final String id)
            throws SchedulesRepositoryNotFoundException {
        final FullScheduleModel scheduleModel = mSchedulesData.getScheduleById(id);
        if (scheduleModel == null) {
            throw new SchedulesRepositoryNotFoundException(
                    "Requested schedule with id: " + id + " was not found!");
        }
        final List<String> scheduleDeviceIdList =
                mScheduleToDevicesDataLink.getRightEntityIdListByLeftEntityId(
                        scheduleModel.getId());

        final List<DeviceModel> scheduleDeviceList = new ArrayList<>();
        for (String deviceId : scheduleDeviceIdList) {
            final FullDeviceModel deviceModel = mDevicesData.getDeviceById(deviceId);
            scheduleDeviceList.add(
                    new DeviceModel(
                            deviceId,
                            deviceModel.getName(),
                            deviceModel.getStatus()
                    )
            );
        }

        return new ScheduleRepositoryModel(
                scheduleModel.getId(),
                scheduleModel.getName(),
                scheduleModel.getPeriod(),
                scheduleModel.getVolume(),
                scheduleDeviceList
        );
    }

    @Override
    public ScheduleModel getCompactScheduleById(final String id)
            throws SchedulesRepositoryNotFoundException {
        final FullScheduleModel scheduleModel = mSchedulesData.getScheduleById(id);
        if (scheduleModel == null) {
            throw new SchedulesRepositoryNotFoundException(
                    "Requested schedule with id: " + id + " was not found!");
        }
        return new ScheduleModel(
                scheduleModel.getId(),
                scheduleModel.getName(),
                scheduleModel.getPeriod(),
                scheduleModel.getVolume()
        );
    }

    @Override
    public String getScheduleIdByIdOfAttachedDevice(String deviceId)
            throws SchedulesRepositoryNotFoundException {
        final String scheduleId = mScheduleToDevicesDataLink.getLeftEntityIdByRightEntityId(
                deviceId);
        if (scheduleId == null) {
            throw new SchedulesRepositoryNotFoundException(
                    "No schedule contains a device with the ID: " + deviceId);
        }
        return scheduleId;
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
