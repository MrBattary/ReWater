package michael.linker.rewater.data.web;

import michael.linker.rewater.data.web.model.FullNetworkModel;
import michael.linker.rewater.data.web.model.NetworkListModel;
import michael.linker.rewater.data.web.model.EditableNetworkModel;

public interface INetworksData {
    /**
     * Get all networks.
     *
     * @return model with list on networks in it
     */
    NetworkListModel getNetworkList();

    /**
     * Get specific network by it's id.
     *
     * @param id ID of the network
     * @return model of the required network
     */
    FullNetworkModel getNetworkById(String id);

    /**
     * Add a new network.
     *
     * @param model new network model, ID can be null
     */
    void addNetwork(EditableNetworkModel model);

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
