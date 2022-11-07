package michael.linker.rewater.data.model;

import java.util.List;

public class NetworkListModel {
    private final List<FullNetworkModel> mFullNetworkModelList;

    public NetworkListModel(List<FullNetworkModel> fullNetworkModelList) {
        mFullNetworkModelList = fullNetworkModelList;
    }

    public List<FullNetworkModel> getNetworkModelList() {
        return mFullNetworkModelList;
    }
}
