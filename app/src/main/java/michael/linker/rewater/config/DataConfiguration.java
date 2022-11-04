package michael.linker.rewater.config;

import michael.linker.rewater.data.networks.NetworksLocalData;

public class DataConfiguration {
    private static NetworksLocalData sNetworksLocalData;

    public static NetworksLocalData getNetworksData() {
        if (sNetworksLocalData == null) {
            sNetworksLocalData = new NetworksLocalData();
        }
        return sNetworksLocalData;
    }
}
