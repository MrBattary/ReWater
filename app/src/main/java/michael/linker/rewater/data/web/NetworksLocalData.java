package michael.linker.rewater.data.web;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import michael.linker.rewater.data.model.EditableNetworkModel;
import michael.linker.rewater.data.model.Status;
import michael.linker.rewater.data.model.FullNetworkModel;
import michael.linker.rewater.data.model.NetworkListModel;
import michael.linker.rewater.ui.model.DetailedStatusModel;

public class NetworksLocalData implements INetworksData {
    private final List<FullNetworkModel> mFullNetworkModels;

    public NetworksLocalData() {
        mFullNetworkModels = new LinkedList<>();
        mFullNetworkModels.add(new FullNetworkModel(UUID.randomUUID().toString(),
                "First network",
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit. In at dignissim ligula."
                        + " Duis ornare interdum lacus ullamcorper porta. Duis elementum.",
                new DetailedStatusModel(Status.OK, Status.OK)));
        mFullNetworkModels.add(new FullNetworkModel(UUID.randomUUID().toString(),
                "Second network",
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vivamus dictum.",
                new DetailedStatusModel(Status.OK, Status.WARNING)));
        mFullNetworkModels.add(new FullNetworkModel(UUID.randomUUID().toString(),
                "Third network",
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Duis tincidunt placerat"
                        + " dui nec euismod. Integer.",
                new DetailedStatusModel(Status.WARNING, Status.OK)));
        mFullNetworkModels.add(new FullNetworkModel(UUID.randomUUID().toString(),
                "Fourth network",
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Duis ut vulputate felis"
                        + ". Praesent et luctus.",
                new DetailedStatusModel(Status.WARNING, Status.DEFECT)));
        mFullNetworkModels.add(new FullNetworkModel(UUID.randomUUID().toString(),
                "Fifth network",
                null,
                new DetailedStatusModel(Status.DEFECT, Status.OK)));
    }

    @Override
    public NetworkListModel getNetworkList() {
        return new NetworkListModel(mFullNetworkModels);
    }

    @Override
    public FullNetworkModel getNetwork(final String id) {
        for (FullNetworkModel networkModel : mFullNetworkModels) {
            if (id.equals(networkModel.getId())) {
                return networkModel;
            }
        }
        return null;
    }

    @Override
    public void addNetwork(final EditableNetworkModel model) {
        mFullNetworkModels.add(new FullNetworkModel(UUID.randomUUID().toString(), model));
    }

    @Override
    public void updateNetwork(final String id, final FullNetworkModel model) {
        for (int i = 0; i < mFullNetworkModels.size(); i++) {
            if (id.equals(mFullNetworkModels.get(i).getId())) {
                mFullNetworkModels.set(i, new FullNetworkModel(
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
        for (int i = 0; i < mFullNetworkModels.size(); i++) {
            if (id.equals(mFullNetworkModels.get(i).getId())) {
                mFullNetworkModels.remove(i);
                return;
            }
        }
    }
}
