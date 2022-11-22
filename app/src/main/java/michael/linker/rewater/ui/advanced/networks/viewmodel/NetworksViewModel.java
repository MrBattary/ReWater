package michael.linker.rewater.ui.advanced.networks.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;
import java.util.stream.Collectors;

import michael.linker.rewater.config.RepositoryConfiguration;
import michael.linker.rewater.data.repository.networks.INetworksRepository;
import michael.linker.rewater.data.repository.networks.NetworksRepositoryAlreadyExistsException;
import michael.linker.rewater.data.repository.networks.NetworksRepositoryNotFoundException;
import michael.linker.rewater.data.repository.networks.model.NetworkModel;
import michael.linker.rewater.data.repository.networks.model.NewNetworkModel;
import michael.linker.rewater.data.repository.networks.model.UpdateNetworkModel;

public class NetworksViewModel extends ViewModel {
    private final INetworksRepository mNetworksRepository;
    private final MutableLiveData<List<NetworkModel>> mCompactNetworkModels;
    private final MutableLiveData<List<String>> mAlreadyTakenNetworkNames;
    private final MutableLiveData<String> mEditableNetworkId;
    private final MutableLiveData<NetworkModel> mEditableNetworkModel;

    public NetworksViewModel() {
        mNetworksRepository = RepositoryConfiguration.getNetworksRepository();
        mCompactNetworkModels = new MutableLiveData<>();
        mAlreadyTakenNetworkNames = new MutableLiveData<>();
        mEditableNetworkId = new MutableLiveData<>();
        mEditableNetworkModel = new MutableLiveData<>();
        this.updateListsFromRepositories();
    }

    public LiveData<List<String>> getAlreadyTakenNetworkNames() {
        return mAlreadyTakenNetworkNames;
    }

    public LiveData<List<NetworkModel>> getCompactNetworkModels() {
        return mCompactNetworkModels;
    }

    public LiveData<NetworkModel> getEditableNetworkModel() {
        return mEditableNetworkModel;
    }

    public LiveData<String> getEditableNetworkId() {
        return mEditableNetworkId;
    }

    public void setEditableNetworkId(final String id) throws NetworksViewModelFailedException {
        try {
            final NetworkModel model = mNetworksRepository.getNetworkById(id);
            mEditableNetworkModel.setValue(model);
            mEditableNetworkId.setValue(id);
        } catch (NetworksRepositoryNotFoundException e) {
            throw new NetworksViewModelFailedException(e.getMessage());
        }
    }

    public void updateNetwork(final String id, final UpdateNetworkModel model)
            throws NetworksViewModelFailedException {
        try {
            mNetworksRepository.updateNetwork(id, model);
            this.updateListsFromRepositories();
        } catch (NetworksRepositoryNotFoundException e) {
            throw new NetworksViewModelFailedException(e.getMessage());
        }
    }

    public void removeNetwork(final String id) {
        mNetworksRepository.removeNetwork(id);
        this.updateListsFromRepositories();
    }

    public void addNetwork(final NewNetworkModel model) {
        try {
            mNetworksRepository.addNetwork(model);
            this.updateListsFromRepositories();
        } catch (NetworksRepositoryAlreadyExistsException e) {
            throw new NetworksViewModelFailedException(e.getMessage());
        }
    }

    public void updateListsFromRepositories() {
        mCompactNetworkModels.setValue(mNetworksRepository.getNetworkList());
        mAlreadyTakenNetworkNames.setValue(mNetworksRepository.getNetworkList().stream()
                .map(NetworkModel::getHeading)
                .collect(Collectors.toList()));
    }
}