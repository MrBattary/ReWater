package michael.linker.rewater.data.repository.networks;

import androidx.lifecycle.LiveData;

import java.util.List;

import michael.linker.rewater.data.model.status.Status;
import michael.linker.rewater.data.repository.networks.model.CreateOrUpdateNetworkRepositoryModel;
import michael.linker.rewater.data.repository.networks.model.NetworkRepositoryModel;

/**
 * The "Bridge" between data (web/local) sources and view models
 */
public interface INetworksRepository {
    /**
     * Returns current data containing the general status of all the user's networks.
     *
     * @return live data of the current status.
     */
    LiveData<Status> getNetworksOverallStatusLiveData();

    /**
     * Requires update of the all networks list from the data source to the internal memory.
     * Also updates the NetworksOverallStatusLiveData
     * @see INetworksRepository::getNetworksOverallStatusLiveData
     */
    void updateNetworkList();

    /**
     * Get a simple list of the all networks from the internal memory.
     *
     * @return the list of network models in it
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
     * Add a new network.
     *
     * @param model new network model, ID can be null
     * @throws NetworksRepositoryAlreadyExistsException if network with provided name already exists
     */
    void addNetwork(CreateOrUpdateNetworkRepositoryModel model)
            throws NetworksRepositoryAlreadyExistsException;

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
