package michael.linker.rewater.data.repository.schedules;

import java.util.ArrayList;
import java.util.List;

import michael.linker.rewater.config.DataConfiguration;
import michael.linker.rewater.data.repository.devices.model.DeviceModel;
import michael.linker.rewater.data.repository.schedules.model.ScheduleModel;
import michael.linker.rewater.data.repository.schedules.model.ScheduleRepositoryModel;
import michael.linker.rewater.data.web.IDevicesData;
import michael.linker.rewater.data.web.ISchedulesData;
import michael.linker.rewater.data.web.links.IOneToManyDataLink;
import michael.linker.rewater.data.web.model.FullDeviceModel;
import michael.linker.rewater.data.web.model.FullScheduleModel;

public class SchedulesRepository implements ISchedulesRepository {
    private final ISchedulesData mSchedulesData;
    private final IDevicesData mDevicesData;
    private final IOneToManyDataLink mScheduleToDevicesDataLink;

    public SchedulesRepository() {
        mSchedulesData = DataConfiguration.getSchedulesData();
        mDevicesData = DataConfiguration.getDevicesData();
        mScheduleToDevicesDataLink = DataConfiguration.getScheduleToDevicesDataLink();
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
    public List<ScheduleRepositoryModel> getScheduleList() {
        final List<ScheduleRepositoryModel> modelList = new ArrayList<>();
        final List<FullScheduleModel> dataModelList =
                mSchedulesData.getScheduleList().getScheduleModelList();
        for (FullScheduleModel scheduleModel : dataModelList) {
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
        return modelList;
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
}
