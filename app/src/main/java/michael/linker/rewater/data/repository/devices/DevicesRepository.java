package michael.linker.rewater.data.repository.devices;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import michael.linker.rewater.config.DataConfiguration;
import michael.linker.rewater.data.model.Status;
import michael.linker.rewater.data.repository.devices.model.AddDeviceModel;
import michael.linker.rewater.data.repository.devices.model.DeviceModel;
import michael.linker.rewater.data.repository.devices.model.DeviceHardwareModel;
import michael.linker.rewater.data.repository.devices.model.UpdateDeviceModel;
import michael.linker.rewater.data.web.IDevicesData;
import michael.linker.rewater.data.web.links.IOneToManyDataLink;
import michael.linker.rewater.data.web.model.FullDeviceModel;
import michael.linker.rewater.ui.model.DetailedStatusModel;

public class DevicesRepository implements IDevicesRepository {
    private final IDevicesData mDevicesData;
    private final IOneToManyDataLink mScheduleToDevicesDataLink;

    public DevicesRepository() {
        mDevicesData = DataConfiguration.getDevicesData();
        mScheduleToDevicesDataLink = DataConfiguration.getScheduleToDevicesDataLink();
    }

    @Override
    public List<DeviceModel> getDeviceList() {
        final List<DeviceModel> deviceModels = new ArrayList<>();
        final List<FullDeviceModel> dataDeviceModelList =
                mDevicesData.getDevicesList().getFullDeviceModels();

        for (FullDeviceModel dataDeviceModel : dataDeviceModelList) {
            deviceModels.add(new DeviceModel(
                    dataDeviceModel.getId(),
                    dataDeviceModel.getName(),
                    dataDeviceModel.getStatus()
            ));
        }
        return deviceModels;
    }

    @Override
    public DeviceModel getDeviceById(final String id)
            throws DevicesRepositoryNotFoundException {
        final FullDeviceModel dataDeviceModel = mDevicesData.getDeviceById(id);
        if (dataDeviceModel == null) {
            throw new DevicesRepositoryNotFoundException(
                    "Requested device with id: " + id + " was not found!");
        }

        return new DeviceModel(
                dataDeviceModel.getId(),
                dataDeviceModel.getName(),
                dataDeviceModel.getStatus()
        );
    }

    @Override
    public void removeDevice(final String deviceId) {
        final String parentScheduleId = mScheduleToDevicesDataLink
                .getLeftEntityIdByRightEntityId(deviceId);
        if (parentScheduleId != null) {
            mScheduleToDevicesDataLink.removeRightEntityId(deviceId);
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

        final String newScheduleId = model.getNewParentScheduleId();
        if (!Objects.equals(oldScheduleId, newScheduleId)) {
            mScheduleToDevicesDataLink.removeRightEntityId(id);
            if (newScheduleId != null) {
                mScheduleToDevicesDataLink.addDataLink(newScheduleId, id);
            }
        }
        mDevicesData.updateDevice(id, new FullDeviceModel(
                id,
                model.getName(),
                dataDeviceModel.getStatus())
        );
    }

    @Override
    public DeviceModel getDeviceByHardware(final DeviceHardwareModel model)
            throws DevicesRepositoryNotFoundException {
        // TODO Request to the server
        return new DeviceModel(null, null, null);
    }

    @Override
    public void addNewDevice(final AddDeviceModel model)
            throws DevicesRepositoryAlreadyExistsException {
        final String newDeviceUuid = UUID.randomUUID().toString();

        // TODO: Also send hardware id to the server

        mDevicesData.addDevice(
                new FullDeviceModel(
                        newDeviceUuid,
                        model.getName(),
                        new DetailedStatusModel(Status.OK, Status.OK)
                )
        );

        if (model.getNewParentScheduleId() != null) {
            mScheduleToDevicesDataLink.addDataLink(model.getNewParentScheduleId(), newDeviceUuid);
        }
    }
}
