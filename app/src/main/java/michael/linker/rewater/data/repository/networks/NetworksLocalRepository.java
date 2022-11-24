package michael.linker.rewater.data.repository.networks;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import michael.linker.rewater.config.DataConfiguration;
import michael.linker.rewater.data.local.stub.IDevicesData;
import michael.linker.rewater.data.local.stub.INetworksData;
import michael.linker.rewater.data.local.stub.ISchedulesData;
import michael.linker.rewater.data.local.stub.links.IOneToManyDataLink;
import michael.linker.rewater.data.local.stub.model.FullNetworkModel;
import michael.linker.rewater.data.model.status.DetailedStatusModel;
import michael.linker.rewater.data.model.status.Status;
import michael.linker.rewater.data.repository.networks.model.CreateOrUpdateNetworkRepositoryModel;
import michael.linker.rewater.data.repository.networks.model.NetworkRepositoryModel;

public class NetworksLocalRepository implements INetworksRepository {
    private final INetworksData mNetworksData;
    private final ISchedulesData mSchedulesData;
    private final IDevicesData mDevicesData;

    private final IOneToManyDataLink mNetworkToSchedulesDataLink;
    private final IOneToManyDataLink mScheduleToDevicesDataLink;

    private final MutableLiveData<Status> mOverallStatusOfTheAllNetworks;
    private List<NetworkRepositoryModel> internalNetworkList;

    public NetworksLocalRepository() {
        mNetworksData = DataConfiguration.getNetworksData();
        mSchedulesData = DataConfiguration.getSchedulesData();
        mDevicesData = DataConfiguration.getDevicesData();

        mNetworkToSchedulesDataLink = DataConfiguration.getNetworkToSchedulesDataLink();
        mScheduleToDevicesDataLink = DataConfiguration.getScheduleToDevicesDataLink();

        mOverallStatusOfTheAllNetworks = new MutableLiveData<>();
        internalNetworkList = new ArrayList<>();
        this.updateNetworkList();
    }

    @Override
    public LiveData<Status> getNetworksOverallStatusLiveData() {
        return mOverallStatusOfTheAllNetworks;
    }

    @Override
    public void updateNetworkList() {
        final List<FullNetworkModel> dataModelList =
                mNetworksData.getNetworkList();
        final List<NetworkRepositoryModel> modelList = new ArrayList<>();
        for (FullNetworkModel networkModel : dataModelList) {
            modelList.add(this.getNetworkById(networkModel.getId()));
        }
        internalNetworkList = modelList;

        final Status networksWaterStatus = Status.getWorstStatus(
                modelList.stream()
                        .map(network -> network.getStatus().getWater())
                        .collect(Collectors.toList()));
        final Status networksBatteryStatus = Status.getWorstStatus(
                modelList.stream()
                        .map(network -> network.getStatus().getBattery())
                        .collect(Collectors.toList()));
        mOverallStatusOfTheAllNetworks.setValue(
                Status.getWorstStatus(Arrays.asList(networksWaterStatus, networksBatteryStatus)));
    }

    @Override
    public List<NetworkRepositoryModel> getNetworkList() {
        return internalNetworkList;
    }

    @Override
    public NetworkRepositoryModel getNetworkById(final String id)
            throws NetworksRepositoryNotFoundException {
        final FullNetworkModel networkModel = mNetworksData.getNetworkById(id);
        if (networkModel == null) {
            throw new NetworksRepositoryNotFoundException(
                    "Requested network with id: " + id + " was not found!");
        }

        List<String> scheduleInsideNetworkIdList =
                mNetworkToSchedulesDataLink.getRightEntityIdListByLeftEntityId(
                        networkModel.getId());
        List<DetailedStatusModel> scheduleInsideNetworkStatusList = new ArrayList<>();
        for (String scheduleInsideNetworkId : scheduleInsideNetworkIdList) {
            List<String> deviceInsideScheduleIdList =
                    mScheduleToDevicesDataLink.getRightEntityIdListByLeftEntityId(
                            scheduleInsideNetworkId);
            List<DetailedStatusModel> deviceInsideScheduleStatusList = new ArrayList<>();

            for (String deviceInsideScheduleId : deviceInsideScheduleIdList) {
                deviceInsideScheduleStatusList.add(
                        mDevicesData.getDeviceById(deviceInsideScheduleId).getStatus());
            }
            final Status devicesWaterStatus = Status.getWorstStatus(
                    deviceInsideScheduleStatusList.stream()
                            .map(DetailedStatusModel::getWater)
                            .collect(Collectors.toList()));
            final Status devicesBatteryStatus = Status.getWorstStatus(
                    deviceInsideScheduleStatusList.stream()
                            .map(DetailedStatusModel::getBattery)
                            .collect(Collectors.toList()));

            scheduleInsideNetworkStatusList.add(
                    new DetailedStatusModel(devicesWaterStatus, devicesBatteryStatus));
        }

        final Status schedulesWaterStatus = Status.getWorstStatus(
                scheduleInsideNetworkStatusList.stream()
                        .map(DetailedStatusModel::getWater)
                        .collect(Collectors.toList()));
        final Status schedulesBatteryStatus = Status.getWorstStatus(
                scheduleInsideNetworkStatusList.stream()
                        .map(DetailedStatusModel::getBattery)
                        .collect(Collectors.toList()));

        return new NetworkRepositoryModel(
                networkModel.getId(),
                networkModel.getName(),
                networkModel.getDescription(),
                new DetailedStatusModel(schedulesWaterStatus, schedulesBatteryStatus)
        );
    }

    @Override
    public void addNetwork(final CreateOrUpdateNetworkRepositoryModel model)
            throws NetworksRepositoryAlreadyExistsException {
        final List<String> alreadyUsedNames = mNetworksData.getNetworkList()
                .stream()
                .map(FullNetworkModel::getName)
                .collect(Collectors.toList());
        if (alreadyUsedNames.contains(model.getHeading())) {
            throw new NetworksRepositoryAlreadyExistsException(
                    "Network with heading: " + model.getHeading() + " already exists!");
        }
        mNetworksData.addNetwork(
                new FullNetworkModel(
                        null, model.getHeading(), model.getDescription()
                ));
    }

    @Override
    public void updateNetwork(final String id, final CreateOrUpdateNetworkRepositoryModel model)
            throws NetworksRepositoryNotFoundException {
        final FullNetworkModel dataNetworkModel = mNetworksData.getNetworkById(id);
        if (dataNetworkModel == null) {
            throw new NetworksRepositoryNotFoundException(
                    "Requested network with id: " + id + " was not found and can't be updated!");
        }
        mNetworksData.updateNetwork(id,
                new FullNetworkModel(
                        dataNetworkModel.getId(),
                        model.getHeading(),
                        model.getDescription()));
    }

    @Override
    public void removeNetwork(final String id) {
        // Cascade unlink and schedules remove
        final List<String> childScheduleIdList =
                mNetworkToSchedulesDataLink.getRightEntityIdListByLeftEntityId(id);
        for (String childScheduleId : childScheduleIdList) {
            mScheduleToDevicesDataLink.removeAllRightEntityIdsByLeftEntityId(childScheduleId);
            mSchedulesData.removeSchedule(childScheduleId);
        }
        mNetworkToSchedulesDataLink.removeAllRightEntityIdsByLeftEntityId(id);
        // Remove
        mNetworksData.removeNetwork(id);
    }
}
