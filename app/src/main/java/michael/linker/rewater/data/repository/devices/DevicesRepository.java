package michael.linker.rewater.data.repository.devices;

import java.util.ArrayList;
import java.util.List;

import michael.linker.rewater.config.DataConfiguration;
import michael.linker.rewater.data.model.IdNameModel;
import michael.linker.rewater.data.repository.devices.model.CompactDeviceModel;
import michael.linker.rewater.data.web.IDevicesData;
import michael.linker.rewater.data.web.INetworksData;
import michael.linker.rewater.data.web.ISchedulesData;
import michael.linker.rewater.data.web.model.FullDeviceModel;
import michael.linker.rewater.data.web.model.FullNetworkModel;
import michael.linker.rewater.data.web.model.FullScheduleModel;

public class DevicesRepository implements IDevicesRepository {
    private final INetworksData mNetworksData;
    private final ISchedulesData mSchedulesData;
    private final IDevicesData mDevicesData;

    public DevicesRepository() {
        mNetworksData = DataConfiguration.getNetworksData();
        mSchedulesData = DataConfiguration.getSchedulesData();
        mDevicesData = DataConfiguration.getDevicesData();
    }

    @Override
    public List<CompactDeviceModel> getCompactDeviceList() {
        final List<FullDeviceModel> dataDeviceModelList =
                mDevicesData.getDevicesList().getFullDeviceModels();
        final List<CompactDeviceModel> compactDeviceModels = new ArrayList<>();
        for (FullDeviceModel dataDeviceModel : dataDeviceModelList) {
            final IdNameModel parentSchedule = this.getParentScheduleIdNameModel(
                    dataDeviceModel.getParentScheduleId());
            final IdNameModel parentNetwork = this.getParentNetworkIdNameModel(
                    dataDeviceModel.getParentNetworkId());

            compactDeviceModels.add(new CompactDeviceModel(
                    dataDeviceModel.getId(),
                    dataDeviceModel.getName(),
                    parentSchedule,
                    parentNetwork,
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
                getParentScheduleIdNameModel(dataDeviceModel.getParentScheduleId()),
                getParentNetworkIdNameModel(dataDeviceModel.getParentNetworkId()),
                dataDeviceModel.getStatus()
        );
    }

    private IdNameModel getParentScheduleIdNameModel(final String parentScheduleId) {
        IdNameModel parentScheduleIdNameModel = null;
        final FullScheduleModel dataScheduleModel = mSchedulesData.getScheduleById(
                parentScheduleId);
        if (dataScheduleModel != null) {
            parentScheduleIdNameModel = new IdNameModel(dataScheduleModel.getId(),
                    dataScheduleModel.getName());
        }
        return parentScheduleIdNameModel;
    }

    private IdNameModel getParentNetworkIdNameModel(final String parentNetworkId) {
        IdNameModel parentNetworkIdNameModel = null;
        final FullNetworkModel dataNetworkModel = mNetworksData.getNetworkById(parentNetworkId);
        if (dataNetworkModel != null) {
            parentNetworkIdNameModel = new IdNameModel(dataNetworkModel.getId(),
                    dataNetworkModel.getName());
        }
        return parentNetworkIdNameModel;
    }
}
