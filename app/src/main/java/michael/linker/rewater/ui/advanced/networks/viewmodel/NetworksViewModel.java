package michael.linker.rewater.ui.advanced.networks.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;
import java.util.stream.Collectors;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;
import michael.linker.rewater.config.RepositoryConfiguration;
import michael.linker.rewater.data.repository.networks.INetworksRepository;
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
        Single.fromCallable(() -> mNetworksRepository.getNetworkById(id))
                .doOnSuccess(model -> {
                    mEditableNetworkModel.postValue(new NetworkUiModel(model));
                    mEditableNetworkId.postValue(id);
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(m -> {
                }, e -> {
                    throw new NetworksViewModelFailedException(e.getMessage());
                });
    }

    public void updateNetwork(final String id, final CreateOrUpdateNetworkRepositoryModel model)
            throws NetworksViewModelFailedException {
        Single.fromCallable(() -> {
                    mNetworksRepository.updateNetwork(id, model);
                    return true;
                })
                .doOnSuccess(b -> this.updateListsFromRepositories())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(m -> {
                }, e -> {
                    throw new NetworksViewModelFailedException(e.getMessage());
                });
    }

    public void removeNetwork(final String id) {
        Single.fromCallable(() -> {
                    mNetworksRepository.removeNetwork(id);
                    return true;
                })
                .doOnSuccess(b -> this.updateListsFromRepositories())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe();
    }

    public void addNetwork(final CreateOrUpdateNetworkRepositoryModel model) {
        Single.fromCallable(() -> {
                    mNetworksRepository.addNetwork(model);
                    return true;
                })
                .doOnSuccess(b -> this.updateListsFromRepositories())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(m -> {
                }, e -> {
                    throw new NetworksViewModelFailedException(e.getMessage());
                });
    }

    public void updateListsFromRepositories() {
        Single.fromCallable(() -> {
                    mNetworksRepository.updateNetworkList();
                    return true;
                })
                .doOnSuccess(
                        b -> {
                            final List<NetworkRepositoryModel> networkRepositoryModelList =
                                    mNetworksRepository.getNetworkList();
                            mCompactNetworkModels.postValue(
                                    networkRepositoryModelList.stream()
                                            .map(NetworkUiModel::new)
                                            .collect(Collectors.toList()));
                            mAlreadyTakenNetworkNames.postValue(
                                    networkRepositoryModelList.stream()
                                            .map(NetworkRepositoryModel::getName)
                                            .collect(Collectors.toList()));
                        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe();
    }
}