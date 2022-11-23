package michael.linker.rewater.data.repository.networks;

import java.util.List;

import michael.linker.rewater.data.repository.networks.model.CreateOrUpdateNetworkRepositoryModel;
import michael.linker.rewater.data.repository.networks.model.NetworkRepositoryModel;

/**
 * The "Bridge" between data (web/local) sources and view models
 */
public interface INetworksRepository {
    /**
     * Get a simple list of the all networks.
     *
     * @return the list of compact network models in it
     */
    List<NetworkRepositoryModel> getNetworkList();

    /**
     * Get specific network by it's ID.
     *
     * @param id ID of the network
     * @return model of the required network
     * @throws NetworksRepositoryNotFoundException if network with provided id does not exist
     */
    NetworkRepositoryModel getNetworkById(String id) throws NetworksRepositoryNotFoundException;

    /**
     * DEPRECATED
     * Get network ID by inner schedule ID.
     *
     * @param scheduleId inner schedule ID
     * @return network ID
     * @throws NetworksRepositoryNotFoundException if no network contains a schedule with the
     * provided ID
     */
    String getNetworkIdByIdOfAttachedSchedule(String scheduleId)
            throws NetworksRepositoryNotFoundException;

    /**
     * Add a new network.
     *
     * @param model new network model, ID can be null
     * @throws NetworksRepositoryAlreadyExistsException if network with provided name already exists
     */
    void addNetwork(CreateOrUpdateNetworkRepositoryModel model) throws NetworksRepositoryAlreadyExistsException;

    /**
     * Update an existing network.
     *
     * @param id    ID of the network to be updated
     * @param model model with data for update
     * @throws NetworksRepositoryNotFoundException if network with provided id does not exist
     */
    void updateNetwork(String id, CreateOrUpdateNetworkRepositoryModel model)
            throws NetworksRepositoryNotFoundException;

    /**
     * Remove an existing network.
     *
     * @param id ID of the network to be removed
     */
    void removeNetwork(String id);
}
