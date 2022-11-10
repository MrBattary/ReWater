package michael.linker.rewater.data.repository.networks;

import java.util.List;

import michael.linker.rewater.data.repository.networks.model.CompactNetworkModel;
import michael.linker.rewater.data.repository.networks.model.EditableNetworkModel;

/**
 * The "Bridge" between data (web/local) sources and view models
 */
public interface INetworksRepository {
    /**
     * Get all networks simple list.
     *
     * @return model with list on networks in it
     */
    List<CompactNetworkModel> getCompactNetworkList();

    /**
     * Get specific network by it's id.
     *
     * @param id ID of the network
     * @return model of the required network
     * @throws NetworksRepositoryAlreadyExistsException if network with provided id does not exist
     */
    CompactNetworkModel getCompactNetworkById(String id) throws NetworksRepositoryNotFoundException;

    /**
     * Add a new network.
     *
     * @param model new network model, ID can be null
     * @throws NetworksRepositoryAlreadyExistsException if network with provided name already exists
     */
    void addNetwork(EditableNetworkModel model) throws NetworksRepositoryAlreadyExistsException;

    /**
     * Update an existing network.
     *
     * @param id    ID of the network to be updated
     * @param model model with data for update
     * @throws NetworksRepositoryAlreadyExistsException if network with provided id does not exist
     */
    void updateNetwork(String id, EditableNetworkModel model)
            throws NetworksRepositoryNotFoundException;

    /**
     * Remove an existing network.
     *
     * @param id ID of the network to be removed
     */
    void removeNetwork(String id);
}