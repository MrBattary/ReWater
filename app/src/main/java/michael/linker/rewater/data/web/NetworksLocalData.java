package michael.linker.rewater.data.web;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import michael.linker.rewater.data.model.Status;
import michael.linker.rewater.data.web.model.EditableNetworkModel;
import michael.linker.rewater.data.web.model.FullNetworkModel;
import michael.linker.rewater.data.web.model.NetworkListModel;
import michael.linker.rewater.ui.model.DetailedStatusModel;

public class NetworksLocalData implements INetworksData {
    private final List<FullNetworkModel> mFullNetworkModels;

    public NetworksLocalData() {
        mFullNetworkModels = new LinkedList<>();
        mFullNetworkModels.add(new FullNetworkModel(
                "9feefdbe-f35a-4ccb-93dc-0baf6955805b", "First network",
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit. In at dignissim ligula."
                        + " Duis ornare interdum lacus ullamcorper porta. Duis elementum.",
                new DetailedStatusModel(Status.OK, Status.OK)));
        mFullNetworkModels.add(new FullNetworkModel(
                "b70c023b-2016-4583-8d7c-daf4c9e7f979", "Second network",
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vivamus dictum.",
                new DetailedStatusModel(Status.OK, Status.WARNING)));
        mFullNetworkModels.add(new FullNetworkModel(
                "7f9dd006-59b8-4e86-bc31-01528652a601", "Third network",
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Duis tincidunt placerat"
                        + " dui nec euismod. Integer.",
                new DetailedStatusModel(Status.WARNING, Status.OK)));
        mFullNetworkModels.add(new FullNetworkModel(
                "ea84ab3c-0c54-436e-a710-e87d7592e490", "Fourth network",
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Duis ut vulputate felis"
                        + ". Praesent et luctus.",
                new DetailedStatusModel(Status.WARNING, Status.DEFECT)));
        mFullNetworkModels.add(new FullNetworkModel(
                "6d9b41d7-f009-4c55-94ea-02ab5c0baa7c", "Fifth network",
                null,
                new DetailedStatusModel(Status.DEFECT, Status.OK)));
        mFullNetworkModels.add(new FullNetworkModel(
                "ca23b691-2141-4395-b560-c79d6d71ff46",
                "Sixth network very very very very very long name",
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nullam dolor mauris, "
                        + "scelerisque at faucibus non, malesuada ut est. Fusce aliquam, leo nec "
                        + "vulputate ornare, neque sapien suscipit est, ut leo.",
                new DetailedStatusModel(Status.OK, Status.OK)));
    }

    @Override
    public NetworkListModel getNetworkList() {
        return new NetworkListModel(mFullNetworkModels);
    }

    @Override
    public FullNetworkModel getNetworkById(final String id) {
        for (FullNetworkModel networkModel : mFullNetworkModels) {
            if (networkModel.getId().equals(id)) {
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
                        model.getName(),
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
