package michael.linker.rewater.data.network;

import michael.linker.rewater.model.local.network.NetworkModel;
import michael.linker.rewater.model.local.network.NetworksModel;

public interface INetworksData {
    /**
     * Get all networks.
     *
     * @return model with list on networks in it
     */
    NetworksModel getNetworks();

    /**
     * Add a new network.
     * Attention! ID of the new network will be overwritten.
     *
     * @param model new network model, ID can be null.
     */
    void addNetwork(NetworkModel model);
}
