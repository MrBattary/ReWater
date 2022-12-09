package michael.linker.rewater.data.repository.networks;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import michael.linker.rewater.data.model.status.Status;
import michael.linker.rewater.data.repository.networks.model.CreateOrUpdateNetworkRepositoryModel;
import michael.linker.rewater.data.repository.networks.model.NetworkRepositoryModel;
import michael.linker.rewater.data.web.api.networks.NetworksApi;
import michael.linker.rewater.data.web.api.networks.request.CreateOrUpdateNetworkRequest;
import michael.linker.rewater.data.web.gate.exceptions.group.ClientErrorException;
import michael.linker.rewater.data.web.gate.exceptions.status.BadRequestHttpException;
import michael.linker.rewater.data.web.gate.exceptions.status.NotFoundHttpException;

public class NetworksWebRepository implements INetworksRepository {
    private final NetworksApi mApi;

    private final MutableLiveData<Status> mOverallStatusOfTheAllNetworks;
    private List<NetworkRepositoryModel> mInternalNetworkList;

    public NetworksWebRepository() {
        mApi = new NetworksApi();

        mOverallStatusOfTheAllNetworks = new MutableLiveData<>();
        mInternalNetworkList = new ArrayList<>();
        this.updateNetworkList();
    }

    @Override
    public LiveData<Status> getNetworksOverallStatusLiveData() {
        return mOverallStatusOfTheAllNetworks;
    }

    @Override
    public void updateNetworkList() {
        mInternalNetworkList = mApi.getAllNetworks().stream()
                .map(NetworkRepositoryModel::new)
                .collect(Collectors.toList());

        final Status networksWaterStatus = Status.getWorstStatus(
                mInternalNetworkList.stream()
                        .map(network -> network.getStatus().getWater())
                        .collect(Collectors.toList()));
        final Status networksBatteryStatus = Status.getWorstStatus(
                mInternalNetworkList.stream()
                        .map(network -> network.getStatus().getBattery())
                        .collect(Collectors.toList()));
        mOverallStatusOfTheAllNetworks.postValue(
                Status.getWorstStatus(Arrays.asList(networksWaterStatus, networksBatteryStatus)));
    }

    @Override
    public List<NetworkRepositoryModel> getNetworkList() {
        return mInternalNetworkList;
    }

    @Override
    public NetworkRepositoryModel getNetworkById(String id)
            throws NetworksRepositoryNotFoundException {
        try {
            return new NetworkRepositoryModel(mApi.getNetworkById(id));
        } catch (NotFoundHttpException e) {
            throw new NetworksRepositoryNotFoundException(
                    "Requested network with id: " + id + " was not found!");
        }
    }

    @Override
    public void addNetwork(CreateOrUpdateNetworkRepositoryModel model)
            throws NetworksRepositoryAlreadyExistsException {
        try {
            mApi.createNetwork(new CreateOrUpdateNetworkRequest(model));
        } catch (BadRequestHttpException e) {
            throw new NetworksRepositoryAlreadyExistsException(
                    "Network with heading: " + model.getName() + " already exists!");
        }
    }

    @Override
    public void updateNetwork(String id, CreateOrUpdateNetworkRepositoryModel model)
            throws NetworksRepositoryNotFoundException {
        try {
            mApi.updateNetwork(id, new CreateOrUpdateNetworkRequest(model));
        } catch (NotFoundHttpException e) {
            throw new NetworksRepositoryNotFoundException(
                    "Requested network with id: " + id + " was not found and can't be updated!");
        }
    }

    @Override
    public void removeNetwork(String id) {
        try {
            mApi.deleteNetworkById(id);
        } catch (ClientErrorException ignored) {
        }
    }
}
