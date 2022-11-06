package michael.linker.rewater.data.network;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import michael.linker.rewater.constant.Status;
import michael.linker.rewater.model.local.network.NetworkModel;
import michael.linker.rewater.model.local.network.NetworksModel;
import michael.linker.rewater.model.local.status.DetailedStatusModel;

public class NetworksLocalData implements INetworksData {
    private final List<NetworkModel> mNetworkModels;

    public NetworksLocalData() {
        mNetworkModels = new LinkedList<>();
        this.addNetwork(new NetworkModel(null, "First network",
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit. In at dignissim ligula."
                        + " Duis ornare interdum lacus ullamcorper porta. Duis elementum.",
                new DetailedStatusModel(Status.OK, Status.OK)));
        this.addNetwork(new NetworkModel(null, "Second network",
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vivamus dictum.",
                new DetailedStatusModel(Status.OK, Status.WARNING)));
        this.addNetwork(new NetworkModel(null, "Third network",
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Duis tincidunt placerat"
                        + " dui nec euismod. Integer.",
                new DetailedStatusModel(Status.WARNING, Status.OK)));
        this.addNetwork(new NetworkModel(null, "Fourth network",
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Duis ut vulputate felis"
                        + ". Praesent et luctus.",
                new DetailedStatusModel(Status.WARNING, Status.DEFECT)));
        this.addNetwork(new NetworkModel(null, "Fifth network",
                null,
                new DetailedStatusModel(Status.DEFECT, Status.OK)));
    }

    @Override
    public NetworksModel getNetworks() {
        return new NetworksModel(mNetworkModels);
    }

    @Override
    public void addNetwork(final NetworkModel model) {
        mNetworkModels.add(
                new NetworkModel(UUID.randomUUID().toString(), model.getHeading(),
                        model.getDescription(),
                        model.getStatus()));
    }

    @Override
    public void updateNetwork(final String id, final NetworkModel model) {
        for (int i = 0; i < mNetworkModels.size(); i++) {
            if (id.equals(mNetworkModels.get(i).getId())) {
                mNetworkModels.set(i, new NetworkModel(
                        id,
                        model.getHeading(),
                        model.getDescription(),
                        model.getStatus()));
                return;
            }
        }
    }

    @Override
    public void removeNetwork(final String id) {
        for (int i = 0; i < mNetworkModels.size(); i++) {
            if (id.equals(mNetworkModels.get(i).getId())) {
                mNetworkModels.remove(i);
                return;
            }
        }
    }
}
