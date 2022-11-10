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
            IdNameModel parentSchedule = null;
            final FullScheduleModel dataScheduleModel = mSchedulesData.getScheduleById(
                    dataDeviceModel.getParentScheduleId());
            if (dataScheduleModel != null) {
                parentSchedule = new IdNameModel(dataScheduleModel.getId(),
                        dataScheduleModel.getName());
            }

            IdNameModel parentNetwork = null;
            final FullNetworkModel dataNetworkModel = mNetworksData.getNetworkById(
                    dataDeviceModel.getParentNetworkId());
            if (dataNetworkModel != null) {
                parentNetwork = new IdNameModel(dataNetworkModel.getId(),
                        dataNetworkModel.getName());
            }

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
}
