package michael.linker.rewater.model.network;

import java.util.List;

public class NetworksModel {
    private final List<NetworkItemModel> mNetworkModelList;

    public NetworksModel(List<NetworkItemModel> networkModelList) {
        mNetworkModelList = networkModelList;
    }

    public List<NetworkItemModel> getNetworkModelList() {
        return mNetworkModelList;
    }
}
