package michael.linker.rewater.data.network;

import michael.linker.rewater.data.model.FullNetworkModel;
import michael.linker.rewater.data.model.NetworkListModel;
import michael.linker.rewater.data.model.NewNetworkModel;

public interface INetworksData {
    /**
     * Get all networks.
     *
     * @return model with list on networks in it
     */
    NetworkListModel getNetworkList();

    /**
     * Add a new network.
     * Attention! ID of the new network will be overwritten.
     *
     * @param model new network model, ID can be null.
     */
    void addNetwork(NewNetworkModel model);

    /**
     * Update an existing network.
     *
     * @param id ID of the network to be updated
     * @param model model with data for update
     */
    void updateNetwork(String id, FullNetworkModel model);

    /**
     * Remove an existing network.
     *
     * @param id ID of the network to be removed
     */
    void removeNetwork(String id);
}
