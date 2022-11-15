package michael.linker.rewater.data.repository.networks;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import michael.linker.rewater.config.DataConfiguration;
import michael.linker.rewater.data.repository.networks.model.NetworkModel;
import michael.linker.rewater.data.repository.networks.model.NewNetworkModel;
import michael.linker.rewater.data.repository.networks.model.UpdateNetworkModel;
import michael.linker.rewater.data.web.INetworksData;
import michael.linker.rewater.data.web.links.IOneToManyDataLink;
import michael.linker.rewater.data.web.model.FullNetworkModel;

public class NetworksRepository implements INetworksRepository {
    private final INetworksData mNetworksData;
    private final IOneToManyDataLink mNetworkToSchedulesDataLink;
    private final IOneToManyDataLink mScheduleToDevicesDataLink;

    public NetworksRepository() {
        mNetworksData = DataConfiguration.getNetworksData();
        mNetworkToSchedulesDataLink = DataConfiguration.getNetworkToSchedulesDataLink();
        mScheduleToDevicesDataLink = DataConfiguration.getScheduleToDevicesDataLink();
    }

    @Override
    public List<NetworkModel> getNetworkList() {
        final List<FullNetworkModel> dataModelList =
                mNetworksData.getNetworkList().getNetworkModelList();
        final List<NetworkModel> modelList = new ArrayList<>();
        for (FullNetworkModel fullModel : dataModelList) {
            modelList.add(new NetworkModel(
                    fullModel.getId(),
                    fullModel.getName(),
                    fullModel.getDescription(),
                    fullModel.getStatus()
            ));
        }
        return modelList;
    }

    @Override
    public NetworkModel getNetworkById(final String id)
            throws NetworksRepositoryNotFoundException {
        final FullNetworkModel networkModel = mNetworksData.getNetworkById(id);
        if (networkModel == null) {
            throw new NetworksRepositoryNotFoundException(
                    "Requested network with id: " + id + " was not found!");
        }
        return new NetworkModel(
                networkModel.getId(),
                networkModel.getName(),
                networkModel.getDescription(),
                networkModel.getStatus()
        );
    }

    @Override
    public String getNetworkIdByIdOfAttachedSchedule(String scheduleId)
            throws NetworksRepositoryNotFoundException {
        final String networkId = mNetworkToSchedulesDataLink.getLeftEntityIdByRightEntityId(
                scheduleId);
        if (networkId == null) {
            throw new NetworksRepositoryNotFoundException(
                    "No network contains a schedule with the ID: " + scheduleId);
        }
        return networkId;
    }

    @Override
    public boolean isNetworkExists(final String id) {
        return mNetworksData.getNetworkById(id) != null;
    }

    @Override
    public void addNetwork(final NewNetworkModel model)
            throws NetworksRepositoryAlreadyExistsException {
        final List<String> alreadyUsedNames = mNetworksData.getNetworkList().getNetworkModelList()
                .stream()
                .map(FullNetworkModel::getName)
                .collect(Collectors.toList());
        if (alreadyUsedNames.contains(model.getHeading())) {
            throw new NetworksRepositoryAlreadyExistsException(
                    "Network with heading: " + model.getHeading() + " already exists!");
        }
        mNetworksData.addNetwork(new michael.linker.rewater.data.web.model.EditableNetworkModel(
                model.getHeading(), model.getDescription()
        ));
    }

    @Override
    public void updateNetwork(final String id, final UpdateNetworkModel model)
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
                        model.getDescription(),
                        dataNetworkModel.getStatus()));
    }

    @Override
    public void removeNetwork(final String id) {
        // Cascade unlink
        final List<String> childScheduleIdList =
                mNetworkToSchedulesDataLink.getRightEntityIdListByLeftEntityId(id);
        for (String childScheduleId : childScheduleIdList) {
            mScheduleToDevicesDataLink.removeAllRightEntityIdsByLeftEntityId(childScheduleId);
        }
        mNetworkToSchedulesDataLink.removeAllRightEntityIdsByLeftEntityId(id);
        // Remove
        mNetworksData.removeNetwork(id);
    }
}
