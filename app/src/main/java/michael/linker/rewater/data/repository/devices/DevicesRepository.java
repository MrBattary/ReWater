package michael.linker.rewater.data.repository.devices;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

import michael.linker.rewater.config.DataConfiguration;
import michael.linker.rewater.data.local.stub.IDevicesData;
import michael.linker.rewater.data.local.stub.INetworksData;
import michael.linker.rewater.data.local.stub.ISchedulesData;
import michael.linker.rewater.data.local.stub.links.IOneToManyDataLink;
import michael.linker.rewater.data.local.stub.model.FullDeviceModel;
import michael.linker.rewater.data.local.stub.model.FullNetworkModel;
import michael.linker.rewater.data.local.stub.model.FullScheduleModel;
import michael.linker.rewater.data.model.IdNameModel;
import michael.linker.rewater.data.model.status.DetailedStatusModel;
import michael.linker.rewater.data.model.status.Status;
import michael.linker.rewater.data.repository.devices.model.CreateDeviceRepositoryModel;
import michael.linker.rewater.data.repository.devices.model.DeviceIdNameRepositoryModel;
import michael.linker.rewater.data.repository.devices.model.DeviceRepositoryModel;
import michael.linker.rewater.data.repository.devices.model.UpdateDeviceRepositoryModel;

public class DevicesRepository implements IDevicesRepository {
    private final INetworksData mNetworksData;
    private final ISchedulesData mSchedulesData;
    private final IDevicesData mDevicesData;
    private final IOneToManyDataLink mScheduleToDevicesDataLink;
    private final IOneToManyDataLink mNetworkToSchedulesDataLink;

    public DevicesRepository() {
        mNetworksData = DataConfiguration.getNetworksData();
        mSchedulesData = DataConfiguration.getSchedulesData();
        mDevicesData = DataConfiguration.getDevicesData();
        mScheduleToDevicesDataLink = DataConfiguration.getScheduleToDevicesDataLink();
        mNetworkToSchedulesDataLink = DataConfiguration.getNetworkToSchedulesDataLink();
    }

    @Override
    public List<DeviceRepositoryModel> getDeviceList() {
        final List<DeviceRepositoryModel> deviceRepositoryModels = new ArrayList<>();
        final List<FullDeviceModel> dataDeviceModelList =
                mDevicesData.getDevicesList().getFullDeviceModels();

        for (FullDeviceModel dataDeviceModel : dataDeviceModelList) {
            IdNameModel parentScheduleIdNameModel = new IdNameModel(null, null);
            IdNameModel parentNetworkIdNameModel = new IdNameModel(null, null);

            final String parentScheduleId =
                    mScheduleToDevicesDataLink.getLeftEntityIdByRightEntityId(
                            dataDeviceModel.getId());
            if (parentScheduleId != null) {
                final FullScheduleModel scheduleModel =
                        mSchedulesData.getScheduleById(parentScheduleId);
                parentScheduleIdNameModel =
                        new IdNameModel(scheduleModel.getId(), scheduleModel.getName());

                final String parentNetworkId =
                        mNetworkToSchedulesDataLink.getLeftEntityIdByRightEntityId(
                                parentScheduleId);
                final FullNetworkModel deviceModel = mNetworksData.getNetworkById(parentNetworkId);
                parentNetworkIdNameModel =
                        new IdNameModel(deviceModel.getId(), deviceModel.getName());
            }


            deviceRepositoryModels.add(new DeviceRepositoryModel(
                    dataDeviceModel.getId(),
                    dataDeviceModel.getName(),
                    parentScheduleIdNameModel,
                    parentNetworkIdNameModel,
                    dataDeviceModel.getStatus()
            ));
        }
        return deviceRepositoryModels;
    }

    @Override
    public List<DeviceIdNameRepositoryModel> getDeviceAttachList() {
        final List<DeviceIdNameRepositoryModel> deviceModels = new ArrayList<>();
        final List<FullDeviceModel> dataDeviceModelList =
                mDevicesData.getDevicesList().getFullDeviceModels();
        final List<String> unattachedDeviceIdList = dataDeviceModelList.stream()
                .map(FullDeviceModel::getId)
                .collect(Collectors.toList());

        for (String scheduleId : mScheduleToDevicesDataLink.getLeftEntityIdList()) {
            final List<String> attachedToScheduleDeviceIdList =
                    mScheduleToDevicesDataLink.getRightEntityIdListByLeftEntityId(scheduleId);
            for (String attachedDeviceId : attachedToScheduleDeviceIdList) {
                unattachedDeviceIdList.remove(attachedDeviceId);
            }
        }

        for (String unattachedDeviceId : unattachedDeviceIdList) {
            final FullDeviceModel deviceModel = mDevicesData.getDeviceById(unattachedDeviceId);
            deviceModels.add(
                    new DeviceIdNameRepositoryModel(
                            deviceModel.getId(),
                            deviceModel.getName()
                    )
            );
        }

        return deviceModels;
    }

    @Override
    public DeviceRepositoryModel getDeviceById(final String id)
            throws DevicesRepositoryNotFoundException {
        final FullDeviceModel dataDeviceModel = mDevicesData.getDeviceById(id);
        if (dataDeviceModel == null) {
            throw new DevicesRepositoryNotFoundException(
                    "Requested device with id: " + id + " was not found!");
        }

        IdNameModel parentScheduleIdNameModel = new IdNameModel(null, null);
        IdNameModel parentNetworkIdNameModel = new IdNameModel(null, null);

        final String parentScheduleId =
                mScheduleToDevicesDataLink.getLeftEntityIdByRightEntityId(
                        dataDeviceModel.getId());
        if (parentScheduleId != null) {
            final FullScheduleModel scheduleModel =
                    mSchedulesData.getScheduleById(parentScheduleId);
            parentScheduleIdNameModel =
                    new IdNameModel(scheduleModel.getId(), scheduleModel.getName());

            final String parentNetworkId =
                    mNetworkToSchedulesDataLink.getLeftEntityIdByRightEntityId(
                            parentScheduleId);
            final FullNetworkModel deviceModel = mNetworksData.getNetworkById(parentNetworkId);
            parentNetworkIdNameModel =
                    new IdNameModel(deviceModel.getId(), deviceModel.getName());
        }


        return new DeviceRepositoryModel(
                dataDeviceModel.getId(),
                dataDeviceModel.getName(),
                parentScheduleIdNameModel,
                parentNetworkIdNameModel,
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
    public void updateDevice(String id, UpdateDeviceRepositoryModel model)
            throws DevicesRepositoryNotFoundException {
        final FullDeviceModel dataDeviceModel = mDevicesData.getDeviceById(id);
        if (dataDeviceModel == null) {
            throw new DevicesRepositoryNotFoundException(
                    "Requested device with id: " + id + " was not found and can't be updated!"
            );
        }

        String oldScheduleId = mScheduleToDevicesDataLink.getLeftEntityIdByRightEntityId(id);

        final String newScheduleId = model.getScheduleId();
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
    public DeviceRepositoryModel getDeviceByHardware(final String hardwareId)
            throws DevicesRepositoryNotFoundException {
        // TODO Request to the server
        return new DeviceRepositoryModel(null, null, null, null, null);
    }

    @Override
    public void createDevice(final CreateDeviceRepositoryModel model)
            throws DevicesRepositoryAlreadyExistsException {
        final String newDeviceUuid = UUID.randomUUID().toString();

        // TODO: Also send hardware id to the server
        // model.getDeviceHardwareId();

        mDevicesData.addDevice(
                new FullDeviceModel(
                        newDeviceUuid,
                        model.getName(),
                        new DetailedStatusModel(Status.OK, Status.OK)
                )
        );

        if (model.getScheduleId() != null) {
            mScheduleToDevicesDataLink.addDataLink(model.getScheduleId(), newDeviceUuid);
        }
    }
}
