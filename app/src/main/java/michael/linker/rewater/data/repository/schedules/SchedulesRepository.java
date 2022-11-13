package michael.linker.rewater.data.repository.schedules;

import java.util.ArrayList;
import java.util.List;

import michael.linker.rewater.config.DataConfiguration;
import michael.linker.rewater.data.repository.schedules.model.CompactScheduleModel;
import michael.linker.rewater.data.web.ISchedulesData;
import michael.linker.rewater.data.web.links.IOneToManyDataLink;
import michael.linker.rewater.data.web.model.FullScheduleModel;

public class SchedulesRepository implements ISchedulesRepository {
    private final ISchedulesData mSchedulesData;
    private final IOneToManyDataLink mScheduleToDevicesDataLink;

    public SchedulesRepository() {
        mSchedulesData = DataConfiguration.getSchedulesData();
        mScheduleToDevicesDataLink = DataConfiguration.getScheduleToDevicesDataLink();
    }

    @Override
    public List<CompactScheduleModel> getCompactScheduleList() {
        final List<FullScheduleModel> dataModelList =
                mSchedulesData.getScheduleList().getScheduleModelList();
        final List<CompactScheduleModel> modelList = new ArrayList<>();
        for (FullScheduleModel dataFullModel : dataModelList) {
            modelList.add(new CompactScheduleModel(
                    dataFullModel.getId(),
                    dataFullModel.getName(),
                    dataFullModel.getPeriod(),
                    dataFullModel.getVolume()
            ));
        }
        return modelList;
    }

    @Override
    public CompactScheduleModel getCompactScheduleById(final String id)
            throws SchedulesRepositoryNotFoundException {
        final FullScheduleModel scheduleModel = mSchedulesData.getScheduleById(id);
        if (scheduleModel == null) {
            throw new SchedulesRepositoryNotFoundException(
                    "Requested schedule with id: " + id + " was not found!");
        }
        return new CompactScheduleModel(
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
