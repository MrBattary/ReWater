package michael.linker.rewater.model.local.network;

import java.util.List;

public class NetworksModel {
    private final List<NetworkModel> mNetworkModelList;

    public NetworksModel(List<NetworkModel> networkModelList) {
        mNetworkModelList = networkModelList;
    }

    public List<NetworkModel> getNetworkModelList() {
        return mNetworkModelList;
    }
}
