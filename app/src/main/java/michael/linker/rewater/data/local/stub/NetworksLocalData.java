package michael.linker.rewater.data.local.stub;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import michael.linker.rewater.data.local.stub.model.FullNetworkModel;

public class NetworksLocalData implements INetworksData {
    private final List<FullNetworkModel> mFullNetworkModels;

    public NetworksLocalData() {
        mFullNetworkModels = new LinkedList<>();
        mFullNetworkModels.add(new FullNetworkModel(
                "9feefdbe-f35a-4ccb-93dc-0baf6955805b", "Home network",
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit. In at dignissim ligula."
                        + " Duis ornare interdum lacus ullamcorper porta. Duis elementum."));
        mFullNetworkModels.add(new FullNetworkModel(
                "b70c023b-2016-4583-8d7c-daf4c9e7f979", "Summer cottage",
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vivamus dictum."));
        mFullNetworkModels.add(new FullNetworkModel(
                "7f9dd006-59b8-4e86-bc31-01528652a601", "My workplace",
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nullam dolor mauris, "
                        + "scelerisque at faucibus non, malesuada ut est. Fusce aliquam, leo nec "
                        + "vulputate ornare, neque sapien suscipit est, ut leo."));
        mFullNetworkModels.add(new FullNetworkModel(
                "ae84ab3c-0c54-436e-a710-e87d7592e490", "Emptiness network",
                ""));
    }

    @Override
    public List<FullNetworkModel> getNetworkList() {
        return new ArrayList<>(mFullNetworkModels);
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
    public void addNetwork(final FullNetworkModel model) {
        mFullNetworkModels.add(
                new FullNetworkModel(
                        UUID.randomUUID().toString(),
                        model.getName(),
                        model.getDescription()));
    }

    @Override
    public void updateNetwork(final String id, final FullNetworkModel model) {
        for (int i = 0; i < mFullNetworkModels.size(); i++) {
            if (id.equals(mFullNetworkModels.get(i).getId())) {
                mFullNetworkModels.set(i, new FullNetworkModel(
                        id,
                        model.getName(),
                        model.getDescription()));
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
