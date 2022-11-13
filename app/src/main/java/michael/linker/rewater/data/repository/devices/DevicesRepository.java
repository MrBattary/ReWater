package michael.linker.rewater.data.repository.devices;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import michael.linker.rewater.config.DataConfiguration;
import michael.linker.rewater.data.repository.devices.model.CompactDeviceModel;
import michael.linker.rewater.data.repository.devices.model.UpdateDeviceModel;
import michael.linker.rewater.data.web.IDevicesData;
import michael.linker.rewater.data.web.ISchedulesData;
import michael.linker.rewater.data.web.links.IOneToManyDataLink;
import michael.linker.rewater.data.web.model.FullDeviceModel;
import michael.linker.rewater.data.web.model.FullScheduleModel;

public class DevicesRepository implements IDevicesRepository {
    private final ISchedulesData mSchedulesData;
    private final IDevicesData mDevicesData;
    private final IOneToManyDataLink mScheduleToDevicesDataLink;

    public DevicesRepository() {
        mSchedulesData = DataConfiguration.getSchedulesData();
        mDevicesData = DataConfiguration.getDevicesData();
        mScheduleToDevicesDataLink = DataConfiguration.getScheduleToDevicesDataLink();
    }

    @Override
    public List<CompactDeviceModel> getCompactDeviceList() {
        final List<CompactDeviceModel> compactDeviceModels = new ArrayList<>();
        final List<FullDeviceModel> dataDeviceModelList =
                mDevicesData.getDevicesList().getFullDeviceModels();

        for (FullDeviceModel dataDeviceModel : dataDeviceModelList) {
            compactDeviceModels.add(new CompactDeviceModel(
                    dataDeviceModel.getId(),
                    dataDeviceModel.getName(),
                    dataDeviceModel.getStatus()
            ));
        }
        return compactDeviceModels;
    }

    @Override
    public CompactDeviceModel getCompactNetworkById(final String id)
            throws DevicesRepositoryNotFoundException {
        final FullDeviceModel dataDeviceModel = mDevicesData.getDeviceById(id);
        if (dataDeviceModel == null) {
            throw new DevicesRepositoryNotFoundException(
                    "Requested device with id: " + id + " was not found!");
        }

        return new CompactDeviceModel(
                dataDeviceModel.getId(),
                dataDeviceModel.getName(),
                dataDeviceModel.getStatus()
        );
    }

    @Override
    public void removeDevice(final String deviceId) {
        final FullDeviceModel dataDeviceModel = mDevicesData.getDeviceById(deviceId);

        final String parentScheduleId = mScheduleToDevicesDataLink
                .getLeftEntityIdByRightEntityId(deviceId);
        if (parentScheduleId != null) {

            final FullScheduleModel parentSchedule = mSchedulesData.getScheduleById(
                    parentScheduleId);
            // Remove data link
            mScheduleToDevicesDataLink.removeRightEntityId(deviceId);

            // Change schedule entity data
            final List<String> newListOfAttachedDevices = parentSchedule.getAttachedDevicesIds()
                    .stream()
                    .filter(iId -> !deviceId.equals(iId))
                    .collect(Collectors.toList());

            mSchedulesData.updateSchedule(dataDeviceModel.getId(), new FullScheduleModel(
                    parentSchedule.getId(), parentSchedule.getName(),
                    parentSchedule.getPeriod(),
                    parentSchedule.getVolume(),
                    newListOfAttachedDevices
            ));
        }
        mDevicesData.removeDeviceById(deviceId);
    }

    @Override
    public void updateDevice(String id, UpdateDeviceModel model)
            throws DevicesRepositoryNotFoundException {
        final FullDeviceModel dataDeviceModel = mDevicesData.getDeviceById(id);
        if (dataDeviceModel == null) {
            throw new DevicesRepositoryNotFoundException(
                    "Requested device with id: " + id + " was not found and can't be updated!"
            );
        }

        String oldScheduleId = mScheduleToDevicesDataLink.getLeftEntityIdByRightEntityId(id);

        final String newScheduleId = model.getParentScheduleNewId();
        if (!Objects.equals(oldScheduleId, newScheduleId)) {
            mScheduleToDevicesDataLink.removeRightEntityId(id);
            if(newScheduleId != null) {
                mScheduleToDevicesDataLink.addDataLink(newScheduleId, id);
            }
        }
        mDevicesData.updateDevice(id, new FullDeviceModel(
                id,
                model.getDeviceNewModel().getName(),
                dataDeviceModel.getStatus())
        );
    }
}
