package michael.linker.rewater.data.networks;

import michael.linker.rewater.model.network.NetworkItemModel;
import michael.linker.rewater.model.network.NetworksModel;

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
    void addNetwork(NetworkItemModel model);
}
