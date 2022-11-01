package michael.linker.rewater.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import michael.linker.rewater.constant.Status;
import michael.linker.rewater.model.network.NetworkItemModel;
import michael.linker.rewater.model.network.NetworksModel;
import michael.linker.rewater.model.status.DetailedStatusModel;

public class NetworksData {
    private final Map<String, NetworkItemModel> mNetworksModelMap;

    public NetworksData() {
        mNetworksModelMap = new HashMap<>();
        String uuid = UUID.randomUUID().toString();
        mNetworksModelMap.put(uuid, new NetworkItemModel(uuid, "First network",
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit. In at dignissim ligula."
                        + " Duis ornare interdum lacus ullamcorper porta. Duis elementum.",
                new DetailedStatusModel(Status.OK, Status.OK)));
        uuid = UUID.randomUUID().toString();
        mNetworksModelMap.put(uuid, new NetworkItemModel(uuid, "Second network",
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vivamus dictum.",
                new DetailedStatusModel(Status.OK, Status.WARNING)));
        uuid = UUID.randomUUID().toString();
        mNetworksModelMap.put(uuid, new NetworkItemModel(uuid, "Third network",
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Duis tincidunt placerat"
                        + " dui nec euismod. Integer.",
                new DetailedStatusModel(Status.WARNING, Status.OK)));
        uuid = UUID.randomUUID().toString();
        mNetworksModelMap.put(uuid, new NetworkItemModel(uuid, "Fourth network",
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Duis tincidunt placerat"
                        + " dui nec euismod. Integer.",
                new DetailedStatusModel(Status.WARNING, Status.DEFECT)));
        uuid = UUID.randomUUID().toString();
        mNetworksModelMap.put(uuid, new NetworkItemModel(uuid, "Fifth network",
                "",
                new DetailedStatusModel(Status.DEFECT, Status.OK)));
    }

    public NetworksModel getNetworks() {
        return new NetworksModel(new ArrayList<>(mNetworksModelMap.values()));
    }

    public void addNetwork(final NetworkItemModel model) {
        String uuid = UUID.randomUUID().toString();
        mNetworksModelMap.put(uuid,
                new NetworkItemModel(uuid, model.getHeading(), model.getDescription(),
                        model.getStatus()));
    }
}
