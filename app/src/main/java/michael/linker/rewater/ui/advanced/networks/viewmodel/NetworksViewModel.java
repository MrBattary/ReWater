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
import michael.linker.rewater.data.repository.networks.model.CreateOrUpdateNetworkRepositoryModel;
import michael.linker.rewater.data.repository.networks.model.NetworkRepositoryModel;
import michael.linker.rewater.ui.advanced.networks.model.NetworkUiModel;

public class NetworksViewModel extends ViewModel {
    private final INetworksRepository mNetworksRepository;
    private final MutableLiveData<List<NetworkUiModel>> mCompactNetworkModels;
    private final MutableLiveData<List<String>> mAlreadyTakenNetworkNames;
    private final MutableLiveData<String> mEditableNetworkId;
    private final MutableLiveData<NetworkUiModel> mEditableNetworkModel;

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

    public LiveData<List<NetworkUiModel>> getCompactNetworkModels() {
        return mCompactNetworkModels;
    }

    public LiveData<NetworkUiModel> getEditableNetworkModel() {
        return mEditableNetworkModel;
    }

    public LiveData<String> getEditableNetworkId() {
        return mEditableNetworkId;
    }

    public void setEditableNetworkId(final String id) throws NetworksViewModelFailedException {
        try {
            final NetworkRepositoryModel model = mNetworksRepository.getNetworkById(id);
            mEditableNetworkModel.setValue(new NetworkUiModel(model));
            mEditableNetworkId.setValue(id);
        } catch (NetworksRepositoryNotFoundException e) {
            throw new NetworksViewModelFailedException(e.getMessage());
        }
    }

    public void updateNetwork(final String id, final CreateOrUpdateNetworkRepositoryModel model)
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

    public void addNetwork(final CreateOrUpdateNetworkRepositoryModel model) {
        try {
            mNetworksRepository.addNetwork(model);
            this.updateListsFromRepositories();
        } catch (NetworksRepositoryAlreadyExistsException e) {
            throw new NetworksViewModelFailedException(e.getMessage());
        }
    }

    public void updateListsFromRepositories() {
        mNetworksRepository.updateNetworkList();
        final List<NetworkRepositoryModel> networkRepositoryModelList =
                mNetworksRepository.getNetworkList();
        mCompactNetworkModels.setValue(
                networkRepositoryModelList.stream()
                        .map(NetworkUiModel::new)
                        .collect(Collectors.toList()));
        mAlreadyTakenNetworkNames.setValue(
                networkRepositoryModelList.stream()
                        .map(NetworkRepositoryModel::getName)
                        .collect(Collectors.toList()));
    }
}