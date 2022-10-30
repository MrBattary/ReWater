package michael.linker.rewater.config;

import michael.linker.rewater.data.NetworksData;

public class DataConfiguration {
    private static NetworksData networksData;

    public NetworksData getNetworksData() {
        if (networksData == null) {
            networksData = new NetworksData();
        }
        return networksData;
    }
}
