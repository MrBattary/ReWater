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
import michael.linker.rewater.data.repository.networks.model.CompactNetworkModel;
import michael.linker.rewater.data.repository.networks.model.EditableNetworkModel;

public class NetworksViewModel extends ViewModel {
    private final INetworksRepository mNetworksRepository;
    private final MutableLiveData<List<CompactNetworkModel>> mCompactNetworkModels;
    private final MutableLiveData<List<String>> mAlreadyTakenNetworkNames;
    private final MutableLiveData<String> mEditableNetworkId;
    private final MutableLiveData<CompactNetworkModel> mEditableNetworkModel;

    public NetworksViewModel() {
        mNetworksRepository = RepositoryConfiguration.getNetworksRepository();
        mCompactNetworkModels = new MutableLiveData<>();
        mAlreadyTakenNetworkNames = new MutableLiveData<>();
        mEditableNetworkId = new MutableLiveData<>();
        mEditableNetworkModel = new MutableLiveData<>();
        this.updateNetworkList();
    }

    public LiveData<List<String>> getAlreadyTakenNetworkNames() {
        return mAlreadyTakenNetworkNames;
    }

    public LiveData<List<CompactNetworkModel>> getCompactNetworkModels() {
        return mCompactNetworkModels;
    }

    public LiveData<CompactNetworkModel> getEditableNetworkModel() {
        return mEditableNetworkModel;
    }

    public LiveData<String> getEditableNetworkId() {
        return mEditableNetworkId;
    }

    public void setEditableNetworkId(final String id) throws NetworksViewModelFailedException {
        try {
            final CompactNetworkModel model = mNetworksRepository.getCompactNetworkById(id);
            mEditableNetworkModel.setValue(model);
            mEditableNetworkId.setValue(id);
        } catch (NetworksRepositoryNotFoundException e) {
            throw new NetworksViewModelFailedException(e.getMessage());
        }
    }

    public void updateNetwork(final String id, final EditableNetworkModel model)
            throws NetworksViewModelFailedException {
        try {
            mNetworksRepository.updateNetwork(id, model);
            this.updateNetworkList();
        } catch (NetworksRepositoryNotFoundException e) {
            throw new NetworksViewModelFailedException(e.getMessage());
        }
    }

    public void removeNetwork(final String id) {
        mNetworksRepository.removeNetwork(id);
        this.updateNetworkList();
    }

    public void addNetwork(final EditableNetworkModel model) {
        try {
            mNetworksRepository.addNetwork(model);
            this.updateNetworkList();
        } catch (NetworksRepositoryAlreadyExistsException e) {
            throw new NetworksViewModelFailedException(e.getMessage());
        }
    }

    private void updateNetworkList() {
        mCompactNetworkModels.setValue(mNetworksRepository.getCompactNetworkList());
        mAlreadyTakenNetworkNames.setValue(mNetworksRepository.getCompactNetworkList().stream()
                .map(CompactNetworkModel::getHeading)
                .collect(Collectors.toList()));
    }
}