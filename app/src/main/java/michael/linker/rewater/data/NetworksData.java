package michael.linker.rewater.data;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import michael.linker.rewater.constant.Status;
import michael.linker.rewater.model.network.NetworkItemModel;
import michael.linker.rewater.model.network.NetworksModel;
import michael.linker.rewater.model.status.DetailedStatusModel;

public class NetworksData {
    private final List<NetworkItemModel> mNetworkItemModels;

    public NetworksData() {
        mNetworkItemModels = new LinkedList<>();
        this.addNetwork(new NetworkItemModel(null, "First network",
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit. In at dignissim ligula."
                        + " Duis ornare interdum lacus ullamcorper porta. Duis elementum.",
                new DetailedStatusModel(Status.OK, Status.OK)));
        this.addNetwork(new NetworkItemModel(null, "Second network",
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vivamus dictum.",
                new DetailedStatusModel(Status.OK, Status.WARNING)));
        this.addNetwork(new NetworkItemModel(null, "Third network",
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Duis tincidunt placerat"
                        + " dui nec euismod. Integer.",
                new DetailedStatusModel(Status.WARNING, Status.OK)));
        this.addNetwork(new NetworkItemModel(null, "Fourth network",
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Duis ut vulputate felis"
                        + ". Praesent et luctus.",
                new DetailedStatusModel(Status.WARNING, Status.DEFECT)));
        this.addNetwork(new NetworkItemModel(null, "Fifth network",
                null,
                new DetailedStatusModel(Status.DEFECT, Status.OK)));
    }

    public NetworksModel getNetworks() {
        return new NetworksModel(mNetworkItemModels);
    }

    public void addNetwork(final NetworkItemModel model) {
        mNetworkItemModels.add(
                new NetworkItemModel(UUID.randomUUID().toString(), model.getHeading(),
                        model.getDescription(),
                        model.getStatus()));
    }
}
